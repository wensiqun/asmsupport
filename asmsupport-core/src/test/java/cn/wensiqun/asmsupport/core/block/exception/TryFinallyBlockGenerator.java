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

public class TryFinallyBlockGenerator extends AbstractExample
{
    public static void main(String[] args)
    {
        
        final MyList testMethodNames = new MyList();
        ClassResolver creator = new ClassResolver(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.TryFinallyBlockGeneratorExample", null, null);

        final IClass runtime = creator.getClassLoader().getType(RuntimeException.class);
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryFinally"), null, null, null, null,  new KernelMethodBody(){

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

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally"), null, null, null, null,  new KernelMethodBody(){

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
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryFinally_TryError"), null, null, null, null,  new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        throw_(new_(runtime));
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
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerTryError"), null, null, null, null,  new KernelMethodBody(){

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
                                throw_(new_(runtime));
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
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerFinallyError"), null, null, null, null,  new KernelMethodBody(){

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
                                throw_(new_(runtime));
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
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterTryError"), null, null, null, null,  new KernelMethodBody(){

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
                        throw_(new_(runtime));
                        
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
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterFinallyError"), null, null, null, null,  new KernelMethodBody(){

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
                        throw_(new_(runtime));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerBothError"), null, null, null, null,  new KernelMethodBody(){

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
                                throw_(new_(runtime));
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        finally_inner"));
                                throw_(new_(runtime));
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
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_OutterBothError"), null, null, null, null,  new KernelMethodBody(){

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
                        throw_(new_(runtime));
                        
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                        throw_(new_(runtime));
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("nestedTryFinally_InnerBothOuterFinallyError"), null, null, null, null,  new KernelMethodBody(){

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
                                throw_(new_(runtime));
                            }
                            
                        }).finally_(new KernelFinally(){

                            @Override
                            public void body()
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("        finally_inner"));
                                throw_(new_(runtime));
                            }
                            
                        });
                        
                    }
                    
                }).finally_(new KernelFinally(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    finally"));
                        throw_(new_(runtime));
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
