package cn.wensiqun.asmsupport.client.def.behavior;

import cn.wensiqun.asmsupport.client.def.Param;


/**
 * 
 * All operators about number.
 * 
 * @author WSQ
 *
 */
public interface NumBehavior extends CommonBehavior {

	
	/**
	 * Add a parameter to current number behavior
	 */
    NumBehavior add(Param para);
    
    /**
	 * Sub a parameter to current number behavior
	 */
    NumBehavior sub(Param para);
    
    /**
     * Do a multiplication between current number and argument
     */
    NumBehavior mul(Param para);
    
    /**
     * Do a division between current number and argument
     */
    NumBehavior div(Param para);
    
    /**
     * Do a modular arithmetic between current number and argument
     */
    NumBehavior mod(Param para);
    
    /**
     * Do a bit and between current number and argument
     * 
     */
    NumBehavior band(Param para);
    
    /**
     * Do a bit or between current number and argument
     */
    NumBehavior bor(Param para);
    
    /**
     * Do a bit xor between current number and argument
     */
    NumBehavior bxor(Param para);

    /**
     * Do a shift left between current number and argument
     */
    NumBehavior shl(Param para);

    /**
     * Do a shift right between current number and argument
     */
    NumBehavior shr(Param para);

    /**
     * Do a unassigned shift right between current number and argument
     */
    NumBehavior ushr(Param para);
    
    /**
     * Compare current number with argument, check the current number whether or not 
     * greater than the passed argument.
     */
    BoolBehavior gt(Param para);
    
    /**
     * Compare current number with argument, check the current number whether or not 
     * greater equal the passed argument.
     */
    BoolBehavior ge(Param para);
    
    /**
     * Compare current number with argument, check the current number whether or not 
     * less than the passed argument.
     */
    BoolBehavior lt(Param para);
    
    /**
     * Compare current number with argument, check the current number whether or not 
     * less equal the passed argument.
     */
    BoolBehavior le(Param para);
}
