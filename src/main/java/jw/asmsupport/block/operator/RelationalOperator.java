package jw.asmsupport.block.operator;

import jw.asmsupport.Parameterized;
import jw.asmsupport.operators.relational.Equal;
import jw.asmsupport.operators.relational.GreaterEqual;
import jw.asmsupport.operators.relational.GreaterThan;
import jw.asmsupport.operators.relational.LessEqual;
import jw.asmsupport.operators.relational.LessThan;
import jw.asmsupport.operators.relational.NotEqual;

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
