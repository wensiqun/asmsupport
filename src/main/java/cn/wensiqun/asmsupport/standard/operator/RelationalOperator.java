package cn.wensiqun.asmsupport.standard.operator;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.Equal;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.GreaterEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.GreaterThan;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.LessEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.LessThan;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.NotEqual;


/**
 * 生成关系运算操作
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface RelationalOperator {
    
    
    /**
     * 
     * The greater than operator
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 > factor2;</b>
     * </p>
     * 
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public GreaterThan _gt(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * The greater equals operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 >= factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public GreaterEqual _ge(Parameterized factor1, Parameterized factor2);
    
    /**
     * The less than equals.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 < factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public LessThan _lt(Parameterized factor1, Parameterized factor2);

    /**
     * 
     * The less equals operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 <= factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public LessEqual _le(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * The equal operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 == factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public Equal _eq(Parameterized factor1, Parameterized factor2);

    /**
     * 
     * The not equal operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 != factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public NotEqual _ne(Parameterized factor1, Parameterized factor2);
}
