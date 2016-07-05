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
package cn.wensiqun.asmsupport.core.block.control.loop;

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.standard.block.loop.IDoWhile;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class KernelDoWhile extends KernelProgramBlock implements Loop, IDoWhile {

    private KernelParam condition;

    Label conditionLbl;
    Label contentStart;

    public KernelDoWhile(KernelParam condition) {
        this.condition = condition;
        conditionLbl = new Label();
        contentStart = new Label();
        condition.asArgument();
    }

    @Override
    public void generate() {
        body();
    }

    @Override
    public void doExecute(MethodContext context) {
        Instructions instructions = getMethod().getInstructions();
        instructions.mark(contentStart);
        for (Executable exe : getChildren()) {
            exe.execute(context);
        }

        instructions.mark(conditionLbl);

        if (condition instanceof Jumpable) {
            ((Jumpable) condition).jumpPositive(context, null, contentStart, getEnd());
        } else {
            condition.loadToStack(context);
            instructions.unbox(condition.getResultType().getType());
            instructions.ifZCmp(Instructions.NE, contentStart);
        }
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
    public Label getBreakLabel() {
        return getEnd();
    }

    @Override
    public Label getContinueLabel() {
        return conditionLbl;
    }

    @Override
    public String toString() {
        return "While Block:" + super.toString();
    }
}
