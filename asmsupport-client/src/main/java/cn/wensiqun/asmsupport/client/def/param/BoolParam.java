package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;

/**
 * All operators about boolean type.
 * 
 * @author WSQ
 */
public interface BoolParam extends CommonParam {

	/**
	 * Do condition and operator with passed parameter.
	 */
    BoolParam and(Param param);
    
    /**
	 * Do condition and operator with boolean parameter.
     */
    BoolParam and(boolean param);

    /**
	 * Do condition or operator with passed parameter.
     */
    BoolParam or(Param param);

    /**
	 * Do condition or operator with boolean parameter.
     */
    BoolParam or(boolean param);

    /**
	 * Do logic and operator with passed parameter.
     */
    BoolParam logicAnd(Param param);
    
    /**
	 * Do logic and operator with boolean parameter.
     */
    BoolParam logicAnd(boolean param);
    
    /**
	 * Do logic and operator with boolean parameter.
     */
    BoolParam logicOr(Param param);
    
    /**
	 * Do logic or operator with passed parameter.
     */
    BoolParam logicOr(boolean param);

    /**
	 * Do logic xor operator with passed parameter.
     */
    BoolParam logicXor(Param param);

    /**
	 * Do logic xor operator with boolean parameter.
     */
    BoolParam logicXor(boolean param);
    
}
