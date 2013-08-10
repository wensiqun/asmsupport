/**
 * 
 */
package jw.asmsupport.operators.numerical.bitwise;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.Operators;

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
