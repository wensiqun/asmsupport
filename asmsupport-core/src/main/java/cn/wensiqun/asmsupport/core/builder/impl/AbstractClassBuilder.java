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

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.builder.IClassBuilder;
import cn.wensiqun.asmsupport.core.builder.IFieldBuilder;
import cn.wensiqun.asmsupport.core.builder.IMethodBuilder;
import cn.wensiqun.asmsupport.core.utils.CommonUtils;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassWriter;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;


public abstract class AbstractClassBuilder implements IClassBuilder{
	
    private static final Log LOG = LogFactory.getLog(AbstractClassBuilder.class);

    protected List<IMethodBuilder> methodCreaters = new ArrayList<IMethodBuilder>();

    protected List<IFieldBuilder> fieldCreators = new ArrayList<IFieldBuilder>();

    protected String classOutPutPath;
	
    protected boolean existedStaticBlock = false;
	
    protected ClassWriter cw;

    protected ASMSupportClassLoader ASMSupportClassLoader;
    
    /**
     * 
     * @param ASMSupportClassLoader
     */
	public AbstractClassBuilder(ASMSupportClassLoader ASMSupportClassLoader) {
		this.ASMSupportClassLoader = ASMSupportClassLoader;
	}

	protected void checkStaticBlock(){
    	if(existedStaticBlock){
    		throw new UnsupportedOperationException("the static block has alreay exist this method!");
    	}
    }
    
    protected Class<?> loadClass(String name, byte[] b) {
        try {
        	return ASMSupportClassLoader.defineClass(name, b, getCurrentClass());
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
		return ASMSupportClassLoader;
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