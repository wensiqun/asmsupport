package cn.wensiqun.asmsupport.sample.core.helloworld;

import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;

/**
 * Create a "example.generated.HelloWorld" class and contain a main method.
 * Run the main method will be print a line "Hello World"
 */
public class HelloWorld extends AbstractExample{

	public static void main(String[] args) {
		ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.helloworld.HelloWorldExample", null, null);
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelStaticMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						call(systemOut, "println", Value.value("Hello World"));
						//don't forget return.
						return_();
					}
			
		});
		generate(creator);
	}

}
