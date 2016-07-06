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
package cn.wensiqun.asmsupport.core.operator.numerical;

import cn.wensiqun.asmsupport.core.context.MethodExecuteContext;
import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.AbstractParamOperator;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.AbstractArithmetic;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

/**
 * Represent a numerical operation
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class AbstractNumerical extends AbstractParamOperator {

    private static final Log LOG = LogFactory.getLog(AbstractArithmetic.class);

    /** The result type of current numerical operation */
    protected IClass targetClass;

    protected AbstractNumerical(KernelProgramBlock block, Operator operatorSymbol) {
        super(block, operatorSymbol);
    }

    /**
     * Push the operand of numerical operation to stack
     * @param context
     */
    protected abstract void factorToStack(MethodExecuteContext context);

    /**
     * Defined a common method use in sub class 
     * 
     * @param factor the operand
     */
    protected void pushFactorToStack(MethodExecuteContext context, KernelParam factor) {

    	IClass factorCls = factor.getResultType();
        Instructions instructions = context.getInstructions();
        // factor to stack
        LOG.print("push the first arithmetic factor to stack");
        factor.push(context);

        IClass factorPrimitiveAClass = factorCls;
        // unbox if needs
        if (!factorCls.isPrimitive()) {
            LOG.print("unbox " + factorCls);
            instructions.unbox(factorCls.getType());
            factorPrimitiveAClass = IClassUtils.getPrimitiveAClass(factorCls);
        }

        // cast if needs
        if (factorPrimitiveAClass.getCastOrder() < targetClass.getCastOrder()
                && targetClass.getCastOrder() > getType(int.class).getCastOrder()) {
            LOG.print("cast factor from " + factorCls + " to " + targetClass);
            instructions.cast(factorPrimitiveAClass.getType(), targetClass.getType());
        }
    }

    @Override
    public final IClass getResultType() {
        return targetClass;
    }

}
