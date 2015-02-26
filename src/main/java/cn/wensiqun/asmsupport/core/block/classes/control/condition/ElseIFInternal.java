package cn.wensiqun.asmsupport.core.block.classes.control.condition;

import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.classes.control.ControlType;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.standard.branch.IElseIF;

public abstract class ElseIFInternal extends ConditionBranchBlock implements IElseIF<ElseIFInternal, ElseInternal>
{

    private Parameterized condition;
    
    public ElseIFInternal(Parameterized condition) {
        this.condition = condition;
        condition.asArgument();
    }

    @Override
    protected void init() {
        if(!condition.getParamterizedType().equals(AClass.BOOLEAN_WRAP_ACLASS) &&
           !condition.getParamterizedType().equals(AClass.BOOLEAN_ACLASS) ){
            throw new ASMSupportException("the condition type of if statement must be boolean or Boolean, but was " + condition.getParamterizedType());
        }
    }
    
    @Override
    public void generate()
    {
        body();
    }

    @Override
    protected void doExecute()
    {
        Label endLbl = getEnd();
    	insnHelper.nop();
    	if(condition instanceof Jumpable){
        	Jumpable jmp = (Jumpable) condition;
        	jmp.setJumpLable(endLbl);
        	jmp.executeAndJump(ControlType.IF);
        }else{
            condition.loadToStack(this);
            insnHelper.unbox(condition.getParamterizedType().getType());
            insnHelper.ifZCmp(InstructionHelper.EQ, endLbl);
        }

    	for(Executable exe : getQueue()){
            exe.execute();
        }
    	
    	if(nextBranch != null)
        {
        	insnHelper.goTo(getSerialEnd());
        }
    }

    @Override
    public ElseIFInternal _elseif(ElseIFInternal elsIf)
    {
    	initNextBranch(elsIf);
    	return elsIf;
    }

    @Override
    public ElseInternal _else(ElseInternal els)
    {
    	initNextBranch(els);
    	return els;
    }

}
