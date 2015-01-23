package example.create;

import java.util.Random;

import cn.wensiqun.asmsupport.core.block.classes.control.condition.ElseInternal;
import cn.wensiqun.asmsupport.core.block.classes.control.condition.IFInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.CommonMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.ModifiedMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassModifierInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import example.AbstractExample;

public class CreateClassAndThanExtend extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreatorInternal superCreator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.create.CreateClassAndThanExtendExampleSuper", null, null);
		
		superCreator.createMethod(Opcodes.ACC_PUBLIC, "commonMethod", null, null, null, null, new CommonMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_invoke(systemOut, "println", Value.value("say hello!"));
				_return();
			}
		});
		

		superCreator.setClassOutPutPath(".//");
		Class superClass = superCreator.startup();
		
		final GlobalVariable out = systemOut;
		
		ClassModifierInternal byModifyModifer = new ClassModifierInternal(ByModify.class);
		byModifyModifer.createGlobalVariable("age", Opcodes.ACC_STATIC + Opcodes.ACC_PRIVATE, AClass.INT_ACLASS);
		byModifyModifer.createMethod("asmcreate", null,null,null,null, Opcodes.ACC_PUBLIC, new CommonMethodBodyInternal(){
			@Override
			public void body(LocalVariable... argus) {
				_invoke(out, "println", Value.value("created by asm"));
				_return();
			}
		});
		
		byModifyModifer.modifyMethod(ASConstant.CLINIT, null, new ModifiedMethodBodyInternal(){
			@Override
			public void body(LocalVariable... argus) {
				GlobalVariable age = getMethodOwner().getGlobalVariable("age");
				_assign(age, Value.value(20));
				this._invokeOriginalMethod();
				GlobalVariable name = getMethodOwner().getGlobalVariable("name");
				_assign(name, Value.value("wensiqun"));
				_invoke(out, "println", name);
				_return();
			}
		});
		
		byModifyModifer.modifyMethod("helloWorld", null, new ModifiedMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_invoke(out, "println", Value.value("before"));
				
				AClass randomClass = AClassFactory.getProductClass(Random.class);
				LocalVariable random = this._createVariable("random", randomClass, false, this._new(randomClass, Value.value(1L)));
				_if(new IFInternal(_invoke(random, "nextBoolean")){
					@Override
					public void body() {
						_invokeOriginalMethod();
					}

				})._else(new ElseInternal(){
					@Override
					public void body() {
						_invoke(out, "println", Value.value("call self"));
					}
					
				});
				_invoke(out, "println", Value.value("after"));
				_return();
			}
			
		});
		
		byModifyModifer.modifyMethod("String", null, new ModifiedMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_invoke(out, "println", Value.value("before"));
				LocalVariable lv = this._createVariable(null, getOriginalMethodReturnClass(), true, _invokeOriginalMethod());
				_invoke(out, "println", Value.value("after"));	
				_return(lv);
			}
			
		});
		byModifyModifer.setClassOutPutPath("./generated");
		Class<?> ByModify = byModifyModifer.startup();
		
        ClassCreatorInternal childCreator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.create.CreateClassAndThanExtendExample", ByModify, null);
		
		/*childCreator.createMethod("commonMethod", null, null, null, null, Opcodes.ACC_PUBLIC, new CommonMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				invoke(systemOut, "println", Value.value("say hello!"));
				runReturn();
			}
		});*/
		

		childCreator.createStaticMethod(Opcodes.ACC_PUBLIC, "main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

	        @Override
			public void body(LocalVariable... argus) {
	        	_invoke(_new(getMethodOwner()), "helloWorld");
			    _return();
			}
			
		});
		
		childCreator.setClassOutPutPath(".//");
		
		//这个就是个开关。前面我们把该创建的方法变量都放到了传送带上了。调用startup
		//启动传送带，将上面的东西一个个处理给我返回一个我们需要的成品（就是class了）
		Class<?> cls = childCreator.startup();
		
		//如果创建的是非枚举类型或者非接口类型则调用main方法
		if(childCreator instanceof ClassCreatorInternal){
			try {
				cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		
	}

}
