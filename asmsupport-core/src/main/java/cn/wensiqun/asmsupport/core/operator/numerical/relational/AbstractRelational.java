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
package cn.wensiqun.asmsupport.core.operator.numerical.relational;

import cn.wensiqun.asmsupport.core.context.MethodExecuteContext;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.operator.AbstractParamOperator;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.memory.OperandStack;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class AbstractRelational extends AbstractParamOperator implements Jumpable  {

    private static final Log LOG = LogFactory.getLog(AbstractRelational.class);

	/** the left factor of arithmetic */
    protected KernelParam leftFactor;

	/** the left factor of arithmetic */
    protected KernelParam rightFactor;
    
    private boolean byOtherUsed;
    
    protected IClass targetClass;
    
    protected Label trueLbl;
    protected Label falseLbl;
    
    protected AbstractRelational(KernelProgramBlock block, KernelParam leftFactor, KernelParam rightFactor, Operator operator) {
        super(block, operator);
        this.leftFactor = leftFactor;
        this.rightFactor = rightFactor;
        falseLbl = new Label();
        trueLbl = new Label();
    }

    @Override
    protected void initAdditionalProperties() {
        //replace Value object
    	IClass leftPrimitiveType = IClassUtils.getPrimitiveAClass(leftFactor.getResultType());
    	IClass rightPrimitiveType = IClassUtils.getPrimitiveAClass(rightFactor.getResultType());
        
        if(leftPrimitiveType.getCastOrder() > rightPrimitiveType.getCastOrder()){
            targetClass = leftPrimitiveType;
        } else {
            targetClass = rightPrimitiveType;
        }

        if(leftFactor instanceof Value && ((Value) leftFactor).getValue() != null)
            ((Value)leftFactor).convert(targetClass);
        
        if(rightFactor instanceof Value && ((Value) rightFactor).getValue() != null)
            ((Value)rightFactor).convert(targetClass);
    }
    
    protected final void checkFactorForNumerical(IClass ftrCls){
        if(!ftrCls.isPrimitive() || 
           ftrCls.equals(getType(boolean.class))){
            throw new ASMSupportException("this operator " + getOperatorSymbol().getSymbol() + " cannot support for type " + ftrCls );
        }
    }
    
    @Override
    public void push(MethodExecuteContext context) {
        this.execute(context);
    }

    @Override
    public void execute(MethodExecuteContext context) {
        if(byOtherUsed){
            if(LOG.isPrintEnabled()){
            	LOG.print("Run operator " + leftFactor.getResultType() + getOperatorSymbol().getSymbol() + rightFactor.getResultType());
            }
            super.execute(context);
        }else{
            throw new ASMSupportException("The operator " + leftFactor.getResultType() + getOperatorSymbol().getSymbol() +
                                          rightFactor.getResultType() + " has not been used by other operator.");
        }
    }

    @Override
    public IClass getResultType() {
        return getType(boolean.class);
    }

    @Override
    public void asArgument() {
        byOtherUsed = true;
        getParent().removeChild(this);
    }

    protected abstract void factorsToStack(MethodExecuteContext context);

	@Override
    protected void doExecute(MethodExecuteContext context) {
		instructionGenerate(context);
        defaultStackOperator(context);
    }
	
	protected void instructionGenerate(MethodExecuteContext context){
		factorsToStack(context);
		
        negativeCmp(context, falseLbl);

        MethodVisitor mv = context.getInstructions().getMv();
        
        //push true to stack
        mv.visitInsn(Opcodes.ICONST_0 + 1);
        mv.visitJumpInsn(Opcodes.GOTO, trueLbl);
        mv.visitLabel(falseLbl);
        
        //push false to stack
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitLabel(trueLbl);
	}
	
	protected void defaultStackOperator(MethodExecuteContext context){
        OperandStack stack = context.getInstructions().getOperandStack();
        stack.pop();
        stack.pop();
        stack.push(Type.INT_TYPE);
	}
	
    @Override
    public void jumpPositive(MethodExecuteContext context, KernelParam from, Label posLbl, Label negLbl) {
        factorsToStack(context);
        positiveCmp(context, posLbl);
    }

    @Override
    public void jumpNegative(MethodExecuteContext context, KernelParam from, Label posLbl, Label negLbl) {
        factorsToStack(context);
        negativeCmp(context, negLbl);
    }
    
    protected abstract void negativeCmp(MethodExecuteContext context, Label lbl);

    protected abstract void positiveCmp(MethodExecuteContext context, Label lbl);
    
}
