package bug.fixed.test65150;

import junit.framework.Assert;

import org.objectweb.asm.Opcodes;

import bug.fixed.AbstractFix;
import cn.wensiqun.asmsupport.block.method.clinit.ClinitBody;
import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;

public class Main extends AbstractFix {

	public static void main(String[] args) {
		
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.test65150.Test65150", 
				ParentClass.class, null);
		
		creator.createGlobalVariable("DEFAULT_VALUE", Opcodes.ACC_STATIC, AClass.INT_ACLASS);
		
		creator.createStaticBlock(new ClinitBody(){

			@Override
			public void body() {
				
				invoke(systemOut, "println", Value.value("INIT DEFAULT_VALUE"));
				
				assign(this.getMethodOwner().getGlobalVariable("DEFAULT_VALUE"), Value.value(100));
				
			    runReturn();
			}
			
		});
		
		creator.createStaticMethod("main", new AClass[]{
				AClassFactory.getProductClass(String[].class)}, 
				new String[]{"args"}, null, null,
				Opcodes.ACC_PUBLIC, new StaticMethodBody(){

	        @Override
			public void body(LocalVariable... argus) {
	        	
	        	invoke(systemOut, "println", append(Value.value("COMMON_PRE : "), getMethodOwner().getGlobalVariable("COMMON_PRE")));
	        	
	        	invoke(systemOut, "println", append(Value.value("COMMON_POST : "), getMethodOwner().getGlobalVariable("COMMON_POST")));
	        	
			    runReturn();
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
		
		final AClass Test65150AClass = AClassFactory.getProductClass(Test65150);
		
		creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.test65150.Test65150_ALT", 
				null, null);
		
		creator.createStaticMethod("main", new AClass[]{
				AClassFactory.getProductClass(String[].class)}, 
				new String[]{"args"}, null, null,
				Opcodes.ACC_PUBLIC, new StaticMethodBody(){

	        @Override
			public void body(LocalVariable... argus) {
	        	
	        	invoke(systemOut, "println", append(Value.value("DEFAULT_VALUE : "), Test65150AClass.getGlobalVariable("DEFAULT_VALUE")));
	        	
			    runReturn();
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
