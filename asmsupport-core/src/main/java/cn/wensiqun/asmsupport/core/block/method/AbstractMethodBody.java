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

import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.core.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.core.operator.common.LocalVariableCreator;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.utils.common.BlockStack;
import cn.wensiqun.asmsupport.core.utils.common.ExceptionTableEntry;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeComponent;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeLogicVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.def.var.meta.VarMeta;
import cn.wensiqun.asmsupport.utils.Modifiers;
import cn.wensiqun.asmsupport.utils.ASConstants;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMethodBody extends KernelProgramBlock {

    private static final Log LOG = LogFactory.getLog(AbstractMethodBody.class);

    /** The method of current method */
    protected LocalVariable[] arguments;

    /** super key word  */
    private SuperVariable superVariable;

    /** this key word */
    private ThisVariable thisVariable;

    protected BlockStack stack;

    private List<ExceptionTableEntry> exceptions;

    private AMethod method;

    public AbstractMethodBody() {
        exceptions = new ArrayList<>();
        this.stack = new BlockStack();
    }

    /**
     * Get the method argument that's corresponding to current block,
     * the parameter represent as a {@link LocalVariable}
     */
    public LocalVariable[] getArguments() {
        return arguments;
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
        if (!Modifiers.isStatic(meta.getModifiers())) {
            OperatorFactory.newOperator(LocalVariableCreator.class, new Class<?>[] { KernelProgramBlock.class,
                    String.class, Type.class, Type.class }, getExecutor(), ASConstants.THIS, meta.getOwner().getType(),
                    method.getMeta().getOwner().getType());
        }

        String[] argNames = meta.getParameterNames();
        IClass[] argumentClasses = meta.getParameterTypes();
        arguments = new LocalVariable[argNames.length];
        for (int i = 0; i < argNames.length; i++) {
            ScopeLogicVariable slv = new ScopeLogicVariable(argNames[i], getScope(), argumentClasses[i].getType(),
                    argumentClasses[i].getType());
            VarMeta lve = new VarMeta(argNames[i], 0, argumentClasses[i]);
            LocalVariable lv = new LocalVariable(lve);
            lv.setScopeLogicVar(slv);
            arguments[i] = lv;
        }
    }

    @Override
    public void doExecute() {
        AMethod method = getMethod();
        if (LOG.isPrintEnabled()) {
            StringBuilder str = new StringBuilder("Create method: ------------");
            str.append(method.getMeta().getMethodString());
            LOG.print(str);
        }

        for (Executable exe : getQueue()) {
            exe.execute();
        }

        for (ExceptionTableEntry tci : exceptions) {
            if (tci.getEnd().getOffset() - tci.getStart().getOffset() > 0) {
                Type type = tci.getException();
                getMethod().getInstructions().tryCatchBlock(tci.getStart(), tci.getEnd(), tci.getHandler(),
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
        getMethod().getInstructions().maxs(s, l);
    }

    /**
     * local variable declaration.
     */
    private void declarationVariable(Scope parent) {
        List<ScopeComponent> components = parent.getComponents();
        ScopeComponent com;
        Scope lastBrotherScope;
        for (int i = 0; i < components.size(); i++) {
            com = components.get(i);
            if (com instanceof ScopeLogicVariable) {
                ScopeLogicVariable slv = (ScopeLogicVariable) com;
                if (slv.isAnonymous()) {
                    continue;
                }
                getMethod().getInstructions().declarationVariable(slv.getName(), slv.getType().getDescriptor(), null,
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
        exceptions.add(info);
    }

    @Override
    public BlockStack getBlockTracker() {
        return stack;
    }



    /**
     * Get super keyword variable.
     *
     * @return
     */
    public SuperVariable getSuperVariable() {
        if(superVariable == null){
            superVariable = new SuperVariable(getMethod().getMeta().getActuallyOwner());
        }
        return superVariable;
    }

    /**
     * Get this keyword variable
     * @return
     */
    public ThisVariable getThisVariable() {
        if(thisVariable == null){
            thisVariable = new ThisVariable(getMethod().getMeta().getActuallyOwner());
        }
        return thisVariable;
    }


    /**
     * Set {@link AMethod}
     *
     * @param method
     */
    public void setMethod(AMethod method) {
        this.method = method;
    }

    /**
     * Get method of the body.
     * @return
     */
    public AMethod getMethod() {
        if(method == null) {
            return getExecutor().getMethod();
        } else {
            return method;
        }
    }

}
