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

import cn.wensiqun.asmsupport.core.context.MethodExecuteContext;
import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.utils.InstructionNode;
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
        if (!condition.getResultType().equals(getType(Boolean.class))
                && !condition.getResultType().equals(getType(boolean.class))) {
            throw new ASMSupportException("the condition type of if statement must be boolean or Boolean, but was "
                    + condition.getResultType());
        }
    }

    @Override
    public void generate() {
        body();
    }

    @Override
    protected void doExecute(MethodExecuteContext context) {
        Instructions instructions = context.getInstructions();
        Label posLbl = new Label();
        if (condition instanceof Jumpable) {
            ((Jumpable) condition).jumpNegative(context, null, posLbl, getEnd());
        } else {
            condition.push(context);
            instructions.unbox(condition.getResultType().getType());
            instructions.ifZCmp(Instructions.EQ, getEnd());
        }

        instructions.mark(posLbl);
        for (InstructionNode exe : getChildren()) {
            exe.execute(context);
        }

        if (nextBranch != null) {
            instructions.goTo(getSerialEnd());
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
