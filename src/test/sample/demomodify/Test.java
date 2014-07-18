package demomodify;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.control.Else;
import cn.wensiqun.asmsupport.block.control.IF;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.block.method.common.ModifiedMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassModifier;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.utils.ASConstant;
import demo.CreateMethod;

@org.junit.Ignore
public class Test {

	static GlobalVariable out = CreateMethod.out;
	
	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		ClassModifier cm = new ClassModifier(ByModify.class);
		cm.createGlobalVariable("age", Opcodes.ACC_STATIC + Opcodes.ACC_PRIVATE, AClass.INT_ACLASS);
		cm.createMethod("asmcreate", null,null,null,null, Opcodes.ACC_PUBLIC, new CommonMethodBody(){
			@Override
			public void body(LocalVariable... argus) {
				invoke(out, "println", Value.value("created by asm"));
				runReturn();
			}
		});
		
		cm.modifyMethod(ASConstant.CLINIT, null, new ModifiedMethodBody(){
			@Override
			public void body(LocalVariable... argus) {
				GlobalVariable age = getMethodOwner().getGlobalVariable("age");
				assign(age, Value.value(20));
				this.invokeOriginalMethod();
				GlobalVariable name = getMethodOwner().getGlobalVariable("name");
				assign(name, Value.value("wensiqun"));
				invoke(out, "println", name);
				runReturn();
			}
		});
		
		cm.modifyMethod("helloWorld", null, new ModifiedMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				invoke(out, "println", Value.value("before"));
				
				AClass randomClass = AClassFactory.getProductClass(Random.class);
				LocalVariable random = this.createVariable("random", randomClass, false, this.invokeConstructor(randomClass, Value.value(1L)));
				ifthan(new IF(invoke(random, "nextBoolean")){
					@Override
					public void body() {
						invokeOriginalMethod();
					}

				}).elsethan(new Else(){
					@Override
					public void body() {
						invoke(out, "println", Value.value("call self"));
					}
					
				});
				invoke(out, "println", Value.value("after"));
				runReturn();
			}
			
		});
		
		cm.modifyMethod("String", null, new ModifiedMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				invoke(out, "println", Value.value("before"));
				LocalVariable lv = this.createVariable(null, getOriginalMethodReturnClass(), true, invokeOriginalMethod());
				invoke(out, "println", Value.value("after"));	
				runReturn(lv);
			}
			
		});
		cm.setClassOutPutPath(".\\target\\generate\\");
		Class<?> c = cm.startup();
		
		/*IByModify bm = (IByModify) c.newInstance();
		Method asmcreate = c.getDeclaredMethod("helloWorld");
		asmcreate.invoke(bm);*/
		
		IByModify bm =  new ByModifyChild();
		bm.helloWorld();
	}

}
