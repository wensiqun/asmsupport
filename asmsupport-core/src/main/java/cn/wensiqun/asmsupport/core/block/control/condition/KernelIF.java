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
package cn.wensiqun.asmsupport.core.block.control.condition;

import cn.wensiqun.asmsupport.core.BytecodeExecutor;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.standard.block.branch.IIF;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

public abstract class KernelIF extends ConditionBranchBlock implements IIF<KernelElseIF, KernelElse> {
    private KernelParam condition;

    public KernelIF(KernelParam condition) {
        this.condition = condition;
        condition.asArgument();
    }

    @Override
    protected void init() {
        if (!condition.getResultType().equals(getClassHolder().getType(Boolean.class))
                && !condition.getResultType().equals(getClassHolder().getType(boolean.class))) {
            throw new ASMSupportException("the condition type of if statement must be boolean or Boolean, but was "
                    + condition.getResultType());
        }
    }

    @Override
    public void generate() {
        body();
    }

    @Override
    protected void doExecute() {
        Label posLbl = new Label();

        if (condition instanceof Jumpable) {
            ((Jumpable) condition).jumpNegative(null, posLbl, getEnd());// .executeJump(Opcodes.JUMP_NEGATIVE,
                                                                        // endLbl);
        } else {
            condition.loadToStack(this);
            instructionHelper.unbox(condition.getResultType().getType());
            instructionHelper.ifZCmp(InstructionHelper.EQ, getEnd());
        }

        instructionHelper.mark(posLbl);
        for (BytecodeExecutor exe : getQueue()) {
            exe.execute();
        }

        if (nextBranch != null) {
            instructionHelper.goTo(getSerialEnd());
        }

    }

    @Override
    public KernelElseIF elseif(KernelElseIF elsIf) {
        initNextBranch(elsIf);
        return elsIf;
    }

    @Override
    public KernelElse else_(KernelElse els) {
        initNextBranch(els);
        return els;
    }

    @Override
    public String toString() {
        return "IF Block:" + condition.toString();
    }
}
