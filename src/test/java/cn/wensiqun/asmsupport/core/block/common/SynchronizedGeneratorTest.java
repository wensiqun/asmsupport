package cn.wensiqun.asmsupport.core.block.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.core.AbstractExample;
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
        
        creator.createField("list", Opcodes.ACC_PUBLIC, AClassFactory.defType(List.class));
        
        creator.createConstructor(Opcodes.ACC_PUBLIC, null, null, null, new ConstructorBodyInternal() {

            @Override
            public void body(LocalVariable... argus) {
            	supercall(argus);
                assign(this_().field("lock"), new_(AClass.OBJECT_ACLASS));
                assign(this_().field("list"), new_(AClassFactory.defType(ArrayList.class)));
				return_();
            }
            
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC, "syncThis", null, null, null, null, new MethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus) {
                final GlobalVariable list = this_().field("list");
                sync(new SynchronizedInternal(this_()){
                    @Override
                    public void body(Parameterized e) {
                        final LocalVariable i = var("i", AClass.INT_ACLASS, false, Value.value(0));
                        while_(new WhileInternal(lt(i, Value.value(10))){

                            @Override
                            public void body() {
                                call(list, "add", i);
                                postinc(i);
                            }
                            
                        });
                    }
                    
                });
                return_();
            }
            
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC, "syncLock", null, null, null, null, new MethodBodyInternal(){
            
            @Override
            public void body(LocalVariable... argus) {
                final GlobalVariable list = this_().field("list");
                sync(new SynchronizedInternal(this_().field("lock")){
                    @Override
                    public void body(Parameterized e) {
                        final LocalVariable i = var("i", AClass.INT_ACLASS, false, Value.value(0));
                        while_(new WhileInternal(lt(i, Value.value(10))){

                            @Override
                            public void body() {
                                call(list, "add", i);
                                postinc(i);
                            }
                            
                        });
                    }
                    
                });
                return_();
            }
            
        });

        Class<?> syncGenExamp = generate(creator, false);
        Class<?> thisThread = createThread(AClassFactory.defType(syncGenExamp), "This");
        Class<?> lockThread = createThread(AClassFactory.defType(syncGenExamp), "Lock");
        Class<?> junitTestCls = createTestJunit(AClassFactory.defType(syncGenExamp), 
        		AClassFactory.defType(thisThread), AClassFactory.defType(lockThread));
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
          	   supercall();
				assign(this_().field("sgst"), argus[0]);
				this.return_();
			}
        	
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC, "run", null, null, null, null, new MethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				this.try_(new TryInternal(){

					@Override
					public void body() {
						call(AClassFactory.defType(Thread.class), "sleep", Value.value(100));
					    call(this_().field("sgst"), "sync" + name);
					}
					
				}).catch_(new CatchInternal(AClassFactory.defType(InterruptedException.class)) {

					@Override
					public void body(LocalVariable e) {
					}
					
				});
				return_();
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
                 final LocalVariable sgst = var("sgst", syncCls, false, new_(syncCls));
                
                 final LocalVariable es = var(
                        "es", 
                        AClassFactory.defType(ExecutorService.class),
                        false, 
                        call(AClassFactory.defType(Executors.class), "newFixedThreadPool", Value.value(10))
                );
                 final LocalVariable objs = var(
                        "objs", 
                        AClassFactory.defType(List.class),
                        false, 
                        new_(AClassFactory.defType(ArrayList.class))
                );
                 final LocalVariable i = var(
                        "i", 
                        AClass.INT_ACLASS,
                        false, 
                        Value.value(0)
               );
                this.while_(new WhileInternal(lt(i, Value.value(10))){
						@Override
						public void body() {
							call(objs, "add", call(es, "submit", new_(threadClass, sgst)));
							postinc(i);
						}
					});
                call(es, "shutdown");
                while_(new WhileInternal(no(call(es, "isTerminated"))){
						@Override
						public void body() {
							
						}
					});
                call(AClassFactory.defType(SynchronizedGeneratorTest.class), "assertEquals",
                		Value.value("Assert.assertEquals(100, sgst.list.size())"), Value.value(100), call(sgst.field("list"), "size"));
                
                assign(i, Value.value(0));
                while_(new WhileInternal(lt(i, Value.value(100))) {

					@Override
					public void body() {
		                call(AClassFactory.defType(SynchronizedGeneratorTest.class), "assertEquals",
		                		i,
		                		mod(i, Value.value(10)), 
		                		call(checkcast(call(sgst.field("list"), "get", i), AClass.INTEGER_WRAP_ACLASS), "intValue"));
						postinc(i);
					}
                	
                });
                
                return_();
            }
            
        });
    }
    
}
