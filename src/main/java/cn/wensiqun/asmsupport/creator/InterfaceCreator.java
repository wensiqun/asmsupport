/**
 * 
 */
package cn.wensiqun.asmsupport.creator;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.clinit.ClinitBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.utils.ASConstant;


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
	 * declare an interface method
	 * 
	 * @param name method name
	 * @param argClasses method argument types
	 * @param returnClass method return type, if null than indicate void
	 * @param exceptions what exception you want explicit throw.
	 */
	public void createMethod(String name, AClass[] argClasses, AClass returnClass, AClass[] exceptions) {
		String[] argNames = new String[argClasses.length];
		for(int i=0; i<argNames.length; i++){
			argNames[i] = "arg" + i;
		}
        methodCreaters.add(
        		MethodCreator.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, null));
    }
	
	/**
	 * <P>
	 *     create a global variable. the modifiers "public static final".</br>
	 *     if type is primitive than the default value is same to JVM</br>
	 *     if type if Object than default value is null.
	 * </p>
	 * 
	 * <p>
	 *     if you want assign special value to this variable, <br>
	 *     you need do it at static block.
	 * </p>
	 * 
	 * @param name variable name
	 * @param fieldClass
	 */
    public void createGlobalVariable(String name, AClass fieldClass){
    	GlobalVariableCreator fc = new GlobalVariableCreator(name, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, fieldClass);
        fieldCreators.add(fc);
    }
	
    /**
     * <p>
     *     create a static block. <br>
     *     you can call this method just on time.<br>
     *     this is different write java code. we can write multiple static block
     * </p>
     * 
     * @param mb Method Body
     */
    public InterfaceCreator createStaticBlock(ClinitBody clinitb) {
    	checkStaticBlock();
    	existedStaticBlock = true;
        methodCreaters.add(0,  MethodCreator.methodCreatorForAdd(ASConstant.CLINIT, null, null, null, null,
                Opcodes.ACC_STATIC, clinitb));
        return this;
    }
	
	@Override
	protected final void createDefaultConstructor() {
	}

}
