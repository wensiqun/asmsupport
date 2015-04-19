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


import cn.wensiqun.asmsupport.core.block.method.clinit.StaticBlockBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.common.MethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.init.ConstructorBodyInternal;
import cn.wensiqun.asmsupport.core.creator.FieldCreator;
import cn.wensiqun.asmsupport.core.creator.IFieldCreator;
import cn.wensiqun.asmsupport.core.creator.IMethodCreator;
import cn.wensiqun.asmsupport.core.creator.MethodCreator;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.clazz.AClass;


/**
 * 
 * 
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ClassCreator extends AbstractClassCreatorContext {

    public ClassCreator(int version, int access, String name,
            Class<?> superCls, Class<?>[] interfaces) {
        super(version, access, name, superCls, interfaces);
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
    public IFieldCreator createField(String name, int modifiers, AClass type) {
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
    public IFieldCreator createField(String name, int modifiers, AClass type, Object val) {
        IFieldCreator fc = new FieldCreator(name, modifiers, type, val);
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
	public IMethodCreator createConstructor(int access, AClass[] argTypes, String[] argNames, AClass[] exceptions, ConstructorBodyInternal body) {
		IMethodCreator creator = MethodCreator.methodCreatorForAdd(ASConstant.INIT, argTypes, argNames,
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
	public IMethodCreator createMethodForDummy(int access, String name, AClass[] argTypes, String[] argNames,
            AClass returnClass, AClass[] exceptions, MethodBodyInternal body) {
        IMethodCreator creator = MethodCreator.methodCreatorForAdd(name, argTypes, argNames,
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
	public IMethodCreator createMethod(int access, String name, AClass[] argTypes, String[] argNames,
			AClass returnClass, AClass[] exceptions, MethodBodyInternal body) {
		if((access & Opcodes.ACC_STATIC) != 0){
    		access -= Opcodes.ACC_STATIC;
    	}
		IMethodCreator creator = MethodCreator.methodCreatorForAdd(name, argTypes, argNames,
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
	public IMethodCreator createStaticMethod(int access, String name, AClass[] argTypes, String[] argNames,
			AClass returnClass, AClass[] exceptions, StaticMethodBodyInternal body) {
		if((access & Opcodes.ACC_STATIC) == 0){
    		access += Opcodes.ACC_STATIC;
    	}
		IMethodCreator creator = MethodCreator.methodCreatorForAdd(name, argTypes, argNames,
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
	public IMethodCreator createStaticBlock(StaticBlockBodyInternal block) {
    	checkStaticBlock();
    	existedStaticBlock = true;
    	IMethodCreator creator = MethodCreator.methodCreatorForAdd(ASConstant.CLINIT, null, null, null, null,
                Opcodes.ACC_STATIC, block);
    	methodCreaters.add(0, creator);
    	return creator;
	}

    @Override
    protected void createDefaultConstructor() {
        createConstructor(Opcodes.ACC_PUBLIC, null, null, null, new ConstructorBodyInternal() {
            @Override
            public void body(LocalVariable... argus) {
                supercall();
                return_();
            }
            
        });
    }
}
