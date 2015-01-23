package example.operators;


import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import example.AbstractExample;

public class AssignmentGenerate extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.AssignmentGenerateExample", null, null);

		creator.createStaticMethod(Opcodes.ACC_PUBLIC, "commonMethod", null, null, AClass.STRING_ACLASS, null, new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_return(Value.value("I'm from commonMethod"));
			}
		});
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				//创建个String变量默认赋值为null
				LocalVariable string = _createVariable("string", AClass.STRING_ACLASS, false, null);
				
				_assign(string, _invokeStatic(getMethodOwner(), "commonMethod"));
				_invoke(systemOut, "println", _append(Value.value("first asign :"), string));
				
				_assign(string, Value.value("second assing value"));
				_invoke(systemOut, "println", _append(Value.value("second asign :"), string));
				
				_return();
			}
        });
		generate(creator);
	}
}
