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
public class LeftShift extends BinaryBitwise {

    protected LeftShift(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.LEFT_SHIFT;
    }

    @Override
    public void innerRunExe() {
        insnHelper.leftShift(targetClass.getType());
    }

}
