package cn.wensiqun.asmsupport.issues.fixed.earlier.test65150;

import junit.framework.Assert;
import cn.wensiqun.asmsupport.core.block.method.clinit.KernelStaticBlockBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.issues.AbstractFix;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class Main extends AbstractFix {

	public static void main(String[] args) {
		
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.test65150.Test65150", 
				ParentClass.class, null);
		
		creator.createField("DEFAULT_VALUE", Opcodes.ACC_STATIC, AClassFactory.getType(int.class));
		
		creator.createStaticBlock(new KernelStaticBlockBody(){

			@Override
			public void body() {
				
				call(systemOut, "println", Value.value("INIT DEFAULT_VALUE"));
				
				assign(val(getMethodDeclaringClass()).field("DEFAULT_VALUE"), Value.value(100));
				
			    return_();
			}
			
		});
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC, "main", new AClass[]{
				AClassFactory.getType(String[].class)}, 
				new String[]{"args"}, null, null,
				new KernelStaticMethodBody(){

	        @Override
			public void body(LocalVariable... argus) {
	        	
	        	call(systemOut, "println", stradd(Value.value("COMMON_PRE : "), val(getMethodDeclaringClass()).field("COMMON_PRE")));
	        	
	        	call(systemOut, "println", stradd(Value.value("COMMON_POST : "), val(getMethodDeclaringClass()).field("COMMON_POST")));
	        	
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
		
		final AClass Test65150AClass = AClassFactory.getType(Test65150);
		
		creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.test65150.Test65150_ALT", 
				null, null);
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC, "main", new AClass[]{
				AClassFactory.getType(String[].class)}, 
				new String[]{"args"}, null, null,
				new KernelStaticMethodBody(){

	        @Override
			public void body(LocalVariable... argus) {
	        	
	        	call(systemOut, "println", stradd(Value.value("DEFAULT_VALUE : "), val(Test65150AClass).field("DEFAULT_VALUE")));
	        	
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
