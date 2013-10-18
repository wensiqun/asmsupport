package cn.wensiqun.asmsupport.operators;

import cn.wensiqun.asmsupport.block.ProgramBlock;

public abstract class BreakStack extends AbstractOperator {

	//private boolean affectReturn = true;
	
	private boolean autoCreate;
	
	public boolean isAutoCreate() {
		return autoCreate;
	}

	public void setAutoCreate(boolean autoCreate) {
		this.autoCreate = autoCreate;
	}

	protected BreakStack(ProgramBlock block) {
		super(block);
	}
	
	@Override
	public void checkUnreachableCode() {
		super.checkUnreachableCode();
		//如果是跳出栈的操作 比如return或者break则这是一个标志表示当前block已经跳出
        if(!autoCreate){
    		block.setReturned(true);
		}
	}

	@Override
	protected final void executing() {
	    breakStackExecuting();
	}

    protected abstract void breakStackExecuting();
	
}
