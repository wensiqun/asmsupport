package cn.wensiqun.asmsupport.core.clazz;

import cn.wensiqun.asmsupport.core.exception.ClassException;
import cn.wensiqun.asmsupport.core.utils.lang.ClassUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AClassFactory {
	
	
	private static AClass getAClass(Class<?> cls){
		AClass aclass;
		if(cls.isArray()){
			aclass = getArrayClass(ClassUtils.getRootComponentType(cls), Type.getType(cls).getDimensions());
		}else{
			aclass = new ProductClass(cls);
		}
		return aclass;
	}
	
	
    /**
     * 通过一个已经存在的Class获取一个AClass
     * @param cls
     * @return
     */
    public static AClass getProductClass(Class<?> cls){
    	return getAClass(cls);
    }
    
    
    /**
     * 
     * @param arrayCls
     * @return
     */
    public static ArrayClass getArrayClass(Class<?> arrayCls){
        if(!arrayCls.isArray()){
            throw new ClassException("the class" + arrayCls + " is not an array class");
        }
        return (ArrayClass) getAClass(arrayCls);
    }
    
    /**
     * 获取数组class
     * @param cls
     * @param dim
     * @return
     */
    public static ArrayClass getArrayClass(Class<?> cls, int dim){
        if(cls.isArray()){
            throw new ClassException("the class " + cls + " has already an array class");
        }
        return new ArrayClass(getProductClass(cls), dim);
    }
    
    /**
     * 获取数组class
     * @param cls
     * @param dim
     * @return
     */
    public static ArrayClass getArrayClass(AClass rootComponent, int dim){
    	if(rootComponent.isArray()){
            throw new ClassException("the class " + rootComponent + " has already an array class");
        }
		
        StringBuilder arrayClassDesc = new StringBuilder();
        int tmpDim = dim;
        while(tmpDim-- > 0){
        	arrayClassDesc.append("[");
        }
        arrayClassDesc.append(rootComponent.getDescription());
        return new ArrayClass(rootComponent, dim);
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
    protected static SemiClass newSemiClass(int version, int access, String name, Class<?> superCls, Class<?>[] interfaces){
        return new SemiClass(version, access, name, superCls, interfaces);
    }
    

}
