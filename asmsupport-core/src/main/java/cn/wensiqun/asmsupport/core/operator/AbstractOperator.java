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
package cn.wensiqun.asmsupport.core.operator;

import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.utils.InstructionNode;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 * 
 */
public abstract class AbstractOperator extends InstructionNode {

    private int compileOrder;
    
    private Operator operatorSymbol;
    
    protected AbstractOperator(KernelProgramBlock block, Operator operatorSymbol) {
        setParent(block);
        this.operatorSymbol = operatorSymbol;
        block.getChildren().add(this);
    }

    public KernelProgramBlock getParent() {
        return (KernelProgramBlock) super.getParent();
    }

    @Override
    public final void prepare() {

        startingPrepare();

        initAdditionalProperties();

        verifyArgument();

        endingPrepare();
    }

    /**
     * Template for {@link #prepare()} method. <h4>Order :</h4>
     * <ol>
     * <li><b>startPrepare</b></li>
     * <li>initAdditionalProperties</li>
     * <li>verifyArgument</li>
     * <li>endingPrepare</li>
     * </ol>
     */
    protected void startingPrepare() {
    }

    /**
     * Template for {@link #prepare()} method. <h4>Order :</h4>
     * <ol>
     * <li>startPrepare</li>
     * <li><b>initAdditionalProperties</b></li>
     * <li>verifyArgument</li>
     * <li>endingPrepare</li>
     * </ol>
     */
    protected void initAdditionalProperties() {
    }

    /**
     * Template for {@link #prepare()} method. <h4>Order :</h4>
     * <ol>
     * <li>startPrepare</li>
     * <li>initAdditionalProperties</li>
     * <li><b>verifyArgument</b></li>
     * <li>endingPrepare</li>
     * </ol>
     */
    protected void verifyArgument() {
    }

    /**
     * Template for {@link #prepare()} method. <h4>Order :</h4>
     * <ol>
     * <li>startPrepare</li>
     * <li>initAdditionalProperties</li>
     * <li>verifyArgument</li>
     * <li><b>endingPrepare</b></li>
     * </ol>
     */
    protected void endingPrepare() {
    }

    /**
     * invoke by OperatorFactory
     */
    protected void checkAsArgument() {
    }

    @Override
    public void execute() {
        compileOrder = getParent().getMethod().getNextInstructionNumber();
        try {
            doExecute();
        } catch (Exception e) {
            throw new ASMSupportException("Error while generate bytecode (" + toString() + ")", e);
        }
    }

    protected abstract void doExecute();

    /**
     * <p>
     * Auto cast top element of stack from original type to target type.
     * </p>
     *
     * <strong>support auto cast type :</strong>
     * <ol>
     * <li>primitive type to wrapper type(box)</li>
     * <li>wrapper type to primitive type(unbox)</li>
     * <li>low type to high type</li>
     * <li>if enforce is <b>true</b> : allow high type to low type</li>
     * </ol>
     *
     * @param original
     * @param target
     * @param enforce
     */
    protected void autoCast(IClass original, IClass target, boolean enforce) {
        if (enforce) {
            if (original.isPrimitive() && target.isPrimitive()) {
                getInstructions().cast(original.getType(), target.getType());

                return;
            } else if (original.isPrimitive() && IClassUtils.isPrimitiveWrapAClass(target)) {
                Type targetPrimitiveType = IClassUtils.getPrimitiveAClass(target).getType();

                getInstructions().cast(original.getType(), targetPrimitiveType);

                getInstructions().box(targetPrimitiveType);

                return;
            } else if (IClassUtils.isPrimitiveWrapAClass(original) && target.isPrimitive()) {
                getInstructions().unbox(original.getType());

                Type originalPrimitiveType = IClassUtils.getPrimitiveAClass(original).getType();

                getInstructions().cast(originalPrimitiveType, target.getType());

                return;
            } else if (original.isPrimitive() && target.equals(getType(Object.class))) {
                getInstructions().box(original.getType());
                return;
            }
        } else {
            if (original.isPrimitive() && target.isPrimitive()) {
                if (!original.equals(getType(Boolean.class)) &&
                	!target.equals(getType(Boolean.class)) &&
                	original.getCastOrder() <= target.getCastOrder()) {
                    getInstructions().cast(original.getType(), target.getType());
                    return;
                }
            } else if (original.isPrimitive()
                    && (IClassUtils.getPrimitiveWrapAClass(original).equals(target) || target
                            .equals(getType(Object.class)))) {
                getInstructions().box(original.getType());
                return;
            } else if (IClassUtils.isPrimitiveWrapAClass(original)
                    && target.isPrimitive()
                    && original.equals(IClassUtils.getPrimitiveWrapAClass(target))) {
                Type primType = Instructions.getUnBoxedType(original.getType());
                getInstructions().unbox(original.getType());
                getInstructions().cast(primType, target.getType());
                return;
            }
        }
    }

    public final int getCompileOrder() {
        return compileOrder;
    }

    public Operator getOperatorSymbol() {
        return operatorSymbol;
    }

    protected IClass getType(Class<?> clazz) {
        return getParent().getMethod()
                .getClassLoader().getType(clazz);
    }

    protected Instructions getInstructions() {
        return getParent().getMethod().getInstructions();
    }
}
