package cn.wensiqun.asmsupport.issues.fixed.earlier.test65150;

import cn.wensiqun.asmsupport.core.block.method.clinit.KernelStaticBlockBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.issues.AbstractFix;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import junit.framework.Assert;

public class Main extends AbstractFix {

	public static void main(String[] args) {
		
		CachedThreadLocalClassLoader classLoader = CachedThreadLocalClassLoader.getInstance();
		
		ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.test65150.Test65150", 
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
		
		creator.createMethod(Opcodes.ACC_PUBLIC, "main", new IClass[]{
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
		creator.startup();
		Class Test65150 = null;
		try {
			Test65150 = Class.forName("bug.fixed.test65150.Test65150");
			System.out.println(Test65150);
		} catch (ClassNotFoundException e) {
			Assert.fail();
		}
		
		final IClass Test65150AClass = classLoader.getType(Test65150);
		
		creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.test65150.Test65150_ALT", 
				null, null);
		
		creator.createMethod(Opcodes.ACC_PUBLIC, "main", new IClass[]{classLoader.getType(String[].class)},
				new String[]{"args"}, null, null,
				new KernelMethodBody(){

	        @Override
			public void body(LocalVariable... argus) {
	        	
	        	call(systemOut, "println", stradd(val("DEFAULT_VALUE : "), val(Test65150AClass).field("DEFAULT_VALUE")));
	        	
			    return_();
			}
			
		}); 
		

		creator.startup();
		Class Test65150_ALT = null;
		try {
			Test65150_ALT = Class.forName("bug.fixed.test65150.Test65150_ALT");
			System.out.println(Test65150_ALT);
		} catch (ClassNotFoundException e) {
			Assert.fail();
		}

		try {
			Test65150.getMethod("main", String[].class).invoke(Test65150, new Object[]{null});
			Test65150_ALT.getMethod("main", String[].class).invoke(Test65150_ALT, new Object[]{null});
		} catch (Exception e) {
			Assert.fail();
		}
		
	}
	
	@org.junit.Test
	public void test(){
		main(null);
		Assert.assertTrue(true);
	}

}
