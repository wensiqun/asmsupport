package proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.wensiqun.asmsupport.client.ConstructorBody;
import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.IF;
import cn.wensiqun.asmsupport.client.MethodBody;
import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.lang.StringUtils;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class ProxyGenerator {

	private Map<Class<?>, List<Method>> map = new HashMap<Class<?>, List<Method>>();
	
	private Map<Method, Class<?>> adviceMap = new HashMap<Method, Class<?>>();
	
    public void register(Class<?> targetClass, Method targetMethod, Class<? extends MethodInvocation> adviceClass) {
    	List<Method> methods;
		if(map.containsKey(targetClass)) {
			methods = map.get(targetClass);
		} else {
			methods = new ArrayList<Method>();
			map.put(targetClass, methods);
		}
		methods.add(targetMethod);
		adviceMap.put(targetMethod, generateMethodInvocation(adviceClass, targetClass, targetMethod));
	}
	
	/**
	 * Start to generate proxy
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	public IProxyPool start() throws InstantiationException, IllegalAccessException {
		DummyClass proxyPool = new DummyClass(IProxyPool.class.getName() + "Impl").public_().implements_(IProxyPool.class)
				                                                                  .setClassOutPutPath(".//target//sample//");
		proxyPool.newMethod("getProxy").public_().return_(Object.class).argTypes(Object.class).argNames("target").body(new MethodBody() {

			@Override
			public void body(final LocalVariable... args) {

				for(Entry<Class<?>, List<Method>> entry : map.entrySet()) {
					final Class<?> targetType = entry.getKey();
					List<Method> targetMethods = entry.getValue();
					DummyClass proxy = new DummyClass(targetType.getName() + "Proxy").public_().setClassOutPutPath(".//target//sample//");
					if(targetType.isInterface()) {
						proxy.implements_(targetType);
					} else {
						proxy.extends_(targetType);
					}
					
					proxy.newField(targetType, "target").private_();
					
					proxy.newMethod("setTarget").public_().argTypes(targetType).body(new MethodBody() {

						@Override
						public void body(LocalVariable... args) {
							assign(this_().field("target"), args[0]);
							return_();
						}
						
					});
					
					//Override all public method
					Method[] publicMethods = targetType.getMethods();
					for(Method pubMet : publicMethods) {
						if(targetMethods.contains(pubMet)) {
							doProxyOverride(proxy, targetType, pubMet, adviceMap.get(pubMet));
						} else {
							doCommonOverride(proxy, pubMet);
						}
					}
					
					final Class<?> targetProxyClass = proxy.build();
					
					if_(new IF(instanceof_(args[0], targetType)) {

						@Override
						public void body() {
							LocalVariable proxy = var("proxy", targetProxyClass, new_(targetProxyClass));
							call(proxy, "setTarget", checkcast(args[0], targetType));
							return_(proxy);
						}
						
					});
					
				}
				
				return_(args[0]);
			}
			
		});
		
		return (IProxyPool) proxyPool.build().newInstance();
	}
	

	/**
	 * Generate MethodInvocation
	 * 
	 * @param adviceClass
	 * @param type
	 * @param method
	 * @return
	 */
	private Class<? extends MethodInvocation> generateMethodInvocation(Class<? extends MethodInvocation> adviceClass, Class<?> type, final Method method) {
		DummyClass dummy = new DummyClass(type.getName() + StringUtils.upperCase(method.getName(), 0, 1) + "MethodInvocation")
		                   .public_().extends_(adviceClass).setClassOutPutPath(".//target//sample//");
		
		dummy.newField(type, "target").private_();
		
		dummy.newConstructor().public_().argTypes(type).body(new ConstructorBody() {

			@Override
			public void body(LocalVariable... args) {
				supercall();
				assign(this_().field("target"), args[0]);
				return_();
			}
			
		});
		
		dummy.newMethod("callOrigin").protected_().return_(Object.class).argTypes(Object[].class).varargs()
		     .body(new MethodBody() {

			@Override
			public void body(LocalVariable... args) {
				Class<?>[] types = method.getParameterTypes();
				InternalParameterized[] arguments = new InternalParameterized[types.length];
				for(int i=0; i<arguments.length; i++) {
					arguments[i] = checkcast(arrayLoad(args[0], val(i)), types[i]);
				}
				
				if(method.getReturnType() == void.class) {
					call(this_().field("target"), method.getName(), arguments);
					return_(super_().field("VOID_OBJ"));
				} else {
					return_(call(this_().field("target"), method.getName(), arguments));
				}
			}
			
		});
		
		return (Class<? extends MethodInvocation>) dummy.build();
	}
	
	private void doCommonOverride(DummyClass proxy, final Method method) {
		int modifier = method.getModifiers();
		if(ModifierUtils.isNative(modifier) ||
		   ModifierUtils.isFinal(modifier)) {
			return;
		}
		
		proxy.newMethod(method.getName())
		     .setModifier(modifier & ~Opcodes.ACC_ABSTRACT)
		     .return_(method.getReturnType())
		     .argTypes(method.getParameterTypes())
		     .body(new MethodBody() {

				@Override
				public void body(LocalVariable... args) {
					if(void.class == method.getReturnType()) {
						call(this_().field("target"), method.getName(), args);
						return_();
					} else {
						return_(call(this_().field("target"), method.getName(), args));
					}
				}
		 });
	}
	

	private void doProxyOverride(DummyClass proxy, final Class<?> targetClass, final Method method, final Class<?> MethodInvocationClass) {
		proxy.newMethod(method.getName())
		     .public_()
		     .return_(method.getReturnType())
		     .argTypes(method.getParameterTypes())
		     .body(new MethodBody() {
				@Override
				public void body(LocalVariable... args) {
					InternalParameterized[] arguments = new InternalParameterized[args.length + 2];
					arguments[0] = val(targetClass);
					arguments[1] = val(method.getName());
					System.arraycopy(args, 0, arguments, 2, args.length);
					
					if(void.class == method.getReturnType()) {
						call(new_(MethodInvocationClass, this_().field("target")), "invoke", arguments);
						return_();
					} else {
						return_(checkcast(call(new_(MethodInvocationClass, this_().field("target")), "invoke", arguments), method.getReturnType()));
					}
				}
		 });
		
	}
}
