package cn.wensiqun.asmsupport.core.block.loop;

import java.util.ArrayList;
import java.util.List;

import sample.AbstractExample;
import cn.wensiqun.asmsupport.core.block.control.loop.ForEachInternal;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class ForEachBlockGenerator extends AbstractExample {

	public static void main(String[] args)
    {
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.ForEachBlockGeneratorExample", null, null);
		
		 creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, "test", null, null, null, null, new StaticMethodBodyInternal(){

	            @Override
	            public void body(LocalVariable... argus)
	            {
                    
                    LocalVariable list  = _var("list", List.class, _new(ArrayList.class));
                    _invoke(list, "add", Value.value("ForEach "));
                    _invoke(list, "add", Value.value("Test "));
                    _for(new ForEachInternal(list){

                        @Override
                        public void body(LocalVariable l) {
                            _invoke(TesterStatics.ATesterStatics, "actuallyPrintln", _checkcast(l, String.class));
                        }
                        
                    });
					_return();
	            }
		 });
	        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
            new StaticMethodBodyInternal(){
                @Override
                public void body(LocalVariable... argus) {
                	_invoke(getMethodOwner(), "test");
                    _return();
                }
        
        });

        generate(creator);
    }
	
}
