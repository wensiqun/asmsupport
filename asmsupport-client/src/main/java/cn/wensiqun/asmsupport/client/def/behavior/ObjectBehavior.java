package cn.wensiqun.asmsupport.client.def.behavior;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;


/**
 * All operator about Object
 * 
 * @author WSQ
 *
 */
public interface ObjectBehavior extends CommonBehavior {

	/**
	 * Call a method from current object.
	 */
    ObjectBehavior call(String methodName, Param... arguments);
    
    /**
     * Checkcast current object to specify type.
     */
    ObjectBehavior cast(Class<?> type);

    /**
     * Checkcast current object to specify type.
     */
    ObjectBehavior cast(AClass type);
    
    /**
     * Do an instanceof operator from current object.
     */
    BoolBehavior instanceof_(Class<?> type);

    /**
     * Do an instanceof operator from current object.
     */
    BoolBehavior instanceof_(AClass type);
    
}
