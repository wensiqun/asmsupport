/**
 * 
 */
package cn.wensiqun.asmsupport.operators.logical;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.operators.Operators;
import cn.wensiqun.asmsupport.utils.memory.Stack;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ShortCircuitAnd extends ConditionOperator {
    
    protected ShortCircuitAnd(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.CONDITION_AND;
    }
    
    
    @Override
    protected void executingProcess() {
        MethodVisitor mv = insnHelper.getMv();
        AClass ftrCls1 = factor1.getParamterizedType();
        AClass ftrCls2 = factor2.getParamterizedType();
        
        factor1.loadToStack(block);
        insnHelper.unbox(ftrCls1.getType());
        mv.visitJumpInsn(Opcodes.IFEQ, falseLbl);
        
        factor2.loadToStack(block);
        insnHelper.unbox(ftrCls2.getType());
        mv.visitJumpInsn(Opcodes.IFEQ, falseLbl);
        

        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitJumpInsn(Opcodes.GOTO, trueLbl);
        mv.visitLabel(falseLbl);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitLabel(trueLbl);
        
        Stack stack = block.getMethod().getStack();
        stack.pop();
        stack.pop();
        stack.push(Type.BOOLEAN_TYPE);
        
    }

}
