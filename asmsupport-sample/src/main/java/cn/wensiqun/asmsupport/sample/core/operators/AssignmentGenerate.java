package cn.wensiqun.asmsupport.sample.core.operators;


import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public class AssignmentGenerate extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.AssignmentGenerateExample", null, null);

		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "commonMethod", null, null, classLoader.getType(String.class), null, new KernelMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				return_(val("I'm from commonMethod"));
			}
		});
		
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new IClass[]{classLoader.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				//创建个String变量默认赋值为null
				LocalVariable string = var("string", classLoader.getType(String.class), null);
				
				assign(string, call(getMethodDeclaringClass(), "commonMethod"));
				call(systemOut, "println", stradd(val("first asign :"), string));
				
				assign(string, val("second assing value"));
				call(systemOut, "println", stradd(val("second asign :"), string));
				
				return_();
			}
        });
		generate(creator);
	}
}
