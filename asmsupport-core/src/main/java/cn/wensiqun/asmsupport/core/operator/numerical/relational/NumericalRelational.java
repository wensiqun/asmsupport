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
package cn.wensiqun.asmsupport.core.operator.numerical.relational;

import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class NumericalRelational extends AbstractRelational {

    private static final Log LOG = LogFactory.getLog(NumericalRelational.class);

    protected NumericalRelational(KernelProgramBlock block, KernelParam leftFactor, KernelParam rightFactor, Operator operator) {
        super(block, leftFactor, rightFactor, operator);
    }

    @Override
    protected void verifyArgument() {
    	IClass ftrCls1 = IClassUtils.getPrimitiveAClass(leftFactor.getResultType());
    	IClass ftrCls2 = IClassUtils.getPrimitiveAClass(rightFactor.getResultType());
        checkFactorForNumerical(ftrCls1);
        checkFactorForNumerical(ftrCls2);
    }

    @Override
    protected void checkAsArgument() {
        leftFactor.asArgument();
        rightFactor.asArgument();
    }
    
    @Override
    protected void factorsToStack() {
        pushFactorToStack(leftFactor);
        pushFactorToStack(rightFactor);
    }

    private void pushFactorToStack(KernelParam factor) {
    	IClass factorCls = factor.getResultType();
        Instructions instructions = getInstructions();

        // factor to stack
        LOG.print("push the first arithmetic factor to stack");
        factor.loadToStack(getParent());

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

}
