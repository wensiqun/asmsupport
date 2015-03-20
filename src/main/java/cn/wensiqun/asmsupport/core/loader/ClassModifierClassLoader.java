/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.core.loader;

import java.io.InputStream;

import cn.wensiqun.asmsupport.core.asm.adapter.ClassModifierClassAdapter;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassModifier;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassReader;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassWriter;

public class ClassModifierClassLoader extends ClassLoader {
	
	private ClassModifier modifier;
	private String className;
	private byte[] modifiedClassBytes;
	
	public ClassModifierClassLoader(ClassModifier modifier){
		this.modifier = modifier;
		className = modifier.getCurrentClass().getName();
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();

        if (!name.equals(className)) {
			return loader.loadClass(name);
        }
		
		// gets an input stream to read the bytecode of the class
		String resource = name.replace('.', '/') + ".class";
		InputStream is = loader.getResourceAsStream(resource);
		
		// adapts the class on the fly
		try {
			//modify class
			ClassReader cr = new ClassReader(is);
			ClassWriter cw = new ClassWriter(0);
			ClassVisitor cv = new ClassModifierClassAdapter(cw, modifier);
			modifier.setClassWriter(cw);
			cr.accept(cv, 0);
			modifiedClassBytes = cw.toByteArray();
			
		} catch (Exception e) {
			throw new ASMSupportException(e.getMessage(), e);
		}
        return loader.loadClass(name);
	}

	public byte[] getModifiedClassBytes() {
		return modifiedClassBytes;
	}
	
}
