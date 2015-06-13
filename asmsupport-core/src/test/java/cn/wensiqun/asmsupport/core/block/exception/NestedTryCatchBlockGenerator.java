package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelCatch;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelTry;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.MyList;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;

public class NestedTryCatchBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
        final AClass runtime = AClassFactory.getType(RuntimeException.class);
        
        final MyList testMethodNames = new MyList();
        ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.NestedTryCatchBlockGeneratorExample", null, null);
        
        
        creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, testMethodNames.put("fullTryCatch1"), null, null, null, null,
            new KernelStaticMethodBody(){
                @Override
                public void body(LocalVariable... argus) {
                    call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                    try_(new KernelTry(){
                        @Override
                        public void body()
                        {
                            call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                            try_(new KernelTry(){

                                @Override
                                public void body()
                                {
                                    call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                    throw_(new_(AClassFactory.getType(Exception.class)));
                                }
                                
                            }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                                @Override
                                public void body(LocalVariable e)
                                {
                                    call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch"));
                                    throw_(e);
                                }
                                
                            });
                        }
                    }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                        @Override
                        public void body(LocalVariable e)
                        {
                            call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch"));
                        }
                        
                    });
                    call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("End"));
                    return_();
                }
        
        });
        
        
        creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, testMethodNames.put("fullTryCatch2"), null, null, null, null,
                new KernelStaticMethodBody(){
                    @Override
                    public void body(LocalVariable... argus) {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                        try_(new KernelTry(){
                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                                try_(new KernelTry(){

                                    @Override
                                    public void body()
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                        throw_(new_(AClassFactory.getType(Exception.class)));
                                    }
                                    
                                }).catch_(new KernelCatch(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
                                    }
                                    
                                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

									@Override
									public void body(LocalVariable e) {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
                                        throw_(new_(runtime));
									}
                                	
                                });
                            }
                        }).catch_(new KernelCatch(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch(RuntimeException)"));
                                try_(new KernelTry(){

									@Override
									public void body() {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                        throw_(new_(AClassFactory.getType(Exception.class)));
									}
                                	
                                }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

									@Override
									public void body(LocalVariable e) {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
									}
                                	
                                });
                            }
                            
                        }).catch_(new KernelCatch(AClassFactory.getType(Exception.class)){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch(Exception)"));
                            }
                            
                        });
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("End"));
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
