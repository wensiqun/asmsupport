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

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class VisitIntInsn extends AbstractOperator {

	private int opcode;
	private int operand;
	
	protected VisitIntInsn(KernelProgramBlock block, int opcode, int operand) {
		super(block, Operator.COMMON);
		this.opcode = opcode;
		this.operand = operand;
	}

	@Override
	protected void doExecute() {
        getInstructions().getMv().visitIntInsn(opcode, operand);
	}

}
