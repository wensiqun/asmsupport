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

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.ByteCodeExecutor;
import cn.wensiqun.asmsupport.core.block.AbstractKernelBlock;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.control.SerialBlock;
import cn.wensiqun.asmsupport.core.clazz.AnyException;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.asmdirect.GOTO;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Marker;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Store;
import cn.wensiqun.asmsupport.core.operator.common.KernelReturn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.utils.collections.CollectionUtils;
import cn.wensiqun.asmsupport.core.utils.common.TryCatchInfo;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

public class ExceptionSerialBlock extends SerialBlock {
    private KernelTry tryBlock;

    private List<KernelCatch> catchs;

    private ImplicitCatch implicitCatch;

    private KernelFinally finallyBlock;

    private List<Label> anyCatchRange;

    private List<TryCatchInfo> tryCatchInfoes;

    public ExceptionSerialBlock(KernelProgramBlock parent, KernelTry tryBlock) {
        super(parent);
        this.tryBlock = tryBlock;
        initEpisode(tryBlock);
        getQueue().add(tryBlock);
    }

    @Override
    public void prepare() {
        if (CollectionUtils.isEmpty(catchs) && finallyBlock == null) {
            throw new ASMSupportException(
                    "Syntax error, insert \"Finally\" block or \"Catch\" block to complete Try Block.");
        } else if (CollectionUtils.isNotEmpty(catchs) && finallyBlock == null) {
            tryBlock.prepare();

            if (!tryBlock.isFinish()) {
                OperatorFactory.newOperator(GOTO.class, new Class[] { KernelProgramBlock.class, Label.class },
                        tryBlock, getSerialEnd());
            }

            for (KernelCatch c : catchs) {
                c.prepare();
                if (!c.isFinish()) {
                    OperatorFactory.newOperator(GOTO.class, new Class[] { KernelProgramBlock.class, Label.class }, c,
                            getSerialEnd());
                }
            }
        } else if (CollectionUtils.isEmpty(catchs) && finallyBlock != null) {
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

            for (KernelCatch c : catchs) {
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

            addAnyExceptionCatchRange(catchs.get(catchs.size() - 1).getEnd());

            implicitCatch.prepare();

            finallyBlock.prepare();
        }

        /*
         * implicitCatch and finallyBlock is not null if finallyBlock is null
         */
        if (finallyBlock != null) {
            // build Exception Table(just for any type)
            for (int i = 0; i < anyCatchRange.size();) {
                Label start = anyCatchRange.get(i++);
                Label end = anyCatchRange.get(i++);
                this.addTreCatchInfo(start, end, implicitCatch.getStart(), AnyException.ANY);
            }
        }

        // for exception table
        if (CollectionUtils.isNotEmpty(tryCatchInfoes)) {
            for (TryCatchInfo info : tryCatchInfoes) {
                targetParent.getMethod().getMethodBody().addTryCatchInfo(info);
            }
        }
    }

    @Override
    public void execute() {
        tryBlock.execute();

        if (CollectionUtils.isNotEmpty(catchs)) {
            for (KernelCatch c : catchs) {
                c.execute();
            }
        }

        if (finallyBlock != null) {
            implicitCatch.execute();
            finallyBlock.execute();
        }
    }

    void appendEpisode(KernelCatch catchBlock) {
        if (catchs == null) {
            catchs = new ArrayList<KernelCatch>();
            getQueue().addAfter(tryBlock, catchBlock);
        } else {
            KernelCatch previous = catchs.get(catchs.size() - 1);
            AClass exceptionType = catchBlock.getExceptionType();

            if (exceptionType != null && exceptionType.isChildOrEqual(previous.getExceptionType())) {
                throw new ASMSupportException("Unreachable catch block for " + exceptionType
                        + ". It is already handled by the catch block for " + exceptionType);
            }

            getQueue().addAfter(previous, catchBlock);
        }

        initEpisode(catchBlock);
        catchs.add(catchBlock);

        // add Exception Table:
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

            // dosen't detected previous whether serial block.
            OperatorFactory.newOperator(Marker.class, false, new Class[] { KernelProgramBlock.class, Label.class },
                    breakBlock, startLbl);

            // append finally block code to list
            finallyBlock.generateTo(breakBlock);

            {
                // *hard to understand*
                // append the b to end of the list
                breakBlock.getQueue().add(ret);

                // if already returned in inserted finally code
                // remove only the break stack operator
                if (breakBlock.isFinish()) {
                    // remove only brk node
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
            container = new ArrayList<KernelReturn>();
        }
        for (ByteCodeExecutor executor : block.getQueue()) {
            if (executor instanceof KernelReturn) {
                container.add((KernelReturn) executor);
            } else if (executor instanceof AbstractKernelBlock && !(executor instanceof ImplicitCatch)) {
                fetchAllBreakStack((AbstractKernelBlock) executor, container);
            }
        }
        return container;
    }

    private void addTreCatchInfo(Label start, Label end, Label handler, AClass type) {
        if (tryCatchInfoes == null) {
            tryCatchInfoes = new ArrayList<TryCatchInfo>();
        }
        tryCatchInfoes.add(new TryCatchInfo(start, end, handler, type.getType()));
    }

    public void addAnyExceptionCatchRange(Label label) {
        if (anyCatchRange == null)
            anyCatchRange = new ArrayList<Label>();
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
            LocalVariable exception = getLocalAnonymousVariableModel(AnyException.ANY);

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
            insnHelper.getMv().getStack().push(AnyException.ANY.getType());
            for (ByteCodeExecutor exe : getQueue()) {
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
            return catchs.get(catchs.size() - 1).getEnd();
        }
    }

}
