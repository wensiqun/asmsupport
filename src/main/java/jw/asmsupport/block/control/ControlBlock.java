package jw.asmsupport.block.control;


import org.objectweb.asm.Label;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class ControlBlock extends SeriesBlock {

    private Label end;

    public ControlBlock() {
        super();
        end = new Label();
    }
    
    abstract Label getLastLabel();
    
    Label getEndLabel(){
        return end;
    }
}
