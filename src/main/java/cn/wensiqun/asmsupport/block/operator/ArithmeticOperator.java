package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Addition;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Division;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Modulus;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Multiplication;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Subtraction;


/**
 * 生成算数运算
 *
 * @author wensiqun(at)gmail
 */
public interface ArithmeticOperator {
    
    /**
     * 生成加法操作指令例如：factor1 + factor2,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">factor1 + factor2;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link Addition}
     */
    public Addition add(Parameterized factor1, Parameterized factor2);

    /**
     * 生成减法操作指令例如：factor1 - factor2,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">factor1 - factor12;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link Subtraction}
     */
    public Subtraction sub(Parameterized factor1, Parameterized factor2);
    
    /**
     * 生成乘法操作指令例如：factor1 * factor2,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">factor1 * factor12;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link Multiplication}
     */
    public Multiplication mul(Parameterized factor1, Parameterized factor2);
    
    /**
     * 生成除法操作指令例如：factor1 / factor2,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">factor1 / factor12;</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link Division}
     */
    public Division div(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * 生成取模操作指令例如：factor1 % factor2,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int count = <b style="color:#FF3300">factor1 % factor12;</b>
     * </p>
     * 
     * 
     * @param factor1
     * @param factor2
     * @return {@link Modulus}
     */
    public Modulus mod(Parameterized factor1, Parameterized factor2);
    
}
