package jw.asmsupport.utils.finder.clazz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TypeHierarchyClassFinder extends ClassFetcher {
	
	private static final Log LOG = LogFactory.getLog(TypeHierarchyClassFinder.class);
	
    private Class<?> parentClass;
    private List<Class<?>> subClasses = new ArrayList<Class<?>>();
    
    
	public TypeHierarchyClassFinder(ClassLoader classLoader, Class<?> parentClass) throws Exception {
		super(classLoader);
		this.parentClass = parentClass;
		init();
	}
	
	private void init(){
        for (String className : classNames) {
			try {
				Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(className);
	        	if(parentClass.isAssignableFrom(cls)){
	        		subClasses.add(cls);
	        	}
			} catch (Throwable e) {
				
			}
        }
	}
	
	public List<Class<?>> subClasses(){
		return subClasses;
	}
    
}
