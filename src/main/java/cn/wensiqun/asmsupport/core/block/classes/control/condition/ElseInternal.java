package cn.wensiqun.asmsupport.core.block.classes.control.condition;

import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.standard.branch.IElse;

public abstract class ElseInternal extends ConditionBranchBlock implements IElse
{

    @Override
    public void generate()
    {
        body();
    }

    @Override
    protected void doExecute()
    {
        for(Executable exe : getQueue()){
            exe.execute();
        }
    }

}
