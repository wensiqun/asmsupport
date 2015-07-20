package cn.wensiqun.asmsupport.issues.fixed.earlier.test4646;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Assert;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.issues.fixed.earlier.test4646.entity.Child;
import cn.wensiqun.asmsupport.issues.fixed.earlier.test4646.entity.ChildChild;
import cn.wensiqun.asmsupport.issues.fixed.earlier.test4646.entity.Super;
import cn.wensiqun.asmsupport.issues.fixed.earlier.test4646.parent.AbstractClass;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;


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
		
		CachedThreadLocalClassLoader classLoader = CachedThreadLocalClassLoader.getInstance();
		
		ClassBuilderImpl creator = 
				new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.Test4646", 
						classLoader.getType(AbstractClass.class), null);
        
		final IClass childChild = classLoader.getType(ChildChild.class);
		
		creator.createMethod(Opcodes.ACC_PUBLIC, "abstractClassAbstractMethod", 
				null, null, childChild,
				null, new KernelMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				return_(new_(childChild));
			}
					
		});
		
		creator.createMethod(Opcodes.ACC_PUBLIC, "interfaceMethod", 
				null, null, childChild,
				null, new KernelMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				return_(new_(childChild));
			}
					
		});
		
		creator.createMethod(Opcodes.ACC_PUBLIC, "abstractClassMethod", 
				null, null, childChild,
				null, new KernelMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				return_(new_(childChild));
			}
					
		});
		
		creator.createMethod(Opcodes.ACC_PUBLIC, "interfaceReturnTypeIsChild", 
				null, null, childChild,
				null, new KernelMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				return_(new_(childChild));
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
