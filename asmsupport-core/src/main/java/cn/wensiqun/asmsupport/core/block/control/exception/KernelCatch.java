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
package cn.wensiqun.asmsupport.core.block.control.exception;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.control.EpisodeBlock;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Store;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.utils.InstructionNode;
import cn.wensiqun.asmsupport.standard.block.exception.ICatch;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

public abstract class KernelCatch extends EpisodeBlock<ExceptionSerialBlock> implements
        ICatch<LocalVariable, KernelCatch, KernelFinally> {

    private Object exceptionType;

    public KernelCatch(Class exceptionType) {
        if (exceptionType == null) {
            throw new ASMSupportException("Missing catch exception type.");
        }
        this.exceptionType = exceptionType;
    }

    public KernelCatch(IClass exceptionType) {
        if (exceptionType == null) {
            throw new ASMSupportException("Missing catch exception type.");
        }
        this.exceptionType = exceptionType;
    }

    @Override
    public void generate() {
        LocalVariable exception = getLocalAnonymousVariableModel(getExceptionType());
        OperatorFactory.newOperator(Store.class, new Class[] { KernelProgramBlock.class, LocalVariable.class },
                getExecutor(), exception);
        body(exception);
    }

    @Override
    protected void doExecute() {
        // the exception variable already exists at the top of the statck.
        getMethod().getInstructions().getMv().getStack().push(getExceptionType().getType());

        for (InstructionNode node : getChildren()) {
            node.execute();
        }
    }

    @Override
    public KernelCatch catch_(KernelCatch catchBlock) {
        ExceptionSerialBlock serial = getSerial();

        if (serial.getFinally() != null) {
            throw new ASMSupportException("Exists finally block. please create catch before finally block");
        }
        getSerial().appendEpisode(catchBlock);
        return catchBlock;
    }

    @Override
    public KernelFinally finally_(KernelFinally block) {
        ExceptionSerialBlock serial = getSerial();
        if (serial.getFinally() != null) {
            throw new ASMSupportException("Already exists finally block.");
        }
        getSerial().appendEpisode(block);

        return block;
    }

    IClass getExceptionType() {
        if(exceptionType instanceof IClass)
            return (IClass)exceptionType;
        return (IClass) (exceptionType = getType((Class)exceptionType));
    }

}
