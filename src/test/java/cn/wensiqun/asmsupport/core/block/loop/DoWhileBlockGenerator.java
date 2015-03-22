package cn.wensiqun.asmsupport.core.block.loop;

import sample.AbstractExample;
import cn.wensiqun.asmsupport.core.block.control.loop.DoWhileInternal;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class DoWhileBlockGenerator extends AbstractExample {

	public static void main(String[] args)
    {
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.DoWhileBlockGeneratorExample", null, null);
		
		 creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, "test", null, null, null, null, new StaticMethodBodyInternal(){

	            @Override
	            public void body(LocalVariable... argus)
	            {
                    
                    final LocalVariable intVar1  = var("intVar1", AClass.INT_ACLASS, false, Value.value(10));
                    
                    dowhile(new DoWhileInternal(gt(postdec(intVar1), Value.value(0))){

                        @Override
                        public void body() {
                            call(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", call(AClass.STRING_ACLASS, "valueOf", intVar1));
                        }
                        
                    });
                    
                    final LocalVariable intVar2  = var("intVar2", AClass.INT_ACLASS, false, Value.value(10));
                    
                    dowhile(new DoWhileInternal(gt(predec(intVar2), Value.value(0))){

                        @Override
                        public void body() {
                            call(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", call(AClass.STRING_ACLASS, "valueOf", intVar2));
                        }
                        
                    });
	                
                    final LocalVariable byteVar  = var("byteVar", AClass.BYTE_ACLASS, false, Value.value((byte)10));
                    
	            	dowhile(new DoWhileInternal(gt(postdec(byteVar), Value.value(0))){

						@Override
						public void body() {
							call(TesterStatics.ATesterStatics, 
		                    		"actuallyPrintln", call(AClass.STRING_ACLASS, "valueOf", byteVar));
						}
	            		
	            	});
	            	
	            	final LocalVariable doubleVar  = var("doubleVar", AClass.DOUBLE_ACLASS, false, Value.value(10D));
                    
                    dowhile(new DoWhileInternal(gt(predec(doubleVar), Value.value(0))){

                        @Override
                        public void body() {
                            call(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", call(AClass.STRING_ACLASS, "valueOf", doubleVar));
                        }
                        
                    });
                    
                    final LocalVariable shortObj  = var("shortObj", AClass.SHORT_WRAP_ACLASS, false, Value.value((short)10));
                    
                    dowhile(new DoWhileInternal(gt(postdec(shortObj), Value.value((short)0))){

                        @Override
                        public void body() {
                            call(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", call(AClass.STRING_ACLASS, "valueOf", shortObj));
                        }
                        
                    });
                    
                    final LocalVariable longObj  = var("longObj", AClass.LONG_WRAP_ACLASS, false, Value.value(10L));
                    
                    dowhile(new DoWhileInternal(gt(predec(longObj), Value.value(0))){

                        @Override
                        public void body() {
                            call(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", call(AClass.STRING_ACLASS, "valueOf", longObj));
                        }
                        
                    });
                    
                    
					return_();
	            }
		 });
	        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.defType(String[].class)}, new String[]{"args"}, null, null,
            new StaticMethodBodyInternal(){
                @Override
                public void body(LocalVariable... argus) {
                	call(getMethodOwner(), "test");
                    return_();
                }
        
        });

        generate(creator);
    }
	
}
