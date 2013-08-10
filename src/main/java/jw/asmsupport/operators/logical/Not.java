/**
 * 
 */
package jw.asmsupport.operators.logical;



import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.Operators;
import jw.asmsupport.utils.Stack;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class Not extends UnaryLogical {

    protected Not(ProgramBlock block, Parameterized factor) {
        super(block, factor);
        this.operator = Operators.NOT;
    }

    @Override
    protected void executingProcess() {
        MethodVisitor mv = insnHelper.getMv();
        mv.visitJumpInsn(Opcodes.IFEQ, trueLbl);
        
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, falseLbl);
        
        mv.visitLabel(trueLbl);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLabel(falseLbl);
        
        Stack stack = block.getMethod().getStack();
        stack.pop();
        stack.push(Type.BOOLEAN_TYPE);
        stack.printState();
    }

}
