package jw.asmsupport.block.control;


import org.objectweb.asm.Label;

import jw.asmsupport.Executeable;
import jw.asmsupport.Parameterized;
import jw.asmsupport.asm.InstructionHelper;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.exception.ASMSupportException;
import jw.asmsupport.operators.Jumpable;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class WhileLoop extends ProgramBlock implements ILoop {

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
    public void executing() {
        insnHelper.nop();
        if(!isDoWhile){
            insnHelper.goTo(condiLbl);
        }
        
        insnHelper.mark(startLbl);
        insnHelper.nop();
        for(Executeable exe : getExecuteQueue()){
            exe.execute();
        }

        //if(!inversContinueLblMark){
        insnHelper.mark(condiLbl);
        //}

        if(condition instanceof Jumpable){
        	Jumpable jmp = (Jumpable) condition;
        	jmp.setJumpLable(startLbl);
        	jmp.executeAndJump(ControlType.WHILE);
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
        //condition.asArgument();
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
