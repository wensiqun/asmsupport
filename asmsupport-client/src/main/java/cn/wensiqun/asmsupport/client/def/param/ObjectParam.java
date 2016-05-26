package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;


/**
 * All operator about Object
 * 
 * @author WSQ
 *
 */
public interface ObjectParam extends CommonParam {

	/**
	 * Call a method from current object.
	 */
    ObjectParam call(String methodName, Param... arguments);
    
    /**
     * Checkcast current object to specify type.
     */
    ObjectParam cast(Class<?> type);

    /**
     * Checkcast current object to specify type.
     */
    ObjectParam cast(IClass type);
    
    /**
     * Do an instanceof operator from current object.
     */
    BoolParam instanceof_(Class<?> type);

    /**
     * Do an instanceof operator from current object.
     */
    BoolParam instanceof_(IClass type);
    
}
