package bug.fixed.test4646;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Assert;
import bug.fixed.Utils;
import bug.fixed.test4646.entity.Child;
import bug.fixed.test4646.entity.ChildChild;
import bug.fixed.test4646.entity.Super;
import bug.fixed.test4646.parent.AbstractClass;
import cn.wensiqun.asmsupport.core.block.classes.method.common.CommonMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;


public class MainTest {

	/**
	 * @param args
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ClassCreatorInternal creator = 
				new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.Test4646", 
						AbstractClass.class, null);
        
		final AClass childChild = AClassFactory.getProductClass(ChildChild.class);
		
		creator.createMethod(Opcodes.ACC_PUBLIC, "abstractClassAbstractMethod", 
				null, null, AClassFactory.getProductClass(ChildChild.class),
				null, new CommonMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_return(_new(childChild));
			}
					
		});
		
		creator.createMethod(Opcodes.ACC_PUBLIC, "interfaceMethod", 
				null, null, AClassFactory.getProductClass(ChildChild.class),
				null, new CommonMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_return(_new(childChild));
			}
					
		});
		
		creator.createMethod(Opcodes.ACC_PUBLIC, "abstractClassMethod", 
				null, null, AClassFactory.getProductClass(ChildChild.class),
				null, new CommonMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_return(_new(childChild));
			}
					
		});
		
		creator.createMethod(Opcodes.ACC_PUBLIC, "interfaceReturnTypeIsChild", 
				null, null, AClassFactory.getProductClass(ChildChild.class),
				null, new CommonMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_return(_new(childChild));
			}
					
		});
		
		Class<?> cls = creator.startup();
		Method[] methods = cls.getMethods();
		int count = 0;
		for(Method m : methods){
			if(checkBridge(m))
				count++;
		}
		Assert.assertEquals(5, count);
	}
	
	private static boolean checkBridge(Method m){
		if(!m.isBridge()){
			return false;
		}
		
		if (m.getReturnType().equals(Super.class) && m.getName().equals("interfaceMethod")) {
			return true;
		}
		
		if (m.getReturnType().equals(Child.class) && m.getName().equals("interfaceMethod")) {
			return true;
		}

		if (m.getReturnType().equals(Child.class) && m.getName().equals("abstractClassMethod")) {
			return true;
		}

		if (m.getReturnType().equals(Child.class) && m.getName().equals("abstractClassAbstractMethod")) {
			return true;
		}

		if (m.getReturnType().equals(Child.class) && m.getName().equals("interfaceReturnTypeIsChild")) {
			return true;
		}

		return false;
	}
	
	
	@org.junit.Test
	public void test() throws Exception{
		try{
			main(null);
		}catch(Throwable e){
			Assert.fail();
		}
	}

}
