package cn.wensiqun.asmsupport.operators;


import org.objectweb.asm.Label;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.control.ControlType;


public interface Jumpable extends Parameterized {

    void executeAndJump(ControlType control);
    
    void setJumpLable(Label lbl);
}
