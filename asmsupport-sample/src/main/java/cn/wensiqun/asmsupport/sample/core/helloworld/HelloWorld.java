package cn.wensiqun.asmsupport.sample.core.helloworld;

import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.build.resolver.ClassResolver;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

/**
 * Create a "example.generated.HelloWorld" class and contain a main method.
 * Run the main method will be print a line "Hello World"
 */
public class HelloWorld extends AbstractExample{

	public static void main(String[] args) {
		ClassResolver creator = new ClassResolver(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.helloworld.HelloWorldExample", null, null);
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new IClass[]{classLoader.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						call(systemOut, "println", val("Hello World"));
						//don't forget return.
						return_();
					}
			
		});
		generate(creator);
	}

}
