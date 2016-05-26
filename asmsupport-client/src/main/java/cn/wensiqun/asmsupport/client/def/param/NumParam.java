package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.def.Param;


/**
 * 
 * All operators about number.
 * 
 * @author WSQ
 *
 */
public interface NumParam extends CommonParam {

	
	/**
	 * Add a parameter to current number behavior
	 */
    NumParam add(Param para);
    
    /**
	 * Sub a parameter to current number behavior
	 */
    NumParam sub(Param para);
    
    /**
     * Do a multiplication between current number and argument
     */
    NumParam mul(Param para);
    
    /**
     * Do a division between current number and argument
     */
    NumParam div(Param para);
    
    /**
     * Do a modular arithmetic between current number and argument
     */
    NumParam mod(Param para);
    
    /**
     * Do a bit and between current number and argument
     * 
     */
    NumParam band(Param para);
    
    /**
     * Do a bit or between current number and argument
     */
    NumParam bor(Param para);
    
    /**
     * Do a bit xor between current number and argument
     */
    NumParam bxor(Param para);

    /**
     * Do a shift left between current number and argument
     */
    NumParam shl(Param para);

    /**
     * Do a shift right between current number and argument
     */
    NumParam shr(Param para);

    /**
     * Do a unassigned shift right between current number and argument
     */
    NumParam ushr(Param para);
    
    /**
     * Compare current number with argument, check the current number whether or not 
     * greater than the passed argument.
     */
    BoolParam gt(Param para);
    
    /**
     * Compare current number with argument, check the current number whether or not 
     * greater equal the passed argument.
     */
    BoolParam ge(Param para);
    
    /**
     * Compare current number with argument, check the current number whether or not 
     * less than the passed argument.
     */
    BoolParam lt(Param para);
    
    /**
     * Compare current number with argument, check the current number whether or not 
     * less equal the passed argument.
     */
    BoolParam le(Param para);
}
