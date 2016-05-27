package cn.wensiqun.asmsupport.issues.fixed.earlier.test2054;

import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.issues.IssuesConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import junit.framework.Assert;

import java.lang.reflect.Method;

public class MainTest {

	public static void main(String[] args) throws Exception {
		ClassBuilderImpl creator = 
				new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "fixed.Test2054", null, null);
		creator.setClassOutPutPath(IssuesConstant.classOutPutPath);
        
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main",
				new IClass[]{creator.getClassLoader().getType(String[].class)}, new String[]{"args"}, null, null,
                new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus) {
            	call(val(System.class).field("out"), "println", val("Prepare to build MainObject."));
                new_(getType(MyObject.class), val("I'm direct pass argument."));
            	return_();
            }
        });
		Class<?> cls = creator.startup();
		Method main = cls.getMethod("main", String[].class);
		main.invoke(main, new Object[0]);
	}
	
	@org.junit.Test
	public void test() throws Exception{
		try{
			main(null);
			Assert.assertTrue(false);
		}catch(Exception e){
			if(e.getMessage().startsWith("No such method")) {
		        Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

}
