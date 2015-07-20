package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelCatch;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelFinally;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelTry;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.MyList;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public class TryFinallyWithReturnBlockGenerator extends AbstractExample
{
    public static void main(String[] args)
    {
        
        final MyList testMethodNames = new MyList();
        ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.TryFinallyWithReturnBlockGeneratorExample", null, null);

        final IClass runtime = creator.getClassLoader().getType(RuntimeException.class);
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryFinally"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                    }
                    
                });
                return_();
            }
        });

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                call(getType(TesterStatics.class), "actuallyPrintln", val("start"));
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try{"));
                        try_(new KernelTry(){
                            
                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        try_inner"));
                            }
                            
                        }).finally_(new KernelFinally(){
                            
                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        finally_inner"));
                            }
                            
                        });
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    }"));
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                    }
                    
                });
                call(getType(TesterStatics.class), "actuallyPrintln", val("end"));
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryFinally_TryReturn"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        return_();
                    }
                    
                    public void setFinish(boolean f)
                    {
                    	super.setFinish(f);
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerTryReturn"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        try_inner"));
                                return_();
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        finally_inner"));
                            }
                            
                        });
                        
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerFinallyReturn"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        try_inner"));
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        finally_inner"));
                                return_();
                            }
                            
                        });
                        
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterTryReturn"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        try_inner"));
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        finally_inner"));
                            }
                            
                        });
                        return_();
                        
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterFinallyReturn"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        try_inner"));
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        finally_inner"));
                            }
                            
                        });
                        
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                        return_();
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerBothReturn"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        try_inner"));
                                return_();
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        finally_inner"));
                                return_();
                            }
                            
                        });
                        
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterBothReturn"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        try_inner"));
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        finally_inner"));
                            }
                            
                        });
                        return_();
                        
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                        return_();
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerBothOuterFinallyReturn"), null, null, null, null,  new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        
                        try_(new KernelTry(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        try_inner"));
                                return_();
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        finally_inner"));
                                return_();
                            }
                            
                        });
                        
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                        return_();
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
