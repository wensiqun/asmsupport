package cn.wensiqun.asmsupport.operators.listener;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.block.control.Catch;
import cn.wensiqun.asmsupport.block.control.Finally;
import cn.wensiqun.asmsupport.block.control.Try;
import cn.wensiqun.asmsupport.operators.AbstractOperator;
import cn.wensiqun.asmsupport.operators.Return;
import cn.wensiqun.asmsupport.operators.util.NewOperatorListener;
import cn.wensiqun.asmsupport.operators.util.OperatorFactory;

/**
 * 在Catch程序块中创建Return操作并且存在Finally块的时候的监听器
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ReturnOperatoInCatchWithFinallyListener extends NewOperatorListener {
	
	private Finally finallyBlock;
	
	public ReturnOperatoInCatchWithFinallyListener() {
		super();
	}
	
	/**
	 * 获取程序块的上一层的Try程序块
	 * @param block 需要查找的程序块
	 * @return
	 */
	private Try directTryBlock(ProgramBlock block){
		assert block != null;
		while(block != null){
			if(block instanceof Try){
				return (Try) block;
			}else{
				block = block.getOwnerBlock();
			}
		}
		return null;
	}
	
	/**
	 * 获取程序块上一层的catch块
	 * @param block
	 * @return
	 */
	private Catch directCatchBlock(ProgramBlock block){
		assert block != null;
		while(block != null){
			if(block instanceof Catch){
				return (Catch) block;
			}else{
				block = block.getOwnerBlock();
			}
		}
		return null;
	}

	@Override
	protected boolean triggerCondition() {
		Try tryBlock = directTryBlock(getExecuteBlock());
		if(tryBlock != null){
			finallyBlock = tryBlock.getFinallyBlock();
			if(finallyBlock != null){
				return true;
			}else{
				return false;
			}
		}
		
		//如果当前的操作在catch中并且有finally语句块则触发监听事件		
		Catch catchBlock = directCatchBlock(getExecuteBlock());
		if(catchBlock != null){
			finallyBlock = catchBlock.getFinallyBlock();
			if(finallyBlock != null){
				return true;
			}else{
				return false;
			}
		}
		
		return false;
	}

	@Override
	protected void beforeNew() {
		//如果在catch中有return 需要创建一个隐式的finally语句块内的内容
		if(getOperatorClass().equals(Return.class)){
			ProgramBlock cloneFinally = finallyBlock.getCopy();
		    cloneFinally.setExecuteBlock(getExecuteBlock());

		    //对于隐式的finally语句块中不监听
		    OperatorFactory.removeMultiTimeListener(this);
		    cloneFinally.generateInsn();
		    OperatorFactory.addMultiTimeListener(this);
		    
		}
	}

	@Override
	protected void afterNew(AbstractOperator instance) {
		/*if(instance instanceof Return){
			//Return bs = ((Return) instance);
			getExecuteBlock().removeExe(instance);
			//Finally currentFinallyBlock = getIncludedOrEqualFinally(getExecuteBlock());
			
		}*/
		finallyBlock = null;
	}
	
}
