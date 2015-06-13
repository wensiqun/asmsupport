package cn.wensiqun.asmsupport.issues.fixed.earlier.test2463;

import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.issues.Utils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;

public class MainTest {

	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ClassBuilderImpl creator = 
				new ClassBuilderImpl(Opcodes.V1_6, Opcodes.ACC_PUBLIC , "test.Test2463", AbstractClass.class, null);

		LogFactory.LOG_FACTORY_LOCAL.set(new LogFactory()); 
        
		creator.createMethod(Opcodes.ACC_PUBLIC, "getMyObject", null, null, AClassFactory.getType(MyObject.class),
				null, new KernelMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
		            	return_(new_(AClassFactory.getType(MyObject.class)));
					}
			
		});
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
				"main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
                new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus) {
                call(new_(getMethodDeclaringClass()), "getMyObject");
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
