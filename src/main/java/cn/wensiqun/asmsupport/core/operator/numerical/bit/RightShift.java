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
public class RightShift extends BinaryBitwise {

    protected RightShift(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.RIGHT_SHIFT;
    }

    @Override
    public void innerRunExe() {
        insnHelper.rightShift(targetClass.getType());
    }

}
