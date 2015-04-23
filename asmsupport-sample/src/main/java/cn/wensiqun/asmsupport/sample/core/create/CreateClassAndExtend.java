package cn.wensiqun.asmsupport.sample.core.create;

import java.util.Random;

import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelElse;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelIF;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelModifiedMethodBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassModifier;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class CreateClassAndExtend extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        String classOutPutPath = ".//target//asmsupport-test-generated";
		final GlobalVariable out = systemOut;
		
		ClassModifier byModifyModifer = new ClassModifier(ByModify.class);
		byModifyModifer.createField("age", Opcodes.ACC_STATIC + Opcodes.ACC_PRIVATE, AClassFactory.getType(int.class));
		byModifyModifer.createMethod("asmcreate", null,null,null,null, Opcodes.ACC_PUBLIC, new KernelMethodBody(){
			@Override
			public void body(LocalVariable... argus) {
				call(out, "println", Value.value("created by asm"));
				return_();
			}
		});
		
		byModifyModifer.modifyMethod(ASConstant.CLINIT, null, new KernelModifiedMethodBody(){
			@Override
			public void body(LocalVariable... argus) {
				GlobalVariable age = val(getMethodOwner()).field("age");
				assign(age, val(20));
				this.callOrig();
				GlobalVariable name = val(getMethodOwner()).field("name");
				assign(name, Value.value("wensiqun"));
				call(out, "println", name);
				return_();
			}
		});
		
		byModifyModifer.modifyMethod("helloWorld", null, new KernelModifiedMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				call(out, "println", Value.value("before"));
				
				AClass randomClass = AClassFactory.getType(Random.class);
				LocalVariable random = this.var("random", randomClass, this.new_(randomClass, Value.value(1L)));
				if_(new KernelIF(call(random, "nextBoolean")){
					@Override
					public void body() {
						callOrig();
					}

				}).else_(new KernelElse(){
					@Override
					public void body() {
						call(out, "println", Value.value("call self"));
					}
					
				});
				call(out, "println", Value.value("after"));
				return_();
			}
			
		});
		
		byModifyModifer.modifyMethod("String", null, new KernelModifiedMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				call(out, "println", Value.value("before"));
				LocalVariable lv = this.var(getOrigReturnType(), callOrig());
				call(out, "println", Value.value("after"));	
				return_(lv);
			}
			
		});
		byModifyModifer.setClassOutPutPath(classOutPutPath);
		Class<?> ByModify = byModifyModifer.startup();
		
        ClassCreator childCreator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.create.CreateClassAndExtendExample", ByModify, null);
		
		childCreator.createStaticMethod(Opcodes.ACC_PUBLIC, "main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelStaticMethodBody(){

	        @Override
			public void body(LocalVariable... argus) {
	        	call(new_(getMethodOwner()), "helloWorld");
			    return_();
			}
			
		});
		
		childCreator.setClassOutPutPath(classOutPutPath);
		
		//这个就是个开关。前面我们把该创建的方法变量都放到了传送带上了。调用startup
		//启动传送带，将上面的东西一个个处理给我返回一个我们需要的成品（就是class了）
		Class<?> cls = childCreator.startup();
		
		//如果创建的是非枚举类型或者非接口类型则调用main方法
		if(childCreator instanceof ClassCreator){
			try {
				cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		
	}

}
