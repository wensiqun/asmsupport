package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.FinallyInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.TryInternal;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.MyList;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class TryCatchFinallyBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
        final AClass runtime = AClassFactory.defType(RuntimeException.class);
        final AClass nullpointer = AClassFactory.defType(NullPointerException.class);
        
        final MyList testMethodNames = new MyList();
        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.TryCatchFinallyBlockGeneratorExample", null, null);
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "runtimeException", null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(runtime));
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "exception", null, null, null, new AClass[]{AClassFactory.defType(Exception.class)},  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(AClassFactory.defType(Exception.class)));
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_tryDirectException"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        throw_(new_(AClassFactory.defType(Exception.class)));
				    }
                    
                }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                }).finally_(new FinallyInternal(){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
					}
                	
                });
                return_();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_runtimeInTryNoSuitableCatch"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        throw_(new_(runtime));
				    }
                    
                }).catch_(new CatchInternal(nullpointer){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                }).finally_(new FinallyInternal(){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
					}
                	
                });
                return_();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_nestedTryCatchInFinally"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        throw_(new_(runtime));
				    }
                    
                }).catch_(new CatchInternal(nullpointer){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                }).finally_(new FinallyInternal(){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
						try_(new TryInternal(){

							@Override
							public void body() {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
							    call(getMethodOwner(), "exception");
							}
							
						}).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

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
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_tryMethodException"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        call(getMethodOwner(), "runtimeException");
				    }
                    
                }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                }).finally_(new FinallyInternal(){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
					}
                	
                });
                return_();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_catchDirectException"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        throw_(new_(AClassFactory.defType(Exception.class)));
				    }
                    
                }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                        try_(new TryInternal(){

							@Override
							public void body() {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
		                        throw_(new_(AClassFactory.defType(Exception.class)));
							}
                        	
                        }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

							@Override
							public void body(LocalVariable e) {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch"));
		                        try_(new TryInternal(){

									@Override
									public void body() {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
				                        call(getMethodOwner(), "exception");
									}
		                        	
		                        }).catch_(new CatchInternal(runtime){

									@Override
									public void body(LocalVariable e) {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
									}
		                        	
		                        }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

									@Override
									public void body(LocalVariable e) {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(Exception)"));
										throw_(new_(runtime));
									}
		                        	
		                        });
							}
                        	
                        });
                    }
                    
                }).finally_(new FinallyInternal(){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
                        this.try_(new TryInternal(){

							@Override
							public void body() {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
								throw_(new_(runtime));
							}
                        	
                        }).catch_(new CatchInternal(runtime){

							@Override
							public void body(LocalVariable e) {
								call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
							}
                        	
                        }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

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
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("complexTryCatchFinally"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        try_(new TryInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                throw_(new_(AClassFactory.defType(Exception.class)));
                            }
                            
                        }).catch_(new CatchInternal(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
                                return_();
                            }
                            
                        }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
                                call(getMethodOwner(), "runtimeException");
                            }
                            
                        }).finally_(new FinallyInternal(){

                            @Override
                            public void body() {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Finally"));
                            }
                            
                        });
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch(RuntimeException)"));
                        try_(new TryInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                            }
                            
                        }).catch_(new CatchInternal(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
                            }
                            
                        }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
                            }
                            
                        }).finally_(new FinallyInternal(){

                            @Override
                            public void body() {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Finally"));
                            }
                            
                        });
                    }
                    
                }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch(Exception)"));
                    }
                    
                }).finally_(new FinallyInternal(){

                    @Override
                    public void body() {

                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
                        
                        try_(new TryInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                try_(new TryInternal(){

                                    @Override
                                    public void body()
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
                                    }
                                    
                                }).catch_(new CatchInternal(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
                                    }
                                });
                                throw_(new_(runtime));
                            }
                            
                        }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Exception(Exception)"));
                            }
                            
                        });
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    ===="));
                        try_(new TryInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                try_(new TryInternal(){

                                    @Override
                                    public void body()
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
                                        throw_(new_(runtime));
                                    }
                                    
                                }).catch_(new CatchInternal(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
                                        throw_(e);
                                    }
                                    
                                }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(Exception)"));
                                    }
                                    
                                });
                            }
                            
                        }).catch_(new CatchInternal(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
                                return_();
                            }
                            
                        }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
                                try_(new TryInternal(){

                                    @Override
                                    public void body()
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
                                    }
                                    
                                }).catch_(new CatchInternal(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
                                    }
                                    
                                }).catch_(new CatchInternal(AClassFactory.defType(Exception.class)){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(Exception)"));
                                    }
                                    
                                }).finally_(new FinallyInternal(){

                                    @Override
                                    public void body() {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Finally"));
                                    }
                                    
                                });
                            }
                            
                        }).finally_(new FinallyInternal(){

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
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.defType(String[].class)}, new String[]{"args"}, null, null,
           new StaticMethodBodyInternal(){
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
    
    
    private static void noExceptionCall(ProgramBlockInternal block, final String methodName)
    {
        block.call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("=======" + methodName));
        block.try_(new TryInternal(){

            @Override
            public void body()
            {
                call(getMethodOwner(), methodName);
            }
            
        }).catch_(new CatchInternal(AClassFactory.defType(Throwable.class)){

            @Override
            public void body(LocalVariable e)
            {}
            
        });
    }
    
}
