package cn.wensiqun.asmsupport.operators.asmdirect;


import org.objectweb.asm.Label;

import cn.wensiqun.asmsupport.block.ProgramBlock;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class Marker extends ASMDirect {
    
    private Label label;
    
    public Marker(ProgramBlock block) {
		super(block);
	}

	public Marker(ProgramBlock block, Label lbl) {
        super(block);
        label = lbl;
    }

    @Override
    protected void executing() {
    	if(label != null){
            block.getInsnHelper().mark(label);
    	}
    }
    
}