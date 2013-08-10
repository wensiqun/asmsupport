package jw.asmsupport.block.operator;

import jw.asmsupport.Crementable;
import jw.asmsupport.operators.numerical.crement.AfterDecrement;
import jw.asmsupport.operators.numerical.crement.AfterIncrement;
import jw.asmsupport.operators.numerical.crement.BeforeDecrement;
import jw.asmsupport.operators.numerical.crement.BeforeIncrement;

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
