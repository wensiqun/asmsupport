package cn.wensiqun.asmsupport.core.block.loop;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.control.loop.KernelForEach;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.build.resolver.ClassResolver;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

import java.util.ArrayList;
import java.util.List;

public class ForEachBlockGenerator extends AbstractExample {

	public static void main(String[] args)
    {
		ClassResolver creator = new ClassResolver(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.ForEachBlockGeneratorExample", null, null);
		
		 creator.createMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, "test", null, null, null, null, new KernelMethodBody(){

	            @Override
	            public void body(LocalVariable... argus)
	            {
                    
                    LocalVariable list  = var("list", List.class, new_(ArrayList.class));
                    call(list, "add", val("ForEach "));
                    call(list, "add", val("Test "));
                    for_(new KernelForEach(list){

                        @Override
                        public void body(LocalVariable l) {
                            call(getType(TesterStatics.class), "actuallyPrintln", checkcast(l, String.class));
                        }
                        
                    });
					return_();
	            }
		 });
	        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main",
        		new IClass[]{creator.getClassLoader().getType(String[].class)}, new String[]{"args"}, null, null,
            new KernelMethodBody(){
                @Override
                public void body(LocalVariable... argus) {
                	call(getMethodDeclaringClass(), "test");
                    return_();
                }
        
        });

        generate(creator);
    }
	
}
