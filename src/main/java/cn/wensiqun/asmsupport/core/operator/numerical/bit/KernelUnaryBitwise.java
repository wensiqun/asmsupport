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
package cn.wensiqun.asmsupport.core.operator.numerical.bit;

import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class KernelUnaryBitwise extends AbstractBitwise {

    protected KernelParameterized factor;
    
    protected KernelUnaryBitwise(ProgramBlockInternal block, KernelParameterized factor, Operator operator) {
        super(block, operator);
        this.factor = factor;
    }
    
    @Override
    protected void verifyArgument() {
        AClass ftrCls = factor.getResultType();
        checkFactor(ftrCls);
    }

    @Override
    protected void checkAsArgument() {
        factor.asArgument();
    }

    @Override
    protected void initAdditionalProperties() {
        AClass ftrCls = factor.getResultType();
        targetClass = AClassUtils.getPrimitiveAClass(ftrCls);
    }
    
    @Override
    protected final void factorToStack() {
        factor.loadToStack(block);
        insnHelper.unbox(targetClass.getType());
    }
    
    
}
