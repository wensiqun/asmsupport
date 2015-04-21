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
/**
 * 
 */
package cn.wensiqun.asmsupport.core.creator.clazz;


import cn.wensiqun.asmsupport.core.block.method.clinit.StaticBlockBodyInternal;
import cn.wensiqun.asmsupport.core.creator.FieldCreator;
import cn.wensiqun.asmsupport.core.creator.IFieldCreator;
import cn.wensiqun.asmsupport.core.creator.MethodCreator;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;


public class InterfaceCreator extends AbstractClassCreatorContext {

	/**
	 * Interface Creator
	 * 
	 * @param version JDK version.  
	 * @param name Interface qualified name.
	 * @param interfaces super interfaces.
	 */
	public InterfaceCreator(int version, String name, Class<?>[] interfaces) {
		super(version, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE, name, null, interfaces);
	}
	
	/**
	 * 
	 * @param name
	 * @param argClasses
	 * @param returnClass
	 * @param exceptions
	 */
	public void createMethod(String name, AClass[] argClasses, AClass returnClass, AClass[] exceptions) {
		this.createMethod(name, argClasses, returnClass, exceptions, false);
    }
	
	/**
	 * declare an interface method
	 * 
	 * @param name method name
	 * @param argClasses method argument types
	 * @param returnClass method return type, if null than indicate void
	 * @param exceptions what exception you want explicit throw.
	 */
	public void createMethod(String name, AClass[] argClasses, AClass returnClass, AClass[] exceptions, boolean isVarargs) {
		String[] argNames = new String[argClasses.length];
		for(int i=0; i<argNames.length; i++){
			argNames[i] = "arg" + i;
		}
        methodCreaters.add(
        		MethodCreator.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + (isVarargs ? Opcodes.ACC_VARARGS : 0), null));
    }
	
	/**
     * <P>
     *     create a global variable. the modifiers "public static final".</br>
     *     The default value is same to JVM</br>
     * </p>
     * 
     * <p>
     *     if you want assign special value to this variable, <br>
     *     you need do it at static block.
     * </p>
     * 
	 * @param name
	 * @param type
	 * @return
	 */
	public IFieldCreator createField(String name, AClass type) {
	    return createField(name, type, null);
	}
    
	/**
	 * <P>
	 *     create a global variable with special value. the modifiers "public static final".</br>
	 *     The default value is same to JVM</br>
	 * </p>
	 * 
	 * <p>
	 *     if you want assign special value to this variable, <br>
	 *     you need do it at static block.
	 * </p>
	 * 
	 * @param name variable name
	 * @param type
	 * @param value The initial value, this value is only support static field, 
     *              otherwise will ignored.This parameter, which may be null 
     *              if the field does not have an initial value, 
     *              must be an Integer, a Float, a Long, a Double or a 
     *              String (for int, float, long or String fields respectively). 
     *              This parameter is only used for static fields. Its value is 
     *              ignored for non static fields, which must be initialized 
     *              through bytecode instructions in constructors or methods.
	 */
    public IFieldCreator createField(String name, AClass type, Object value){
    	FieldCreator fc = new FieldCreator(name, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, type, value);
        fieldCreators.add(fc);
        return fc;
    }
	
    /**
     * <p>
     *     create a static block. <br>
     *     you can call this method just on time.<br>
     *     this is different write java code. we can write any java cod
     * </p>
     * 
     * @param mb Method Body
     */
    public InterfaceCreator createStaticBlock(StaticBlockBodyInternal clinitb) {
    	checkStaticBlock();
    	existedStaticBlock = true;
        methodCreaters.add(0,  MethodCreator.methodCreatorForAdd(ASConstant.CLINIT, null, null, null, null,
                Opcodes.ACC_STATIC, clinitb));
        return this;
    }
	
	@Override
	protected final void createDefaultConstructor() {
        // Do nothing
	}

}
