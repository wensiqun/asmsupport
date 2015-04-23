package cn.wensiqun.asmsupport.core.block.condition;

import java.util.List;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelElseIF;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelElse;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelIF;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.MyList;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class ConditionBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
        
        final MyList testMethodNames = new MyList();
        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.ConditionBlockGeneratorExample", null, null);
        
        creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, testMethodNames.put("test"), new AClass[]{AClassFactory.getType(String.class)}, new String[]{"str"}, null, null, new KernelStaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
            	final LocalVariable str = argus[0];
                if_(new KernelIF(call(str, "startsWith", Value.value("A"))){

					@Override
					public void body() {
	                    call(TesterStatics.ATesterStatics, 
	                    		"actuallyPrintln", Value.value("    startsWith A!"));
					}
                	
                });
                

                if_(new KernelIF(call(str, "startsWith", Value.value("B"))){

					@Override
					public void body() {
	                    call(TesterStatics.ATesterStatics, 
	                    		"actuallyPrintln", Value.value("    startsWith B!"));
					}
                	
                });
                

                if_(new KernelIF(call(str, "startsWith", Value.value("C"))){

					@Override
					public void body() {
	                    call(TesterStatics.ATesterStatics, 
	                    		"actuallyPrintln", Value.value("    startsWith C!"));
					}
                	
                });
                
                if_(new KernelIF(eq(Value.value(1), call(str, "length"))){

					@Override
					public void body() {
	                    call(TesterStatics.ATesterStatics, 
	                    		"actuallyPrintln", Value.value("    length is 1!"));
					}
                	
                }).else_(new KernelElse(){

					@Override
					public void body() {
	                    call(TesterStatics.ATesterStatics, 
	                    		"actuallyPrintln", Value.value("    length is not 1!"));
					}
                	
                });
                
                if_(new KernelIF(eq(Value.value(2), call(str, "length"))){

					@Override
					public void body() {
	                    call(TesterStatics.ATesterStatics, 
	                    		"actuallyPrintln", Value.value("    length is 2!"));
					}
                	
                }).else_(new KernelElse(){

					@Override
					public void body() {
	                    call(TesterStatics.ATesterStatics, 
	                    		"actuallyPrintln", Value.value("    length is not 2!"));
					}
                	
                });
                
                if_(new KernelIF(eq(Value.value(3), call(str, "length"))){

					@Override
					public void body() {
	                    call(TesterStatics.ATesterStatics, 
	                    		"actuallyPrintln", Value.value("    length is 3!"));
					}
                	
                }).else_(new KernelElse(){

					@Override
					public void body() {
	                    call(TesterStatics.ATesterStatics, 
	                    		"actuallyPrintln", Value.value("    length is not 3!"));
					}
                	
                });
                
                
                if_(new KernelIF(call(str, "endsWith", Value.value("Z"))){

					@Override
					public void body() {
						
						 call(TesterStatics.ATesterStatics, 
								 "actuallyPrintln", Value.value("    endsWith Z!"));
						 
						 if_(new KernelIF(call(str, "startsWith", Value.value("A"))){

							@Override
							public void body() {

								 call(TesterStatics.ATesterStatics, 
										 "actuallyPrintln", Value.value("        startsWith A!"));
								
								 if_(new KernelIF(eq(call(str, "length"), Value.value(2))){

									@Override
									public void body() {
										call(TesterStatics.ATesterStatics, 
												 "actuallyPrintln", Value.value("            length is 2!"));
									}
										 
							    }).elseif(new KernelElseIF(eq(call(str, "length"), Value.value(3))){

									@Override
									public void body() {
										call(TesterStatics.ATesterStatics, 
												 "actuallyPrintln", Value.value("            length is 3!"));
										
									}
							    	
							    }).else_(new KernelElse(){

									@Override
									public void body() {
										call(TesterStatics.ATesterStatics, 
												 "actuallyPrintln", Value.value("            length is Other!"));
									}
							    	
							    });
							}
							 
						 });
						 
						 if_(new KernelIF(call(str, "startsWith", Value.value("B"))){

							@Override
							public void body() {
								call(TesterStatics.ATesterStatics, 
										 "actuallyPrintln", Value.value("        startsWith B!"));
								 
								if_(new KernelIF(eq(call(str, "length"), Value.value(2))){

									@Override
									public void body() {
										call(TesterStatics.ATesterStatics, 
												 "actuallyPrintln", Value.value("            length is 2!"));
										if_(new KernelIF(eq(call(str, "charAt", Value.value(1)), Value.value('1'))){

											@Override
											public void body() {
												call(TesterStatics.ATesterStatics, 
														 "actuallyPrintln", Value.value("                charAt 1 is '1'!"));
											}
											
										}).else_(new KernelElse(){

											@Override
											public void body() {
												call(TesterStatics.ATesterStatics, 
														 "actuallyPrintln", Value.value("                charAt 1 is 'Other'!"));
											}
											
										});
									}
									
								}).elseif(new KernelElseIF(eq(call(str, "length"), Value.value(3))){

									@Override
									public void body() {

										call(TesterStatics.ATesterStatics, 
												 "actuallyPrintln", Value.value("            length is 3!"));
										
										if_(new KernelIF(eq(call(str, "charAt", Value.value(1)), Value.value('1'))){

											@Override
											public void body() {
												call(TesterStatics.ATesterStatics, 
														 "actuallyPrintln", Value.value("                charAt 1 is '1'!"));
											}
											
										}).elseif(new KernelElseIF(eq(call(str, "charAt", Value.value(1)), Value.value('2'))){

											@Override
											public void body() {
												call(TesterStatics.ATesterStatics, 
														 "actuallyPrintln", Value.value("                charAt 1 is '2'!"));
											}
											
										}).elseif(new KernelElseIF(eq(call(str, "charAt", Value.value(1)), Value.value('3'))){

											@Override
											public void body() {
												call(TesterStatics.ATesterStatics, 
														 "actuallyPrintln", Value.value("                charAt 1 is '3'!"));
											}
											
										}).else_(new KernelElse(){

											@Override
											public void body() {
												call(TesterStatics.ATesterStatics, 
														 "actuallyPrintln", Value.value("                charAt 1 is 'Other'!"));
											}
											
										});
									}
									
								}).else_(new KernelElse(){

									@Override
									public void body() {
										call(TesterStatics.ATesterStatics, 
												 "actuallyPrintln", Value.value("            length is Other!"));
									}
									
								});
								
							}
							 
						 });
					}
                	
                }).elseif(new KernelElseIF(call(str, "endsWith", Value.value("Y"))){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, 
								 "actuallyPrintln", Value.value("    endsWith Y!"));
						if_(new KernelIF(call(str, "startsWith", Value.value("A"))){

							@Override
							public void body() {
								call(TesterStatics.ATesterStatics, 
										 "actuallyPrintln", Value.value("        startsWith A!"));
							}
							
						});
					
						if_(new KernelIF(call(str, "startsWith", Value.value("B"))){

							@Override
							public void body() {
								call(TesterStatics.ATesterStatics, 
										 "actuallyPrintln", Value.value("        startsWith B!"));
							}
							
						});
					}
                	
                }).else_(new KernelElse(){

					@Override
					public void body() {
						call(TesterStatics.ATesterStatics, 
								 "actuallyPrintln", Value.value("    endsWith Other!"));
						
						if_(new KernelIF(call(str, "endsWith", Value.value("X"))){

							@Override
							public void body() {

								call(TesterStatics.ATesterStatics, 
										 "actuallyPrintln", Value.value("        endsWith X!"));
							}
							
						}).elseif(new KernelElseIF(call(str, "endsWith", Value.value("W"))){

							@Override
							public void body() {

								call(TesterStatics.ATesterStatics, 
										 "actuallyPrintln", Value.value("        endsWith W!"));
							}
							
						}).else_(new KernelElse(){

							@Override
							public void body() {
								call(TesterStatics.ATesterStatics, 
										 "actuallyPrintln", Value.value("        endsWith Other!"));
								
								if_(new KernelIF(call(str, "endsWith", Value.value("V"))){

									@Override
									public void body() {

										call(TesterStatics.ATesterStatics, 
												 "actuallyPrintln", Value.value("            endsWith V!"));
									}
									
								}).elseif(new KernelElseIF(call(str, "endsWith", Value.value("U"))){

									@Override
									public void body() {

										call(TesterStatics.ATesterStatics, 
												 "actuallyPrintln", Value.value("            endsWith U!"));
									}
									
								}).else_(new KernelElse(){

									@Override
									public void body() {
										call(TesterStatics.ATesterStatics, 
												 "actuallyPrintln", Value.value("            endsWith Other!"));
										
										
										
									}
									
								});
								
							}
							
						});
						
					}
                	
                });
                	
                	
                
                return_();
            }
        });
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
            new KernelStaticMethodBody(){
                @Override
                public void body(LocalVariable... argus) {
                	List<String> list = ConditionBlockGeneratorSample.allPossiable();
                	for(String str : list)
                	{
                		call(getMethodOwner(), "test", Value.value(str));
                	}
                    return_();
                }
        
        });
        
        generate(creator);
    }
    
}
