package bug.fixed.test65150;

import junit.framework.Assert;

import org.objectweb.asm.Opcodes;

import bug.fixed.AbstractFix;
import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.exception.NoSuchMethod;

public class Main extends AbstractFix {

	public static void main(String[] args) {
		
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.Test65150", 
				ParentClass.class, null);
		
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
		generate(creator);
	}
	
	@org.junit.Test
	public void test(){
		main(null);
		Assert.assertTrue(true);
	}

}
