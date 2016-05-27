package cn.wensiqun.asmsupport.sample.core.create;

import cn.wensiqun.asmsupport.core.block.control.condition.KernelElse;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelIF;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelModifiedMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.builder.impl.ClassModifier;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.utils.ASConstants;

import java.util.Random;

public class CreateClassAndExtend extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        String classOutPutPath = ".//target//asmsupport-test-generated";
		final GlobalVariable out = systemOut;
		
		ClassModifier byModifyModifer = new ClassModifier(ByModify.class);
		byModifyModifer.createField("age", Opcodes.ACC_STATIC + Opcodes.ACC_PRIVATE, classLoader.getType(int.class));
		byModifyModifer.createMethod("asmcreate", null,null,null,null, Opcodes.ACC_PUBLIC, new KernelMethodBody(){
			@Override
			public void body(LocalVariable... argus) {
				call(out, "println", val("created by asm"));
				return_();
			}
		});
		
		byModifyModifer.modifyMethod(ASConstants.CLINIT, null, new KernelModifiedMethodBody(){
			@Override
			public void body(LocalVariable... argus) {
				GlobalVariable age = val(getMethodDeclaringClass()).field("age");
				assign(age, val(20));
				this.callOrig();
				GlobalVariable name = val(getMethodDeclaringClass()).field("name");
				assign(name, val("wensiqun"));
				call(out, "println", name);
				return_();
			}
		});
		
		byModifyModifer.modifyMethod("helloWorld", null, new KernelModifiedMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				call(out, "println", val("before"));
				
				IClass randomClass = getType(Random.class);
				LocalVariable random = this.var("random", randomClass, this.new_(randomClass, val(1L)));
				if_(new KernelIF(call(random, "nextBoolean")){
					@Override
					public void body() {
						callOrig();
					}

				}).else_(new KernelElse(){
					@Override
					public void body() {
						call(out, "println", val("call self"));
					}
					
				});
				call(out, "println", val("after"));
				return_();
			}
			
		});
		
		byModifyModifer.modifyMethod("String", null, new KernelModifiedMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				call(out, "println", val("before"));
				LocalVariable lv = this.var(getOrigReturnType(), callOrig());
				call(out, "println", val("after"));	
				return_(lv);
			}
			
		});
		byModifyModifer.setClassOutPutPath(classOutPutPath);
		Class<?> ByModify = byModifyModifer.startup();
		
        ClassBuilderImpl childCreator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.create.CreateClassAndExtendExample", classLoader.getType(ByModify), null);
		
		childCreator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new IClass[]{classLoader.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelMethodBody(){

	        @Override
			public void body(LocalVariable... argus) {
	        	call(new_(getMethodDeclaringClass()), "helloWorld");
			    return_();
			}
			
		});
		
		childCreator.setClassOutPutPath(classOutPutPath);
		
		//这个就是个开关。前面我们把该创建的方法变量都放到了传送带上了。调用startup
		//启动传送带，将上面的东西一个个处理给我返回一个我们需要的成品（就是class了）
		Class<?> cls = childCreator.startup();
		
		//如果创建的是非枚举类型或者非接口类型则调用main方法
		if(childCreator instanceof ClassBuilderImpl){
			try {
				cls.getMethod("main", String[].class).invoke(cls, new Object[]{null});
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		
	}

}
