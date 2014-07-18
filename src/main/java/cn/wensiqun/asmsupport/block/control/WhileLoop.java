package cn.wensiqun.asmsupport.block.control;


import org.objectweb.asm.Label;

import cn.wensiqun.asmsupport.Executable;
import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.asm.InstructionHelper;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.block.body.Body;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.operators.Jumpable;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class WhileLoop extends ProgramBlock implements ILoop, Body  {

    private Parameterized condition;

    Label condiLbl;
    Label startLbl;
    Label end;
    
    protected boolean isDoWhile;
    
    public WhileLoop(Parameterized condition) {
        super();
        this.condition = condition;
        condiLbl = new Label();
        startLbl = new Label();
        end = new Label();
        condition.asArgument();
    }

    @Override
    public final void generateInsn()
    {
        body();
    }


    @Override
    public void executing() {
        insnHelper.nop();
        if(!isDoWhile){
            insnHelper.goTo(condiLbl);
        }
        
        insnHelper.mark(startLbl);
        insnHelper.nop();
        for(Executable exe : getExecuteQueue()){
            exe.execute();
        }

        insnHelper.mark(condiLbl);

        if(condition instanceof Jumpable){
        	Jumpable jmp = (Jumpable) condition;
        	jmp.setJumpLable(startLbl);
        	jmp.executeAndJump(ControlType.LOOP);
        }else{
            condition.loadToStack(this);
            insnHelper.unbox(condition.getParamterizedType().getType());
            insnHelper.ifZCmp(InstructionHelper.NE, startLbl);
        }
        insnHelper.mark(end);        
    }

    @Override
    protected void init() {
        if(!condition.getParamterizedType().equals(AClass.BOOLEAN_WRAP_ACLASS) &&
           !condition.getParamterizedType().equals(AClass.BOOLEAN_ACLASS) ){
            throw new ASMSupportException("the condition type of if statement must be boolean or Boolean, but was " + condition.getParamterizedType());
        }
    }
    
    @Override
    public Label getBreakLabel() {
        return end;
    }

    @Override
    public Label getContinueLabel() {
        return condiLbl;
    }


	@Override
	public String toString() {
		return "While Block:" + super.toString();
	}
}
