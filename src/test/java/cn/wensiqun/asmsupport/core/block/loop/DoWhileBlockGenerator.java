package cn.wensiqun.asmsupport.core.block.loop;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.control.loop.DoWhileInternal;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.clazz.AClass;

public class DoWhileBlockGenerator extends AbstractExample {

	public static void main(String[] args)
    {
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.DoWhileBlockGeneratorExample", null, null);
		
		 creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, "test", null, null, null, null, new StaticMethodBodyInternal(){

	            @Override
	            public void body(LocalVariable... argus)
	            {
                    
                    final LocalVariable intVar1  = var("intVar1", AClassFactory.getType(int.class), Value.value(10));
                    
                    dowhile(new DoWhileInternal(gt(postdec(intVar1), Value.value(0))){

                        @Override
                        public void body() {
                            call(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", call(AClassFactory.getType(String.class), "valueOf", intVar1));
                        }
                        
                    });
                    
                    final LocalVariable intVar2  = var("intVar2", AClassFactory.getType(int.class), Value.value(10));
                    
                    dowhile(new DoWhileInternal(gt(predec(intVar2), Value.value(0))){

                        @Override
                        public void body() {
                            call(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", call(AClassFactory.getType(String.class), "valueOf", intVar2));
                        }
                        
                    });
	                
                    final LocalVariable byteVar  = var("byteVar", AClassFactory.getType(byte.class), Value.value((byte)10));
                    
	            	dowhile(new DoWhileInternal(gt(postdec(byteVar), Value.value(0))){

						@Override
						public void body() {
							call(TesterStatics.ATesterStatics, 
		                    		"actuallyPrintln", call(AClassFactory.getType(String.class), "valueOf", byteVar));
						}
	            		
	            	});
	            	
	            	final LocalVariable doubleVar  = var("doubleVar", AClassFactory.getType(double.class), Value.value(10D));
                    
                    dowhile(new DoWhileInternal(gt(predec(doubleVar), Value.value(0))){

                        @Override
                        public void body() {
                            call(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", call(AClassFactory.getType(String.class), "valueOf", doubleVar));
                        }
                        
                    });
                    
                    final LocalVariable shortObj  = var("shortObj", AClassFactory.getType(Short.class), Value.value((short)10));
                    
                    dowhile(new DoWhileInternal(gt(postdec(shortObj), Value.value((short)0))){

                        @Override
                        public void body() {
                            call(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", call(AClassFactory.getType(String.class), "valueOf", shortObj));
                        }
                        
                    });
                    
                    final LocalVariable longObj  = var("longObj", AClassFactory.getType(Long.class), Value.value(10L));
                    
                    dowhile(new DoWhileInternal(gt(predec(longObj), Value.value(0))){

                        @Override
                        public void body() {
                            call(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", call(AClassFactory.getType(String.class), "valueOf", longObj));
                        }
                        
                    });
                    
                    
					return_();
	            }
		 });
	        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
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
