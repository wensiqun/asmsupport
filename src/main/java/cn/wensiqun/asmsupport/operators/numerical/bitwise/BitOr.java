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
public class BitOr extends BinaryBitwise {

    protected BitOr(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.BIT_OR;
    }

    @Override
    public void innerRunExe() {
        insnHelper.bitOr(resultClass.getType());
    }

}
