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
import cn.wensiqun.asmsupport.core.block.control.exception.KernelCatch;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelTry;
import cn.wensiqun.asmsupport.core.block.control.loop.KernelWhile;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.block.method.init.KernelConstructorBody;
import cn.wensiqun.asmsupport.core.block.sync.KernelSync;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.utils.AsmsupportClassLoader;

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
        

        ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.SynchronizedGeneratorExample", null, null);
        ClassHolder classHolder = creator.getClassLoader();
        creator.createField("lock", Opcodes.ACC_PRIVATE, creator.getClassLoader().getType(Object.class));
        
        creator.createField("list", Opcodes.ACC_PUBLIC, creator.getClassLoader().getType(List.class));
        
        creator.createConstructor(Opcodes.ACC_PUBLIC, null, null, null, new KernelConstructorBody() {

            @Override
            public void body(LocalVariable... argus) {
            	supercall(argus);
                assign(this_().field("lock"), new_(getType(Object.class)));
                assign(this_().field("list"), new_(getType(ArrayList.class)));
				return_();
            }
            
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC, "syncThis", null, null, null, null, new KernelMethodBody(){

            @Override
            public void body(LocalVariable... argus) {
                final GlobalVariable list = this_().field("list");
                sync(new KernelSync(this_()){
                    @Override
                    public void body(KernelParam e) {
                        final LocalVariable i = var("i", getType(int.class), val(0));
                        while_(new KernelWhile(lt(i, val(10))){

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
        
        creator.createMethod(Opcodes.ACC_PUBLIC, "syncLock", null, null, null, null, new KernelMethodBody(){
            
            @Override
            public void body(LocalVariable... argus) {
                final GlobalVariable list = this_().field("list");
                sync(new KernelSync(this_().field("lock")){
                    @Override
                    public void body(KernelParam e) {
                        final LocalVariable i = var("i", getType(int.class), val(0));
                        while_(new KernelWhile(lt(i, val(10))){

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
        Class<?> thisThread = createThread(classHolder.getType(syncGenExamp), "This");
        Class<?> lockThread = createThread(classHolder.getType(syncGenExamp), "Lock");
        Class<?> junitTestCls = createTestJunit(classHolder.getType(syncGenExamp), 
        		classHolder.getType(thisThread), classHolder.getType(lockThread));
        Object junitTestObj = junitTestCls.newInstance();
        Method testSyncThis = junitTestCls.getMethod("testSyncThis");
        Method testSyncLock = junitTestCls.getMethod("testSyncLock");
        testSyncThis.invoke(junitTestObj);
        testSyncThis.invoke(junitTestObj);
    }
    
    private static Class<?> createThread(AClass synchronizedGeneratorExampleClass, final String name) {
    	
    	AsmsupportClassLoader classLoader = CachedThreadLocalClassLoader.getInstance();
    	
        ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , 
        		"generated.block.Sync" + name + "ThreadExample", 
        		classLoader.getType(Thread.class), null, classLoader);
        
        creator.createField("sgst", Opcodes.ACC_PRIVATE, synchronizedGeneratorExampleClass);
        
        creator.createConstructor(Opcodes.ACC_PUBLIC, new AClass[]{synchronizedGeneratorExampleClass}, new String[]{"sgst"},  null, new KernelConstructorBody(){

			@Override
			public void body(LocalVariable... argus) {
          	   supercall();
				assign(this_().field("sgst"), argus[0]);
				this.return_();
			}
        	
        });
        
        creator.createMethod(Opcodes.ACC_PUBLIC, "run", null, null, null, null, new KernelMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				this.try_(new KernelTry(){

					@Override
					public void body() {
						call(getType(Thread.class), "sleep", val(100));
					    call(this_().field("sgst"), "sync" + name);
					}
					
				}).catch_(new KernelCatch(getType(InterruptedException.class)) {

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
    	ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , 
        		"generated.block.SynchronizedGeneratorExampleTestJunit", 
        		null, null);
    	createTestSyncMethod(creator, "This", syncCls, thisThread);
    	createTestSyncMethod(creator, "Lock", syncCls, lockThread);
    	
    	
    	return generate(creator, false);
    }
    
    private static void createTestSyncMethod(ClassBuilderImpl creator, final String name, final AClass syncCls, final AClass threadClass) {


        creator.createMethod(Opcodes.ACC_PUBLIC, "testSync" + name, null, null, null, null, new KernelMethodBody(){
            
            @Override
            public void body(LocalVariable... argus) {
                 final LocalVariable sgst = var("sgst", syncCls, new_(syncCls));
                
                 final LocalVariable es = var(
                        "es", 
                        getType(ExecutorService.class),
                        call(getType(Executors.class), "newFixedThreadPool", val(10))
                );
                 final LocalVariable objs = var(
                        "objs", 
                        getType(List.class),
                        new_(getType(ArrayList.class))
                );
                 final LocalVariable i = var(
                        "i", 
                        getType(int.class),
                        val(0)
               );
                this.while_(new KernelWhile(lt(i, val(10))){
						@Override
						public void body() {
							call(objs, "add", call(es, "submit", new_(threadClass, sgst)));
							postinc(i);
						}
					});
                call(es, "shutdown");
                while_(new KernelWhile(no(call(es, "isTerminated"))){
						@Override
						public void body() {
							
						}
					});
                call(getType(SynchronizedGeneratorTest.class), "assertEquals",
                		val("Assert.assertEquals(100, sgst.list.size())"), val(100), call(sgst.field("list"), "size"));
                
                assign(i, val(0));
                while_(new KernelWhile(lt(i, val(100))) {

					@Override
					public void body() {
		                call(getType(SynchronizedGeneratorTest.class), "assertEquals",
		                		i,
		                		mod(i, val(10)), 
		                		call(checkcast(call(sgst.field("list"), "get", i), getType(Integer.class)), "intValue"));
						postinc(i);
					}
                	
                });
                
                return_();
            }
            
        });
    }
    
}
