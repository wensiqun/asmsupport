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
package cn.wensiqun.asmsupport.core.asm.adapter;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.asmdirect.VisitVarInsn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;

public class VisitVarInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	private int var;
	
	public VisitVarInsnAdapter(int opcode, int var) {
		this.opcode = opcode;
		this.var = var;
	}

	@Override
	public void newVisitXInsnOperator(KernelProgramBlock block) {
		OperatorFactory.newOperator(VisitVarInsn.class, 
				new Class[]{KernelProgramBlock.class, int.class, int.class}, 
				block, opcode, var);
		//new VisitVarInsn(block, opcode, var);
	}

}
