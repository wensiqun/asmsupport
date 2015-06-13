package cn.wensiqun.asmsupport.core.block.loop;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.control.loop.KernelForEach;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;

public class ForEachBlockGenerator extends AbstractExample {

	public static void main(String[] args)
    {
		ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.ForEachBlockGeneratorExample", null, null);
		
		 creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, "test", null, null, null, null, new KernelStaticMethodBody(){

	            @Override
	            public void body(LocalVariable... argus)
	            {
                    
                    LocalVariable list  = var("list", List.class, new_(ArrayList.class));
                    call(list, "add", Value.value("ForEach "));
                    call(list, "add", Value.value("Test "));
                    for_(new KernelForEach(list){

                        @Override
                        public void body(LocalVariable l) {
                            call(TesterStatics.ATesterStatics, "actuallyPrintln", checkcast(l, String.class));
                        }
                        
                    });
					return_();
	            }
		 });
	        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
            new KernelStaticMethodBody(){
                @Override
                public void body(LocalVariable... argus) {
                	call(getMethodDeclaringClass(), "test");
                    return_();
                }
        
        });

        generate(creator);
    }
	
}
