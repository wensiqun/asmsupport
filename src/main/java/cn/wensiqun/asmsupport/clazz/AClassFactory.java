package cn.wensiqun.asmsupport.clazz;

import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.exception.ClassException;
import cn.wensiqun.asmsupport.utils.ClassUtils;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AClassFactory {
    
/*	private final static Map<String, AClass> cache = new Hashtable<String, AClass>();
	
	protected static void cleanCach(){
		cache.clear();
	}
	
	private static void addToCache(String s, AClass a){
		cache.put(s, a);
	}*/
	
	private static AClass getAndAdd(Class<?> cls){
	    String name = cls.getName();
		/*if(cache.containsKey(name)){
	    	return cache.get(name);
	    }*/
	
		AClass aclass = null;
		if(cls.isArray()){
			aclass = getArrayClass(ClassUtils.getRootComponentType(cls), Type.getType(cls).getDimensions());
		}else{
			aclass = new ProductClass(cls);
		}
		//addToCache(cls.getName(), aclass);
		return aclass;
	}
	
	
	private static ArrayClass getAndAddForArray(AClass rootComponent, int dim){
        if(rootComponent.isArray()){
            throw new ClassException("the class " + rootComponent + " has already a array clss");
        }
		
        StringBuilder arrayClassDesc = new StringBuilder();
        int tmpDim = dim;
        while(tmpDim-- > 0){
        	arrayClassDesc.append("[");
        }
        arrayClassDesc.append(rootComponent.getDescription());
        return new ArrayClass(rootComponent, dim);
        /*String arrayClassDescStr = arrayClassDesc.toString();
        if(cache.containsKey(arrayClassDescStr)){
        	ArrayClass ac = (ArrayClass) cache.get(arrayClassDescStr);
        	return ac;
        }else{
        	ArrayClass arrayClass = new ArrayClass(rootComponent, dim);
        	addToCache(arrayClassDescStr, arrayClass);
        	return arrayClass;
        }*/
	}
	
    /**
     * 通过一个已经存在的Class获取一个AClass
     * @param cls
     * @return
     */
    public static AClass getProductClass(Class<?> cls){
    	return getAndAdd(cls);
    }
    
    
    /**
     * 
     * @param arrayCls
     * @return
     */
    public static ArrayClass getArrayClass(Class<?> arrayCls){
        if(!arrayCls.isArray()){
            throw new ClassException("the class" + arrayCls + " is not a array class");
        }
        return (ArrayClass) getProductClass(arrayCls);
    }
    
    /**
     * 获取数组class
     * @param cls
     * @param dim
     * @return
     */
    public static ArrayClass getArrayClass(Class<?> cls, int dim){
       return new ArrayClass(getProductClass(cls), dim);
    }
    
    /**
     * 获取数组class
     * @param cls
     * @param dim
     * @return
     */
    public static ArrayClass getArrayClass(AClass cls, int dim){
        return getAndAddForArray(cls, dim);
    }
    
    /**
     * 创建一个新的Class
     * @param version
     * @param access
     * @param name
     * @param superCls
     * @param interfaces
     * @return
     */
    protected static SemiClass newSemiClass(int version, int access, String name, Class<?> superCls,
            Class<?>[] interfaces){
        return new SemiClass(version, access, name, superCls, interfaces);
    }
    

}
