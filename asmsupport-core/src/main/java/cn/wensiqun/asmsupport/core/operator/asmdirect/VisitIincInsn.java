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
package cn.wensiqun.asmsupport.core.operator.asmdirect;

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class VisitIincInsn extends AbstractOperator {

	private int var;
	private int increment;
	
	protected VisitIincInsn(KernelProgramBlock block, int var, int increment) {
		super(block, Operator.COMMON);
		this.var = var;
		this.increment = increment;
	}

	@Override
	protected void doExecute(MethodContext context) {
		context.getInstructions().getMv().visitIincInsn(var, increment);
	}

}
