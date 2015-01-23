package cn.wensiqun.asmsupport.core.operator;


import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.control.ControlType;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;


public interface Jumpable extends Parameterized {

    void executeAndJump(ControlType control);
    
    void setJumpLable(Label lbl);
}
