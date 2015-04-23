package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelCatch;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelFinally;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelTry;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.MyList;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class TryCatchFinallyBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
        final AClass runtime = AClassFactory.getType(RuntimeException.class);
        final AClass nullpointer = AClassFactory.getType(NullPointerException.class);
        
        final MyList testMethodNames = new MyList();
        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.TryCatchFinallyBlockGeneratorExample", null, null);
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "runtimeException", null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(runtime));
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "exception", null, null, null, new AClass[]{AClassFactory.getType(Exception.class)},  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(AClassFactory.getType(Exception.class)));
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_tryDirectException"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        throw_(new_(AClassFactory.getType(Exception.class)));
				    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                }).finally_(new KernelFinally(){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
					}
                	
                });
                return_();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_runtimeInTryNoSuitableCatch"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        throw_(new_(runtime));
				    }
                    
                }).catch_(new KernelCatch(nullpointer){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                }).finally_(new KernelFinally(){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
					}
                	
                });
                return_();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_nestedTryCatchInFinally"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        throw_(new_(runtime));
				    }
                    
                }).catch_(new KernelCatch(nullpointer){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                }).finally_(new KernelFinally(){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
						try_(new KernelTry(){

							@Override
							public void body() {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
							    call(getMethodOwner(), "exception");
							}
							
						}).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

							@Override
							public void body(LocalVariable e) {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch"));
							}
							
						});
						
					}
                	
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_tryMethodException"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        call(getMethodOwner(), "runtimeException");
				    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                }).finally_(new KernelFinally(){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
					}
                	
                });
                return_();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_catchDirectException"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        throw_(new_(AClassFactory.getType(Exception.class)));
				    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                        try_(new KernelTry(){

							@Override
							public void body() {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
		                        throw_(new_(AClassFactory.getType(Exception.class)));
							}
                        	
                        }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

							@Override
							public void body(LocalVariable e) {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch"));
		                        try_(new KernelTry(){

									@Override
									public void body() {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
				                        call(getMethodOwner(), "exception");
									}
		                        	
		                        }).catch_(new KernelCatch(runtime){

									@Override
									public void body(LocalVariable e) {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
									}
		                        	
		                        }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

									@Override
									public void body(LocalVariable e) {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(Exception)"));
										throw_(new_(runtime));
									}
		                        	
		                        });
							}
                        	
                        });
                    }
                    
                }).finally_(new KernelFinally(){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
                        this.try_(new KernelTry(){

							@Override
							public void body() {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
								throw_(new_(runtime));
							}
                        	
                        }).catch_(new KernelCatch(runtime){

							@Override
							public void body(LocalVariable e) {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
							}
                        	
                        }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

							@Override
							public void body(LocalVariable e) {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
		                        
							}
                        	
                        });
					}
                	
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("complexTryCatchFinally"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                throw_(new_(AClassFactory.getType(Exception.class)));
                            }
                            
                        }).catch_(new KernelCatch(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
                                return_();
                            }
                            
                        }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
                                call(getMethodOwner(), "runtimeException");
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body() {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Finally"));
                            }
                            
                        });
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch(RuntimeException)"));
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                            }
                            
                        }).catch_(new KernelCatch(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
                            }
                            
                        }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body() {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Finally"));
                            }
                            
                        });
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch(Exception)"));
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body() {

                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
                        
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                try_(new KernelTry(){

                                    @Override
                                    public void body()
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
                                    }
                                    
                                }).catch_(new KernelCatch(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
                                    }
                                });
                                throw_(new_(runtime));
                            }
                            
                        }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Exception(Exception)"));
                            }
                            
                        });
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    ===="));
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                try_(new KernelTry(){

                                    @Override
                                    public void body()
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
                                        throw_(new_(runtime));
                                    }
                                    
                                }).catch_(new KernelCatch(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
                                        throw_(e);
                                    }
                                    
                                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(Exception)"));
                                    }
                                    
                                });
                            }
                            
                        }).catch_(new KernelCatch(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
                                return_();
                            }
                            
                        }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
                                try_(new KernelTry(){

                                    @Override
                                    public void body()
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
                                    }
                                    
                                }).catch_(new KernelCatch(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
                                    }
                                    
                                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(Exception)"));
                                    }
                                    
                                }).finally_(new KernelFinally(){

                                    @Override
                                    public void body() {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Finally"));
                                    }
                                    
                                });
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body() {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Finally"));
                            }
                            
                        });
                    }
                    
                });
                return_();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
           new KernelStaticMethodBody(){
                @Override
                public void body(LocalVariable... argus) {
                    for(String name : testMethodNames)
                    {
                        noExceptionCall(this, name);
                    }
                    return_();
                }
        
        });
        
        generate(creator);
    }
    
    
    private static void noExceptionCall(KernelProgramBlock block, final String methodName)
    {
        block.call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("=======" + methodName));
        block.try_(new KernelTry(){

            @Override
            public void body()
            {
                call(getMethodOwner(), methodName);
            }
            
        }).catch_(new KernelCatch(AClassFactory.getType(Throwable.class)){

            @Override
            public void body(LocalVariable e)
            {}
            
        });
    }
    
}
