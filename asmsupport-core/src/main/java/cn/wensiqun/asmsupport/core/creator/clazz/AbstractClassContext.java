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
package cn.wensiqun.asmsupport.core.creator.clazz;

import java.util.List;

import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.IClassContext;
import cn.wensiqun.asmsupport.core.creator.IFieldCreator;
import cn.wensiqun.asmsupport.core.creator.IMethodCreator;
import cn.wensiqun.asmsupport.core.loader.ASMClassLoader;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassWriter;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;


public abstract class AbstractClassContext extends AClassFactory implements IClassContext{

    protected List<IMethodCreator> methodCreaters;

    protected List<IFieldCreator> fieldCreators;

    protected String classOutPutPath;
	
    protected boolean existedStaticBlock;
	
    protected ClassWriter cw;

    private   ClassLoader parentClassLoader;
    
	protected void checkStaticBlock(){
    	if(existedStaticBlock){
    		throw new UnsupportedOperationException("the static block has alreay exist this method!");
    	}
    }
    
    protected Class<?> loadClass(String name, byte[] b) {
        Class<?> clazz = null;
        try {
        	ASMClassLoader classLoader;
        	if(parentClassLoader != null){
        		classLoader = ASMClassLoader.getInstance(parentClassLoader);
        	}else{
        		classLoader = ASMClassLoader.getInstance();
        	}
        	clazz = classLoader.defineClass(name, b);
        } catch (Exception e) {
            throw new ASMSupportException("Error on define class " + name, e);
        }
        return clazz;
    }

    @Override
    public final ClassVisitor getClassVisitor() {
        return cw;
    }

	public String getClassOutPutPath() {
		return classOutPutPath;
	}
	
    public final void setClassOutPutPath(String classOutPutPath) {
        this.classOutPutPath = classOutPutPath;
    }

	public ClassLoader getParentClassLoader() {
		return parentClassLoader;
	}

	public void setParentClassLoader(ClassLoader parentClassLoader) {
		this.parentClassLoader = parentClassLoader;
	}

}