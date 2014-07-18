package bug.fixed.test2463;

import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;

import org.objectweb.asm.Opcodes;

import bug.fixed.Utils;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;

public class MainTest {

	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ClassCreator creator = 
				new ClassCreator(Opcodes.V1_6, Opcodes.ACC_PUBLIC , "test.Test2463", AbstractClass.class, null);
        
		creator.createMethod("getMyObject", null, null, AClassFactory.getProductClass(MyObject.class),
				null, Opcodes.ACC_PUBLIC, new CommonMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
		            	runReturn(invokeConstructor(AClassFactory.getProductClass(MyObject.class)));
					}
			
		});
		
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

            @Override
            public void body(LocalVariable... argus) {
                invoke(invokeConstructor(getMethodOwner()), "getMyObject");
            	runReturn();
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
