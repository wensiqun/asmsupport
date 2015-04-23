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
import cn.wensiqun.asmsupport.core.operator.asmdirect.VisitTypeInsn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;

public class VisitTypeInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	private String type;
	
	public VisitTypeInsnAdapter(int opcode, String type) {
		this.opcode = opcode;
		this.type = type;
	}

	@Override
	public void newVisitXInsnOperator(KernelProgramBlock block) {
		OperatorFactory.newOperator(VisitTypeInsn.class, 
				new Class[]{KernelProgramBlock.class, int.class, String.class}, 
				block, opcode, type);
		//new VisitTypeInsn(block, opcode, type);
	}

}
