package jw.asmsupport.utils.finder.method;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jw.asmsupport.utils.finder.clazz.ClassFinder;
import jw.asmsupport.utils.finder.clazz.DefaultFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;

public class StackLocalMethodVisitorTest {

    public static void main(String args[]) throws IOException{
    	StackLocalMethodVisitorTest s = new StackLocalMethodVisitorTest();
    	s.test();
    }
	
	
    private static Log log = LogFactory.getLog(StackLocalMethodVisitorTest.class);
    
	public static void setUpBeforeClass() throws Exception {
	}

	public void setUp() throws Exception {
	}
	
	public void test() throws IOException {
		List<String> classNames = new ArrayList<String>();
		Collection<URL> urls = ClassFinder.getUrls(Thread.currentThread().getContextClassLoader(), true);
		
		Set<String> protocols = new HashSet<String>(){
	            
	        	/**
				 * 
				 */
				private static final long serialVersionUID = -2518600163107096151L;

				{
	                add("jar");
	            }
	    };
		
		for (URL location : urls) {
            try {
                if (protocols.contains(location.getProtocol())) {
                    classNames.addAll(ClassFinder.jar(location, new DefaultFilter()));
                } else if ("file".equals(location.getProtocol())) {
                    try {
                        // See if it's actually a jar
                        URL jarUrl = new URL("jar", "", location.toExternalForm() + "!/");
                        JarURLConnection juc = (JarURLConnection) jarUrl.openConnection();
                        juc.getJarFile();
                        classNames.addAll(ClassFinder.jar(jarUrl, new DefaultFilter()));
                    } catch (IOException e) {
                        classNames.addAll(ClassFinder.file(location, new DefaultFilter()));
                    }
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
		
		for(String className : classNames){
			log.info(className);
		    ClassReader cr = new ClassReader(className);
		    ClassVisitor ca = new TestClassVisitor();
		    cr.accept(ca, 0);
		}
		System.out.println(classNames.size());
		
		
	}
	
    public static class TestClassVisitor extends EmptyVisitor {
    	
    	Type classType;

		@Override
		public void visit(int version, int access, String name,
				String signature, String superName, String[] interfaces) {
			classType = Type.getObjectType(name);
			super.visit(version, access, name, signature, superName, interfaces);
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc,
				String signature, String[] exceptions) {
			System.out.println("    " + name + " " + desc);
			//if(name.contains("$")){
				return super.visitMethod(access, name, name, signature, exceptions);
			//}
			//return new StackLocalMethodVisitor(desc, access, classType);
		}
    	
    }
}
