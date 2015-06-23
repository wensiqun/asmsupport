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


import cn.wensiqun.asmsupport.core.block.method.clinit.KernelStaticBlockBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.block.method.init.KernelConstructorBody;
import cn.wensiqun.asmsupport.core.builder.IFieldBuilder;
import cn.wensiqun.asmsupport.core.builder.IMethodBuilder;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.AsmsupportClassLoader;
import cn.wensiqun.asmsupport.utils.AsmsupportConstant;


/**
 * 
 * 
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class ClassBuilderImpl extends AbstractClassCreator {

	
	public ClassBuilderImpl(int version, int access, String name,
			IClass superCls, Class<?>[] interfaces) {
        super(version, access, name, superCls, interfaces, CachedThreadLocalClassLoader.getInstance());
    }
	
	
    public ClassBuilderImpl(int version, int access, String name,
    		IClass superCls, Class<?>[] interfaces, AsmsupportClassLoader classLoader) {
        super(version, access, name, superCls, interfaces, classLoader);
    }
    

    /**
     * 
     * Create a field with null value.
     * 
     * @param name            the field name
     * @param modifiers       the field modifiers
     * @param type      the field type
     * @return
     */
    public IFieldBuilder createField(String name, int modifiers, IClass type) {
        return createField(name, modifiers, type, null);
    }
    
    /**
     * Create a field with special value
     * 
     * @param name
     * @param modifiers
     * @param type
     * @param value The initial value, this value is only support static field, 
     *              otherwise will ignored.This parameter, which may be null 
     *              if the field does not have an initial value, 
     *              must be an Integer, a Float, a Long, a Double or a 
     *              String (for int, float, long or String fields respectively). 
     *              This parameter is only used for static fields. Its value is 
     *              ignored for non static fields, which must be initialized 
     *              through bytecode instructions in constructors or methods. 
     * @return
     */
    public IFieldBuilder createField(String name, int modifiers, IClass type, Object val) {
        IFieldBuilder fc = new FieldBuildImpl(name, modifiers, type, val);
        fieldCreators.add(fc);
        return fc;
    }

    /**
     * 
     * create constructor.
     * 
     * @param access
     * @param argTypes
     * @param argNames
     * @param body
     * @return
     */
	public IMethodBuilder createConstructor(int access, IClass[] argTypes, String[] argNames, IClass[] exceptions, KernelConstructorBody body) {
		IMethodBuilder creator = MethodBuilderImpl.methodCreatorForAdd(AsmsupportConstant.INIT, argTypes, argNames,
                null, exceptions, access, body);
        methodCreaters.add(creator);
        haveInitMethod = true;
        return creator;
	}

	/**
	 * Crate method for dummy call, remove static check that different to {@link #createMethod} method
	 * 
	 * @param access
	 * @param name
	 * @param argTypes
	 * @param argNames
	 * @param returnClass
	 * @param exceptions
	 * @param body
	 * @return
	 */
	public IMethodBuilder createMethodForDummy(int access, String name, IClass[] argTypes, String[] argNames,
			IClass returnClass, IClass[] exceptions, KernelMethodBody body) {
        IMethodBuilder creator = MethodBuilderImpl.methodCreatorForAdd(name, argTypes, argNames,
                returnClass, exceptions, access, body);
        methodCreaters.add(creator);
        return creator;
	}

	/**
     * Create a method 
     * 
     * @param access          the method modifiers
     * @param name            the method name
     * @param argClasses      the method argument type list
     * @param argNames        the method arguments name list
     * @param returnClass     the method return class
     * @param exceptions      throw exceptions for this method
     * @param body            method body that is method logic implementation
     * @return
     */
	public IMethodBuilder createMethod(int access, String name, IClass[] argTypes, String[] argNames,
			IClass returnClass, IClass[] exceptions, KernelMethodBody body) {
		if((access & Opcodes.ACC_STATIC) != 0){
    		access -= Opcodes.ACC_STATIC;
    	}
		IMethodBuilder creator = MethodBuilderImpl.methodCreatorForAdd(name, argTypes, argNames,
                returnClass, exceptions, access, body);
		methodCreaters.add(creator);
		return creator;
	}

	/**
     * Create a static method 
     * 
     * @param access          the method modifiers
     * @param name            the method name
     * @param argTypes      the method argument type list
     * @param argNames        the method arguments name list
     * @param returnClass     the method return class
     * @param exceptions      throw exceptions for this method
     * @param body            method body that is method logic implementation
     * @return
     */
	public IMethodBuilder createStaticMethod(int access, String name, IClass[] argTypes, String[] argNames,
			IClass returnClass, IClass[] exceptions, KernelStaticMethodBody body) {
		if((access & Opcodes.ACC_STATIC) == 0){
    		access += Opcodes.ACC_STATIC;
    	}
		IMethodBuilder creator = MethodBuilderImpl.methodCreatorForAdd(name, argTypes, argNames,
                returnClass, exceptions, access, body);
        methodCreaters.add(creator);
        return creator;
	}

	/**
	 * Create static block.
	 * 
	 * @param block
	 * @return
	 */
	public IMethodBuilder createStaticBlock(KernelStaticBlockBody block) {
    	checkStaticBlock();
    	existedStaticBlock = true;
    	IMethodBuilder creator = MethodBuilderImpl.methodCreatorForAdd(AsmsupportConstant.CLINIT, null, null, null, null,
                Opcodes.ACC_STATIC, block);
    	methodCreaters.add(0, creator);
    	return creator;
	}

    @Override
    protected void createDefaultConstructor() {
        createConstructor(Opcodes.ACC_PUBLIC, null, null, null, new KernelConstructorBody() {
            @Override
            public void body(LocalVariable... argus) {
                supercall();
                return_();
            }
            
        });
    }
}
