package cn.wensiqun.asmsupport.core.operator.asmdirect;


import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.UnreachableCodeCheckSkipable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class Marker extends ASMDirect implements UnreachableCodeCheckSkipable {
    
    private Label label;
    
    protected Marker(ProgramBlockInternal block) {
		super(block);
	}

    protected Marker(ProgramBlockInternal block, Label lbl) {
        super(block);
        label = lbl;
    }

    @Override
    protected void doExecute() {
    	if(label != null){
            block.getInsnHelper().mark(label);
    	}
    }
    
}