package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelCatch;
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

public class TryCatchBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
        final AClass runtime = AClassFactory.getType(RuntimeException.class);
        
        final MyList testMethodNames = new MyList();
        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.TryCatchBlockGeneratorExample", null, null);
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "runtimeException", null, null, null, null, new KernelStaticMethodBody(){
        	
            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(runtime));
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "exception", null, null, null, new AClass[]{AClassFactory.getType(Exception.class)}, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                throw_(new_(AClassFactory.getType(Exception.class)));
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_errorBeforePrintInTry"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_errorAfterPrintInTry"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_runtimeExceptionBeforePrintInCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_runtimeExceptionAfterPrintInCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionBeforePrintInTry_runtimeExceptionBeforePrintInCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionBeforePrintInTry_runtimeExceptionAfterPrintInCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });        

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionAfterPrintInTry_runtimeExceptionBeforePrintInCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatch_exceptionAfterPrintInTry_runtimeExceptionAfterPrintInCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeBeforePrintInTry"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeAfterPrintInTry"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });

        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeBeforePrintInExceptionCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_runtimeAfterPrintInExceptionCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        });
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });


        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        });


        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(getMethodDeclaringClass(), "exception");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                });
                return_();
            }
        }); 
        

        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                    }
                    
                });
                return_();
            }
        }); 
        

        
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, testMethodNames.put("tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch"), null, null, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                try_(new KernelTry(){

                    @Override
                    public void body()
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    try"));
                        call(getMethodDeclaringClass(), "exception");
                    }
                    
                }).catch_(new KernelCatch(runtime){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(getMethodDeclaringClass(), "runtimeException");
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    runtime exception"));
                    }
                    
                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                    @Override
                    public void body(LocalVariable e)
                    {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    exception"));
                        call(getMethodDeclaringClass(), "runtimeException");
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
                call(getMethodDeclaringClass(), methodName);
            }
            
        }).catch_(new KernelCatch(AClassFactory.getType(Throwable.class)){

            @Override
            public void body(LocalVariable e)
            {}
            
        });
    }
    
}
