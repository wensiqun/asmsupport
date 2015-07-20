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
/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.numerical.crement;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.numerical.AbstractNumerical;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

public abstract class AbstractCrement extends AbstractNumerical {

	private KernelParam factor;

	protected AbstractCrement(KernelProgramBlock block, KernelParam factor,
			Operator operator) {
		super(block, operator);
		ClassHolder classHolder = block.getClassHolder();
		IClass resultType = factor.getResultType();
		if(resultType != null &&
		   !classHolder.getType(boolean.class).equals(resultType) &&
		   !classHolder.getType(Boolean.class).equals(resultType) &&
		   (IClassUtils.isPrimitiveWrapAClass(resultType) || 
		   resultType.isPrimitive())) {
			this.factor = factor;
		} else {
			throw new ASMSupportException("Can't do '" + operator + "' on : " + factor);
		}
	}

	@Override
	public void loadToStack(KernelProgramBlock block) {
		execute();
	}

	@Override
	public void asArgument() {
		block.removeExe(this);
	}

	@Override
	protected void factorToStack() {

	}

	@Override
	protected void initAdditionalProperties() {
		targetClass = factor.getResultType();
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
	protected void doExecute() {
		Type type = targetClass.getType();
		boolean asArgument = !block.getQueue().contains(this);
		boolean isPos = Operator.POS_DEC.equals(getOperatorSymbol())
				|| Operator.POS_INC.equals(getOperatorSymbol());
		boolean isInc = Operator.PRE_INC.equals(getOperatorSymbol())
				|| Operator.POS_INC.equals(getOperatorSymbol());
		if (factor instanceof LocalVariable
				&& Type.INT_TYPE.equals(targetClass.getType())) {
			if (asArgument && isPos) {
				factor.loadToStack(block);
			}

			insnHelper.iinc(((LocalVariable) factor).getScopeLogicVar()
					.getInitStartPos(), isInc ? 1 : -1);

			if (asArgument && !isPos) {
				factor.loadToStack(block);
			}
		} else {
			IClass primitiveClass = IClassUtils.getPrimitiveAClass(targetClass);
			Type primitiveType = primitiveClass.getType();

			// factor load to stack
			factor.loadToStack(block);

			if (asArgument && isPos)
				insnHelper.dup(type);

			// unbox
			autoCast(targetClass, primitiveClass, true);

			// load 1 to stack
			getIncreaseValue().loadToStack(block);

			// generate xadd/xsub for decrement
			if (isInc) {
				insnHelper.add(primitiveType);
			} else {
				insnHelper.sub(primitiveType);
			}

			// box and cast
			autoCast(
					primitiveType.getSort() <= Type.INT ? block.getClassHolder().getType(int.class)
							: primitiveClass, targetClass, true);

			if (asArgument && !isPos)
				insnHelper.dup(type);

			insnHelper.commonPutField((ExplicitVariable) factor);
		}
	}

	private Value getIncreaseValue() {
		IClass type = factor.getResultType();
		ClassHolder holder = block.getClassHolder();
		if (type.equals(holder.getType(double.class))
				|| type.equals(holder.getType(Double.class))) {
			return block.val(1d);
		} else if (type.equals(holder.getType(float.class))
				|| type.equals(holder.getType(Float.class))) {
			return block.val(1f);
		} else if (type.equals(holder.getType(long.class))
				|| type.equals(holder.getType(Long.class))) {
			return block.val(1L);
		} else {
			return block.val(1);
		}
	}

}
