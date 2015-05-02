package cn.wensiqun.asmsupport.client.def.behavior;

import cn.wensiqun.asmsupport.client.def.Param;

/**
 * All operators about array type.
 * 
 * @author WSQ
 */
public interface ArrayBehavior extends ObjectBehavior {

	/**
	 * Load array element from current array.
	 * 
	 * @param firstDim the first dimension value
	 * @param dims if this array is multiple dimension, specify other dimensions value
	 */
    UncertainBehavior load(Param firstDim, Param... dims);
    
    /**
     * Get length from this array
     * 
     * @param dims if this array is on or multiple dimension, specify dimensions value
     */
    NumBehavior length(Param... dims);
    
    /**
     * Set value to current array to specify dimension
     * 
     * @param value the value
     * @param firstDim the first dimension value
     * @param dims if this array is multiple dimension, specify other dimensions value
     */
    UncertainBehavior store(Param value, Param firstDim, Param... dims);
}
