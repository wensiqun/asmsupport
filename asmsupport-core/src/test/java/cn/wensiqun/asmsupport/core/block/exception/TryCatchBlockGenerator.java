package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelCatch;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelTry;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.build.resolver.ClassResolver;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.MyList;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public class TryCatchBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
        
        final MyList testMethodNames = new MyList();
        ClassResolver creator = new ClassResolver(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.TryCatchBlockGeneratorExample", null, null);

        final IClass runtime = creator.getClassLoader().getType(RuntimeException.class);
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "runtimeException", null, null, null, null, new KernelMethodBody(){
        	
            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(runtime));
            }
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "exception", null, null, null, new IClass[]{creator.getClassLoader().getType(Exception.class)}, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(getType(Exception.class)));
            }
        });
        
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_errorBeforePrintInTry"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_errorAfterPrintInTry"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_runtimeExceptionBeforePrintInCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_runtimeExceptionAfterPrintInCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });

        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionBeforePrintInTry_runtimeExceptionBeforePrintInCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionBeforePrintInTry_runtimeExceptionAfterPrintInCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });        

        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionAfterPrintInTry_runtimeExceptionBeforePrintInCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionAfterPrintInTry_runtimeExceptionAfterPrintInCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });

        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeBeforePrintInTry"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeAfterPrintInTry"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });
        

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeBeforePrintInExceptionCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeAfterPrintInExceptionCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });
        

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });
        

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });


        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        });


        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        }); 

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        }); 

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        }); 

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getType(TesterStatics.class), "actuallyPrintln", val("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        
        
        
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new IClass[]{creator.getClassLoader().getType(String[].class)}, new String[]{"args"}, null, null,
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
