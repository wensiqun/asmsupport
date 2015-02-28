package cn.wensiqun.asmsupport.standard.operator;

import cn.wensiqun.asmsupport.core.block.SynchronizedInternal;
import cn.wensiqun.asmsupport.core.block.control.condition.IFInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.TryInternal;
import cn.wensiqun.asmsupport.core.block.control.loop.WhileInternal;


/**
 * 
 * 创建程序块
 * 
 * @author wensiqun(at)gmail
 *
 */
public interface CreateBlockOperator<_IF , _While, _DoWhile, _ForEach, _Try, _Synchronized> {

    /**
     * 创建if程序块.
     * <ul>
     * <li>通过{@link IFInternal#_else(cn.wensiqun.asmsupport.block.control.Else)}或者
     * {@link cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf#_else(cn.wensiqun.asmsupport.block.control.Else)}
     * 创建else程序块
     * </li>
     * <li>
     * 通过{@link If#_elseif(cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf)}或者
     * {@link cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf#_elseif(cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf)}
     * 创建else if程序块
     *</li>
     * </ul>
     * 
     * @param ifs IF对象
     * @return {@link If}
     * @see IFInternal#_else(cn.wensiqun.asmsupport.block.control.Else)
     * @see IFInternal#_elseif(cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf)
     * @see cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf#_else(cn.wensiqun.asmsupport.block.control.Else)
     * @see cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf#_elseif(cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf)
     */
    public _IF _if(_IF ifBlock);
    
    /**
     * 
     * 创建while循环程序块
     * 
     * @param whileLoop WhileLoop对象
     * @return {@link WhileInternal}
     */
    public _While _while(_While whileLoop);
    
    /**
     * 创建do...while程序块
     * 
     * @param doWhileLoop DoWhileLoop对象
     * @return {@link DoWhileLoop}
     */
    public _DoWhile _dowhile(_DoWhile doWhile);
    
    /**
     * 创建for each程序块
     * 
     * @param forEach ForEachLoop对象
     * @return {@link ForEachLoop}
     */
    public _ForEach _for(final _ForEach forEach);
    
    
    /**
     * 创建try程序块.
     * 
     * <ul>
     * <li>通过{@link TryInternal#_catch(cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal)}或者
     * {@link cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal#_catch(cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal)}创建
     * catch程序块
     * </li>
     * <li>通过{@link TryInternal#_finally(cn.wensiqun.asmsupport.block.control.Finally)}或者
     * {@link cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal#_finally(cn.wensiqun.asmsupport.block.control.Finally)}创建
     * finally程序块
     * </li>
     * </ul>
     * 
     * @param tryPara
     * @return
     */
    public _Try _try(final _Try tryPara);
    
    /**
     * 创建Synchronized同步块
     * @param sync Synchronized对象
     * @return {@link SynchronizedInternal}
     */
    public _Synchronized _sync(_Synchronized sync);
	
}
