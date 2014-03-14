package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.Crementable;
import cn.wensiqun.asmsupport.operators.numerical.crement.AfterDecrement;
import cn.wensiqun.asmsupport.operators.numerical.crement.AfterIncrement;
import cn.wensiqun.asmsupport.operators.numerical.crement.BeforeDecrement;
import cn.wensiqun.asmsupport.operators.numerical.crement.BeforeIncrement;


/**
 * 创建自增或自减操作
 * 
 * @author wensiqun(at)gmail
 *
 */
public interface CrementOperator {
    
    /**
     * 生成类似--i操作指令
     * 
     * @param crement
     * @return {@link BeforeDecrement}
     */
    public BeforeDecrement beforeDec(Crementable crement);
    
    /**
     * 生成类似i--操作指令
     * 
     * @param crement
     * @return {@link AfterDecrement}
     */
    public AfterDecrement afterDec(Crementable crement);
    
    /**
     * 生成类似++i操作指令
     * 
     * @param crement
     * @return {@link BeforeIncrement}
     */
    public BeforeIncrement beforeInc(Crementable crement);
    
    /**
     * 生成类似i++操作指令
     * 
     * @param crement
     * @return {@link AfterIncrement}
     */
    public AfterIncrement afterInc(Crementable crement);
    
}
