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

import cn.wensiqun.asmsupport.core.ByteCodeExecutor;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.exception.ASMSupportException;

/**
 * 
 * @author 温斯群(Joe Wen)
 * 
 */
public abstract class AbstractOperator extends ByteCodeExecutor {

    protected KernelProgramBlock block;

    protected InstructionHelper insnHelper;

    private int compileOrder;
    
    private Operator operatorSymbol;

    protected AbstractOperator(KernelProgramBlock block, Operator operatorSymbol) {
        this.insnHelper = block.getInsnHelper();
        this.block = block;
        this.operatorSymbol = operatorSymbol;
        // addQueue();
        block.getQueue().add(this);
    }

    public KernelProgramBlock getBlock() {
        return block;
    }

    @Override
    public final void prepare() {

        startingPrepare();

        initAdditionalProperties();

        verifyArgument();

        endingPrepare();
    }

    /**
     * template for {@link #prepare()} method. <h4>Order :</h4>
     * <ol>
     * <li><b>startPrepare</b></li>
     * <li>initAdditionalProperties</li>
     * <li>verifyArgument</li>
     * <li>checkCrement</li>
     * <li>endingPrepare</li>
     * </ol>
     */
    protected void startingPrepare() {
    }

    /**
     * template for {@link #prepare()} method. <h4>Order :</h4>
     * <ol>
     * <li>startPrepare</li>
     * <li><b>initAdditionalProperties</b></li>
     * <li>verifyArgument</li>
     * <li>checkCrement</li>
     * <li>endingPrepare</li>
     * </ol>
     */
    protected void initAdditionalProperties() {
    }

    /**
     * template for {@link #prepare()} method. <h4>Order :</h4>
     * <ol>
     * <li>startPrepare</li>
     * <li>initAdditionalProperties</li>
     * <li><b>verifyArgument</b></li>
     * <li>checkCrement</li>
     * <li>endingPrepare</li>
     * </ol>
     */
    protected void verifyArgument() {
    }

    /**
     * template for {@link #prepare()} method. <h4>Order :</h4>
     * <ol>
     * <li>startPrepare</li>
     * <li>initAdditionalProperties</li>
     * <li>verifyArgument</li>
     * <li><b>checkCrement</b></li>
     * <li>endingPrepare</li>
     * </ol>
     */
    protected void checkCrement() {
    }

    /**
     * template for {@link #prepare()} method. <h4>Order :</h4>
     * <ol>
     * <li>startPrepare</li>
     * <li>initAdditionalProperties</li>
     * <li>verifyArgument</li>
     * <li>checkCrement</li>
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
        compileOrder = insnHelper.getMethod().nextInsNumber();
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
    protected void autoCast(AClass original, AClass target, boolean enforce) {
        if (enforce) {
            if (original.isPrimitive() && target.isPrimitive()) {
                insnHelper.cast(original.getType(), target.getType());

                return;
            } else if (original.isPrimitive() && AClassUtils.isPrimitiveWrapAClass(target)) {
                Type targetPrimitiveType = AClassUtils.getPrimitiveAClass(target).getType();

                insnHelper.cast(original.getType(), targetPrimitiveType);

                insnHelper.box(targetPrimitiveType);

                return;
            } else if (AClassUtils.isPrimitiveWrapAClass(original) && target.isPrimitive()) {
                insnHelper.unbox(original.getType());

                Type originalPrimitiveType = AClassUtils.getPrimitiveAClass(original).getType();

                insnHelper.cast(originalPrimitiveType, target.getType());

                return;
            } else if (original.isPrimitive() && target.equals(AClassFactory.getType(Object.class))) {
                insnHelper.box(original.getType());
                return;
            }
        } else {
            if (original.isPrimitive() && target.isPrimitive()) {
                if (!original.equals(AClassFactory.getType(Boolean.class)) && 
                	!target.equals(AClassFactory.getType(Boolean.class)) && 
                	original.getCastOrder() <= target.getCastOrder()) {
                    insnHelper.cast(original.getType(), target.getType());
                    return;
                }
            } else if (original.isPrimitive()
                    && (AClassUtils.getPrimitiveWrapAClass(original).equals(target) || target
                            .equals(AClassFactory.getType(Object.class)))) {
                insnHelper.box(original.getType());
                return;
            } else if (AClassUtils.isPrimitiveWrapAClass(original)
                    && original.equals(AClassUtils.getPrimitiveWrapAClass(target))) {
                Type primType = InstructionHelper.getUnBoxedType(original.getType());
                insnHelper.unbox(original.getType());
                insnHelper.cast(primType, target.getType());
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
    
}
