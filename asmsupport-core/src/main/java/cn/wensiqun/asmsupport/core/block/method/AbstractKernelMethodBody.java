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
package cn.wensiqun.asmsupport.core.block.method;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.common.LocalVariableCreator;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.utils.common.ExceptionTableEntry;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeComponent;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeLogicVariable;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.def.var.meta.VarMeta;
import cn.wensiqun.asmsupport.utils.AsmsupportConstant;

public abstract class AbstractKernelMethodBody extends KernelProgramBlock {

    private static final Log LOG = LogFactory.getLog(AbstractKernelMethodBody.class);

    private List<ExceptionTableEntry> exceptionTable;

    protected LocalVariable[] argments;

    public AbstractKernelMethodBody() {
        super();
        exceptionTable = new ArrayList<ExceptionTableEntry>();
    }

    public LocalVariable[] getMethodArguments() {
        return argments;
    }

    @Override
    public final void generate() {
        generateBody();
    }

    /**
     * generate the method body
     */
    public abstract void generateBody();

    @Override
    protected void init() {
        AMethod method = getMethod();
        AMethodMeta meta = method.getMeta();
        if (!ModifierUtils.isStatic(meta.getModifier())) {
            OperatorFactory.newOperator(LocalVariableCreator.class, new Class<?>[] { KernelProgramBlock.class,
                    String.class, Type.class, Type.class }, getExecutor(), AsmsupportConstant.THIS, meta.getOwner().getType(),
                    method.getMeta().getOwner().getType());
        }

        String[] argNames = meta.getArgNames();
        IClass[] argClsses = meta.getArgClasses();
        argments = new LocalVariable[argNames.length];
        for (int i = 0; i < argNames.length; i++) {
            ScopeLogicVariable slv = new ScopeLogicVariable(argNames[i], getScope(), argClsses[i].getType(),
                    argClsses[i].getType());
            VarMeta lve = new VarMeta(argNames[i], 0, argClsses[i]);
            LocalVariable lv = new LocalVariable(lve);
            lv.setScopeLogicVar(slv);
            argments[i] = lv;
        }
        method.setParameters(argments);
    }

    @Override
    public final void doExecute() {
        AMethod method = getMethod();
        if (LOG.isPrintEnabled()) {
            StringBuilder str = new StringBuilder("create method: ------------");
            str.append(method.getMeta().getMethodString());
            LOG.print(str);
        }

        for (Executable exe : getQueue()) {
            exe.execute();
        }

        for (ExceptionTableEntry tci : exceptionTable) {
            if (tci.getEnd().getOffset() - tci.getStart().getOffset() > 0) {
                Type type = tci.getException();
                insnHelper.tryCatchBlock(tci.getStart(), tci.getEnd(), tci.getHandler(), 
                		(type == null || type == Type.ANY_EXP_TYPE) ? null : type);
            }
        }

    }

    /**
     * Finish method body generated
     */
    public void endMethodBody() {
        declarationVariable(getScope());
        int s = getMethod().getStack().getMaxSize();
        int l = getScope().getLocals().getSize();
        insnHelper.maxs(s, l);
    }

    /**
     * local variable declaration.
     */
    private void declarationVariable(Scope parent) {
        List<ScopeComponent> coms = parent.getComponents();
        ScopeComponent com;
        Scope lastBrotherScope = null;
        for (int i = 0; i < coms.size(); i++) {
            com = coms.get(i);
            if (com instanceof ScopeLogicVariable) {
                ScopeLogicVariable slv = (ScopeLogicVariable) com;
                if (slv.isAnonymous()) {
                    continue;
                }
                insnHelper.declarationVariable(slv.getName(), slv.getType().getDescriptor(), null,
                        slv.getSpecifiedStartLabel(), parent.getEnd(), slv.getInitStartPos());
            } else {
                lastBrotherScope = (Scope) com;
                declarationVariable(lastBrotherScope);
            }
        }
    }

    public void addExceptionTableEntry(Label start, Label end, Label handler, Type exception) {
        addExceptionTableEntry(new ExceptionTableEntry(start, end, handler, exception));
    }

    public void addExceptionTableEntry(ExceptionTableEntry info) {
        exceptionTable.add(info);
    }

}
