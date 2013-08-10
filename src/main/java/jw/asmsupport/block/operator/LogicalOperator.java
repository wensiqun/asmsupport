package jw.asmsupport.block.operator;

import jw.asmsupport.Parameterized;
import jw.asmsupport.operators.logical.LogicalAnd;
import jw.asmsupport.operators.logical.LogicalOr;
import jw.asmsupport.operators.logical.LogicalXor;
import jw.asmsupport.operators.logical.Not;
import jw.asmsupport.operators.logical.ShortCircuitAnd;
import jw.asmsupport.operators.logical.ShortCircuitOr;

public interface LogicalOperator {
    /**
     * factor1 & factor2
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public LogicalAnd logicalAnd(Parameterized factor1, Parameterized factor2);
    
    /**
     * factor1 | factor2
     *  
     * @param factor1
     * @param factor2
     * @return
     */
    public LogicalOr logicalOr(Parameterized factor1, Parameterized factor2);

    /**
     * factor1 ^ factor2
     *  
     * @param factor1
     * @param factor2
     * @return
     */
    public LogicalXor logicalXor(Parameterized factor1, Parameterized factor2);
    
    /**
     * factor1 && factor2
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public ShortCircuitAnd conditionalAnd(Parameterized factor1, Parameterized factor2);
    
    /**
     * factor1 || factor2
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public ShortCircuitOr conditionalOr(Parameterized factor1, Parameterized factor2);
    
    /**
     * !factor
     * 
     * @param factor
     * @return
     */
    public Not not(Parameterized factor);
}
