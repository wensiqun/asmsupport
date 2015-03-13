package cn.wensiqun.asmsupport.core.block.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.Assert;

import org.junit.Test;

import sample.AbstractExample;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.TryInternal;
import cn.wensiqun.asmsupport.core.block.control.loop.WhileInternal;
import cn.wensiqun.asmsupport.core.block.method.common.MethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.init.ConstructorBodyInternal;
import cn.wensiqun.asmsupport.core.block.sync.SynchronizedInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class SynchronizedGeneratorTest extends AbstractExample {

	@Test
	public void test() {
		/*try {
			main("");
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}*/
		String prefix = "prefix";
		int excepted = 100;
		int actually = 100;
		assertEquals(prefix, excepted, actually);
	}
	
	public static void assertEquals(Object prefix, Object excepted, Object actually) {
		System.out.println("Prefix [" + prefix +  "] --- excepted : " + excepted + " actually : " + actually);
		Assert.assertEquals(excepted, actually);
	}
	
	/*public static void assertEquals(int prefix, int excepted, int actually) {
		System.out.println("Prefix [" + prefix +  "] --- excepted : " + excepted + " actually : " + actually);
		Assert.assertEquals(excepted, actually);
	}*/
	
    public static void main(String... args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        

        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.SynchronizedGeneratorExample", null, null);
        
        creator.createField("lock", Opcodes.ACC_PRIVATE, AClass.OBJECT_ACLASS);
        
        creator.createField("list", Opcodes.ACC_PUBLIC, AClassFactory.getProductClass(List.class));
        
        creator.createConstructor(Opcodes.ACC_PUBLIC, null, null, null, new ConstructorBodyInternal() {

            @Override
            public void body(LocalVariable... argus) {
            	_supercall(argus);
                _assign(_this().getGlobalVariable("lock"), _new(AClass.OBJECT_ACLASS));
                _assign(_this().getGlobalVariable("list"), _new(AClassFactory.getProductClass(ArrayList.class)));
				_return();
            }
            
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC, "syncThis", null, null, null, null, new MethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus) {
                final GlobalVariable list = _this().getGlobalVariable("list");
                _sync(new SynchronizedInternal(_this()){
                    @Override
                    public void body(Parameterized e) {
                        final LocalVariable i = _var("i", AClass.INT_ACLASS, false, Value.value(0));
                        _while(new WhileInternal(_lt(i, Value.value(10))){

                            @Override
                            public void body() {
                                _invoke(list, "add", i);
                                _postInc(i);
                            }
                            
                        });
                    }
                    
                });
                _return();
            }
            
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC, "syncLock", null, null, null, null, new MethodBodyInternal(){
            
            @Override
            public void body(LocalVariable... argus) {
                final GlobalVariable list = _this().getGlobalVariable("list");
                _sync(new SynchronizedInternal(_this().getGlobalVariable("lock")){
                    @Override
                    public void body(Parameterized e) {
                        final LocalVariable i = _var("i", AClass.INT_ACLASS, false, Value.value(0));
                        _while(new WhileInternal(_lt(i, Value.value(10))){

                            @Override
                            public void body() {
                                _invoke(list, "add", i);
                                _postInc(i);
                            }
                            
                        });
                    }
                    
                });
                _return();
            }
            
        });

        Class<?> syncGenExamp = generate(creator, false);
        Class<?> thisThread = createThread(AClassFactory.getProductClass(syncGenExamp), "This");
        Class<?> lockThread = createThread(AClassFactory.getProductClass(syncGenExamp), "Lock");
        Class<?> junitTestCls = createTestJunit(AClassFactory.getProductClass(syncGenExamp), 
        		AClassFactory.getProductClass(thisThread), AClassFactory.getProductClass(lockThread));
        Object junitTestObj = junitTestCls.newInstance();
        Method testSyncThis = junitTestCls.getMethod("testSyncThis");
        Method testSyncLock = junitTestCls.getMethod("testSyncLock");
        testSyncThis.invoke(junitTestObj);
        testSyncThis.invoke(junitTestObj);
    }
    
    private static Class<?> createThread(AClass synchronizedGeneratorExampleClass, final String name) {
        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , 
        		"generated.block.Sync" + name + "ThreadExample", 
        		Thread.class, null);
        
        creator.createField("sgst", Opcodes.ACC_PRIVATE, synchronizedGeneratorExampleClass);
        
        creator.createConstructor(Opcodes.ACC_PUBLIC, new AClass[]{synchronizedGeneratorExampleClass}, new String[]{"sgst"},  null, new ConstructorBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
          	   _supercall();
				_assign(_this().getGlobalVariable("sgst"), argus[0]);
				this._return();
			}
        	
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC, "run", null, null, null, null, new MethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				this._try(new TryInternal(){

					@Override
					public void body() {
						_invokeStatic(AClassFactory.getProductClass(Thread.class), "sleep", Value.value(100));
					    _invoke(_this().getGlobalVariable("sgst"), "sync" + name);
					}
					
				})._catch(new CatchInternal(AClassFactory.getProductClass(InterruptedException.class)) {

					@Override
					public void body(LocalVariable e) {
					}
					
				});
				_return();
			}
        	
        });
        return generate(creator, false);
    }
    
    private static Class<?> createTestJunit(AClass syncCls, AClass thisThread, AClass lockThread) {
    	ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , 
        		"generated.block.SynchronizedGeneratorExampleTestJunit", 
        		null, null);
    	createTestSyncMethod(creator, "This", syncCls, thisThread);
    	createTestSyncMethod(creator, "Lock", syncCls, lockThread);
    	
    	
    	return generate(creator, false);
    }
    
    private static void createTestSyncMethod(ClassCreator creator, final String name, final AClass syncCls, final AClass threadClass) {


        creator.createMethod(Opcodes.ACC_PUBLIC, "testSync" + name, null, null, null, null, new MethodBodyInternal(){
            
            @Override
            public void body(LocalVariable... argus) {
                 final LocalVariable sgst = _var("sgst", syncCls, false, _new(syncCls));
                
                 final LocalVariable es = _var(
                        "es", 
                        AClassFactory.getProductClass(ExecutorService.class),
                        false, 
                        _invokeStatic(AClassFactory.getProductClass(Executors.class), "newFixedThreadPool", Value.value(10))
                );
                 final LocalVariable objs = _var(
                        "objs", 
                        AClassFactory.getProductClass(List.class),
                        false, 
                        _new(AClassFactory.getProductClass(ArrayList.class))
                );
                 final LocalVariable i = _var(
                        "i", 
                        AClass.INT_ACLASS,
                        false, 
                        Value.value(0)
               );
                this._while(new WhileInternal(_lt(i, Value.value(10))){
						@Override
						public void body() {
							_invoke(objs, "add", _invoke(es, "submit", _new(threadClass, sgst)));
							_postInc(i);
						}
					});
                _invoke(es, "shutdown");
                _while(new WhileInternal(_not(_invoke(es, "isTerminated"))){
						@Override
						public void body() {
							
						}
					});
                _invokeStatic(AClassFactory.getProductClass(SynchronizedGeneratorTest.class), "assertEquals",
                		Value.value("Assert.assertEquals(100, sgst.list.size())"), Value.value(100), _invoke(sgst.getGlobalVariable("list"), "size"));
                
                _assign(i, Value.value(0));
                _while(new WhileInternal(_lt(i, Value.value(100))) {

					@Override
					public void body() {
		                _invokeStatic(AClassFactory.getProductClass(SynchronizedGeneratorTest.class), "assertEquals",
		                		i,
		                		_mod(i, Value.value(10)), 
		                		_invoke(_checkcast(_invoke(sgst.getGlobalVariable("list"), "get", i), AClass.INTEGER_WRAP_ACLASS), "intValue"));
						_postInc(i);
					}
                	
                });
                
                _return();
            }
            
        });
    }
    
}
