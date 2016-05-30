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
package cn.wensiqun.asmsupport.core.build.resolver;

import cn.wensiqun.asmsupport.core.block.method.clinit.KernelStaticBlockBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.block.method.init.KernelConstructorBody;
import cn.wensiqun.asmsupport.core.build.BytecodeResolver;
import cn.wensiqun.asmsupport.core.build.FieldBuilder;
import cn.wensiqun.asmsupport.core.build.MethodBuilder;
import cn.wensiqun.asmsupport.core.build.impl.DefaultMethodBuilder;
import cn.wensiqun.asmsupport.core.build.impl.FieldBuildImpl;
import cn.wensiqun.asmsupport.core.utils.CommonUtils;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassWriter;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.ASConstants;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractBytecodeResolver implements BytecodeResolver {
	
    private static final Log LOG = LogFactory.getLog(AbstractBytecodeResolver.class);

    protected List<MethodBuilder> methods = new ArrayList<>();

    protected List<FieldBuilder> fields = new ArrayList<>();

    protected String classOutPutPath;
	
    protected ClassWriter cw;

    protected ASMSupportClassLoader classLoader;

    private int clinitNum;
    
    /**
     * 
     * @param classLoader
     */
	public AbstractBytecodeResolver(ASMSupportClassLoader classLoader) {
		this.classLoader = classLoader;
	}
    
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

	@Override
	public void setClassOutputPath(String classOutPutPath) {
		this.classOutPutPath = classOutPutPath;
	}
	
	@Override
	public ASMSupportClassLoader getClassLoader() {
		return classLoader;
	}

	@Override
	public byte[] toBytes() {
		create();
		prepare();
		return execute();
	}

	@Override
    public Class<?> resolve() {
		byte[] code = toBytes();
		IClass currentClass = getCurrentClass();
        if(!StringUtils.isBlank(classOutPutPath)){
        	CommonUtils.toLocal(code, classOutPutPath, currentClass.getName());
        }
        if(LOG.isPrintEnabled()){
        	LOG.print("End create class : " + currentClass.getName().replace('.', '/'));
        }
        return loadClass(currentClass.getName(), code);
    }

    /**
     * Create a field with special value
     *
     * @param name
     * @param modifiers
     * @param type
     * @param val The initial value, this value is only support static field,
     *              otherwise will ignored.This parameter, which may be null
     *              if the field does not have an initial value,
     *              must be an Integer, a Float, a Long, a Double or a
     *              String (for int, float, long or String fields respectively).
     *              This parameter is only used for static fields. Its value is
     *              ignored for non static fields, which must be initialized
     *              through bytecode instructions in constructors or methods.
     * @return
     */
    protected final FieldBuilder createFieldInternal(String name, int modifiers, IClass type, Object val) {
        FieldBuilder fc = new FieldBuildImpl(name, modifiers, type, val);
        fields.add(fc);
        return fc;
    }

    /**
     * Create static block.
     *
     * @param block
     * @return
     */
    protected MethodBuilder createStaticBlockInternal(KernelStaticBlockBody block) {
        DefaultMethodBuilder creator;
        if(clinitNum > 0) {
            creator = DefaultMethodBuilder.buildForDelegate((DefaultMethodBuilder) methods.get(0), block);
        } else {
            creator = DefaultMethodBuilder.buildForNew(ASConstants.CLINIT, null, null, null, null,
                    Opcodes.ACC_STATIC, block);
        }
        methods.add(clinitNum++, creator);
        return creator;
    }

    /**
     *
     * Create a constructor.
     *
     * @param access
     * @param argTypes
     * @param argNames
     * @param body
     * @return
     */
    protected final MethodBuilder createConstructorInternal(int access, IClass[] argTypes, String[] argNames,
                                                            IClass[] exceptions, KernelConstructorBody body) {
        MethodBuilder creator = DefaultMethodBuilder.buildForNew(ASConstants.INIT, argTypes, argNames,
                null, exceptions, access, body);
        methods.add(creator);
        return creator;
    }

    /**
     * Create a method
     *
     * @param name
     * @param argClasses
     * @param argNames
     * @param returnClass
     * @param exceptions
     * @param access
     * @param mb
     * @return
     */
    protected final MethodBuilder createMethodInternal(
            String name, IClass[] argClasses, String[] argNames, IClass returnClass,
            IClass[] exceptions, int access, KernelMethodBody mb) {
        DefaultMethodBuilder methodBuilder = DefaultMethodBuilder.buildForNew(name, argClasses, argNames, returnClass, exceptions,
                access, mb);
        methods.add(methodBuilder);
        return methodBuilder;
    }

}