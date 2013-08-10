package jw.asmsupport.utils.finder.method;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.AClassFactory;
import jw.asmsupport.utils.ClassUtils;
import jw.asmsupport.utils.finder.clazz.ClassFinder;
import jw.asmsupport.utils.finder.clazz.DefaultFilter;
import jw.asmsupport.utils.finder.method.MethodInfoCollecter.MethodVisitorInfo;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;

public class MethodInvokerFinder {
	
	private List<InstanWrapper> instanMethodInvokeInfo;
	private Method byInvokedMethod;
	
	public MethodInvokerFinder(Method byInvokedMethod) {
		super();
		this.byInvokedMethod = byInvokedMethod;
	}

	public List<InstanWrapper> find(List<String> classNames) throws IOException{
		instanMethodInvokeInfo = new ArrayList<InstanWrapper>();
		
		for(String className : classNames){
			
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(className.replace('.', '/')
		                + ".class");
		    	ClassReader cr = new ClassReader(is);
		    	
		    	final MethodInfoCollecter methodInfoCollecter = new MethodInfoCollecter();
		    	cr.accept(methodInfoCollecter, 0);
		    	
		    	ClassVisitor ca = new EmptyVisitor(){
		        	
		        	Type classType;

		    		@Override
		    		public void visit(int version, int access, String name,
		    				String signature, String superName, String[] interfaces) {
		    			classType = Type.getObjectType(name);
		    		}

		    		@Override
		    		public MethodVisitor visitMethod(int access, String name, String desc,
		    				String signature, String[] exceptions) {
		    			//System.out.println("    " + name + " " + desc);
		    			if(name.contains("$")){
		    				return super.visitMethod(access, name, name, signature, exceptions);
		    			}
		    			String key = MethodInfoCollecter.getKey(name, desc);
		    			MethodVisitorInfo methodVisitorInfo = methodInfoCollecter.getMethodInfors().get(key);
		    			return new MethodInvokerFinderMethodVisitor(access, name, desc, classType, methodVisitorInfo);
		    		}
		        	
		        };
		    	cr.accept(ca, 0);
		}
		return instanMethodInvokeInfo;
	}
	
	public List<InstanWrapper> find() throws IOException{
		Collection<URL> urls = ClassFinder.getUrls(Thread.currentThread().getContextClassLoader(), true);
		
		Set<String> protocols = new HashSet<String>();
		protocols.add("jar");
		
		List<String> classNames = ClassFinder.fetchClassNamesFromUrls(urls, protocols, new DefaultFilter());
		
		/*for (URL location : urls) {
            try {
                if (protocols.contains(location.getProtocol())) {
                    classNames.addAll(ClassFinder.jar(location));
                } else if ("file".equals(location.getProtocol())) {
                    try {
                        // See if it's actually a jar
                        URL jarUrl = new URL("jar", "", location.toExternalForm() + "!/");
                        JarURLConnection juc = (JarURLConnection) jarUrl.openConnection();
                        juc.getJarFile();
                        classNames.addAll(ClassFinder.jar(jarUrl));
                    } catch (IOException e) {
                        classNames.addAll(ClassFinder.file(location));
                    }
                }
            } catch (Exception e) {
                if (log.isErrorEnabled())
                    log.error("Unable to read URL ["+location.toExternalForm()+"]", e);
            }
        }*/
		
		return find(classNames);
	}
	
	
	public class MethodInvokerFinderMethodVisitor extends StackLocalMethodVisitor {

		private String   byInvokedMethodDesc;
		private String   byInvokedMethodName;
		private Class<?> byInvokedMethodOwner;
		private Type     owner;
		private String   invokerMethodName;
		private String   invokerMethodDesc;
		
		public MethodInvokerFinderMethodVisitor(int modifiers, String name,
				String methodDesc,Type owner, MethodVisitorInfo  methodVisitorInfo) {
			super(methodDesc, modifiers, owner, methodVisitorInfo);
			this.byInvokedMethodDesc = Type.getMethodDescriptor(byInvokedMethod);
			this.byInvokedMethodOwner = byInvokedMethod.getDeclaringClass();
			this.byInvokedMethodName = byInvokedMethod.getName();
			this.invokerMethodName = name;
			this.invokerMethodDesc = methodDesc;
			this.owner = owner;
		}

		@Override
		public void visitMethodInsn(int opcode, String owner, String name,
				String desc) {
			if(desc.equals(byInvokedMethodDesc) && name.equals(byInvokedMethodName)){
				try{
					AClass ownerClass = AClassFactory.getProductClass(ClassUtils.forName(owner.replace("/", ".")));
					AClass byInvokedMethodOwnerAClass = AClassFactory.getProductClass(byInvokedMethodOwner);
					if(ownerClass.isChildOrEqual(byInvokedMethodOwnerAClass)){
						InstanWrapper iw = new InstanWrapper();
						iw.setInvokerMethodName(invokerMethodName);
						iw.setInvokerMethodDesc(invokerMethodDesc);
						//log.info("---" + this.owner.getClassName());
						iw.setInvoker(ClassUtils.forName(this.owner.getClassName()));
						
						int argumentsSize = byInvokedMethod.getParameterTypes().length;
						List<Type> allTypeInStack = new ArrayList<Type>();
						for(int i=0; i<argumentsSize;i++){
							allTypeInStack.add(stack.get(stack.size() - 1 - i));
						}
						
						
						Class<?>[] argumentActuallyTypes = new Class<?>[argumentsSize];
						for(int i=argumentsSize; i>0;i--){
							Type typeInStack = allTypeInStack.get(i-1);
							Class<?> argCls = null; 
							int sort = typeInStack.getSort();
							switch(sort){
							case 1 :
						        argCls = boolean.class;
						        break;
							case 2 :
								argCls = char.class;
						        break;
							case 3 :
								argCls = byte.class;
						        break;
							case 4 :
								argCls = short.class;
						        break;
							case 5 :
								argCls = int.class;
						        break;
							case 6 :
								argCls = float.class;
						        break;
							case 7 :
								argCls = long.class;
						        break;
							case 8 :
								argCls = double.class;
						        break;
							case 9 :
								argCls = ClassUtils.forName(typeInStack.getDescriptor());
						        break;
							default :
							    argCls = ClassUtils.forName(typeInStack.getClassName());
						        break;
							}
							argumentActuallyTypes[argumentsSize-i] = argCls;
						}
						iw.setArgumentActuallyTypes(argumentActuallyTypes);
						instanMethodInvokeInfo.add(iw);
					}
				} catch (Throwable e) {
			    }
			}
			super.visitMethodInsn(opcode, owner, name, desc);
		}
		
	}
	
	public static class InstanWrapper {
		private Class<?> invoker;
		
		private Class<?>[] argumentActuallyTypes;

		private int line;
		
		private String invokerMethodName;
		private String invokerMethodDesc;
		
		public Class<?> getInvoker() {
			return invoker;
		}

		public void setInvoker(Class<?> invoker) {
			this.invoker = invoker;
		}

		public Class<?>[] getArgumentActuallyTypes() {
			return argumentActuallyTypes;
		}

		public void setArgumentActuallyTypes(Class<?>[] argumentActuallyTypes) {
			this.argumentActuallyTypes = argumentActuallyTypes;
		}

		public int getLine() {
			return line;
		}

		public void setLine(int line) {
			this.line = line;
		}
		
		public String getInvokerMethodName() {
			return invokerMethodName;
		}

		public void setInvokerMethodName(String invokerMethodName) {
			this.invokerMethodName = invokerMethodName;
		}

		public String getInvokerMethodDesc() {
			return invokerMethodDesc;
		}

		public void setInvokerMethodDesc(String invokerMethodDesc) {
			this.invokerMethodDesc = invokerMethodDesc;
		}
	}
}
