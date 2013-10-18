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
public class AfterIncrement extends AbstractIncrement {
    
    protected AfterIncrement(ProgramBlock block, Crementable factor) {
        super(block, factor);
    }

    @Override
    public void after() {
        this.execute();
    }

    @Override
    public void before() {
    }
        
    


}
