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

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.numerical.AbstractNumerical;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

public abstract class AbstractCrement extends AbstractNumerical {

	private KernelParam factor;

	protected AbstractCrement(KernelProgramBlock block, KernelParam factor,
			Operator operator) {
		super(block, operator);
		IClass resultType = factor.getResultType();
		if(resultType != null &&
		   !getType(boolean.class).equals(resultType) &&
		   !getType(Boolean.class).equals(resultType) &&
		   (IClassUtils.isPrimitiveWrapAClass(resultType) || 
		   resultType.isPrimitive())) {
			this.factor = factor;
		} else {
			throw new ASMSupportException("Can't do '" + operator + "' on : " + factor);
		}
	}

	@Override
	public void loadToStack(MethodContext context) {
		execute(context);
	}

	@Override
	public void asArgument() {
		getParent().removeChild(this);
	}

	@Override
	protected void factorToStack(MethodContext context) {}

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
	protected void doExecute(MethodContext context) {
		Instructions instructions = context.getInstructions();
		Type type = targetClass.getType();
		KernelProgramBlock block = getParent();
		boolean asArgument = !block.getChildren().contains(this);
		boolean isPos = Operator.POS_DEC.equals(getOperatorSymbol())
				|| Operator.POS_INC.equals(getOperatorSymbol());
		boolean isInc = Operator.PRE_INC.equals(getOperatorSymbol())
				|| Operator.POS_INC.equals(getOperatorSymbol());
		if (factor instanceof LocalVariable
				&& Type.INT_TYPE.equals(targetClass.getType())) {
			if (asArgument && isPos) {
				factor.loadToStack(context);
			}

			instructions.iinc(((LocalVariable) factor).getScopeLogicVar()
					.getInitStartPos(), isInc ? 1 : -1);

			if (asArgument && !isPos) {
				factor.loadToStack(context);
			}
		} else {
			IClass primitiveClass = IClassUtils.getPrimitiveAClass(targetClass);
			Type primitiveType = primitiveClass.getType();

			// factor load to stack
			factor.loadToStack(context);

			if (asArgument && isPos)
				instructions.dup(type);

			// unbox
			autoCast(context, targetClass, primitiveClass, true);

			// load 1 to stack
			getIncreaseValue().loadToStack(context);

			// generate xadd/xsub for decrement
			if (isInc) {
				instructions.add(primitiveType);
			} else {
				instructions.sub(primitiveType);
			}

			// box and cast
			autoCast(context,
					primitiveType.getSort() <= Type.INT ? getType(int.class)
							: primitiveClass, targetClass, true);

			if (asArgument && !isPos)
				instructions.dup(type);

			instructions.commonPutField((ExplicitVariable) factor);
		}
	}

	private Value getIncreaseValue() {
		IClass type = factor.getResultType();
		KernelProgramBlock block = getParent();
		if (type.equals(getType(double.class))
				|| type.equals(getType(Double.class))) {
			return block.val(1d);
		} else if (type.equals(getType(float.class))
				|| type.equals(getType(Float.class))) {
			return block.val(1f);
		} else if (type.equals(getType(long.class))
				|| type.equals(getType(Long.class))) {
			return block.val(1L);
		} else {
			return block.val(1);
		}
	}

}
