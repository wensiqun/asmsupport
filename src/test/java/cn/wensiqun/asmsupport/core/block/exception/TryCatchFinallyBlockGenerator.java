package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.classes.control.exception.CatchInternal;
import cn.wensiqun.asmsupport.core.block.classes.control.exception.FinallyInternal;
import cn.wensiqun.asmsupport.core.block.classes.control.exception.TryInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.MyList;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import example.AbstractExample;

public class TryCatchFinallyBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
        final AClass runtime = AClassFactory.getProductClass(RuntimeException.class);
        final AClass nullpointer = AClassFactory.getProductClass(NullPointerException.class);
        
        final MyList testMethodNames = new MyList();
        ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.TryCatchFinallyBlockGeneratorExample", null, null);
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "runtimeException", null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _throw(_new(runtime));
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "exception", null, null, null, new AClass[]{AClass.EXCEPTION_ACLASS},  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _throw(_new(AClass.EXCEPTION_ACLASS));
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_tryDirectException"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        _throw(_new(AClass.EXCEPTION_ACLASS));
				    }
                    
                })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                })._finally(new FinallyInternal(){

					@Override
					public void body() {
						_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
					}
                	
                });
                _return();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_runtimeInTryNoSuitableCatch"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        _throw(_new(runtime));
				    }
                    
                })._catch(new CatchInternal(nullpointer){

                    @Override
                    public void body(LocalVariable e)
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                })._finally(new FinallyInternal(){

					@Override
					public void body() {
						_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
					}
                	
                });
                _return();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_nestedTryCatchInFinally"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        _throw(_new(runtime));
				    }
                    
                })._catch(new CatchInternal(nullpointer){

                    @Override
                    public void body(LocalVariable e)
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                })._finally(new FinallyInternal(){

					@Override
					public void body() {
						_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
						_try(new TryInternal(){

							@Override
							public void body() {
								_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
							    _invokeStatic(getMethodOwner(), "exception");
							}
							
						})._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

							@Override
							public void body(LocalVariable e) {
								_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch"));
							}
							
						});
						
					}
                	
                });
                _return();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_tryMethodException"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        _invokeStatic(getMethodOwner(), "runtimeException");
				    }
                    
                })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                    }
                    
                })._finally(new FinallyInternal(){

					@Override
					public void body() {
						_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
					}
                	
                });
                _return();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchFinally_catchDirectException"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        _throw(_new(AClass.EXCEPTION_ACLASS));
				    }
                    
                })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                        _try(new TryInternal(){

							@Override
							public void body() {
								_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
		                        _throw(_new(AClass.EXCEPTION_ACLASS));
							}
                        	
                        })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

							@Override
							public void body(LocalVariable e) {
								_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch"));
		                        _try(new TryInternal(){

									@Override
									public void body() {
										_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
				                        _invokeStatic(getMethodOwner(), "exception");
									}
		                        	
		                        })._catch(new CatchInternal(runtime){

									@Override
									public void body(LocalVariable e) {
										_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
									}
		                        	
		                        })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

									@Override
									public void body(LocalVariable e) {
										_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(Exception)"));
										_throw(_new(runtime));
									}
		                        	
		                        });
							}
                        	
                        });
                    }
                    
                })._finally(new FinallyInternal(){

					@Override
					public void body() {
						_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
                        this._try(new TryInternal(){

							@Override
							public void body() {
								_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
								_throw(_new(runtime));
							}
                        	
                        })._catch(new CatchInternal(runtime){

							@Override
							public void body(LocalVariable e) {
								_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
							}
                        	
                        })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

							@Override
							public void body(LocalVariable e) {
								_invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
		                        
							}
                        	
                        });
					}
                	
                });
                _return();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("complexTryCatchFinally"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                        _try(new TryInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                _throw(_new(AClass.EXCEPTION_ACLASS));
                            }
                            
                        })._catch(new CatchInternal(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
                                _return();
                            }
                            
                        })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

                            @Override
                            public void body(LocalVariable e)
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
                                _invokeStatic(getMethodOwner(), "runtimeException");
                            }
                            
                        })._finally(new FinallyInternal(){

                            @Override
                            public void body() {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Finally"));
                            }
                            
                        });
                    }
                    
                })._catch(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch(RuntimeException)"));
                        _try(new TryInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                            }
                            
                        })._catch(new CatchInternal(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
                            }
                            
                        })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

                            @Override
                            public void body(LocalVariable e)
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
                            }
                            
                        })._finally(new FinallyInternal(){

                            @Override
                            public void body() {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Finally"));
                            }
                            
                        });
                    }
                    
                })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch(Exception)"));
                    }
                    
                })._finally(new FinallyInternal(){

                    @Override
                    public void body() {

                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Finally"));
                        
                        _try(new TryInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                _try(new TryInternal(){

                                    @Override
                                    public void body()
                                    {
                                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
                                    }
                                    
                                })._catch(new CatchInternal(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
                                    }
                                });
                                _throw(_new(runtime));
                            }
                            
                        })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

                            @Override
                            public void body(LocalVariable e)
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Exception(Exception)"));
                            }
                            
                        });
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    ===="));
                        _try(new TryInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                _try(new TryInternal(){

                                    @Override
                                    public void body()
                                    {
                                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
                                        _throw(_new(runtime));
                                    }
                                    
                                })._catch(new CatchInternal(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
                                        _throw(e);
                                    }
                                    
                                })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(Exception)"));
                                    }
                                    
                                });
                            }
                            
                        })._catch(new CatchInternal(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
                                _return();
                            }
                            
                        })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

                            @Override
                            public void body(LocalVariable e)
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
                                _try(new TryInternal(){

                                    @Override
                                    public void body()
                                    {
                                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Try"));
                                    }
                                    
                                })._catch(new CatchInternal(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(RuntimeException)"));
                                    }
                                    
                                })._catch(new CatchInternal(AClass.EXCEPTION_ACLASS){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Catch(Exception)"));
                                    }
                                    
                                })._finally(new FinallyInternal(){

                                    @Override
                                    public void body() {
                                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("            |-Finally"));
                                    }
                                    
                                });
                            }
                            
                        })._finally(new FinallyInternal(){

                            @Override
                            public void body() {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Finally"));
                            }
                            
                        });
                    }
                    
                });
                _return();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
           new StaticMethodBodyInternal(){
                @Override
                public void body(LocalVariable... argus) {
                    for(String name : testMethodNames)
                    {
                        noExceptionCall(this, name);
                    }
                    _return();
                }
        
        });
        
        generate(creator);
    }
    
    
    private static void noExceptionCall(ProgramBlockInternal block, final String methodName)
    {
        block._invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("=======" + methodName));
        block._try(new TryInternal(){

            @Override
            public void body()
            {
                _invokeStatic(getMethodOwner(), methodName);
            }
            
        })._catch(new CatchInternal(AClass.THROWABLE_ACLASS){

            @Override
            public void body(LocalVariable e)
            {}
            
        });
    }
    
}
