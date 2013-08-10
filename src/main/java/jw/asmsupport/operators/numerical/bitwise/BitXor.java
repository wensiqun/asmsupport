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
public class BitXor extends BinaryBitwise {

    protected BitXor(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.XOR;
    }

    @Override
    public void innerRunExe() {
        insnHelper.bitXor(resultClass.getType());
    }

}
