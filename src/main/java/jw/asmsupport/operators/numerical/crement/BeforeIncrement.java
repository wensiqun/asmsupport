/**
 * 
 */
package jw.asmsupport.operators.numerical.crement;

import jw.asmsupport.Crementable;
import jw.asmsupport.block.ProgramBlock;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class BeforeIncrement extends AbstractIncrement {
    
    protected BeforeIncrement(ProgramBlock block, Crementable factor) {
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
