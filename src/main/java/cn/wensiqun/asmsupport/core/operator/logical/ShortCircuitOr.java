/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.logical;


import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.operator.Operators;
import cn.wensiqun.asmsupport.core.utils.memory.Stack;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ShortCircuitOr extends ConditionOperator implements Jumpable {
    
    protected ShortCircuitOr(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.CONDITION_OR;
    }
    
    @Override
    protected void executing() {
        Label trueLbl = new Label();
        Label falseLbl = new Label();
        
        MethodVisitor mv = insnHelper.getMv();

        jumpPositive(this, trueLbl, falseLbl);
        
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, falseLbl);
        mv.visitLabel(trueLbl);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLabel(falseLbl);
        
        Stack stack = block.getMethod().getStack();
        stack.pop();
        stack.pop();
        stack.push(Type.BOOLEAN_TYPE);
    }

    @Override
    public void jumpPositive(Parameterized from, Label posLbl, Label negLbl) {
        MethodVisitor mv = insnHelper.getMv();
        if(factor1 instanceof Jumpable) {
            ((Jumpable) factor1).jumpPositive(this, posLbl, negLbl);
        } else {
            factor1.loadToStack(block);
            insnHelper.unbox(factor1.getParamterizedType().getType());
            mv.visitJumpInsn(Opcodes.IFNE, posLbl);
        }

        if(factor2 instanceof Jumpable) {
            ((Jumpable) factor2).jumpPositive(this, posLbl, negLbl);
        } else {
            factor2.loadToStack(block);
            insnHelper.unbox(factor2.getParamterizedType().getType());
            mv.visitJumpInsn(Opcodes.IFNE, posLbl);
        }
    }

    @Override
    public void jumpNegative(Parameterized from, Label posLbl, Label negLbl) {
        MethodVisitor mv = insnHelper.getMv();
        Label label4And = new Label();
        if(factor1 instanceof Jumpable) {
            ((Jumpable) factor1).jumpPositive(this, posLbl, label4And);
        }else if(factor1 instanceof Jumpable) {
            ((Jumpable) factor1).jumpPositive(this, posLbl, negLbl);
        } else {
            factor1.loadToStack(block);
            insnHelper.unbox(factor1.getParamterizedType().getType());
            mv.visitJumpInsn(Opcodes.IFNE, posLbl);
        }
        insnHelper.mark(label4And);
        if(factor2 instanceof Jumpable) {
            ((Jumpable) factor2).jumpNegative(this, posLbl, negLbl);
        } else {
            factor2.loadToStack(block);
            insnHelper.unbox(factor2.getParamterizedType().getType());
            mv.visitJumpInsn(Opcodes.IFEQ, negLbl);
        }
    }

}
