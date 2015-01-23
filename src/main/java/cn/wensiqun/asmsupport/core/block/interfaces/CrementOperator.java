package cn.wensiqun.asmsupport.core.block.interfaces;

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PostposeDecrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PostposeIncrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PreposeDecrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PreposeIncrment;


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
     * @return {@link PreposeDecrment}
     */
    public PreposeDecrment _preDec(Crementable crement);
    
    /**
     * 生成类似i--操作指令
     * 
     * @param crement
     * @return {@link PreposeIncrement}
     */
    public PostposeDecrment _postDec(Crementable crement);
    
    /**
     * 生成类似++i操作指令
     * 
     * @param crement
     * @return {@link PreposeIncrement}
     */
    public PreposeIncrment _preInc(Crementable crement);
    
    /**
     * 生成类似i++操作指令
     * 
     * @param crement
     * @return {@link PostposeIncrement}
     */
    public PostposeIncrment _postInc(Crementable crement);
    
}

