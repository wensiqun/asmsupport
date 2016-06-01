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
package cn.wensiqun.asmsupport.core.exception;

import cn.wensiqun.asmsupport.core.utils.memory.OperandStack;

public class InstructionException extends RuntimeException {

	private int insn;
	private OperandStack stackCopy;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2822269556587915571L;

	public InstructionException(String message, int insn, OperandStack stackCopy) {
		super(message);
		this.insn = insn;
		this.stackCopy = stackCopy;
	}

	public int getInsn() {
		return insn;
	}

	public OperandStack getStackCopy() {
		return stackCopy;
	}

}
