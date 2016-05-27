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
package cn.wensiqun.asmsupport.core.builder.impl;

import cn.wensiqun.asmsupport.core.builder.ClassBuilder;
import cn.wensiqun.asmsupport.core.builder.FieldBuilder;
import cn.wensiqun.asmsupport.core.builder.MethodBuilder;
import cn.wensiqun.asmsupport.core.utils.CommonUtils;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassWriter;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractClassBuilder implements ClassBuilder {
	
    private static final Log LOG = LogFactory.getLog(AbstractClassBuilder.class);

    protected List<MethodBuilder> methodBuilders = new ArrayList<>();

    protected List<FieldBuilder> fieldBuilders = new ArrayList<>();

    protected String classOutPutPath;
	
    protected ClassWriter cw;

    protected ASMSupportClassLoader classLoader;
    
    /**
     * 
     * @param ASMSupportClassLoader
     */
	public AbstractClassBuilder(ASMSupportClassLoader ASMSupportClassLoader) {
		this.classLoader = ASMSupportClassLoader;
	}

	//protected abstract boolean existCLInit();
    
    protected Class<?> loadClass(String name, byte[] b) {
        try {
        	return classLoader.defineClass(name, b, getCurrentClass());
        } catch (Exception e) {
            throw new ASMSupportException("Error on define class " + name, e);
        }
    }

    @Override
    public final ClassVisitor getClassVisitor() {
        return cw;
    }

	public String getClassOutPutPath() {
		return classOutPutPath;
	}

	@Override
	public void setClassOutPutPath(String classOutPutPath) {
		this.classOutPutPath = classOutPutPath;
	}
	
	@Override
	public ASMSupportClassLoader getClassLoader() {
		return classLoader;
	}
	
	
	@Override
	public byte[] toClassBytes() {
		create();
		prepare();
		return execute();
	}

	@Override
    public Class<?> startup() {
		byte[] code = toClassBytes();
		IClass currentClass = getCurrentClass();
        if(!StringUtils.isBlank(classOutPutPath)){
        	CommonUtils.toLocal(code, classOutPutPath, currentClass.getName());
        }
        if(LOG.isPrintEnabled()){
        	LOG.print("End create class : " + currentClass.getName().replace('.', '/'));
        }
        return loadClass(currentClass.getName(), code);
    }
	

}