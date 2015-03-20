/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.standard.action;

import cn.wensiqun.asmsupport.core.block.control.condition.IFInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.TryInternal;
import cn.wensiqun.asmsupport.core.block.control.loop.WhileInternal;
import cn.wensiqun.asmsupport.core.block.sync.SynchronizedInternal;


/**
 * 
 * 创建程序块
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface CreateBlockAction<_IF , _While, _DoWhile, _ForEach, _Try, _Synchronized> {

    /**
     * 创建if程序块.
     * <ul>
     * <li>通过{@link IFInternal#else_(cn.wensiqun.asmsupport.block.control.Else)}或者
     * {@link cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf#else_(cn.wensiqun.asmsupport.block.control.Else)}
     * 创建else程序块
     * </li>
     * <li>
     * 通过{@link If#elseif(cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf)}或者
     * {@link cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf#elseif(cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf)}
     * 创建else if程序块
     *</li>
     * </ul>
     * 
     * @param ifs IF对象
     * @return {@link If}
     * @see IFInternal#else_(cn.wensiqun.asmsupport.block.control.Else)
     * @see IFInternal#elseif(cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf)
     * @see cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf#else_(cn.wensiqun.asmsupport.block.control.Else)
     * @see cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf#elseif(cn.wensiqun.asmsupport.block.classes.control.condition.ElseIf)
     */
    public _IF if_(_IF ifBlock);
    
    /**
     * 
     * 创建while循环程序块
     * 
     * @param whileLoop WhileLoop对象
     * @return {@link WhileInternal}
     */
    public _While while_(_While whileLoop);
    
    /**
     * 创建do...while程序块
     * 
     * @param doWhileLoop DoWhileLoop对象
     * @return {@link DoWhileLoop}
     */
    public _DoWhile dowhile(_DoWhile doWhile);
    
    /**
     * 创建for each程序块
     * 
     * @param forEach ForEachLoop对象
     * @return {@link ForEachLoop}
     */
    public _ForEach for_(final _ForEach forEach);
    
    
    /**
     * 创建try程序块.
     * 
     * <ul>
     * <li>通过{@link TryInternal#catch_(cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal)}或者
     * {@link cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal#catch_(cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal)}创建
     * catch程序块
     * </li>
     * <li>通过{@link TryInternal#finally_(cn.wensiqun.asmsupport.block.control.Finally)}或者
     * {@link cn.wensiqun.asmsupport.core.block.control.exception.CatchInternal#finally_(cn.wensiqun.asmsupport.block.control.Finally)}创建
     * finally程序块
     * </li>
     * </ul>
     * 
     * @param tryPara
     * @return
     */
    public _Try try_(final _Try tryPara);
    
    /**
     * 创建Synchronized同步块
     * @param sync Synchronized对象
     * @return {@link SynchronizedInternal}
     */
    public _Synchronized sync(_Synchronized sync);
	
}
