package bug.fixed.test2054;

import junit.framework.Assert;
import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.exception.NoSuchMethod;
import cn.wensiqun.asmsupport.generic.creator.IClassContext;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class MainTest {

	public static Class<?> generate(IClassContext creator){
		creator.setClassOutPutPath(".//target//");
		Class<?> cls = creator.startup();
		if(creator instanceof ClassCreatorInternal){
			try {
				cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return cls;
	}
	
	public static void main(String[] args) {
		ClassCreatorInternal creator = 
				new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.Test2054", null, null);
        
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
                new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus) {
                _new(AClassFactory.getProductClass(MyObject.class), Value.value("i'm direct pass argument."));
            	_return();
            }
        });
		generate(creator);
	}
	
	@org.junit.Test
	public void test() throws Exception{
		try{
			main(null);
			Assert.assertTrue(false);
		}catch(NoSuchMethod e){
			Assert.assertTrue(true);
		}catch(Throwable e){
			Assert.assertTrue(false);
		}
	}

}
