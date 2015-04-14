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

import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.standard.branch.IElseIF;

public abstract class ElseIFInternal extends ConditionBranchBlock implements IElseIF<ElseIFInternal, ElseInternal> {

    private Parameterized condition;

    public ElseIFInternal(Parameterized condition) {
        this.condition = condition;
        condition.asArgument();
    }

    @Override
    protected void init() {
        if (!condition.getParamterizedType().equals(AClassFactory.defType(Boolean.class))
                && !condition.getParamterizedType().equals(AClassFactory.defType(boolean.class))) {
            throw new ASMSupportException("the condition type of if statement must be boolean or Boolean, but was "
                    + condition.getParamterizedType());
        }
    }

    @Override
    public void generate() {
        body();
    }

    @Override
    protected void doExecute() {
        Label posLbl = new Label();
        insnHelper.nop();
        if (condition instanceof Jumpable) {
            ((Jumpable) condition).jumpNegative(null, posLbl, getEnd());
        } else {
            condition.loadToStack(this);
            insnHelper.unbox(condition.getParamterizedType().getType());
            insnHelper.ifZCmp(InstructionHelper.EQ, getEnd());
        }

        insnHelper.mark(posLbl);
        for (Executable exe : getQueue()) {
            exe.execute();
        }

        if (nextBranch != null) {
            insnHelper.goTo(getSerialEnd());
        }
    }

    @Override
    public ElseIFInternal elseif(ElseIFInternal elsIf) {
        initNextBranch(elsIf);
        return elsIf;
    }

    @Override
    public ElseInternal else_(ElseInternal els) {
        initNextBranch(els);
        return els;
    }

}
