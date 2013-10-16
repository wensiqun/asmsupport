package bug.fixed.test2054;

import junit.framework.Assert;
import jw.asmsupport.block.method.common.StaticMethodBody;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.AClassFactory;
import jw.asmsupport.creator.ClassCreator;
import jw.asmsupport.creator.IClassContext;
import jw.asmsupport.definition.value.Value;
import jw.asmsupport.definition.variable.LocalVariable;
import jw.asmsupport.exception.NoSuchMethod;

import org.objectweb.asm.Opcodes;

public class Main {

	public static Class<?> generate(IClassContext creator){
		creator.setClassOutPutPath(".//target//");
		Class<?> cls = creator.startup();
		if(creator instanceof ClassCreator){
			try {
				cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return cls;
	}
	
	public static void main(String[] args) {
		ClassCreator creator = 
				new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.Test2054", null, null);
        
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

            @Override
            public void generateBody(LocalVariable... argus) {
                invokeConstructor(AClassFactory.getProductClass(MyObject.class), Value.value("i'm direct pass argument."));
            	runReturn();
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
