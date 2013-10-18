package demoInterface;



import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.creator.InterfaceCreator;

@org.junit.Ignore
public class InterfaceCreatorDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InterfaceCreator ifc = new InterfaceCreator(Opcodes.V1_6, "demo.TestInterface", null);
		ifc.createMethod("interfaceMethod", new AClass[]{AClass.STRING_ACLASS, AClass.INT_ACLASS}, AClass.BOOLEAN_ACLASS, null);

		ifc.setClassOutPutPath(".\\target\\generate\\");
    	
		ifc.startup();
	}

}
