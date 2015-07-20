package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelCatch;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelTry;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.MyList;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public class NestedTryCatchBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
    	
    	
        
        
        final MyList testMethodNames = new MyList();
        ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.NestedTryCatchBlockGeneratorExample", null, null);
        
        final IClass runtime = creator.getClassLoader().getType(RuntimeException.class);
        
        creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, testMethodNames.put("fullTryCatch1"), null, null, null, null,
            new KernelStaticMethodBody(){
                @Override
                public void body(LocalVariable... argus) {
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
                                
                            }).catch_(new KernelCatch(getType(Exception.class)){

                                @Override
                                public void body(LocalVariable e)
                                {
                                    call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch"));
                                    throw_(e);
                                }
                                
                            });
                        }
                    }).catch_(new KernelCatch(getType(Exception.class)){

                        @Override
                        public void body(LocalVariable e)
                        {
                            call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Catch"));
                        }
                        
                    });
                    call(getType(TesterStatics.class), "actuallyPrintln", val("End"));
                    return_();
                }
        
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, testMethodNames.put("fullTryCatch2"), null, null, null, null,
                new KernelStaticMethodBody(){
                    @Override
                    public void body(LocalVariable... argus) {
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
                                    }
                                    
                                }).catch_(new KernelCatch(getType(Exception.class)){

									@Override
									public void body(LocalVariable e) {
										call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch(Exception)"));
                                        throw_(new_(runtime));
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
									public void body() {
										call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Try"));
                                        throw_(new_(getType(Exception.class)));
									}
                                	
                                }).catch_(new KernelCatch(getType(Exception.class)){

									@Override
									public void body(LocalVariable e) {
										call(getType(TesterStatics.class), "actuallyPrintln", val("        |-Catch(Exception)"));
									}
                                	
                                });
                            }
                            
                        }).catch_(new KernelCatch(getType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(getType(TesterStatics.class), "actuallyPrintln", val("    |-Catch(Exception)"));
                            }
                            
                        });
                        call(getType(TesterStatics.class), "actuallyPrintln", val("End"));
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
