/**
 * 
 */
package cn.wensiqun.asmsupport.operators.numerical.bitwise;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.Operators;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class UnsignedRightShift extends BinaryBitwise {

    protected UnsignedRightShift(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.UNSIGNED_RIGHT_SHIFT;
    }

    @Override
    public void innerRunExe() {
        insnHelper.unsignedRightShift(resultClass.getType());
    }

}
