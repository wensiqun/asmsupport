package cn.wensiqun.asmsupport.standard.action;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.operator.logical.LogicalAnd;
import cn.wensiqun.asmsupport.core.operator.logical.LogicalOr;
import cn.wensiqun.asmsupport.core.operator.logical.LogicalXor;
import cn.wensiqun.asmsupport.core.operator.logical.Not;
import cn.wensiqun.asmsupport.core.operator.logical.ShortCircuitAnd;
import cn.wensiqun.asmsupport.core.operator.logical.ShortCircuitOr;


/**
 * 逻辑操作
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface LogicalAction {
    /**
     * 生成逻辑与操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 & factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link LogicalAnd}
     */
    public LogicalAnd logicalAnd(Parameterized factor1, Parameterized factor2);
    
    /**
     * 生成逻辑或操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 | factor2;</b>
     * </p>
     * 
     *  
     * @param factor1
     * @param factor2
     * @return {@link LogicalOr}
     */
    public LogicalOr logicalOr(Parameterized factor1, Parameterized factor2);

    /**
     * 
     * 生成逻辑异或操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 ^ factor2;</b>
     * </p>
     *  
     * @param factor1
     * @param factor2
     * @return {@link LogicalXor}
     */
    public LogicalXor logicalXor(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * 生成条件与操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 && factor2;</b>
     * </p>
     * 
     * 
     * @param factor1
     * @param factor2
     * @return {@link ShortCircuitAnd}
     */
    public ShortCircuitAnd and(Parameterized factor1, Parameterized factor2, Parameterized... otherFactors);
    
    /**
     * 
     * 生成条件或操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 || factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link ShortCircuitOr}
     */
    public ShortCircuitOr or(Parameterized factor1, Parameterized factor2, Parameterized... otherFactors);
    
    /**
     * 生成条件非操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">!factor;</b>
     * </p>
     * 
     * 
     * @param factor
     * @return {@link Not}
     */
    public Not no(Parameterized factor);
}
