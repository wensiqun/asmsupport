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

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

public class UnreachableCodeException extends ASMSupportException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6866532878000484233L;

	@SuppressWarnings("unused")
	private KernelProgramBlock block;
	
	@SuppressWarnings("unused")
	private AbstractOperator unreachableOperator;
	
	public UnreachableCodeException(KernelProgramBlock block, AbstractOperator unreachableOperator) {
		super();
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}

	public UnreachableCodeException(String message, Throwable cause, KernelProgramBlock block, AbstractOperator unreachableOperator) {
		super(message, cause);
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}

	public UnreachableCodeException(String message, KernelProgramBlock block, AbstractOperator unreachableOperator) {
		super(message);
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}

	public UnreachableCodeException(Throwable cause, KernelProgramBlock block, AbstractOperator unreachableOperator) {
		super(cause);
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}
	
	

}
