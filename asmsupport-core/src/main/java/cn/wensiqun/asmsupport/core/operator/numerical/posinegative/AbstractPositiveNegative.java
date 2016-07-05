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
package cn.wensiqun.asmsupport.core.operator.numerical.posinegative;

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.numerical.AbstractNumerical;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class AbstractPositiveNegative extends AbstractNumerical {

	protected KernelParam factor;

	private boolean byOtherUsed;

	protected AbstractPositiveNegative(KernelProgramBlock block,
			KernelParam factor, Operator operator) {
		super(block, operator);
		this.factor = factor;
	}

	@Override
	public void loadToStack(MethodContext context) {
		this.execute(context);
	}

	@Override
	public void execute(MethodContext context) {
		if (byOtherUsed) {
			super.execute(context);
		} else {
			throw new ArithmeticException(
					"the operator has not been used by other operator.");
		}

	}

	@Override
	protected void initAdditionalProperties() {
		IClass fatCls = factor.getResultType();
		targetClass = IClassUtils.getPrimitiveAClass(fatCls);
	}

	@Override
	protected void verifyArgument() {
		IClass fatCls = factor.getResultType();
		if (!IClassUtils.isArithmetical(fatCls)) {
			throw new ArithmeticException(
					"cannot execute arithmetic operator whit " + fatCls);
		}
	}

	@Override
	protected void checkAsArgument() {
		factor.asArgument();
	}

	@Override
	public void asArgument() {
		getParent().removeChild(this);
		byOtherUsed = true;
	}

	@Override
	protected void factorToStack(MethodContext context) {
		factor.loadToStack(context);
		context.getInstructions().unbox(factor.getResultType().getType());
	}

}
