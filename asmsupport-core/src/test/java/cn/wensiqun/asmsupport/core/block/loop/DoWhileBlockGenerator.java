package cn.wensiqun.asmsupport.core.block.loop;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.control.loop.KernelDoWhile;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public class DoWhileBlockGenerator extends AbstractExample {

	public static void main(String[] args)
    {
		ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.DoWhileBlockGeneratorExample", null, null);
		
		 creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, "test", null, null, null, null, new KernelStaticMethodBody(){

	            @Override
	            public void body(LocalVariable... argus)
	            {
                    
                    final LocalVariable intVar1  = var("intVar1", getType(int.class), val(10));
                    
                    dowhile(new KernelDoWhile(gt(postdec(intVar1), val(0))){

                        @Override
                        public void body() {
                            call(getType(TesterStatics.class), 
                                    "actuallyPrintln", call(getType(String.class), "valueOf", intVar1));
                        }
                        
                    });
                    
                    final LocalVariable intVar2  = var("intVar2", getType(int.class), val(10));
                    
                    dowhile(new KernelDoWhile(gt(predec(intVar2), val(0))){

                        @Override
                        public void body() {
                            call(getType(TesterStatics.class), 
                                    "actuallyPrintln", call(getType(String.class), "valueOf", intVar2));
                        }
                        
                    });
	                
                    final LocalVariable byteVar  = var("byteVar", getType(byte.class), val((byte)10));
                    
	            	dowhile(new KernelDoWhile(gt(postdec(byteVar), val(0))){

						@Override
						public void body() {
							call(getType(TesterStatics.class), 
		                    		"actuallyPrintln", call(getType(String.class), "valueOf", byteVar));
						}
	            		
	            	});
	            	
	            	final LocalVariable doubleVar  = var("doubleVar", getType(double.class), val(10D));
                    
                    dowhile(new KernelDoWhile(gt(predec(doubleVar), val(0))){

                        @Override
                        public void body() {
                            call(getType(TesterStatics.class), 
                                    "actuallyPrintln", call(getType(String.class), "valueOf", doubleVar));
                        }
                        
                    });
                    
                    final LocalVariable shortObj  = var("shortObj", getType(Short.class), val((short)10));
                    
                    dowhile(new KernelDoWhile(gt(postdec(shortObj), val((short)0))){

                        @Override
                        public void body() {
                            call(getType(TesterStatics.class), 
                                    "actuallyPrintln", call(getType(String.class), "valueOf", shortObj));
                        }
                        
                    });
                    
                    final LocalVariable longObj  = var("longObj", getType(Long.class), val(10L));
                    
                    dowhile(new KernelDoWhile(gt(predec(longObj), val(0))){

                        @Override
                        public void body() {
                            call(getType(TesterStatics.class), 
                                    "actuallyPrintln", call(getType(String.class), "valueOf", longObj));
                        }
                        
                    });
                    
                    
					return_();
	            }
		 });
	        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", 
        		new IClass[]{creator.getClassLoader().getType(String[].class)}, new String[]{"args"}, null, null,
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
