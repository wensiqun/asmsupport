package cn.wensiqun.asmsupport.core.operator;


import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;

/**
 * Indicate the operator need support jump instructions.
 *
 */
public interface Jumpable extends Parameterized {

    void jumpPositive(Label posLbl, Label negLbl);
    
    void jumpNegative(Label posLbl, Label negLbl);
    
}
