package cn.wensiqun.asmsupport.core.block.exception;

import sample.AbstractExample;
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

public class NestedTryCatchBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
        final AClass runtime = AClassFactory.defType(RuntimeException.class);
        
        final MyList testMethodNames = new MyList();
        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.NestedTryCatchBlockGeneratorExample", null, null);
        
        
        creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, testMethodNames.put("fullTryCatch1"), null, null, null, null,
            new StaticMethodBodyInternal(){
                @Override
                public void body(LocalVariable... argus) {
                    call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                    try_(new TryInternal(){
                        @Override
                        public void body()
                        {
                            call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                            try_(new TryInternal(){

                                @Override
                                public void body()
                                {
                                    call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                    throw_(new_(AClass.EXCEPTION_ACLASS));
                                }
                                
                            }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

                                @Override
                                public void body(LocalVariable e)
                                {
                                    call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch"));
                                    throw_(e);
                                }
                                
                            });
                        }
                    }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

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
                new StaticMethodBodyInternal(){
                    @Override
                    public void body(LocalVariable... argus) {
                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("Root"));
                        try_(new TryInternal(){
                            @Override
                            public void body()
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Try"));
                                try_(new TryInternal(){

                                    @Override
                                    public void body()
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                        throw_(new_(AClass.EXCEPTION_ACLASS));
                                    }
                                    
                                }).catch_(new CatchInternal(runtime){

                                    @Override
                                    public void body(LocalVariable e)
                                    {
                                        call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(RuntimeException)"));
                                    }
                                    
                                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

									@Override
									public void body(LocalVariable e) {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
                                        throw_(new_(runtime));
									}
                                	
                                });
                            }
                        }).catch_(new CatchInternal(runtime){

                            @Override
                            public void body(LocalVariable e)
                            {
                                call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("    |-Catch(RuntimeException)"));
                                try_(new TryInternal(){

									@Override
									public void body() {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Try"));
                                        throw_(new_(AClass.EXCEPTION_ACLASS));
									}
                                	
                                }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

									@Override
									public void body(LocalVariable e) {
										call(TesterStatics.ATesterStatics, "actuallyPrintln", Value.value("        |-Catch(Exception)"));
									}
                                	
                                });
                            }
                            
                        }).catch_(new CatchInternal(AClass.EXCEPTION_ACLASS){

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
