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

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class UnaryBitwise extends AbstractBitwise {

    protected Parameterized factor;
    
    protected UnaryBitwise(ProgramBlockInternal block, Parameterized factor) {
        super(block);
        this.factor = factor;
    }
    
    @Override
    protected void verifyArgument() {
        AClass ftrCls = factor.getParamterizedType();
        checkFactor(ftrCls);
    }

    @Override
    protected void checkAsArgument() {
        factor.asArgument();
    }

    @Override
    protected void initAdditionalProperties() {
        AClass ftrCls = factor.getParamterizedType();
        targetClass = AClassUtils.getPrimitiveAClass(ftrCls);
    }
    
    @Override
    protected final void factorToStack() {
        factor.loadToStack(block);
        insnHelper.unbox(targetClass.getType());
    }
    
    
}
