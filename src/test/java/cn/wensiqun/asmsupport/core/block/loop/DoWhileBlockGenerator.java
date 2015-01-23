package cn.wensiqun.asmsupport.core.block.loop;

import cn.wensiqun.asmsupport.core.block.classes.control.loop.DoWhileInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import example.AbstractExample;

public class DoWhileBlockGenerator extends AbstractExample {

	public static void main(String[] args)
    {
		ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.DoWhileBlockGeneratorExample", null, null);
		
		 creator.createStaticMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, "test", null, null, null, null, new StaticMethodBodyInternal(){

	            @Override
	            public void body(LocalVariable... argus)
	            {
                    
                    final LocalVariable intVar1  = _createVariable("intVar1", AClass.INT_ACLASS, false, Value.value(10));
                    
                    _dowhile(new DoWhileInternal(_greaterThan(_postDec(intVar1), Value.value(0))){

                        @Override
                        public void body() {
                            _invokeStatic(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", _invokeStatic(AClass.STRING_ACLASS, "valueOf", intVar1));
                        }
                        
                    });
                    
                    final LocalVariable intVar2  = _createVariable("intVar2", AClass.INT_ACLASS, false, Value.value(10));
                    
                    _dowhile(new DoWhileInternal(_greaterThan(_preDec(intVar2), Value.value(0))){

                        @Override
                        public void body() {
                            _invokeStatic(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", _invokeStatic(AClass.STRING_ACLASS, "valueOf", intVar2));
                        }
                        
                    });
	                
                    final LocalVariable byteVar  = _createVariable("byteVar", AClass.BYTE_ACLASS, false, Value.value((byte)10));
                    
	            	_dowhile(new DoWhileInternal(_greaterThan(_postDec(byteVar), Value.value(0))){

						@Override
						public void body() {
							_invokeStatic(TesterStatics.ATesterStatics, 
		                    		"actuallyPrintln", _invokeStatic(AClass.STRING_ACLASS, "valueOf", byteVar));
						}
	            		
	            	});
	            	
	            	final LocalVariable doubleVar  = _createVariable("doubleVar", AClass.DOUBLE_ACLASS, false, Value.value(10D));
                    
                    _dowhile(new DoWhileInternal(_greaterThan(_preDec(doubleVar), Value.value(0))){

                        @Override
                        public void body() {
                            _invokeStatic(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", _invokeStatic(AClass.STRING_ACLASS, "valueOf", doubleVar));
                        }
                        
                    });
                    
                    final LocalVariable shortObj  = _createVariable("shortObj", AClass.SHORT_WRAP_ACLASS, false, Value.value((short)10));
                    
                    _dowhile(new DoWhileInternal(_greaterThan(_postDec(shortObj), Value.value((short)0))){

                        @Override
                        public void body() {
                            _invokeStatic(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", _invokeStatic(AClass.STRING_ACLASS, "valueOf", shortObj));
                        }
                        
                    });
                    
                    final LocalVariable longObj  = _createVariable("longObj", AClass.LONG_WRAP_ACLASS, false, Value.value(10L));
                    
                    _dowhile(new DoWhileInternal(_greaterThan(_preDec(longObj), Value.value(0))){

                        @Override
                        public void body() {
                            _invokeStatic(TesterStatics.ATesterStatics, 
                                    "actuallyPrintln", _invokeStatic(AClass.STRING_ACLASS, "valueOf", longObj));
                        }
                        
                    });
                    
                    
					_return();
	            }
		 });
	        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
            new StaticMethodBodyInternal(){
                @Override
                public void body(LocalVariable... argus) {
                	_invokeStatic(getMethodOwner(), "test");
                    _return();
                }
        
        });

        generate(creator);
    }
	
}
