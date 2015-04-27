/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.logical;


import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.operator.Operator;
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
public class KernelShortCircuitOr extends ConditionOperator implements Jumpable {
    
    protected KernelShortCircuitOr(KernelProgramBlock block, KernelParame factor1, KernelParame factor2) {
        super(block, factor1, factor2, Operator.CONDITION_OR);
    }
    
    @Override
    protected void executing() {
        Label trueLbl = new Label();
        Label orEndLbl = new Label();
        
        MethodVisitor mv = insnHelper.getMv();

        jumpPositive(this, trueLbl, orEndLbl);
        
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, orEndLbl);
        mv.visitLabel(trueLbl);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLabel(orEndLbl);
        
        Stack stack = block.getMethod().getStack();
        stack.pop();
        stack.pop();
        stack.push(Type.BOOLEAN_TYPE);
    }

    @Override
    public void jumpPositive(KernelParame from, Label posLbl, Label negLbl) {
        MethodVisitor mv = insnHelper.getMv();
        Label factor2JudgeLbl = new Label();
        if(factor1 instanceof KernelShortCircuitAnd) {
            ((Jumpable) factor1).jumpPositive(this, posLbl, factor2JudgeLbl);
        } else if(factor1 instanceof Jumpable) {
            ((Jumpable) factor1).jumpPositive(this, posLbl, negLbl);
        } else {
            factor1.loadToStack(block);
            insnHelper.unbox(factor1.getResultType().getType());
            mv.visitJumpInsn(Opcodes.IFNE, posLbl);
        }

        insnHelper.mark(factor2JudgeLbl);
        
        Label conditionCheckEnd = new Label();
        if(factor2 instanceof KernelShortCircuitAnd) {
            ((Jumpable) factor2).jumpPositive(this, posLbl, conditionCheckEnd);
        } else if(factor2 instanceof Jumpable) {
            ((Jumpable) factor2).jumpPositive(this, posLbl, negLbl);
        } else {
            factor2.loadToStack(block);
            insnHelper.unbox(factor2.getResultType().getType());
            mv.visitJumpInsn(Opcodes.IFNE, posLbl);
        }
        insnHelper.mark(conditionCheckEnd);
    }

    @Override
    public void jumpNegative(KernelParame from, Label posLbl, Label negLbl) {
        MethodVisitor mv = insnHelper.getMv();
        Label factor2JudgeLbl = new Label();
        Label conditionCheckEnd = new Label();
        
        if(factor1 instanceof KernelShortCircuitAnd) {
            ((Jumpable) factor1).jumpPositive(this, posLbl, factor2JudgeLbl);
        }else if(factor1 instanceof Jumpable) {
            ((Jumpable) factor1).jumpPositive(this, posLbl, negLbl);
        } else {
            factor1.loadToStack(block);
            insnHelper.unbox(factor1.getResultType().getType());
            mv.visitJumpInsn(Opcodes.IFNE, conditionCheckEnd);
        }
        insnHelper.mark(factor2JudgeLbl);
        if(factor2 instanceof Jumpable) {
            ((Jumpable) factor2).jumpNegative(this, posLbl, negLbl);
        } else {
            factor2.loadToStack(block);
            insnHelper.unbox(factor2.getResultType().getType());
            mv.visitJumpInsn(Opcodes.IFEQ, negLbl);
        }
        insnHelper.mark(conditionCheckEnd);
    }

}
