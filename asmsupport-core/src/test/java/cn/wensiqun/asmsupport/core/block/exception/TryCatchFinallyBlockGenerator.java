package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelCatch;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelFinally;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelTry;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.build.resolver.ClassResolver;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.MyList;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public class TryCatchFinallyBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
        
        final MyList testMethodNames = new MyList();
        ClassResolver creator = new ClassResolver(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.TryCatchFinallyBlockGeneratorExample", null, null);

        final IClass runtime = creator.getClassLoader().getType(RuntimeException.class);
        final IClass nullpointer = creator.getClassLoader().getType(NullPointerException.class);
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "runtimeException", null, null, null, null,  new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(runtime));
            }
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "exception", null, null, null, 
        		new IClass[]{creator.getClassLoader().getType(Exception.class)},  new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(getType(Exception.class)));
            }
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_tryDirectException"), null, null, null, null,  new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(getType(TesterStatics.class), "actuallyPrintln", val("Root"));
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Try"));
                        throw_(new_(getType(Exception.class)));
				    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Catch"));
                    }
                    
                }).finally_(new KernelFinally(){

					@Override
					public void body() {
						call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Finally"));
					}
                	
                });
                return_();
            }
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_runtimeInTryNoSuitableCatch"), null, null, null, null,  new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(getType(TesterStatics.class), "actuallyPrintln", val("Root"));
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Try"));
                        throw_(new_(runtime));
				    }
                    
                }).catch_(new KernelCatch(nullpointer){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Catch"));
                    }
                    
                }).finally_(new KernelFinally(){

					@Override
					public void body() {
						call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Finally"));
					}
                	
                });
                return_();
            }
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_nestedTryCatchInFinally"), null, null, null, null,  new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(getType(TesterStatics.class), "actuallyPrintln", val("Root"));
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Try"));
                        throw_(new_(runtime));
				    }
                    
                }).catch_(new KernelCatch(nullpointer){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Catch"));
                    }
                    
                }).finally_(new KernelFinally(){

					@Override
					public void body() {
						call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Finally"));
						try_(new KernelTry(){

							@Override
							public void body() {
								call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Try"));
							    call(getMethodDeclaringClass(), "exception");
							}
							
						}).catch_(new KernelCatch(getType(Exception.class)){

							@Override
							public void body(LocalVariable e) {
								call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch"));
							}
							
						});
						
					}
                	
                });
                return_();
            }
        });
        
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_tryMethodException"), null, null, null, null,  new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(getType(TesterStatics.class), "actuallyPrintln", val("Root"));
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Try"));
                        call(getMethodDeclaringClass(), "runtimeException");
				    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Catch"));
                    }
                    
                }).finally_(new KernelFinally(){

					@Override
					public void body() {
						call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Finally"));
					}
                	
                });
                return_();
            }
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_catchDirectException"), null, null, null, null,  new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(getType(TesterStatics.class), "actuallyPrintln", val("Root"));
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Try"));
                        throw_(new_(getType(Exception.class)));
				    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Catch"));
                        try_(new KernelTry(){

							@Override
							public void body() {
								call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Try"));
		                        throw_(new_(getType(Exception.class)));
							}
                        	
                        }).catch_(new KernelCatch(getType(Exception.class)){

							@Override
							public void body(LocalVariable e) {
								call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch"));
		                        try_(new KernelTry(){

									@Override
									public void body() {
										call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Try"));
				                        call(getMethodDeclaringClass(), "exception");
									}
		                        	
		                        }).catch_(new KernelCatch(runtime){

									@Override
									public void body(LocalVariable e) {
										call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Catch(RuntimeException)"));
									}
		                        	
		                        }).catch_(new KernelCatch(getType(Exception.class)){

									@Override
									public void body(LocalVariable e) {
										call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Catch(Exception)"));
										throw_(new_(runtime));
									}
		                        	
		                        });
							}
                        	
                        });
                    }
                    
                }).finally_(new KernelFinally(){

					@Override
					public void body() {
						call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Finally"));
                        this.try_(new KernelTry(){

							@Override
							public void body() {
								call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Try"));
								throw_(new_(runtime));
							}
                        	
                        }).catch_(new KernelCatch(runtime){

							@Override
							public void body(LocalVariable e) {
								call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch(RuntimeException)"));
							}
                        	
                        }).catch_(new KernelCatch(getType(Exception.class)){

							@Override
							public void body(LocalVariable e) {
								call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch(Exception)"));
		                        
							}
                        	
                        });
					}
                	
                });
                return_();
            }
        });
        
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("complexTryCatchFinally"), null, null, null, null,  new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(getType(TesterStatics.class), "actuallyPrintln", val("Root"));
                
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Try"));
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Try"));
                                throw_(new_(getType(Exception.class)));
                            }
                            
                        }).catch_(new KernelCatch(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch(RuntimeException)"));
                                return_();
                            }
                            
                        }).catch_(new KernelCatch(getType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch(Exception)"));
                                call(getMethodDeclaringClass(), "runtimeException");
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body() {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Finally"));
                            }
                            
                        });
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Catch(RuntimeException)"));
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Try"));
                            }
                            
                        }).catch_(new KernelCatch(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch(RuntimeException)"));
                            }
                            
                        }).catch_(new KernelCatch(getType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch(Exception)"));
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body() {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Finally"));
                            }
                            
                        });
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Catch(Exception)"));
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body() {

                        call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Finally"));
                        
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Try"));
                                try_(new KernelTry(){

                                    @Override
                                    public void body()
                                    {
                                        call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Try"));
                                    }
                                    
                                }).catch_(new KernelCatch(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Catch(RuntimeException)"));
                                    }
                                });
                                throw_(new_(runtime));
                            }
                            
                        }).catch_(new KernelCatch(getType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Exception(Exception)"));
                            }
                            
                        });
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    ===="));
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Try"));
                                try_(new KernelTry(){

                                    @Override
                                    public void body()
                                    {
                                        call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Try"));
                                        throw_(new_(runtime));
                                    }
                                    
                                }).catch_(new KernelCatch(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Catch(RuntimeException)"));
                                        throw_(e);
                                    }
                                    
                                }).catch_(new KernelCatch(getType(Exception.class)){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Catch(Exception)"));
                                    }
                                    
                                });
                            }
                            
                        }).catch_(new KernelCatch(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch(RuntimeException)"));
                                return_();
                            }
                            
                        }).catch_(new KernelCatch(getType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch(Exception)"));
                                try_(new KernelTry(){

                                    @Override
                                    public void body()
                                    {
                                        call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Try"));
                                    }
                                    
                                }).catch_(new KernelCatch(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Catch(RuntimeException)"));
                                    }
                                    
                                }).catch_(new KernelCatch(getType(Exception.class)){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Catch(Exception)"));
                                    }
                                    
                                }).finally_(new KernelFinally(){

                                    @Override
                                    public void body() {
                                        call(getType(TesterStatics.class), "actuallyPrintln", val("            |-Finally"));
                                    }
                                    
                                });
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body() {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Finally"));
                            }
                            
                        });
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
        block.call(block.getType(TesterStatics.class), "actuallyPrintln", block.val("=======" + methodName));
        block.try_(new KernelTry(){

            @Override
            public void body()
            {
                call(getMethodDeclaringClass(), methodName);
            }
            
        }).catch_(new KernelCatch(block.getType(Throwable.class)){

            @Override
            public void body(LocalVariable e)
            {}
            
        });
    }
    
}
