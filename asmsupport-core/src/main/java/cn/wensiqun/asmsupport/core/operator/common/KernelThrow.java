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
package cn.wensiqun.asmsupport.core.operator.common;

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.BreakStack;
import cn.wensiqun.asmsupport.standard.def.clazz.AnyException;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class KernelThrow extends BreakStack {

    private KernelParam exception;

    protected KernelThrow(KernelProgramBlock block, KernelParam exception) {
        super(block, false);
        this.exception = exception;
    }

    protected KernelThrow(KernelProgramBlock block, KernelParam exception, boolean autoCreate) {
        super(block, true);
        this.exception = exception;
    }

    @Override
    protected void startingPrepare() {
        if (!getParent().getType(AnyException.class).equals(exception.getResultType())) {
            getParent().throwException(exception.getResultType());
        }
        super.startingPrepare();
    }

    @Override
    protected void verifyArgument() {
    	IClass type = exception.getResultType();
        if (getParent().getType(AnyException.class) != type &&
        	!type.isChildOrEqual(getType(Throwable.class))) {
            throw new ASMSupportException("No exception of type " + type
                    + " can be thrown; an exception type must be a subclass of Throwable");
        }
    }

    @Override
    protected void checkAsArgument() {
        exception.asArgument();
    }

    @Override
    protected void breakStackExecuting(MethodContext context) {
        exception.loadToStack(context);
        context.getInstructions().throwException();
    }

    @Override
    public String toString() {
        return " throw " + exception;
    }

    public IClass getThrowExceptionType() {
        return exception.getResultType();
    }
}
