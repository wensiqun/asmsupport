package cn.wensiqun.asmsupport.core.operator;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;

public abstract class BreakStack extends AbstractOperator {

    /*
     * if exists, this label indicate start position of auto create finally block, 
     * otherwise, indicate the current operator label. 
     */
    //private List<Finally> cloneFinallyList;
    
	private boolean autoCreate;

	protected BreakStack(ProgramBlockInternal block, boolean autoCreate) {
		super(block);
		this.autoCreate = autoCreate;
	}

	/*@Override
    protected void addQueue()
    {
        AMethod method = block.getMethod();
        List<Label> finallyStartLabels = new ArrayList<Label>();
        Label endLbl = new Label();
	    if(!method.isCreatingImplicitFinally())
	    {
	        checkFinallyBlock();
	        if(CollectionUtils.isNotEmpty(cloneFinallyList))
	        {
	        	boolean creatingImplicitFinally = method.isCreatingImplicitFinally();
	            method.setCreatingImplicitFinally(true);
	            for(int i=0, len=cloneFinallyList.size(); i<len; i++)
	            {
	            	Label startLbl = new Label();
	            	
	            	Finally finallyBlock = cloneFinallyList.get(i);
	            	finallyStartLabels.add(startLbl);
	            	finallyBlock.getSerial().addAnyExceptionCatchRange(startLbl);
                    finallyBlock.getSerial().addAnyExceptionCatchRange(endLbl);
	            	
	                new Marker(block, startLbl);
	            	finallyBlock.generateInsnTo(block);
	            }
	            method.setCreatingImplicitFinally(creatingImplicitFinally);
	        }
	    }
	    
        super.addQueue();
        new Marker(block, endLbl);
    }*/

    @Override
	protected void startingPrepare() {
		block.setFinish(true);
	}

	public boolean isAutoCreate() {
		return autoCreate;
	}

    @Override
	protected final void doExecute() {
	    breakStackExecuting();
	}
	
    protected abstract void breakStackExecuting();

    /*private void checkFinallyBlock()
    {
    	ProgramBlock block = this.block;
    	while(block != null)
    	{
        	if(block instanceof Try ||
        	   block instanceof Catch)
        	{
        		ExceptionEpisodeBlock episode = ((ExceptionEpisodeBlock)block);
        		ExceptionSerialBlock serial = episode.getSerial();
        		if(serial.getFinally() != null)
        		{
            		if(cloneFinallyList == null)
            		{
            			cloneFinallyList = new ArrayList<Finally>();
            		}
            		cloneFinallyList.add(serial.getFinally());
        		}
        	}
    		block = block.getParent();
    	}
    }
    
    public Label getImplicitFinallyLabel(Finally target)
    {
        if(CollectionUtils.isNotEmpty(cloneFinallyLabelList))
        {
            for(CloneFinallyLabelMap map : cloneFinallyLabelList)
            {
                if(target == map.target)
                {
                    return map.position;
                }
            }
        }
        return null;
    }*/
    
    /*public Label[] getImplicitFinallyAnyExceptionRange(Finally f)
    {
        return new Label[];
    }
    
    public class CloneFinallyLabelMap
    {
    	private Label position;
    	
    	private Finally target;

        public Label getPosition()
        {
            return position;
        }

        public Finally getTarget()
        {
            return target;
        }
    	
    }*/
}
