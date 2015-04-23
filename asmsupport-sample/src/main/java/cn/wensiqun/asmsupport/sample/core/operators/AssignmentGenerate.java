package cn.wensiqun.asmsupport.sample.core.operators;


import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class AssignmentGenerate extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.AssignmentGenerateExample", null, null);

		creator.createStaticMethod(Opcodes.ACC_PUBLIC, "commonMethod", null, null, AClassFactory.getType(String.class), null, new KernelStaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				return_(Value.value("I'm from commonMethod"));
			}
		});
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelStaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				//创建个String变量默认赋值为null
				LocalVariable string = var("string", AClassFactory.getType(String.class), null);
				
				assign(string, call(getMethodOwner(), "commonMethod"));
				call(systemOut, "println", stradd(Value.value("first asign :"), string));
				
				assign(string, Value.value("second assing value"));
				call(systemOut, "println", stradd(Value.value("second asign :"), string));
				
				return_();
			}
        });
		generate(creator);
	}
}
