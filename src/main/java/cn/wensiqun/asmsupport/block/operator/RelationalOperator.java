package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.operators.relational.Equal;
import cn.wensiqun.asmsupport.operators.relational.GreaterEqual;
import cn.wensiqun.asmsupport.operators.relational.GreaterThan;
import cn.wensiqun.asmsupport.operators.relational.LessEqual;
import cn.wensiqun.asmsupport.operators.relational.LessThan;
import cn.wensiqun.asmsupport.operators.relational.NotEqual;

public interface RelationalOperator {
    
    
    /**
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public GreaterThan greaterThan(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public GreaterEqual greaterEqual(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public LessThan lessThan(Parameterized factor1, Parameterized factor2);

    /**
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public LessEqual lessEqual(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public Equal equal(Parameterized factor1, Parameterized factor2);

    /**
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public NotEqual notEqual(Parameterized factor1, Parameterized factor2);
}
