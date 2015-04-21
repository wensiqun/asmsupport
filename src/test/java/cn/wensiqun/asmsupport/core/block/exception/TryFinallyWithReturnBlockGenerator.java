package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.FinallyInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.TryInternal;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.MyList;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class TryFinallyWithReturnBlockGenerator extends AbstractExample
{
    public static void main(String[] args)
    {
        final AClass runtime = AClassFactory.getType(RuntimeException.class);
        
        final MyList testMethodNames = new MyList();
        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.TryFinallyWithReturnBlockGeneratorExample", null, null);
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryFinally"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).finally_(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                return_();
            }
        });

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("start"));
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try{"));
                        try_(new TryInternal(){
                            
                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                            }
                            
                        }).finally_(new FinallyInternal(){
                            
                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                            }
                            
                        });
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    }"));
                    }
                    
                }).finally_(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("end"));
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryFinally_TryReturn"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        return_();
                    }
                    
                    public void setFinish(boolean f)
                    {
                    	super.setFinish(f);
                    }
                    
                }).finally_(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerTryReturn"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        try_(new TryInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                                return_();
                            }
                            
                        }).finally_(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                            }
                            
                        });
                        
                    }
                    
                }).finally_(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerFinallyReturn"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        try_(new TryInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                            }
                            
                        }).finally_(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                                return_();
                            }
                            
                        });
                        
                    }
                    
                }).finally_(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterTryReturn"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        try_(new TryInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                            }
                            
                        }).finally_(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                            }
                            
                        });
                        return_();
                        
                    }
                    
                }).finally_(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterFinallyReturn"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        try_(new TryInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                            }
                            
                        }).finally_(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                            }
                            
                        });
                        
                    }
                    
                }).finally_(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                        return_();
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerBothReturn"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        try_(new TryInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                                return_();
                            }
                            
                        }).finally_(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                                return_();
                            }
                            
                        });
                        
                    }
                    
                }).finally_(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterBothReturn"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        try_(new TryInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                            }
                            
                        }).finally_(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                            }
                            
                        });
                        return_();
                        
                    }
                    
                }).finally_(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                        return_();
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerBothOuterFinallyReturn"), null, null, null, null,  new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        
                        try_(new TryInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        try_inner"));
                                return_();
                            }
                            
                        }).finally_(new FinallyInternal(){

                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        finally_inner"));
                                return_();
                            }
                            
                        });
                        
                    }
                    
                }).finally_(new FinallyInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    finally"));
                        return_();
                    }
                    
                });
                return_();
            }
            
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
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
            
        }).catch_(new CatchInternal(AClassFactory.getType(Throwable.class)){

            @Override
            public void body(LocalVariable e)
            {}
            
        });
    }
    
}
