package sample.operators;


import sample.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class AssignmentGenerate extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.AssignmentGenerateExample", null, null);

		creator.createStaticMethod(Opcodes.ACC_PUBLIC, "commonMethod", null, null, AClass.STRING_ACLASS, null, new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				return_(Value.value("I'm from commonMethod"));
			}
		});
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.deftype(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				//创建个String变量默认赋值为null
				LocalVariable string = var("string", AClass.STRING_ACLASS, false, null);
				
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
