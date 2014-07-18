package demo.control;

import java.lang.reflect.Method;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.control.Finally;
import cn.wensiqun.asmsupport.block.control.Try;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.block.method.init.InitBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import demo.CreateMethod;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class TryCatchTest {

	static GlobalVariable out = CreateMethod.out;
	
	@Test
	public void test(){
		main(null);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "demo.TryCatch", null, null);
        creator.createConstructor(null, null, new InitBody(){

			@Override
			public void body(LocalVariable... argus) {
				invokeSuperConstructor();
				runReturn();
			}
			
		}, Opcodes.ACC_PUBLIC);
		
		createTryFinally(creator);
		
		//new TryCatch(creator).createMethod();
		
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null, 
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
			    new StaticMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						AClass curCls = getMethodOwner();
						LocalVariable self = createVariable("myMain", getMethodOwner(), false, invokeConstructor(curCls));

						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                        Try Catch Operator                    **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "tryFinally");
						
						runReturn();
					}
		});
		
		creator.setClassOutPutPath("D:\\TEMP\\Test\\");
    	
		Class<?> example = creator.startup();
		
        try {
			Method main = example.getMethod("main", String[].class);
			main.invoke(example, new Object[]{null});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public static void createTryFinally(ClassCreator creator ) {

		creator.createMethod("tryFinally", null, null, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						final AClass acls = AClassFactory.getProductClass(Class.class);
						tryDo(new Try(){
							@Override
							public void body() {
								createVariable("stringClass", acls, false, invokeStatic(acls, "forName", Value.value("java.lang.String")));
								runReturn();
							}
						}).finallyThan(new Finally(){
							@Override
							public void body() {
							    invoke(out, "println", Value.value("hello..."));
							}
							
						});

					}
		        }
		);
	}

}
