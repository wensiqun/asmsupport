package cn.wensiqun.asmsupport.block.control;

import java.util.List;

import org.objectweb.asm.Label;

import cn.wensiqun.asmsupport.Executable;
import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.asm.InstructionHelper;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.operators.Jumpable;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class IF extends ControlBlock {
	
    private Parameterized condition;
    
    private ControlBlock elseOrElseIFBlock;

    /** 该程序块中所有可执行的指令 */
    private List<Executable> parentExes;
    
    /** 该程序块中所有可执行的指令 */
    private List<Executable> parentPreExes;
    
    public IF(Parameterized condition) {
        super();
        condition.asArgument();
        this.condition = condition;
    }

	@Override
    public void executing() {
        insnHelper.nop();
        if(condition instanceof Jumpable){
        	Jumpable jmp = (Jumpable) condition;
        	jmp.setJumpLable(getEndLabel());
        	jmp.executeAndJump(ControlType.IF);
        }else{
            condition.loadToStack(this);
            insnHelper.unbox(condition.getParamterizedType().getType());
            insnHelper.ifZCmp(InstructionHelper.EQ, getEndLabel());
        }
        
        insnHelper.nop();
        for(Executable exe : getExecuteQueue()){
            exe.execute();
        }
        
        if(elseOrElseIFBlock != null){
            insnHelper.goTo(getLastLabel());
        }
        
        insnHelper.mark(getEndLabel());
    }

    @Override
    protected void init() {
        if(!condition.getParamterizedType().equals(AClass.BOOLEAN_WRAP_ACLASS) &&
           !condition.getParamterizedType().equals(AClass.BOOLEAN_ACLASS) ){
            throw new ASMSupportException("the condition type of if statement must be boolean or Boolean, but was " + condition.getParamterizedType());
        }
    }
    
    @Override
    Label getLastLabel() {
        if(elseOrElseIFBlock == null){
            return getEndLabel();
        }else{
            return elseOrElseIFBlock.getLastLabel();
        }
    }

    public void elsethan(Else elseblock){
    	
        parentExes.add(elseblock);
        parentPreExes.add(elseblock);
        this.elseOrElseIFBlock = elseblock;
        
        subBlockPrepare(elseblock, getOwnerBlock());
        
        elseblock.setPrevious(this);
    }

    public ElseIF elseif(ElseIF elseIfBlock){
        
    	elseIfBlock.setParentExes(parentExes);
    	elseIfBlock.setParentPreExes(parentPreExes);
        
        parentExes.add(elseIfBlock);
        parentPreExes.add(elseIfBlock);

        subBlockPrepare(elseIfBlock, getOwnerBlock());
        
        elseIfBlock.setPrevious(this);
        
        this.elseOrElseIFBlock = elseIfBlock;
        
        return elseIfBlock;
    }

    public void setParentExes(List<Executable> parentExes) {
        this.parentExes = parentExes;
    }

    public void setParentPreExes(List<Executable> parentPreExes) {
        this.parentPreExes = parentPreExes;
    }


	@Override
	public String toString() {
		return "IF Block:" + super.toString();
	}
}
