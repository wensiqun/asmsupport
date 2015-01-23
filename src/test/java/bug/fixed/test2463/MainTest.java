package bug.fixed.test2463;

import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;
import bug.fixed.Utils;
import cn.wensiqun.asmsupport.core.block.classes.method.common.CommonMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class MainTest {

	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ClassCreatorInternal creator = 
				new ClassCreatorInternal(Opcodes.V1_6, Opcodes.ACC_PUBLIC , "test.Test2463", AbstractClass.class, null);
        
		creator.createMethod(Opcodes.ACC_PUBLIC, "getMyObject", null, null, AClassFactory.getProductClass(MyObject.class),
				null, new CommonMethodBodyInternal(){
					@Override
					public void body(LocalVariable... argus) {
		            	_return(_new(AClassFactory.getProductClass(MyObject.class)));
					}
			
		});
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
				"main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
                new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus) {
                _invoke(_new(getMethodOwner()), "getMyObject");
            	_return();
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
