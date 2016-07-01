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

import cn.wensiqun.asmsupport.core.BytecodeExecutor;
import cn.wensiqun.asmsupport.core.block.AbstractKernelBlock;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.control.SerialBlock;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.asmdirect.GOTO;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Marker;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Store;
import cn.wensiqun.asmsupport.core.operator.common.KernelReturn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.utils.common.ExceptionTableEntry;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AnyException;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.utils.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ExceptionSerialBlock extends SerialBlock {
    private KernelTry tryBlock;

    private List<KernelCatch> catches;

    private ImplicitCatch implicitCatch;

    private KernelFinally finallyBlock;

    private List<Label> anyCatchRange;

    private List<ExceptionTableEntry> tryCatchInfos;

    public ExceptionSerialBlock(KernelProgramBlock parent, KernelTry tryBlock) {
        super(parent);
        this.tryBlock = tryBlock;
        initEpisode(tryBlock);
        getQueue().add(tryBlock);
    }

    @Override
    public void prepare() {
        if (CollectionUtils.isEmpty(catches) && finallyBlock == null) {
            throw new ASMSupportException(
                    "Syntax error, insert \"Finally\" block or \"Catch\" block to complete Try Block.");
        } else if (CollectionUtils.isNotEmpty(catches) && finallyBlock == null) {
            tryBlock.prepare();

            if (!tryBlock.isFinish()) {
                OperatorFactory.newOperator(GOTO.class, new Class[] { KernelProgramBlock.class, Label.class },
                        tryBlock, getSerialEnd());
            }

            for (KernelCatch c : catches) {
                c.prepare();
                if (!c.isFinish()) {
                    OperatorFactory.newOperator(GOTO.class, new Class[] { KernelProgramBlock.class, Label.class }, c,
                            getSerialEnd());
                }
            }
        } else if (CollectionUtils.isEmpty(catches) && finallyBlock != null) {
            addAnyExceptionCatchRange(tryBlock.getStart());

            tryBlock.prepare();

            if (!tryBlock.isFinish()) {
                OperatorFactory.newOperator(GOTO.class, new Class[] { KernelProgramBlock.class, Label.class },
                        tryBlock, finallyBlock.getStart());
            }

            // insert finally for BreakStack operator in try block.
            insertFinallyBeforeReturn(tryBlock);

            addAnyExceptionCatchRange(tryBlock.getEnd());

            implicitCatch.prepare();

            finallyBlock.prepare();
        } else {
            addAnyExceptionCatchRange(tryBlock.getStart());

            tryBlock.prepare();

            if (!tryBlock.isFinish()) {
                OperatorFactory.newOperator(GOTO.class, new Class[] { KernelProgramBlock.class, Label.class },
                        tryBlock, finallyBlock.getStart());
            }

            // insert finally for BreakStack operator in try block.
            insertFinallyBeforeReturn(tryBlock);

            for (KernelCatch c : catches) {
                c.prepare();

                // insert finally for BreakStack operator in try block.
                insertFinallyBeforeReturn(c);

                if (!c.isFinish()) {
                    Label finallyStart = new Label("catch's implicit finally start");
                    Label finallyEnd = new Label("catch's implicit finally end");

                    addAnyExceptionCatchRange(finallyStart);
                    {
                        // inject implicit finally block code at end of catch
                        OperatorFactory.newOperator(Marker.class,
                                new Class[] { KernelProgramBlock.class, Label.class }, c, finallyStart);
                        finallyBlock.generateTo(c);

                        OperatorFactory.newOperator(GOTO.class,
                                new Class[] { KernelProgramBlock.class, Label.class }, c, getSerialEnd());

                        OperatorFactory.newOperator(Marker.class,
                                new Class[] { KernelProgramBlock.class, Label.class }, c, finallyEnd);
                    }
                    addAnyExceptionCatchRange(finallyEnd);
                }

            }

            addAnyExceptionCatchRange(catches.get(catches.size() - 1).getEnd());

            implicitCatch.prepare();

            finallyBlock.prepare();
        }

        /*
         * Implicit catch and finally block is not null if finally block is null
         */
        if (finallyBlock != null) {
            // build Exception Table(just for any type)
            for (int i = 0; i < anyCatchRange.size();) {
                Label start = anyCatchRange.get(i++);
                Label end = anyCatchRange.get(i++);
                this.addTreCatchInfo(start, end, implicitCatch.getStart(), targetParent.getType(AnyException.class));
            }
        }

        // for exception table
        if (CollectionUtils.isNotEmpty(tryCatchInfos)) {
            for (ExceptionTableEntry info : tryCatchInfos) {
                targetParent.getMethod().getBody().addExceptionTableEntry(info);
            }
        }
    }

    @Override
    public void execute() {
        tryBlock.execute();

        if (CollectionUtils.isNotEmpty(catches)) {
            for (KernelCatch c : catches) {
                c.execute();
            }
        }

        if (finallyBlock != null) {
            implicitCatch.execute();
            finallyBlock.execute();
        }
    }

    void appendEpisode(KernelCatch catchBlock) {
        if (catches == null) {
            catches = new ArrayList<>();
            getQueue().addAfter(tryBlock, catchBlock);
        } else {
            KernelCatch previous = catches.get(catches.size() - 1);
            IClass exceptionType = catchBlock.getExceptionType();

            if (exceptionType != null && exceptionType.isChildOrEqual(previous.getExceptionType())) {
                throw new ASMSupportException("Unreachable catch block for " + exceptionType
                        + ". It is already handled by the catch block for " + exceptionType);
            }

            getQueue().addAfter(previous, catchBlock);
        }

        initEpisode(catchBlock);
        catches.add(catchBlock);

        // Add Exception Table:
        addTreCatchInfo(tryBlock.getStart(), tryBlock.getEnd(), catchBlock.getStart(), catchBlock.getExceptionType());
    }

    void appendEpisode(KernelFinally finallyBlock) {
        if (this.finallyBlock != null) {
            throw new ASMSupportException("Finally block already exists.");
        }
        initEpisode(finallyBlock);
        this.finallyBlock = finallyBlock;

        // add implicit catch block;
        implicitCatch = new ImplicitCatch();
        implicitCatch.setParent(targetParent);

        getQueue().setLast(implicitCatch);
        getQueue().setLast(finallyBlock);
    }

    /**
     *
     * @param block
     */
    private void insertFinallyBeforeReturn(AbstractKernelBlock block) {
        List<KernelReturn> returns = fetchAllBreakStack(block, null);

        for (KernelReturn ret : returns) {
            KernelProgramBlock breakBlock = ret.getBlock();

            Label startLbl = new Label("implicit finally before break stack start");
            Label endLbl = new Label("implicit finally before break stack end");
            addAnyExceptionCatchRange(startLbl);
            addAnyExceptionCatchRange(endLbl);

            // first remove node start with b from list
            breakBlock.getQueue().removeFrom(ret);

            breakBlock.setFinish(false);

            // dons't detected previous whether serial block.
            OperatorFactory.newOperator(Marker.class, false, new Class[] { KernelProgramBlock.class, Label.class },
                    breakBlock, startLbl);

            // append finally block code to list
            finallyBlock.generateTo(breakBlock);

            {
                // *hard to understand*
                // append the block to end of the list
                breakBlock.getQueue().add(ret);

                // if already returned in inserted finally code
                // remove only the break stack operator
                if (breakBlock.isFinish()) {
                    // remove only break node
                    breakBlock.getQueue().remove(ret);
                } else {
                    breakBlock.setFinish(true);
                }
            }

            OperatorFactory.newOperator(Marker.class, new Class[] { KernelProgramBlock.class, Label.class },
                    breakBlock, endLbl);
        }
    }

    private List<KernelReturn> fetchAllBreakStack(AbstractKernelBlock block, List<KernelReturn> container) {
        if (container == null) {
            container = new ArrayList<>();
        }
        for (BytecodeExecutor executor : block.getQueue()) {
            if (executor instanceof KernelReturn) {
                container.add((KernelReturn) executor);
            } else if (executor instanceof AbstractKernelBlock && !(executor instanceof ImplicitCatch)) {
                fetchAllBreakStack((AbstractKernelBlock) executor, container);
            }
        }
        return container;
    }

    private void addTreCatchInfo(Label start, Label end, Label handler, IClass type) {
        if (tryCatchInfos == null) {
            tryCatchInfos = new ArrayList<>();
        }
        tryCatchInfos.add(new ExceptionTableEntry(start, end, handler, type.getType()));
    }

    public void addAnyExceptionCatchRange(Label label) {
        if (anyCatchRange == null)
            anyCatchRange = new ArrayList<>();
        anyCatchRange.add(label);
    }

    public KernelFinally getFinally() {
        return finallyBlock;
    }

    /**
	 * 
	 *
	 */
    private class ImplicitCatch extends KernelProgramBlock {

        @Override
        public void generate() {
            LocalVariable exception = getLocalAnonymousVariableModel(targetParent.getType(AnyException.class));

            OperatorFactory.newOperator(Store.class, new Class[] { KernelProgramBlock.class, LocalVariable.class },
                    this, exception);

            // new Store(this, exception);
            ExceptionSerialBlock.this.finallyBlock.generateTo(this);

            if (!this.isFinish()) {
                throw_(exception);
            }
        }

        @Override
        public void doExecute() {
            getMethod().getInstructions().getMv().getStack().push(Type.ANY_EXP_TYPE);
            for (BytecodeExecutor exe : getQueue()) {
                exe.execute();
            }
        }

        @Override
        public void setFinish(boolean finish) {
            super.setFinish(finish);
        }

    }

    @Override
    public Label getSerialStart() {
        return tryBlock.getStart();
    }

    @Override
    public Label getSerialEnd() {
        if (finallyBlock != null) {
            return finallyBlock.getEnd();
        } else {
            return catches.get(catches.size() - 1).getEnd();
        }
    }

}
