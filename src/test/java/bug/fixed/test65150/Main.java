package bug.fixed.test65150;

import junit.framework.Assert;

import org.objectweb.asm.Opcodes;

import bug.fixed.AbstractFix;
import cn.wensiqun.asmsupport.block.method.cinit.CInitBody;
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
		
		creator.createStaticBlock(new CInitBody(){

			@Override
			public void generateBody() {
				
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
			public void generateBody(LocalVariable... argus) {
	        	
	        	invoke(systemOut, "println", append(Value.value("COMMON_PRE : "), getMethodOwner().getGlobalVariable("COMMON_PRE")));
	        	
	        	invoke(systemOut, "println", append(Value.value("COMMON_POST : "), getMethodOwner().getGlobalVariable("COMMON_POST")));
	        	
			    runReturn();
			}
			
		});
		System.out.println(ClassLoader.getSystemClassLoader());
		//generate(creator);
		/*Class Test65150 = null;
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
			public void generateBody(LocalVariable... argus) {
	        	
	        	invoke(systemOut, "println", append(Value.value("DEFAULT_VALUE : "), Test65150AClass.getGlobalVariable("DEFAULT_VALUE")));
	        	
			    runReturn();
			}
			
		});
		
		generate(creator);*/
		
	}
	
	@org.junit.Test
	public void test(){
		main(null);
		Assert.assertTrue(true);
	}

}
