package cn.wensiqun.asmsupport.issues.fixed.earlier.test65150;

import cn.wensiqun.asmsupport.core.block.method.clinit.KernelStaticBlockBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.build.resolver.ClassResolver;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.issues.AbstractFix;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import junit.framework.Assert;

import java.lang.reflect.Method;

public class MainTest extends AbstractFix {

	public static void main(String[] args) {
		
		CachedThreadLocalClassLoader classLoader = CachedThreadLocalClassLoader.getInstance();
		String Test65150Name = "cn.wensiqun.asmsupport.issues.fixed.earlier.test65150.Test65150";
        String Test65150_ALT_Name = "cn.wensiqun.asmsupport.issues.fixed.earlier.test65150.Test65150_ALT";
		ClassResolver creator = new ClassResolver(Opcodes.V1_5, Opcodes.ACC_PUBLIC , Test65150Name,
				classLoader.getType(ParentClass.class), null);
		
		creator.createField("DEFAULT_VALUE", Opcodes.ACC_STATIC, classLoader.getType(int.class));
		
		creator.createStaticBlock(new KernelStaticBlockBody(){

			@Override
			public void body() {
				
				call(systemOut, "println", val("INIT DEFAULT_VALUE"));
				
				assign(val(getMethodDeclaringClass()).field("DEFAULT_VALUE"), val(100));
				
			    return_();
			}
			
		});
		
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new IClass[]{
				classLoader.getType(String[].class)}, 
				new String[]{"args"}, null, null,
				new KernelMethodBody(){

	        @Override
			public void body(LocalVariable... argus) {
	        	
	        	call(systemOut, "println", stradd(val("COMMON_PRE : "), val(getMethodDeclaringClass()).field("COMMON_PRE")));
	        	
	        	call(systemOut, "println", stradd(val("COMMON_POST : "), val(getMethodDeclaringClass()).field("COMMON_POST")));
	        	
			    return_();
			}
			
		});
		creator.resolve();
		Class Test65150 = null;
		try {
			Test65150 = Class.forName(Test65150Name);
			System.out.println(Test65150);
		} catch (ClassNotFoundException e) {
			Assert.fail();
            e.printStackTrace();
		}
		
		final IClass Test65150AClass = classLoader.getType(Test65150);
		creator = new ClassResolver(Opcodes.V1_5, Opcodes.ACC_PUBLIC , Test65150_ALT_Name,
				null, null);
		
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new IClass[]{classLoader.getType(String[].class)},
				new String[]{"args"}, null, null,
				new KernelMethodBody(){

	        @Override
			public void body(LocalVariable... argus) {
	        	
	        	call(systemOut, "println", stradd(val("DEFAULT_VALUE : "), val(Test65150AClass).field("DEFAULT_VALUE")));
	        	
			    return_();
			}
			
		}); 
		

		creator.resolve();
		Class Test65150_ALT = null;
		try {
			Test65150_ALT = Class.forName(Test65150_ALT_Name);
			System.out.println(Test65150_ALT);
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
			Assert.fail();
		}

		try {
			Method method = Test65150.getMethod("main", String[].class);
            method.invoke(Test65150, new Object[]{null});
            method = Test65150_ALT.getMethod("main", String[].class);
            method.invoke(Test65150_ALT, new Object[]{null});
		} catch (Exception e) {
            e.printStackTrace();
			Assert.fail();
		}
		
	}
	
	@org.junit.Test
	public void test(){
		main(null);
		Assert.assertTrue(true);
	}

}
