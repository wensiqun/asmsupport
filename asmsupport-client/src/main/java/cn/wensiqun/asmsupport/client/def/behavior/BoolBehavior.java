package cn.wensiqun.asmsupport.client.def.behavior;

import cn.wensiqun.asmsupport.client.def.Param;

/**
 * All operators about boolean type.
 * 
 * @author WSQ
 */
public interface BoolBehavior extends CommonBehavior {

	/**
	 * Do condition and operator with passed parameter.
	 */
    BoolBehavior and(Param param);
    
    /**
	 * Do condition and operator with boolean parameter.
     */
    BoolBehavior and(boolean param);

    /**
	 * Do condition or operator with passed parameter.
     */
    BoolBehavior or(Param param);

    /**
	 * Do condition or operator with boolean parameter.
     */
    BoolBehavior or(boolean param);

    /**
	 * Do logic and operator with passed parameter.
     */
    BoolBehavior logicAnd(Param param);
    
    /**
	 * Do logic and operator with boolean parameter.
     */
    BoolBehavior logicAnd(boolean param);
    
    /**
	 * Do logic and operator with boolean parameter.
     */
    BoolBehavior logicOr(Param param);
    
    /**
	 * Do logic or operator with passed parameter.
     */
    BoolBehavior logicOr(boolean param);

    /**
	 * Do logic xor operator with passed parameter.
     */
    BoolBehavior logicXor(Param param);

    /**
	 * Do logic xor operator with boolean parameter.
     */
    BoolBehavior logicXor(boolean param);
    
}
