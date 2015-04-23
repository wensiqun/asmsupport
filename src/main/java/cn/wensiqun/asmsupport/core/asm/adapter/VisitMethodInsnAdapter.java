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
import cn.wensiqun.asmsupport.core.operator.asmdirect.VisitMethodInsn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;

public class VisitMethodInsnAdapter implements VisitXInsnAdapter {

	private int opcode;
	private String owner;
	private String name;
	private String desc;
	private boolean itf;

	public VisitMethodInsnAdapter(int opcode, String owner, String name,
			String desc, boolean itf) {
		this.opcode = opcode;
		this.owner = owner;
		this.name = name;
		this.desc = desc;
		this.itf = itf;
	}

	@Override
	public void newVisitXInsnOperator(KernelProgramBlock block) {
		OperatorFactory.newOperator(VisitMethodInsn.class, 
				new Class[]{KernelProgramBlock.class, int.class, String.class, String.class, String.class, boolean.class}, 
				block, opcode, owner, name, desc, itf);
		//new VisitMethodInsn(block, opcode, owner, name, desc);
	}

}
