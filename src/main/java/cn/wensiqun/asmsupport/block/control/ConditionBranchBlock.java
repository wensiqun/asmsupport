package cn.wensiqun.asmsupport.block.control;


import org.objectweb.asm.Label;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class ConditionBranchBlock extends SeriesBlock {

    private Label end;

    public ConditionBranchBlock() {
        super();
        end = new Label();
    }
    
    abstract Label getLastLabel();
    
    Label getEndLabel(){
        return end;
    }
}
