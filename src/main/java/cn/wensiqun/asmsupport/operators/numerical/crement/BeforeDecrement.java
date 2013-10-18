/**
 * 
 */
package cn.wensiqun.asmsupport.operators.numerical.crement;

import cn.wensiqun.asmsupport.Crementable;
import cn.wensiqun.asmsupport.block.ProgramBlock;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class BeforeDecrement extends AbstractDecrement {
    
    protected BeforeDecrement(ProgramBlock block, Crementable factor) {
        super(block, factor);
    }

    @Override
    public void after() {
    }

    @Override
    public void before() {
        this.execute();
    }

}
