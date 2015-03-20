package cn.wensiqun.asmsupport.standard.action;

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PostposeDecrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PostposeIncrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PreposeDecrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PreposeIncrment;


/**
 * 创建自增或自减操作
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface CrementAction {
    
    /**
     * 生成类似--i操作指令
     * 
     * @param crement
     * @return {@link PreposeDecrment}
     */
    public PreposeDecrment predec(Crementable crement);
    
    /**
     * 生成类似i--操作指令
     * 
     * @param crement
     * @return {@link PreposeIncrement}
     */
    public PostposeDecrment postdec(Crementable crement);
    
    /**
     * 生成类似++i操作指令
     * 
     * @param crement
     * @return {@link PreposeIncrement}
     */
    public PreposeIncrment preinc(Crementable crement);
    
    /**
     * 生成类似i++操作指令
     * 
     * @param crement
     * @return {@link PostposeIncrement}
     */
    public PostposeIncrment postinc(Crementable crement);
    
}

