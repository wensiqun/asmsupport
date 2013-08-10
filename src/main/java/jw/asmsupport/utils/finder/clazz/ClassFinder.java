package jw.asmsupport.utils.finder.clazz;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

/**
 * ClassFinder searches the classpath of the specified ClassLoader for
 * packages, classes, constructors, methods, or fields with specific annotations.
 *
 * For security reasons ASM is used to find the annotations.  Classes are not
 * loaded unless they match the requirements of a called findAnnotated* method.
 * Once loaded, these classes are cached.
 *
 * The getClassesNotLoaded() method can be used immediately after any find*
 * method to get a list of classes which matched the find requirements (i.e.
 * contained the annotation), but were unable to be loaded.
 *
 * @author 
 * @version modify by com.opensymphony.xwork2.util.finder.ClassFinder
 */
public class ClassFinder extends ClassFetcher {
    
	private static final Log LOG = LogFactory.getLog(ClassFinder.class);
	
    private final Map<String, ClassInfo> classInfos = new LinkedHashMap<String, ClassInfo>();
    
    private final Map<String, List<Info>> annotated = new HashMap<String, List<Info>>();

    protected final List<String> classesNotLoaded = new ArrayList<String>();

    protected Filter readFilter;
    
    /**
     * 
     * @param classLoader
     * @throws Exception
     */
    public ClassFinder(ClassLoader classLoader) throws Exception {
		this(classLoader, new DefaultFilter(), new DefaultFilter());
    }
    
    /**
     * 
     * @param classLoader
     * @param readFilter
     * @param fetchFilter
     * @throws Exception
     */
	public ClassFinder(ClassLoader classLoader, Filter readFilter, Filter fetchFilter) throws Exception {
		super(classLoader, fetchFilter == null ? new DefaultFilter() : fetchFilter);
		this.readFilter = readFilter == null ? new DefaultFilter() : readFilter ;
		init();
	}
	
	
	
	private void init(){
        for (String className : classNames) {
            try {
                if (!readFilter.filter(className))
                    readClassDef(className);
            } catch (Throwable e) {
                if (LOG.isErrorEnabled())
                    LOG.error("Unable to read class ["+className+"]", e);
            }
        }
	}

	/**
     * Returns a list of classes that could not be loaded in last invoked findAnnotated* method.
     * <p/>
     * The list will only contain entries of classes whose byte code matched the requirements
     * of last invoked find* method, but were unable to be loaded and included in the results.
     * <p/>
     * The list returned is unmodifiable.  Once obtained, the returned list will be a live view of the
     * results from the last findAnnotated* method call.
     * <p/>
     * This method is not thread safe.
     * @return an unmodifiable live view of classes that could not be loaded in previous findAnnotated* call.
     */
    public List<String> getClassesNotLoaded() {
        return Collections.unmodifiableList(classesNotLoaded);
    }

    public List<Class<?>> findClassesInPackage(String packageName, boolean recursive) {
        classesNotLoaded.clear();
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (ClassInfo classInfo : classInfos.values()) {
            try {
                if (recursive && classInfo.getPackageName().startsWith(packageName)){
                    classes.add(classInfo.get());
                } else if (classInfo.getPackageName().equals(packageName)){
                    classes.add(classInfo.get());
                }
            } catch (Throwable e) {
                if (LOG.isErrorEnabled())
                    LOG.error("Error loading class ["+classInfo.getName()+"]", e);
                classesNotLoaded.add(classInfo.getName());
            }
        }
        return classes;
    }

    /*public List<Class<?>> findClasses(ReadFilter<ClassInfo> test) {
        classesNotLoaded.clear();
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (ClassInfo classInfo : classInfos.values()) {
            try {
                if (!test.filter(classInfo)) {
                    classes.add(classInfo.get());
                }
            } catch (Throwable e) {
                if (LOG.isErrorEnabled())
                    LOG.error("Error loading class ["+classInfo.getName()+"]", e);
                classesNotLoaded.add(classInfo.getName());
            }
        }
        return classes;
    }*/

    public List<Class<?>> findClasses() {
        classesNotLoaded.clear();
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (ClassInfo classInfo : classInfos.values()) {
            try {
                classes.add(classInfo.get());
            } catch (Throwable e) {
                if (LOG.isErrorEnabled())
                    LOG.error("Error loading class ["+ classInfo.getName() +"]", e);
                classesNotLoaded.add(classInfo.getName());
            }
        }
        return classes;
    }

    public class Annotatable {
       
    	private final List<AnnotationInfo> annotations = new ArrayList<AnnotationInfo>();

        public Annotatable(AnnotatedElement element) {
            for (Annotation annotation : element.getAnnotations()) {
                annotations.add(new AnnotationInfo(annotation.annotationType().getName()));
            }
        }

        public Annotatable() {
        }

        public List<AnnotationInfo> getAnnotations() {
            return annotations;
        }

    }

    public static interface Info {
        String getName();

        List<AnnotationInfo> getAnnotations();
    }

    public class PackageInfo extends Annotatable implements Info {
        private final String name;
        private final ClassInfo info;
        private final Package pkg;

        public PackageInfo(Package pkg){
            super(pkg);
            this.pkg = pkg;
            this.name = pkg.getName();
            this.info = null;
        }

        public PackageInfo(String name) {
            info = new ClassInfo(name, null);
            this.name = name;
            this.pkg = null;
        }

        public String getName() {
            return name;
        }

        public Package get() throws ClassNotFoundException {
            return (pkg != null)?pkg:info.get().getPackage();
        }
    }

    public class ClassInfo extends Annotatable implements Info {
        private final String name;
        private final List<MethodInfo> methods = new ArrayList<MethodInfo>();
        private final List<MethodInfo> constructors = new ArrayList<MethodInfo>();
        private final String superType;
        private final List<String> interfaces = new ArrayList<String>();
        private final List<String> superInterfaces = new ArrayList<String>();
        private final List<FieldInfo> fields = new ArrayList<FieldInfo>();
        private Class<?> clazz;
        private ClassNotFoundException notFound;

        public ClassInfo(Class<?> clazz) {
            super(clazz);
            this.clazz = clazz;
            this.name = clazz.getName();
            Class<?> superclass = clazz.getSuperclass();
            this.superType = superclass != null ? superclass.getName(): null;
        }

        public ClassInfo(String name, String superType) {
            this.name = name;
            this.superType = superType;
        }

        public String getPackageName(){
            return name.indexOf(".") > 0 ? name.substring(0, name.lastIndexOf(".")) : "" ;
        }

        public List<MethodInfo> getConstructors() {
            return constructors;
        }

        public List<String> getInterfaces() {
            return interfaces;
        }

        public List<String> getSuperInterfaces() {
            return superInterfaces;
        }

        public List<FieldInfo> getFields() {
            return fields;
        }

        public List<MethodInfo> getMethods() {
            return methods;
        }

        public String getName() {
            return name;
        }

        public String getSuperType() {
            return superType;
        }

        public Class<?> get() throws ClassNotFoundException {
            if (clazz != null) return clazz;
            if (notFound != null) throw notFound;
            try {
                this.clazz = classLoader.loadClass(name);
                return clazz;
            } catch (ClassNotFoundException notFound) {
                classesNotLoaded.add(name);
                this.notFound = notFound;
                throw notFound;
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public class MethodInfo extends Annotatable implements Info {
        private final ClassInfo declaringClass;
        private final String returnType;
        private final String name;
        private final List<List<AnnotationInfo>> parameterAnnotations = new ArrayList<List<AnnotationInfo>>();

        public MethodInfo(ClassInfo info, Constructor<?> constructor){
            super(constructor);
            this.declaringClass = info;
            this.name = "<init>";
            this.returnType = Void.TYPE.getName();
        }

        public MethodInfo(ClassInfo info, Method method){
            super(method);
            this.declaringClass = info;
            this.name = method.getName();
            this.returnType = method.getReturnType().getName();
        }

        public MethodInfo(ClassInfo declarignClass, String name, String returnType) {
            this.declaringClass = declarignClass;
            this.name = name;
            this.returnType = returnType;
        }

        public List<List<AnnotationInfo>> getParameterAnnotations() {
            return parameterAnnotations;
        }

        public List<AnnotationInfo> getParameterAnnotations(int index) {
            if (index >= parameterAnnotations.size()) {
                for (int i = parameterAnnotations.size(); i <= index; i++) {
                    List<AnnotationInfo> annotationInfos = new ArrayList<AnnotationInfo>();
                    parameterAnnotations.add(i, annotationInfos);
                }
            }
            return parameterAnnotations.get(index);
        }

        public String getName() {
            return name;
        }

        public ClassInfo getDeclaringClass() {
            return declaringClass;
        }

        public String getReturnType() {
            return returnType;
        }

        @Override
        public String toString() {
            return declaringClass + "@" + name;
        }
    }

    public class FieldInfo extends Annotatable implements Info {
        private final String name;
        private final String type;
        private final ClassInfo declaringClass;

        public FieldInfo(ClassInfo info, Field field){
            super(field);
            this.declaringClass = info;
            this.name = field.getName();
            this.type = field.getType().getName();
        }

        public FieldInfo(ClassInfo declaringClass, String name, String type) {
            this.declaringClass = declaringClass;
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public ClassInfo getDeclaringClass() {
            return declaringClass;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return declaringClass + "#" + name;
        }
    }

    public class AnnotationInfo extends Annotatable implements Info {
        private final String name;

        public AnnotationInfo(Annotation annotation){
            this(annotation.getClass().getName());
        }

        public AnnotationInfo(Class<? extends Annotation> annotation) {
            this.name = annotation.getName().intern();
        }

        public AnnotationInfo(String name) {
            name = name.replaceAll("^L|;$", "");
            name = name.replace('/', '.');
            this.name = name.intern();
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    
    private void readClassDef(String className) {
        if (!className.endsWith(".class")) {
            className = className.replace('.', '/') + ".class";
        }
        try {
            URL resource = classLoader.getResource(className);
            if (resource != null) {
                InputStream in = resource.openStream();
                try {
                    ClassReader classReader = new ClassReader(in);
                    
                    classReader.accept(new InfoBuildingVisitor(), ClassReader.SKIP_DEBUG);
                } finally {
                    in.close();
                }
            } else {
                throw new RuntimeException("Could not load " + className);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load " + className, e);
        }
    }
    
    /* EmptyVisitor implement method  */ 

    private void extractSuperInterfaces(ClassInfo classInfo) {
        String superType = classInfo.getSuperType();

        if (superType != null) {
            ClassInfo base = classInfos.get(superType);

            if (base == null) {
                //try to load base
                String resource = superType.replace('.', '/') + ".class";
                readClassDef(resource);
                base = classInfos.get(superType);
            }

            if (base != null) {
                List<String> interfaces = classInfo.getSuperInterfaces();
                interfaces.addAll(base.getSuperInterfaces());
                interfaces.addAll(base.getInterfaces());
            }
        }
    }
    
	public class InfoBuildingVisitor extends EmptyVisitor {
        private Info info;

        public InfoBuildingVisitor() {
        }

        public InfoBuildingVisitor(Info info) {
            this.info = info;
        }
        
        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        	if (name.endsWith("package-info")) {
                info = new PackageInfo(javaName(name));
            } else {
                ClassInfo classInfo = new ClassInfo(javaName(name), javaName(superName));

                for (String interfce : interfaces) {
                    classInfo.getInterfaces().add(javaName(interfce));
                }
                info = classInfo;
                classInfos.put(classInfo.getName(), classInfo);

                if (extractBaseInterfaces)
                    extractSuperInterfaces(classInfo);
            }
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        	AnnotationInfo annotationInfo = new AnnotationInfo(desc);
            info.getAnnotations().add(annotationInfo);
            getAnnotationInfos(annotationInfo.getName()).add(info);
            return new InfoBuildingVisitor(annotationInfo);
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            ClassInfo classInfo = ((ClassInfo) info);
            FieldInfo fieldInfo = new FieldInfo(classInfo, name, desc);
            classInfo.getFields().add(fieldInfo);
            return new InfoBuildingVisitor(fieldInfo);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        	ClassInfo classInfo = ((ClassInfo) info);
            MethodInfo methodInfo = new MethodInfo(classInfo, name, desc);
            classInfo.getMethods().add(methodInfo);
            return new InfoBuildingVisitor(methodInfo);
        }

        @Override
        public AnnotationVisitor visitParameterAnnotation(int param, String desc, boolean visible) {
            MethodInfo methodInfo = ((MethodInfo) info);
            List<AnnotationInfo> annotationInfos = methodInfo.getParameterAnnotations(param);
            AnnotationInfo annotationInfo = new AnnotationInfo(desc);
            annotationInfos.add(annotationInfo);
            return new InfoBuildingVisitor(annotationInfo);
        }
    }
	
    /*protected List<String> classSearchPath = new ArrayList<String>();
    
    protected boolean filter(String className){
    	if(classSearchPath.size() == 0){
    		return false;
    	}
    	
    	for(String path : classSearchPath){
            if(className.startsWith(path)){
            	return false; 
            }
    	}
    	return true;
    }*/
	
	protected boolean filter(String className){
    	return false;
    }
    
    public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
        List<Info> infos = annotated.get(annotation.getName());
        return CollectionUtils.isNotEmpty(infos);//infos != null && !infos.isEmpty();
    }
    
    private List<Info> getAnnotationInfos(String name) {
        List<Info> infos = annotated.get(name);
        if (infos == null) {
            infos = new ArrayList<Info>();
            annotated.put(name, infos);
        }
        return infos;
    }

    
    public List<Package> findAnnotatedPackages(Class<? extends Annotation> annotation) {
        classesNotLoaded.clear();
        List<Package> packages = new ArrayList<Package>();
        List<Info> infos = getAnnotationInfos(annotation.getName());
        for (Info info : infos) {
            if (info instanceof PackageInfo) {
                PackageInfo packageInfo = (PackageInfo) info;
                try {
                    Package pkg = packageInfo.get();
                    // double check via proper reflection
                    if (pkg.isAnnotationPresent(annotation)) {
                        packages.add(pkg);
                    }
                } catch (ClassNotFoundException e) {
                    classesNotLoaded.add(packageInfo.getName());
                }
            }
        }
        return packages;
    }
    public List<Class<?>> findAnnotatedClasses(Class<? extends Annotation> annotation) {
        classesNotLoaded.clear();
        List<Class<?>> classes = new ArrayList<Class<?>>();
        List<Info> infos = getAnnotationInfos(annotation.getName());
        for (Info info : infos) {
            if (info instanceof ClassInfo) {
                ClassInfo classInfo = (ClassInfo) info;
                try {
                    Class<?> clazz = classInfo.get();
                    // double check via proper reflection
                    if (clazz.isAnnotationPresent(annotation)) {
                        classes.add(clazz);
                    }
                } catch (Throwable e) {
                    if (LOG.isErrorEnabled())
                        LOG.error("Error loading class ["+classInfo.getName()+"]", e);
                    classesNotLoaded.add(classInfo.getName());
                }
            }
        }
        return classes;
    }

    public List<Method> findAnnotatedMethods(Class<? extends Annotation> annotation) {
        classesNotLoaded.clear();
        List<ClassInfo> seen = new ArrayList<ClassInfo>();
        List<Method> methods = new ArrayList<Method>();
        List<Info> infos = getAnnotationInfos(annotation.getName());
        for (Info info : infos) {
            if (info instanceof MethodInfo && !"<init>".equals(info.getName())) {
                MethodInfo methodInfo = (MethodInfo) info;
                ClassInfo classInfo = methodInfo.getDeclaringClass();

                if (seen.contains(classInfo)) continue;

                seen.add(classInfo);

                try {
                    Class<?> clazz = classInfo.get();
                    for (Method method : clazz.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(annotation)) {
                            methods.add(method);
                        }
                    }
                } catch (Throwable e) {
                    if (LOG.isErrorEnabled())
                        LOG.error("Error loading class ["+classInfo.getName()+"]", e);
                    classesNotLoaded.add(classInfo.getName());
                }
            }
        }
        return methods;
    }

    public List<Constructor<?>> findAnnotatedConstructors(Class<? extends Annotation> annotation) {
        classesNotLoaded.clear();
        List<ClassInfo> seen = new ArrayList<ClassInfo>();
        List<Constructor<?>> constructors = new ArrayList<Constructor<?>>();
        List<Info> infos = getAnnotationInfos(annotation.getName());
        for (Info info : infos) {
            if (info instanceof MethodInfo && "<init>".equals(info.getName())) {
                MethodInfo methodInfo = (MethodInfo) info;
                ClassInfo classInfo = methodInfo.getDeclaringClass();

                if (seen.contains(classInfo)) continue;

                seen.add(classInfo);

                try {
                    Class<?> clazz = classInfo.get();
                    for (Constructor<?> constructor : clazz.getConstructors()) {
                        if (constructor.isAnnotationPresent(annotation)) {
                            constructors.add(constructor);
                        }
                    }
                } catch (Throwable e) {
                    if (LOG.isErrorEnabled())
                        LOG.error("Error loading class ["+classInfo.getName()+"]", e);
                    classesNotLoaded.add(classInfo.getName());
                }
            }
        }
        return constructors;
    }

    public List<Field> findAnnotatedFields(Class<? extends Annotation> annotation) {
        classesNotLoaded.clear();
        List<ClassInfo> seen = new ArrayList<ClassInfo>();
        List<Field> fields = new ArrayList<Field>();
        List<Info> infos = getAnnotationInfos(annotation.getName());
        for (Info info : infos) {
            if (info instanceof FieldInfo) {
                FieldInfo fieldInfo = (FieldInfo) info;
                ClassInfo classInfo = fieldInfo.getDeclaringClass();

                if (seen.contains(classInfo)) continue;

                seen.add(classInfo);

                try {
                    Class<?> clazz = classInfo.get();
                    for (Field field : clazz.getDeclaredFields()) {
                        if (field.isAnnotationPresent(annotation)) {
                            fields.add(field);
                        }
                    }
                } catch (Throwable e) {
                    if (LOG.isErrorEnabled())
                        LOG.error("Error loading class ["+classInfo.getName()+"]", e);
                    classesNotLoaded.add(classInfo.getName());
                }
            }
        }
        return fields;
    }

}

