package cn.wensiqun.asmsupport.core.operator;


import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;


public interface Jumpable extends Parameterized {

    void executeAndJump(int cmpType, Label lbl);
    
    //void setJumpLable(Label lbl);
}
