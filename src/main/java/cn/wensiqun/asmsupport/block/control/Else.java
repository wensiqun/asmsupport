/**
 * 
 */
package cn.wensiqun.asmsupport.block.control;


import org.objectweb.asm.Label;

import cn.wensiqun.asmsupport.Executable;
import cn.wensiqun.asmsupport.block.body.Body;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class Else extends ConditionBranchBlock implements Body {
	
	@Override
    public void executing() {
        insnHelper.nop();
        for(Executable exe : getExecuteQueue()){
            exe.execute();
        }
        insnHelper.mark(getEndLabel());
    }
	
    @Override
    protected void init() {
    }

    @Override
    Label getLastLabel() {
        return getEndLabel();
    }

    @Override
	public void setReturned(boolean returned) {
    	super.setReturned(returned);
    	boolean superReturned = true;
    	SeriesBlock previous = getPrevious();
    	while(previous != null){
    		if(!previous.isReturned()){
    			superReturned = false;
    			break;
    		}
    		previous = previous.getPrevious();
    	}
    	if(superReturned){
    		getOwnerBlock().setReturned(returned);
    	}
	}

    @Override
    public final void generateInsn()
    {
        body();
    }
    
    
}
