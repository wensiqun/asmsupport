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

import cn.wensiqun.asmsupport.core.ByteCodeExecutor;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.control.EpisodeBlock;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Store;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.excep.ICatch;
import cn.wensiqun.asmsupport.standard.exception.ASMSupportException;

public abstract class CatchInternal extends EpisodeBlock<ExceptionSerialBlock> implements
        ICatch<LocalVariable, CatchInternal, FinallyInternal> {

    private AClass exceptionType;

    public CatchInternal(AClass exceptionType) {
        if (exceptionType == null) {
            throw new ASMSupportException("missing catch exception type.");
        }
        this.exceptionType = exceptionType;
    }

    @Override
    public void generate() {
        LocalVariable exception = getLocalAnonymousVariableModel(exceptionType);
        OperatorFactory.newOperator(Store.class, new Class[] { ProgramBlockInternal.class, LocalVariable.class },
                getExecutor(), exception);
        // new Store(getExecutor(), exception);
        body(exception);
    }

    @Override
    protected void doExecute() {
        // the exception variable already exists at the top of the statck.
        insnHelper.getMv().getStack().push(exceptionType.getType());

        for (ByteCodeExecutor exe : getQueue()) {
            exe.execute();
        }
    }

    @Override
    public CatchInternal catch_(CatchInternal catchBlock) {
        ExceptionSerialBlock serial = getSerial();

        if (serial.getFinally() != null) {
            throw new ASMSupportException("Exists finally block. please create catch before finally block");
        }
        getSerial().appendEpisode(catchBlock);
        return catchBlock;
    }

    @Override
    public FinallyInternal finally_(FinallyInternal block) {
        ExceptionSerialBlock serial = getSerial();
        if (serial.getFinally() != null) {
            throw new ASMSupportException("Already exists finally block.");
        }
        getSerial().appendEpisode(block);

        return block;
    }

    AClass getExceptionType() {
        return exceptionType;
    }

}
