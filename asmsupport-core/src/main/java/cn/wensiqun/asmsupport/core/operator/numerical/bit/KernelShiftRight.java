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
package cn.wensiqun.asmsupport.core.operator.numerical.bit;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.core.operator.Operator;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class KernelShiftRight extends BinaryBitwise {

    protected KernelShiftRight(KernelProgramBlock block, KernelParame factor1, KernelParame factor2) {
        super(block, factor1, factor2, Operator.SHIFT_RIGHT);
    }

    @Override
    public void innerRunExe() {
        insnHelper.rightShift(targetClass.getType());
    }

}
