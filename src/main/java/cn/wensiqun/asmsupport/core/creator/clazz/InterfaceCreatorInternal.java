/**
 * 
 */
package cn.wensiqun.asmsupport.core.creator.clazz;


import cn.wensiqun.asmsupport.core.block.classes.method.clinit.ClinitBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.creator.FieldCreatorIternel;
import cn.wensiqun.asmsupport.core.creator.MethodCreatorInternal;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;


public class InterfaceCreatorInternal extends AbstractClassCreatorContext {

	/**
	 * Interface Creator
	 * 
	 * @param version JDK version.  
	 * @param name Interface qualified name.
	 * @param interfaces super interfaces.
	 */
	public InterfaceCreatorInternal(int version, String name, Class<?>[] interfaces) {
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
        		MethodCreatorInternal.methodCreatorForAdd(name, argClasses, argNames,
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
    	FieldCreatorIternel fc = new FieldCreatorIternel(name, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, fieldClass);
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
    public InterfaceCreatorInternal createStaticBlock(ClinitBodyInternal clinitb) {
    	checkStaticBlock();
    	existedStaticBlock = true;
        methodCreaters.add(0,  MethodCreatorInternal.methodCreatorForAdd(ASConstant.CLINIT, null, null, null, null,
                Opcodes.ACC_STATIC, clinitb));
        return this;
    }
	
	@Override
	protected final void createDefaultConstructor() {
	}

}
