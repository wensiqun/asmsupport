package cn.wensiqun.asmsupport.core.block.condition;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelElse;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelElseIF;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelIF;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.MyList;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

import java.util.List;

public class ConditionBlockGenerator extends AbstractExample
{
    
    public static void main(String[] args)
    {
        
        final MyList testMethodNames = new MyList();
        ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.ConditionBlockGeneratorExample", null, null);
        
        creator.createMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, testMethodNames.put("test"),
        		new IClass[]{creator.getClassLoader().getType(String.class)}, new String[]{"str"}, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
            	final LocalVariable str = argus[0];
                if_(new KernelIF(call(str, "startsWith", val("A"))){

					@Override
					public void body() {
	                    call(getType(TesterStatics.class), 
	                    		"actuallyPrintln", val("    startsWith A!"));
					}
                	
                });
                

                if_(new KernelIF(call(str, "startsWith", val("B"))){

					@Override
					public void body() {
	                    call(getType(TesterStatics.class), 
	                    		"actuallyPrintln", val("    startsWith B!"));
					}
                	
                });
                

                if_(new KernelIF(call(str, "startsWith", val("C"))){

					@Override
					public void body() {
	                    call(getType(TesterStatics.class), 
	                    		"actuallyPrintln", val("    startsWith C!"));
					}
                	
                });
                
                if_(new KernelIF(eq(val(1), call(str, "length"))){

					@Override
					public void body() {
	                    call(getType(TesterStatics.class), 
	                    		"actuallyPrintln", val("    length is 1!"));
					}
                	
                }).else_(new KernelElse(){

					@Override
					public void body() {
	                    call(getType(TesterStatics.class), 
	                    		"actuallyPrintln", val("    length is not 1!"));
					}
                	
                });
                
                if_(new KernelIF(eq(val(2), call(str, "length"))){

					@Override
					public void body() {
	                    call(getType(TesterStatics.class), 
	                    		"actuallyPrintln", val("    length is 2!"));
					}
                	
                }).else_(new KernelElse(){

					@Override
					public void body() {
	                    call(getType(TesterStatics.class), 
	                    		"actuallyPrintln", val("    length is not 2!"));
					}
                	
                });
                
                if_(new KernelIF(eq(val(3), call(str, "length"))){

					@Override
					public void body() {
	                    call(getType(TesterStatics.class), 
	                    		"actuallyPrintln", val("    length is 3!"));
					}
                	
                }).else_(new KernelElse(){

					@Override
					public void body() {
	                    call(getType(TesterStatics.class), 
	                    		"actuallyPrintln", val("    length is not 3!"));
					}
                	
                });
                
                
                if_(new KernelIF(call(str, "endsWith", val("Z"))){

					@Override
					public void body() {
						
						 call(getType(TesterStatics.class), 
								 "actuallyPrintln", val("    endsWith Z!"));
						 
						 if_(new KernelIF(call(str, "startsWith", val("A"))){

							@Override
							public void body() {

								 call(getType(TesterStatics.class), 
										 "actuallyPrintln", val("        startsWith A!"));
								
								 if_(new KernelIF(eq(call(str, "length"), val(2))){

									@Override
									public void body() {
										call(getType(TesterStatics.class), 
												 "actuallyPrintln", val("            length is 2!"));
									}
										 
							    }).elseif(new KernelElseIF(eq(call(str, "length"), val(3))){

									@Override
									public void body() {
										call(getType(TesterStatics.class), 
												 "actuallyPrintln", val("            length is 3!"));
										
									}
							    	
							    }).else_(new KernelElse(){

									@Override
									public void body() {
										call(getType(TesterStatics.class), 
												 "actuallyPrintln", val("            length is Other!"));
									}
							    	
							    });
							}
							 
						 });
						 
						 if_(new KernelIF(call(str, "startsWith", val("B"))){

							@Override
							public void body() {
								call(getType(TesterStatics.class), 
										 "actuallyPrintln", val("        startsWith B!"));
								 
								if_(new KernelIF(eq(call(str, "length"), val(2))){

									@Override
									public void body() {
										call(getType(TesterStatics.class), 
												 "actuallyPrintln", val("            length is 2!"));
										if_(new KernelIF(eq(call(str, "charAt", val(1)), val('1'))){

											@Override
											public void body() {
												call(getType(TesterStatics.class), 
														 "actuallyPrintln", val("                charAt 1 is '1'!"));
											}
											
										}).else_(new KernelElse(){

											@Override
											public void body() {
												call(getType(TesterStatics.class), 
														 "actuallyPrintln", val("                charAt 1 is 'Other'!"));
											}
											
										});
									}
									
								}).elseif(new KernelElseIF(eq(call(str, "length"), val(3))){

									@Override
									public void body() {

										call(getType(TesterStatics.class), 
												 "actuallyPrintln", val("            length is 3!"));
										
										if_(new KernelIF(eq(call(str, "charAt", val(1)), val('1'))){

											@Override
											public void body() {
												call(getType(TesterStatics.class), 
														 "actuallyPrintln", val("                charAt 1 is '1'!"));
											}
											
										}).elseif(new KernelElseIF(eq(call(str, "charAt", val(1)), val('2'))){

											@Override
											public void body() {
												call(getType(TesterStatics.class), 
														 "actuallyPrintln", val("                charAt 1 is '2'!"));
											}
											
										}).elseif(new KernelElseIF(eq(call(str, "charAt", val(1)), val('3'))){

											@Override
											public void body() {
												call(getType(TesterStatics.class), 
														 "actuallyPrintln", val("                charAt 1 is '3'!"));
											}
											
										}).else_(new KernelElse(){

											@Override
											public void body() {
												call(getType(TesterStatics.class), 
														 "actuallyPrintln", val("                charAt 1 is 'Other'!"));
											}
											
										});
									}
									
								}).else_(new KernelElse(){

									@Override
									public void body() {
										call(getType(TesterStatics.class), 
												 "actuallyPrintln", val("            length is Other!"));
									}
									
								});
								
							}
							 
						 });
					}
                	
                }).elseif(new KernelElseIF(call(str, "endsWith", val("Y"))){

					@Override
					public void body() {
						call(getType(TesterStatics.class), 
								 "actuallyPrintln", val("    endsWith Y!"));
						if_(new KernelIF(call(str, "startsWith", val("A"))){

							@Override
							public void body() {
								call(getType(TesterStatics.class), 
										 "actuallyPrintln", val("        startsWith A!"));
							}
							
						});
					
						if_(new KernelIF(call(str, "startsWith", val("B"))){

							@Override
							public void body() {
								call(getType(TesterStatics.class), 
										 "actuallyPrintln", val("        startsWith B!"));
							}
							
						});
					}
                	
                }).else_(new KernelElse(){

					@Override
					public void body() {
						call(getType(TesterStatics.class), 
								 "actuallyPrintln", val("    endsWith Other!"));
						
						if_(new KernelIF(call(str, "endsWith", val("X"))){

							@Override
							public void body() {

								call(getType(TesterStatics.class), 
										 "actuallyPrintln", val("        endsWith X!"));
							}
							
						}).elseif(new KernelElseIF(call(str, "endsWith", val("W"))){

							@Override
							public void body() {

								call(getType(TesterStatics.class), 
										 "actuallyPrintln", val("        endsWith W!"));
							}
							
						}).else_(new KernelElse(){

							@Override
							public void body() {
								call(getType(TesterStatics.class), 
										 "actuallyPrintln", val("        endsWith Other!"));
								
								if_(new KernelIF(call(str, "endsWith", val("V"))){

									@Override
									public void body() {

										call(getType(TesterStatics.class), 
												 "actuallyPrintln", val("            endsWith V!"));
									}
									
								}).elseif(new KernelElseIF(call(str, "endsWith", val("U"))){

									@Override
									public void body() {

										call(getType(TesterStatics.class), 
												 "actuallyPrintln", val("            endsWith U!"));
									}
									
								}).else_(new KernelElse(){

									@Override
									public void body() {
										call(getType(TesterStatics.class), 
												 "actuallyPrintln", val("            endsWith Other!"));
										
										
										
									}
									
								});
								
							}
							
						});
						
					}
                	
                });
                	
                	
                
                return_();
            }
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new IClass[]{creator.getClassLoader().getType(String[].class)}, new String[]{"args"}, null, null,
            new KernelMethodBody(){
                @Override
                public void body(LocalVariable... argus) {
                	List<String> list = ConditionBlockGeneratorSample.allPossiable();
                	for(String str : list)
                	{
                		call(getMethodDeclaringClass(), "test", val(str));
                	}
                    return_();
                }
        
        });
        
        generate(creator);
    }
    
}
