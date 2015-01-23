package bug.fixed.test65150;

import junit.framework.Assert;
import bug.fixed.AbstractFix;
import cn.wensiqun.asmsupport.core.block.classes.method.clinit.ClinitBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class Main extends AbstractFix {

	public static void main(String[] args) {
		
		ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.test65150.Test65150", 
				ParentClass.class, null);
		
		creator.createField("DEFAULT_VALUE", Opcodes.ACC_STATIC, AClass.INT_ACLASS);
		
		creator.createStaticBlock(new ClinitBodyInternal(){

			@Override
			public void body() {
				
				_invoke(systemOut, "println", Value.value("INIT DEFAULT_VALUE"));
				
				_assign(this.getMethodOwner().getGlobalVariable("DEFAULT_VALUE"), Value.value(100));
				
			    _return();
			}
			
		});
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC, "main", new AClass[]{
				AClassFactory.getProductClass(String[].class)}, 
				new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

	        @Override
			public void body(LocalVariable... argus) {
	        	
	        	_invoke(systemOut, "println", _append(Value.value("COMMON_PRE : "), getMethodOwner().getGlobalVariable("COMMON_PRE")));
	        	
	        	_invoke(systemOut, "println", _append(Value.value("COMMON_POST : "), getMethodOwner().getGlobalVariable("COMMON_POST")));
	        	
			    _return();
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
		
		creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.test65150.Test65150_ALT", 
				null, null);
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC, "main", new AClass[]{
				AClassFactory.getProductClass(String[].class)}, 
				new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

	        @Override
			public void body(LocalVariable... argus) {
	        	
	        	_invoke(systemOut, "println", _append(Value.value("DEFAULT_VALUE : "), Test65150AClass.getGlobalVariable("DEFAULT_VALUE")));
	        	
			    _return();
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
