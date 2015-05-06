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

import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
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
    public final void generate() {
        body();
    }

    @Override
    public void doExecute() {
        insnHelper.mark(contentStart);
        for (Executable exe : getQueue()) {
            exe.execute();
        }

        insnHelper.mark(conditionLbl);

        if (condition instanceof Jumpable) {
            ((Jumpable) condition).jumpPositive(null, contentStart, getEnd());
        } else {
            condition.loadToStack(this);
            insnHelper.unbox(condition.getResultType().getType());
            insnHelper.ifZCmp(InstructionHelper.NE, contentStart);
        }
    }

    @Override
    protected void init() {
        if (!condition.getResultType().equals(AClassFactory.getType(Boolean.class))
                && !condition.getResultType().equals(AClassFactory.getType(boolean.class))) {
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
