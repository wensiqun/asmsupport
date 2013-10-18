package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.Crementable;
import cn.wensiqun.asmsupport.operators.numerical.crement.AfterDecrement;
import cn.wensiqun.asmsupport.operators.numerical.crement.AfterIncrement;
import cn.wensiqun.asmsupport.operators.numerical.crement.BeforeDecrement;
import cn.wensiqun.asmsupport.operators.numerical.crement.BeforeIncrement;

public interface CrementOperator {
    
    /**
     * --i
     * 
     * @param crement
     * @return
     */
    public BeforeDecrement beforeDec(Crementable crement);
    
    /**
     * i--
     * 
     * @param crement
     * @return
     */
    public AfterDecrement afterDec(Crementable crement);
    
    /**
     * ++i
     * 
     * @param crement
     * @return
     */
    public BeforeIncrement beforeInc(Crementable crement);
    
    /**
     * i++
     * 
     * @param crement
     * @return
     */
    public AfterIncrement afterInc(Crementable crement);
    
}
