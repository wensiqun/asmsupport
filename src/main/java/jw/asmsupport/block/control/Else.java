/**
 * 
 */
package jw.asmsupport.block.control;


import org.objectweb.asm.Label;

import jw.asmsupport.Executeable;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class Else extends ControlBlock {
	
	@Override
    public void executing() {
        insnHelper.nop();
        for(Executeable exe : getExecuteQueue()){
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
}
