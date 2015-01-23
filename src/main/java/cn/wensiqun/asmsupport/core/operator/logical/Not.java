/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.logical;



import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.Operators;
import cn.wensiqun.asmsupport.core.utils.memory.Stack;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class Not extends UnaryLogical {

    protected Not(ProgramBlockInternal block, Parameterized factor) {
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
