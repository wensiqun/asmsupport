package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal;
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

public class TryCatchBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
        final AClass runtime = AClassFactory.defType(RuntimeException.class);
        
        final MyList testMethodNames = new MyList();
        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.TryCatchBlockGeneratorExample", null, null);
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "runtimeException", null, null, null, null, new StaticMethodBodyInternal(){
        	
            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(runtime));
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "exception", null, null, null, new AClass[]{AClass.EXCEPTION_ACLASS}, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(AClass.EXCEPTION_ACLASS));
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_errorBeforePrintInTry"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_errorAfterPrintInTry"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_runtimeExceptionBeforePrintInCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_runtimeExceptionAfterPrintInCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionBeforePrintInTry_runtimeExceptionBeforePrintInCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionBeforePrintInTry_runtimeExceptionAfterPrintInCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });        

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionAfterPrintInTry_runtimeExceptionBeforePrintInCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionAfterPrintInTry_runtimeExceptionAfterPrintInCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeBeforePrintInTry"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeAfterPrintInTry"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeBeforePrintInExceptionCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeAfterPrintInExceptionCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });


        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });


        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(getMethodOwner(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodOwner(), "runtimeException");
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new TryInternal(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodOwner(), "exception");
                    }
                    
                }).catch_(new CatchInternal(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodOwner(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodOwner(), "runtimeException");
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
            
        }).catch_(new CatchInternal(AClass.THROWABLE_ACLASS){

            @Override
            public void body(LocalVariable e)
            {}
            
        });
    }
    
}
