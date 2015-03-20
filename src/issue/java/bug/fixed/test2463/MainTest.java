package bug.fixed.test2463;

import java.lang.reflect.InvocationTargetException;

import bug.fixed.Utils;
import junit.framework.Assert;
import cn.wensiqun.asmsupport.core.block.method.common.MethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class MainTest {

	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ClassCreator creator = 
				new ClassCreator(Opcodes.V1_6, Opcodes.ACC_PUBLIC , "test.Test2463", AbstractClass.class, null);
        
		creator.createMethod(Opcodes.ACC_PUBLIC, "getMyObject", null, null, AClassFactory.getProductClass(MyObject.class),
				null, new MethodBodyInternal(){
					@Override
					public void body(LocalVariable... argus) {
		            	return_(new_(AClassFactory.getProductClass(MyObject.class)));
					}
			
		});
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
				"main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
                new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus) {
                call(new_(getMethodOwner()), "getMyObject");
            	return_();
            }
        });
		Utils.generate(creator);
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
