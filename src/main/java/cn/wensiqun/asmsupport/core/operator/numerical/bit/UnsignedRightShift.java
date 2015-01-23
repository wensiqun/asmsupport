/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.numerical.bit;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operators;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class UnsignedRightShift extends BinaryBitwise {

    protected UnsignedRightShift(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.UNSIGNED_RIGHT_SHIFT;
    }

    @Override
    public void innerRunExe() {
        insnHelper.unsignedRightShift(targetClass.getType());
    }

}
