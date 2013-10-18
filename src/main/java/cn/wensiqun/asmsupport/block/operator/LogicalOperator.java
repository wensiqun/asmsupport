package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.operators.logical.LogicalAnd;
import cn.wensiqun.asmsupport.operators.logical.LogicalOr;
import cn.wensiqun.asmsupport.operators.logical.LogicalXor;
import cn.wensiqun.asmsupport.operators.logical.Not;
import cn.wensiqun.asmsupport.operators.logical.ShortCircuitAnd;
import cn.wensiqun.asmsupport.operators.logical.ShortCircuitOr;

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
