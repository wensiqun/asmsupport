package example.operators;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
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
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.ReturnOperatorGenerateExample", null, null);
		
		/* 
		 * 有返回类型的方法
		 */
		creator.createStaticMethod("commonMethod", null, null, AClass.STRING_ACLASS, null, Opcodes.ACC_PUBLIC, new StaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				runReturn(Value.value("I'm from commonMethod"));
			}
		});
		
		/* 
		 * 无返回类型的方法
		 */
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				invoke(systemOut, "println", invokeStatic(getMethodOwner(), "commonMethod"));
				runReturn();
			}
        });
		generate(creator);
	}

}
