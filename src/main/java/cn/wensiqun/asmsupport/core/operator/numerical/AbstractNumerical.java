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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.AbstractArithmetic;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractNumerical extends AbstractOperator implements Parameterized {

    private static final Log LOG = LogFactory.getLog(AbstractArithmetic.class);

    /** 执行的结果类型 */
    protected AClass targetClass;

    protected String operator;

    protected AbstractNumerical(ProgramBlockInternal block) {
        super(block);
    }

    /**
     * 运算因子入栈
     */
    protected abstract void factorToStack();

    /**
     * 
     * @param factor
     */
    protected void pushFactorToStack(Parameterized factor) {

        AClass factorCls = factor.getParamterizedType();

        // factor to stack
        LOG.debug("push the first arithmetic factor to stack");
        factor.loadToStack(block);

        AClass factorPrimitiveAClass = factorCls;
        // unbox if needs
        if (!factorCls.isPrimitive()) {
            LOG.debug("unbox " + factorCls);
            insnHelper.unbox(factorCls.getType());
            factorPrimitiveAClass = AClassUtils.getPrimitiveAClass(factorCls);
        }

        // cast if needs
        if (factorPrimitiveAClass.getCastOrder() < targetClass.getCastOrder()
                && targetClass.getCastOrder() > AClass.INT_ACLASS.getCastOrder()) {
            LOG.debug("cast factor from " + factorCls + " to " + targetClass);
            insnHelper.cast(factorPrimitiveAClass.getType(), targetClass.getType());
        }
    }

    @Override
    public final AClass getParamterizedType() {
        return targetClass;
    }

}
