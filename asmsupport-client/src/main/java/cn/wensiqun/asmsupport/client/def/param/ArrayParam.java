package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;

/**
 * All operators about array type.
 * 
 * @author WSQ
 */
public interface ArrayParam extends ObjectParam {

	/**
	 * Load array element from current array.
	 * 
	 * @param firstDim the first dimension value
	 * @param dims if this array is multiple dimension, specify other dimensions value
	 */
    UncertainParam load(Param firstDim, Param... dims);
    
    /**
     * Get length from this array
     * 
     * @param dims if this array is on or multiple dimension, specify dimensions value
     */
    NumParam length(Param... dims);
    
    /**
     * Set value to current array to specify dimension
     * 
     * @param value the value
     * @param firstDim the first dimension value
     * @param dims if this array is multiple dimension, specify other dimensions value
     */
    UncertainParam store(Param value, Param firstDim, Param... dims);
}
