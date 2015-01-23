package cn.wensiqun.asmsupport.core.block.interfaces;

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
 * @author wensiqun(at)gmail
 *
 */
public interface RelationalOperator {
    
    
    /**
     * 
     * 生成大于操作指令,对应下面的红色java代码
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
    public GreaterThan _greaterThan(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * 生成大于等于操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 >= factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public GreaterEqual _greaterEqual(Parameterized factor1, Parameterized factor2);
    
    /**
     * 生成小于操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 < factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public LessThan _lessThan(Parameterized factor1, Parameterized factor2);

    /**
     * 
     * 生成小于等于操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 <= factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public LessEqual _lessEqual(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * 生成等于操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 == factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public Equal _equals(Parameterized factor1, Parameterized factor2);

    /**
     * 
     * 生成不等于操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 != factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public NotEqual _notEquals(Parameterized factor1, Parameterized factor2);
}
