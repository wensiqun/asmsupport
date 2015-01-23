/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.logical;


import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
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
public class ShortCircuitAnd extends ConditionOperator {
    
    protected ShortCircuitAnd(ProgramBlockInternal block, Parameterized factor1, Parameterized factor2) {
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
