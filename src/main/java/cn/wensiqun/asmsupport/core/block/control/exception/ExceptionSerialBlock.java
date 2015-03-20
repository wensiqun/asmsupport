package cn.wensiqun.asmsupport.core.block.control.exception;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.ByteCodeExecutor;
import cn.wensiqun.asmsupport.core.block.AbstractBlockInternal;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.control.SerialBlock;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AnyException;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.Return;
import cn.wensiqun.asmsupport.core.operator.asmdirect.GOTO;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Marker;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Store;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.utils.common.TryCatchInfo;
import cn.wensiqun.asmsupport.org.apache.commons.collections.CollectionUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;

public class ExceptionSerialBlock extends SerialBlock
{
    private TryInternal tryBlock;
    
    private List<CatchInternal> catchs;
    
    private ImplicitCatch implicitCatch;
    
    private FinallyInternal  finallyBlock;
    
    private List<Label> anyCatchRange;
    
    private List<TryCatchInfo> tryCatchInfoes;
    
    public ExceptionSerialBlock(ProgramBlockInternal parent, TryInternal tryBlock)
    {
        super(parent);
        this.tryBlock = tryBlock;
        initEpisode(tryBlock);
        getQueue().add(tryBlock);
    }

    @Override
    public void prepare()
    {
        if(CollectionUtils.isEmpty(catchs) && finallyBlock == null)
        {
            throw new ASMSupportException("Syntax error, insert \"Finally\" block or \"Catch\" block to complete Try Block.");
        }
        else if(CollectionUtils.isNotEmpty(catchs) && finallyBlock == null)
        {
            tryBlock.prepare();

            if(!tryBlock.isFinish())
            {
                OperatorFactory.newOperator(GOTO.class, 
                        new Class[]{ProgramBlockInternal.class, Label.class}, 
                        tryBlock, getSerialEnd());
            }
            
            //new GOTO(tryBlock, serialEnd);
            
            for(CatchInternal c : catchs)
            {
                c.prepare();
                if(!c.isFinish())
                {
                    OperatorFactory.newOperator(GOTO.class, 
                            new Class[]{ProgramBlockInternal.class, Label.class}, 
                            c, getSerialEnd());
                }
            }
        }
        else if(CollectionUtils.isEmpty(catchs) && finallyBlock != null)
        {
        	addAnyExceptionCatchRange(tryBlock.getStart());
            
            tryBlock.prepare();
            
            if(!tryBlock.isFinish())
            {
                OperatorFactory.newOperator(GOTO.class, 
        				new Class[]{ProgramBlockInternal.class, Label.class}, 
        				tryBlock, finallyBlock.getStart());
                //new GOTO(tryBlock, finallyBlock.getStart());
            }
            
            //insert finally for BreakStack operator in try block.
            insertFinallyBeforeReturn(tryBlock);
            
            addAnyExceptionCatchRange(tryBlock.getEnd());
            
            implicitCatch.prepare();
            
            finallyBlock.prepare();
        }
        else
        {
        	addAnyExceptionCatchRange(tryBlock.getStart());
            
            tryBlock.prepare();
            
            if(!tryBlock.isFinish())
            {
                OperatorFactory.newOperator(GOTO.class, 
                    new Class[]{ProgramBlockInternal.class, Label.class}, 
                    tryBlock, finallyBlock.getStart());
                //new GOTO(tryBlock, finallyBlock.getStart());
            }
            
            //insert finally for BreakStack operator in try block.
            insertFinallyBeforeReturn(tryBlock);
            
            for(CatchInternal c : catchs)
            {
                c.prepare();
                
                //insert finally for BreakStack operator in try block.
                insertFinallyBeforeReturn(c);
                
                if(!c.isFinish())
                {
                    Label finallyStart = new Label("catch's implicit finally start");
                    Label finallyEnd = new Label("catch's implicit finally end");
                    
                    addAnyExceptionCatchRange(finallyStart);
                    {
                        //inject implicit finally block code at end of catch
                        OperatorFactory.newOperator(Marker.class, 
                                new Class[]{ProgramBlockInternal.class, Label.class}, 
                                c, finallyStart);
                        //new Marker(c, finallyStart);
                        finallyBlock.generateTo(c);
                        
                        OperatorFactory.newOperator(GOTO.class, 
                                new Class[]{ProgramBlockInternal.class, Label.class}, 
                                c, getSerialEnd());
                        
                        //new GOTO(c, serialEnd);
                        
                        OperatorFactory.newOperator(Marker.class, 
                                new Class[]{ProgramBlockInternal.class, Label.class}, 
                                c, finallyEnd);
                        //new Marker(c, finallyEnd);
                    }
                    addAnyExceptionCatchRange(finallyEnd);
                }
            	
            }
            
            addAnyExceptionCatchRange(catchs.get(catchs.size() - 1).getEnd());
            
            implicitCatch.prepare();
            
            finallyBlock.prepare();
        }

        /*
         *  implicitCatch and finallyBlock is not null 
         *  if finallyBlock is null
         */
        if(finallyBlock != null)
        {
            //build Exception Table(just for any type)
            for(int i=0; i<anyCatchRange.size();)
            {
            	Label start = anyCatchRange.get(i++);
            	Label end = anyCatchRange.get(i++);
            	this.addTreCatchInfo(start, end, implicitCatch.getStart(), AnyException.ANY);
            }
        }
        
        //for exception table
        if(CollectionUtils.isNotEmpty(tryCatchInfoes))
        {
        	for(TryCatchInfo info : tryCatchInfoes)
        	{
        		targetParent.getMethod().getMethodBody().addTryCatchInfo(info);
        	}
        }
    }
    
    
    @Override
    public void execute()
    {
        tryBlock.execute();
        
        if(CollectionUtils.isNotEmpty(catchs))
        {
            for(CatchInternal c : catchs)
            {
                c.execute();
            }
        }
        
        if(finallyBlock != null)
        {
            implicitCatch.execute();
            finallyBlock.execute();
        }
    }
    
    void appendEpisode(CatchInternal catchBlock)
    {
        if(catchs == null)
        {
            catchs = new ArrayList<CatchInternal>();
            getQueue().addAfter(tryBlock, catchBlock);
        }
        else
        {
            CatchInternal previous = catchs.get(catchs.size() - 1);
            AClass exceptionType = catchBlock.getExceptionType();
            
            if(exceptionType != null &&
               exceptionType.isChildOrEqual(previous.getExceptionType()))
            {
                throw new ASMSupportException("Unreachable catch block for " + exceptionType + 
                    ". It is already handled by the catch block for " + exceptionType);
            }
            
            getQueue().addAfter(previous, catchBlock);
        }
        
        initEpisode(catchBlock);
        catchs.add(catchBlock);
        
        //add Exception Table:
        addTreCatchInfo(tryBlock.getStart(), tryBlock.getEnd(), 
            catchBlock.getStart(), catchBlock.getExceptionType());
    }
    
    void appendEpisode(FinallyInternal finallyBlock)
    {
        if(this.finallyBlock != null)
        {
            throw new ASMSupportException("Finally block already exists.");
        }
        initEpisode(finallyBlock);
        this.finallyBlock = finallyBlock;
        
        //add implicit catch block;
        implicitCatch = new ImplicitCatch();
        implicitCatch.setParent(targetParent);
        
        getQueue().setLast(implicitCatch);
        getQueue().setLast(finallyBlock);
    }


    /**
     * 
     * 
     * @param block
     */
    private void insertFinallyBeforeReturn(AbstractBlockInternal block)
    {
        List<Return> returns = fetchAllBreakStack(block, null);
        
        for(Return ret : returns)
        {
            ProgramBlockInternal breakBlock = ret.getBlock();
            
            Label startLbl = new Label("implicit finally before break stack start");
            Label endLbl = new Label("implicit finally before break stack end");
            addAnyExceptionCatchRange(startLbl);
            addAnyExceptionCatchRange(endLbl);
            
            //first remove node start with b from list
            breakBlock.getQueue().removeFrom(ret);
            
            breakBlock.setFinish(false);
            
            //dosen't detected previous whether serial block.
        	OperatorFactory.newOperator(Marker.class, false,
    				new Class[]{ProgramBlockInternal.class, Label.class}, 
    				breakBlock, startLbl);
        	
            //new Marker(breakBlock, startLbl);
            
            //append finally block code to list
            finallyBlock.generateTo(breakBlock);
            
            {
	            //*hard to understand*
	            //append the b to end of the list
	            breakBlock.getQueue().add(ret);
	            

	            //if already returned in inserted finally code
	            //remove only the break stack operator
	            if(breakBlock.isFinish())
	            {
	            	//remove only brk node
	            	breakBlock.getQueue().remove(ret);
	            }
	            else
	            {
	                breakBlock.setFinish(true);
	            }
            }
            
            OperatorFactory.newOperator(Marker.class, 
    				new Class[]{ProgramBlockInternal.class, Label.class}, 
    				breakBlock, endLbl);
            
            //new Marker(breakBlock, endLbl);
        }
    }
    
    
    private List<Return> fetchAllBreakStack(AbstractBlockInternal block, List<Return> container)
    {
        if(container == null)
        {
            container = new ArrayList<Return>();
        }
        for(ByteCodeExecutor executor : block.getQueue())
        {
            if(executor instanceof Return)
            {
                container.add((Return) executor);
            }
            else if(executor instanceof AbstractBlockInternal &&
            		!(executor instanceof ImplicitCatch))
            {
                fetchAllBreakStack((AbstractBlockInternal) executor, container);
            }
        }
        return container;
    }
    
    
    private void addTreCatchInfo(Label start, Label end, Label handler, AClass type)
    {
    	if(tryCatchInfoes == null)
    	{
    		tryCatchInfoes = new ArrayList<TryCatchInfo>();
    	}
    	tryCatchInfoes.add(new TryCatchInfo(start, end, handler, type.getType()));
    }
    
    public void addAnyExceptionCatchRange(Label label)
    {
        if(anyCatchRange == null)
            anyCatchRange = new ArrayList<Label>();
        anyCatchRange.add(label);
    }
    
    
    public FinallyInternal getFinally() {
		return finallyBlock;
	}
	
	/**
	 * 
	 *
	 */
    private class ImplicitCatch extends ProgramBlockInternal{

        @Override
        public void generate()
        {
            LocalVariable exception = getLocalAnonymousVariableModel(AnyException.ANY);

        	OperatorFactory.newOperator(Store.class, 
    				new Class[]{ProgramBlockInternal.class, LocalVariable.class}, 
    				this, exception);
        	
            //new Store(this, exception);
            
            ExceptionSerialBlock.this.finallyBlock.generateTo(this);
            
            if(!this.isFinish())
            {
                throw_(exception);
            }
        }

        @Override
        public void doExecute()
        {
            insnHelper.getMv().getStack().push(AnyException.ANY.getType());
            for(ByteCodeExecutor exe : getQueue())
            {
                exe.execute();
            }
        }

		@Override
		public void setFinish(boolean finish) {
			super.setFinish(finish);
		}
        
        
        
    }

    @Override
    public Label getSerialStart()
    {
        return tryBlock.getStart();
    }

    @Override
    public Label getSerialEnd()
    {
        if(finallyBlock != null)
        {
            return finallyBlock.getEnd();
        }
        else
        {
            return catchs.get(catchs.size() - 1).getEnd();
        }
    }
    
    
}
