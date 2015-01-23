/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator;

import cn.wensiqun.asmsupport.core.ByteCodeExecutor;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


/**
 * 
 * @author 温斯群(Joe Wen)
 * 
 */
public abstract class AbstractOperator extends ByteCodeExecutor {

    protected ProgramBlockInternal block;
    
    protected InstructionHelper insnHelper;
	
    private int compileOrder;

    protected AbstractOperator(ProgramBlockInternal block) {
        this.insnHelper = block.getInsnHelper();
        this.block = block;
        //addQueue();
        block.getQueue().add(this);
    }
    
    public ProgramBlockInternal getBlock() {
        return block;
    }
    
    /*protected void addQueue()
    {
        block.addExe(this);
    }*/

	@Override
    public final void prepare() {

        startingPrepare();
        
        initAdditionalProperties();
        
        verifyArgument();

        endingPrepare();
    }

	/**
	 * template for {@link #prepare()} method.
	 * <h4>Order :</h4>
	 * <ol>
	 *     <li><b>startPrepare</b></li>
     *     <li>initAdditionalProperties</li>
     *     <li>verifyArgument</li>
     *     <li>checkCrement</li>
     *     <li>endingPrepare</li>
	 * </ol>
	 */
    protected void startingPrepare() {}
    
    /**
     * template for {@link #prepare()} method.
     * <h4>Order :</h4>
     * <ol>
     *     <li>startPrepare</li>
     *     <li><b>initAdditionalProperties</b></li>
     *     <li>verifyArgument</li>
     *     <li>checkCrement</li>
     *     <li>endingPrepare</li>
     * </ol>
     */
    protected void initAdditionalProperties() {}

    /**
     * template for {@link #prepare()} method.
     * <h4>Order :</h4>
     * <ol>
     *     <li>startPrepare</li>
     *     <li>initAdditionalProperties</li>
     *     <li><b>verifyArgument</b></li>
     *     <li>checkCrement</li>
     *     <li>endingPrepare</li>
     * </ol>
     */
    protected void verifyArgument() {}

    /**
     * template for {@link #prepare()} method.
     * <h4>Order :</h4>
     * <ol>
     *     <li>startPrepare</li>
     *     <li>initAdditionalProperties</li>
     *     <li>verifyArgument</li>
     *     <li><b>checkCrement</b></li>
     *     <li>endingPrepare</li>
     * </ol>
     */
    protected void checkCrement() {}

    /**
     * template for {@link #prepare()} method.
     * <h4>Order :</h4>
     * <ol>
     *     <li>startPrepare</li>
     *     <li>initAdditionalProperties</li>
     *     <li>verifyArgument</li>
     *     <li>checkCrement</li>
     *     <li><b>endingPrepare</b></li>
     * </ol>
     */
    protected void endingPrepare() {}

    /**
     * invoke by OperatorFactory
     */
    protected void checkAsArgument() {}

    @Override
    public void execute() {
        compileOrder = insnHelper.getMethod().nextInsNumber();
        doExecute();
    }

    protected abstract void doExecute();
    
    /**
     * <p>Auto cast top element of stack from original type to target type.</p>
     * 
     * <strong>support auto cast type :</strong> 
     * <ol>
     *     <li>primitive type to wrapper type(box)</li>
     *     <li>wrapper type to primitive type(unbox)</li>
     *     <li>low type to high type</li>
     *     <li>if enforce is <b>true</b> : allow high type to low type</li>
     * </ol>
     * 
     * @param original
     * @param target
     * @param enforce
     */
    protected void autoCast(AClass original, AClass target, boolean enforce){
        if(original.isChildOrEqual(target)){
            return;
        }
        
        if(enforce)
        {
            if(original.isPrimitive() && target.isPrimitive())
            {
                insnHelper.cast(original.getType(), target.getType());
                
                return;
            }
            else if(original.isPrimitive() && AClassUtils.isPrimitiveWrapAClass(target))
            {
                Type targetPrimitiveType = AClassUtils.getPrimitiveAClass(target).getType();
                
                insnHelper.cast(original.getType(), targetPrimitiveType);
                
                insnHelper.box(targetPrimitiveType);
                
                return;
            }
            else if(AClassUtils.isPrimitiveWrapAClass(original) && target.isPrimitive())
            {
                insnHelper.unbox(original.getType());
                
                Type originalPrimitiveType = AClassUtils.getPrimitiveAClass(original).getType();

                insnHelper.cast(originalPrimitiveType, target.getType());
                
                return;
            }
            else if(original.isPrimitive() && target.equals(AClass.OBJECT_ACLASS))
            {
                insnHelper.box(original.getType());
                return;
            }
        }
        else
        {
            if(original.isPrimitive() && target.isPrimitive()){
                if(!original.equals(AClass.BOOLEAN_ACLASS) && 
                   !target.equals(AClass.BOOLEAN_ACLASS) && 
                   original.getCastOrder() <= target.getCastOrder()
                 ){
                    insnHelper.cast(original.getType(), target.getType());
                    return;
                }            
            }
            else if(original.isPrimitive() && (AClassUtils.getPrimitiveWrapAClass(original).equals(target) || target.equals(AClass.OBJECT_ACLASS)))
            {
                insnHelper.box(original.getType());
                return;
            }
            else if(AClassUtils.isPrimitiveWrapAClass(original) && original.equals(AClassUtils.getPrimitiveWrapAClass(target)))
            {
                Type primType = InstructionHelper.getUnBoxedType(original.getType());
                insnHelper.unbox(original.getType());
                insnHelper.cast(primType, target.getType());
                return;
            }
        }
        
        throw new ASMSupportException("cannot auto cast from " + original + " to " + target + " also you can use CheckCast to try again!");
        
    }
    
    public final int getCompileOrder() {
        return compileOrder;
    }


}
