package cn.wensiqun.asmsupport.core.operator;


import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;

/**
 * Indicate the operator need support jump instructions.
 * <p>
 * What's the jump in jvm instruction. such as : {@code  if_xxx, ifxx } instruction.
 * </p>
 */
public interface Jumpable extends Parameterized {

    /**
     * <p>
     * To execute positive jump and generate relevant byte code.
     * </p>
     * <p>
     * For example : the operator {@code '1 == 1'} then the positive jump is {@code if_icmpeq}.
     * the operator {@code 'if(isTrue()){ ... }'}(here we suppose the method {@code isTrue})
     * return boolean value, then the positive jump is {@code ifne}
     * </p>
     * @param from
     * @param posLbl
     * @param negLbl
     */
    void jumpPositive(Parameterized from, Label posLbl, Label negLbl);
    
    
    /**
     * <p>
     * To execute negative jump and generate relevant byte code.
     * </p>
     * <p>
     * For example : the operator {@code '1 == 1'} then the negative jump is {@code if_icmpne}.
     * the operator {@code 'if(isTrue()){ ... }'}(here we suppose the method {@code isTrue})
     * return boolean value, then the positive jump is {@code ifeq}
     * </p>
     * @param from
     * @param posLbl
     * @param negLbl
     */
    void jumpNegative(Parameterized from, Label posLbl, Label negLbl);
    
}
