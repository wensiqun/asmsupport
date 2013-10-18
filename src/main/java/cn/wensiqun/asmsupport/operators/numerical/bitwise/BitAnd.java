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
public class BitAnd extends BinaryBitwise {

    protected BitAnd(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        this.operator = Operators.BIT_AND;
    }

    @Override
    public void innerRunExe() {
        insnHelper.bitAnd(resultClass.getType());
    }

}
