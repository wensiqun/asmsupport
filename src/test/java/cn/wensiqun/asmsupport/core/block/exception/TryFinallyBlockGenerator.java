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

public class TryFinallyBlockGenerator extends AbstractExample
{
    public static void main(String[] args)
    {
        final AClass runtime = AClassFactory.getProductClass(RuntimeException.class);
        
        final MyList testMethodNames = new MyList();
        ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.TryFinallyBlockGeneratorExample", null, null);
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryFinally"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                })._finally(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                _return();
            }
        });

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("start"));
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try{"));
                        _try(new TryInternal(){
                            
                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                            }
                            
                        })._finally(new FinallyInternal(){
                            
                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                            }
                            
                        });
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    }"));
                    }
                    
                })._finally(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("end"));
                _return();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryFinally_TryError"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        _throw(_new(runtime));
                    }
                    
                    public void setFinish(boolean f)
                    {
                    	super.setFinish(f);
                    }
                    
                })._finally(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                _return();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerTryError"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        _try(new TryInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                                _throw(_new(runtime));
                            }
                            
                        })._finally(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                            }
                            
                        });
                        
                    }
                    
                })._finally(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                _return();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerFinallyError"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        _try(new TryInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                            }
                            
                        })._finally(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                                _throw(_new(runtime));
                            }
                            
                        });
                        
                    }
                    
                })._finally(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                _return();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterTryError"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        _try(new TryInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                            }
                            
                        })._finally(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                            }
                            
                        });
                        _throw(_new(runtime));
                        
                    }
                    
                })._finally(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                _return();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterFinallyError"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        _try(new TryInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                            }
                            
                        })._finally(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                            }
                            
                        });
                        
                    }
                    
                })._finally(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                        _throw(_new(runtime));
                    }
                    
                });
                _return();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerBothError"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        _try(new TryInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                                _throw(_new(runtime));
                            }
                            
                        })._finally(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                                _throw(_new(runtime));
                            }
                            
                        });
                        
                    }
                    
                })._finally(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                _return();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterBothError"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        _try(new TryInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                            }
                            
                        })._finally(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                            }
                            
                        });
                        _throw(_new(runtime));
                        
                    }
                    
                })._finally(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                        _throw(_new(runtime));
                    }
                    
                });
                _return();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerBothOuterFinallyError"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                _try(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        _try(new TryInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                                _throw(_new(runtime));
                            }
                            
                        })._finally(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                                _throw(_new(runtime));
                            }
                            
                        });
                        
                    }
                    
                })._finally(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        _invokeStatic(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                        _throw(_new(runtime));
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
