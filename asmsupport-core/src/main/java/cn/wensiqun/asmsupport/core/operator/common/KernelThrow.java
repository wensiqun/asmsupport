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

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.AnyException;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.core.operator.BreakStack;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.exception.ASMSupportException;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class KernelThrow extends BreakStack {

    private KernelParame exception;

    protected KernelThrow(KernelProgramBlock block, KernelParame exception) {
        super(block, false);
        this.exception = exception;
    }

    protected KernelThrow(KernelProgramBlock block, KernelParame exception, boolean autoCreate) {
        super(block, true);
        this.exception = exception;
    }

    @Override
    protected void startingPrepare() {
        if (!AnyException.ANY.equals(exception.getResultType())) {
            block.addException(exception.getResultType());
        }
        super.startingPrepare();
    }

    @Override
    protected void verifyArgument() {
        AClass type = exception.getResultType();
        if (AnyException.ANY != type && !type.isChildOrEqual(AClassFactory.getType(Throwable.class))) {
            throw new ASMSupportException("No exception of type " + type
                    + " can be thrown; an exception type must be a subclass of Throwable");
        }
    }

    @Override
    protected void checkCrement() {
        // Do nothing
    }

    @Override
    protected void checkAsArgument() {
        exception.asArgument();
    }

    @Override
    protected void breakStackExecuting() {
        exception.loadToStack(block);
        insnHelper.throwException();
    }

    @Override
    public String toString() {
        return " throw " + exception;
    }

    public AClass getThrowExceptionType() {
        return exception.getResultType();
    }
}
