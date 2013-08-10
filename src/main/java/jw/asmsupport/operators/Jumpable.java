package jw.asmsupport.operators;


import org.objectweb.asm.Label;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.control.ControlType;


public interface Jumpable extends Parameterized {

    void executeAndJump(ControlType control);
    
    void setJumpLable(Label lbl);
}
