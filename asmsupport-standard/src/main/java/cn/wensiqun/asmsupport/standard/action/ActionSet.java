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

import cn.wensiqun.asmsupport.standard.def.IParam;
import cn.wensiqun.asmsupport.standard.def.var.IVar;

/**
 * 
 * The all action.
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface ActionSet<
_P extends IParam,
_V extends IVar,
_IF, _While, _DoWhile, _ForEach, _TRY, _SYN>
extends
OperationSet<_P, _V>,
VariableAction<_P, _V>,
CreateBlockAction<_IF, _While, _DoWhile, _ForEach, _TRY, _SYN> {

    /**
     * Corresponding to break statement in loop.
     *
     */
    void break_();

    /**
     * Corresponding to continue statement in loop.
     *
     */
    void continue_();

    /**
     * Throw an exception.
     *
     * <p style="border:1px solid;width:500px;padding:10px;">
     * <b style="color:#FF3300">throw new RuntimeException()</b>
     * </p>
     *
     * Following code is the asmsupport code.
     * <p style="border:1px solid;width:500px;padding:10px;">
     * throwException(invokeConstructor(RuntimeException_ACLASS));
     * </p>
     *
     *
     * @param exception
     */
    void throw_(_P exception);

    /**
     * Corresponding to return statement with no return value.
     *
     */
    void return_();

    /**
     * Corresponding to return statement with return value.
     *
     * @param param
     *            return value.
     *
     */
    void return_(_P param);
}
