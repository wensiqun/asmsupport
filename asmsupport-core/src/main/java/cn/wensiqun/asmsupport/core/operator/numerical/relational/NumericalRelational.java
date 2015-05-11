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

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class NumericalRelational extends AbstractRelational {

    private static final Log LOG = LogFactory.getLog(NumericalRelational.class);

    protected NumericalRelational(KernelProgramBlock block, KernelParam factor1, KernelParam factor2, Operator operator) {
        super(block, factor1, factor2, operator);
    }

    @Override
    protected void verifyArgument() {
        AClass ftrCls1 = AClassUtils.getPrimitiveAClass(leftFactor.getResultType());
        AClass ftrCls2 = AClassUtils.getPrimitiveAClass(rightFactor.getResultType());
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

        AClass factorCls = factor.getResultType();

        // factor to stack
        LOG.print("push the first arithmetic factor to stack");
        factor.loadToStack(block);

        AClass factorPrimitiveAClass = factorCls;
        // unbox if needs
        if (!factorCls.isPrimitive()) {
            LOG.print("unbox " + factorCls);
            insnHelper.unbox(factorCls.getType());
            factorPrimitiveAClass = AClassUtils.getPrimitiveAClass(factorCls);
        }

        // cast if needs
        if (factorPrimitiveAClass.getCastOrder() < targetClass.getCastOrder()
                && targetClass.getCastOrder() > AClassFactory.getType(int.class).getCastOrder()) {
            LOG.print("cast factor from " + factorCls + " to " + targetClass);
            insnHelper.cast(factorPrimitiveAClass.getType(), targetClass.getType());
        }
    }

}
