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


import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.memory.OperandStack;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class KernelShortCircuitOr extends ConditionOperator implements Jumpable {
    
    protected KernelShortCircuitOr(KernelProgramBlock block, KernelParam leftFactor, KernelParam rightFactor) {
        super(block, leftFactor, rightFactor, Operator.CONDITION_OR);
    }
    
    @Override
    protected void executing() {
        Label trueLbl = new Label();
        Label orEndLbl = new Label();
        
        MethodVisitor mv = getInstructions().getMv();

        jumpPositive(this, trueLbl, orEndLbl);
        
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, orEndLbl);
        mv.visitLabel(trueLbl);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLabel(orEndLbl);
        
        OperandStack stack = block.getMethod().getStack();
        stack.pop();
        stack.pop();
        stack.push(Type.BOOLEAN_TYPE);
    }

    @Override
    public void jumpPositive(KernelParam from, Label posLbl, Label negLbl) {
        Instructions instructions = getInstructions();
        MethodVisitor mv = instructions.getMv();
        Label rightFactorJudgeLbl = new Label();
        if(leftFactor instanceof KernelShortCircuitAnd) {
            ((Jumpable) leftFactor).jumpPositive(this, posLbl, rightFactorJudgeLbl);
        } else if(leftFactor instanceof Jumpable) {
            ((Jumpable) leftFactor).jumpPositive(this, posLbl, negLbl);
        } else {
            leftFactor.loadToStack(block);
            instructions.unbox(leftFactor.getResultType().getType());
            mv.visitJumpInsn(Opcodes.IFNE, posLbl);
        }

        instructions.mark(rightFactorJudgeLbl);
        
        Label conditionCheckEnd = new Label();
        if(rightFactor instanceof KernelShortCircuitAnd) {
            ((Jumpable) rightFactor).jumpPositive(this, posLbl, conditionCheckEnd);
        } else if(rightFactor instanceof Jumpable) {
            ((Jumpable) rightFactor).jumpPositive(this, posLbl, negLbl);
        } else {
            rightFactor.loadToStack(block);
            instructions.unbox(rightFactor.getResultType().getType());
            mv.visitJumpInsn(Opcodes.IFNE, posLbl);
        }
        instructions.mark(conditionCheckEnd);
    }

    @Override
    public void jumpNegative(KernelParam from, Label posLbl, Label negLbl) {
        Instructions instructions = getInstructions();
        MethodVisitor mv = instructions.getMv();
        Label rightFactorJudgeLbl = new Label();
        Label conditionCheckEnd = new Label();
        
        if(leftFactor instanceof KernelShortCircuitAnd) {
            ((Jumpable) leftFactor).jumpPositive(this, posLbl, rightFactorJudgeLbl);
        }else if(leftFactor instanceof Jumpable) {
            ((Jumpable) leftFactor).jumpPositive(this, posLbl, negLbl);
        } else {
            leftFactor.loadToStack(block);
            instructions.unbox(leftFactor.getResultType().getType());
            mv.visitJumpInsn(Opcodes.IFNE, conditionCheckEnd);
        }
        instructions.mark(rightFactorJudgeLbl);
        if(rightFactor instanceof Jumpable) {
            ((Jumpable) rightFactor).jumpNegative(this, posLbl, negLbl);
        } else {
            rightFactor.loadToStack(block);
            instructions.unbox(rightFactor.getResultType().getType());
            mv.visitJumpInsn(Opcodes.IFEQ, negLbl);
        }
        instructions.mark(conditionCheckEnd);
    }

}
