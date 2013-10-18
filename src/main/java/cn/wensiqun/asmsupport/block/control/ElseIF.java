package cn.wensiqun.asmsupport.block.control;

import java.util.List;

import org.objectweb.asm.Label;

import cn.wensiqun.asmsupport.Executeable;
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
public abstract class ElseIF extends ControlBlock {
	
    private Parameterized condition;

    private ControlBlock elseifOrIfBlock;

    /** 该程序块中所有可执行的指令 */
    private List<Executeable> parentExes;
    
    /** 该程序块中所有可执行的指令 */
    private List<Executeable> parentPreExes;
    
    public ElseIF(Parameterized condition) {
        super();
        this.condition = condition;
        condition.asArgument();
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
        for(Executeable exe : getExecuteQueue()){
            exe.execute();
        }
        
        if(elseifOrIfBlock != null){
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
        //condition.asArgument();
    }

    public void elsethan(Else elseblock){
        this.elseifOrIfBlock = elseblock;
        parentExes.add(elseblock);
        parentPreExes.add(elseblock);

        subBlockPrepare(elseblock, getOwnerBlock());
        
        elseblock.setPrevious(this);
    }

    public ElseIF elseif(ElseIF elseblock){
        elseblock.setParentExes(parentExes);
        elseblock.setParentPreExes(parentPreExes);
        
        parentExes.add(elseblock);
        parentPreExes.add(elseblock);

        subBlockPrepare(elseblock, getOwnerBlock());
        
        elseblock.setPrevious(this);
        
        this.elseifOrIfBlock = elseblock;
        
        return elseblock;
    }

    @Override
    Label getLastLabel() {
        if(elseifOrIfBlock == null){
            return getEndLabel();
        }else{
            return elseifOrIfBlock.getLastLabel();
        }
    }

    public void setParentExes(List<Executeable> parentExes) {
        this.parentExes = parentExes;
    }

    public void setParentPreExes(List<Executeable> parentPreExes) {
        this.parentPreExes = parentPreExes;
    }
}
