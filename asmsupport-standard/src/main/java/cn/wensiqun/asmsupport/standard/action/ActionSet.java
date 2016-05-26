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
import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;
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
_F extends IFieldVar, 
_IF, _While, _DoWhile, _ForEach, _TRY, _SYN>
extends
GenericAction<_P>,
ValueAction<_P>,
ClassHolder,
MethodInvokeAction<_P, _F>, 
ArrayAction<_P>, 
ArithmeticAction<_P>, 
BitwiseAction<_P>, 
CrementAction<_P>,
RelationalAction<_P>, 
LogicalAction<_P>,
VariableAction<_P, _V>,
CreateBlockAction<_IF, _While, _DoWhile, _ForEach, _TRY, _SYN> {

}
