package example.operators;


import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import example.AbstractExample;

/**
 * return操作对应的就是java代码中的return关键字。分为两种
 * 一种是无返回类型的。一种是有返回类型的
 *
 */
public class ReturnOperatorGenerate extends AbstractExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.ReturnOperatorGenerateExample", null, null);
		
		/* 
		 * 有返回类型的方法
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC, "commonMethod", null, null, AClass.STRING_ACLASS, null, new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_return(Value.value("I'm from commonMethod"));
			}
		});
		
		/* 
		 * 无返回类型的方法
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
				"main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				_invoke(systemOut, "println", _invokeStatic(getMethodOwner(), "commonMethod"));
				_return();
			}
        });
		generate(creator);
	}

}
