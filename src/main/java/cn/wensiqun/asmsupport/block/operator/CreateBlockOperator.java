package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.block.Synchronized;
import cn.wensiqun.asmsupport.block.control.DoWhileLoop;
import cn.wensiqun.asmsupport.block.control.ForEachLoop;
import cn.wensiqun.asmsupport.block.control.IF;
import cn.wensiqun.asmsupport.block.control.Try;
import cn.wensiqun.asmsupport.block.control.WhileLoop;


/**
 * 
 * 创建程序块
 * 
 * @author wensiqun(at)gmail
 *
 */
public interface CreateBlockOperator {

    /**
     * 创建if程序块.
     * <ul>
     * <li>通过{@link IF#elsethan(cn.wensiqun.asmsupport.block.control.Else)}或者
     * {@link cn.wensiqun.asmsupport.block.control.ElseIF#elsethan(cn.wensiqun.asmsupport.block.control.Else)}
     * 创建else程序块
     * </li>
     * <li>
     * 通过{@link IF#elseif(cn.wensiqun.asmsupport.block.control.ElseIF)}或者
     * {@link cn.wensiqun.asmsupport.block.control.ElseIF#elseif(cn.wensiqun.asmsupport.block.control.ElseIF)}
     * 创建else if程序块
     *</li>
     * </ul>
     * 
     * @param ifs IF对象
     * @return {@link IF}
     * @see IF#elsethan(cn.wensiqun.asmsupport.block.control.Else)
     * @see IF#elseif(cn.wensiqun.asmsupport.block.control.ElseIF)
     * @see cn.wensiqun.asmsupport.block.control.ElseIF#elsethan(cn.wensiqun.asmsupport.block.control.Else)
     * @see cn.wensiqun.asmsupport.block.control.ElseIF#elseif(cn.wensiqun.asmsupport.block.control.ElseIF)
     */
    public IF ifthan(IF ifs);
    
    /**
     * 
     * 创建while循环程序块
     * 
     * @param whileLoop WhileLoop对象
     * @return {@link WhileLoop}
     */
    public WhileLoop whileloop(WhileLoop whileLoop);
    
    /**
     * 创建do...while程序块
     * 
     * @param doWhileLoop DoWhileLoop对象
     * @return {@link DoWhileLoop}
     */
    public WhileLoop dowhile(DoWhileLoop doWhileLoop);
    
    /**
     * 创建for each程序块
     * 
     * @param forEach ForEachLoop对象
     * @return {@link ForEachLoop}
     */
    public ForEachLoop forEach(final ForEachLoop forEach);
    
    
    /**
     * 创建try程序块.
     * 
     * <ul>
     * <li>通过{@link Try#catchException(cn.wensiqun.asmsupport.block.control.Catch)}或者
     * {@link cn.wensiqun.asmsupport.block.control.Catch#catchException(cn.wensiqun.asmsupport.block.control.Catch)}创建
     * catch程序块
     * </li>
     * <li>通过{@link Try#finallyThan(cn.wensiqun.asmsupport.block.control.Finally)}或者
     * {@link cn.wensiqun.asmsupport.block.control.Catch#finallyThan(cn.wensiqun.asmsupport.block.control.Finally)}创建
     * finally程序块
     * </li>
     * </ul>
     * 
     * @param tryPara
     * @return
     */
    public Try tryDo(final Try tryPara);
    
    /**
     * 创建Synchronized同步块
     * @param sync Synchronized对象
     * @return {@link Synchronized}
     */
    public Synchronized syn(Synchronized sync);
	
}
