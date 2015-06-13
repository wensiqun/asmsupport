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

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.operator.AbstractParamOperator;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.AClassUtils;

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
    
    protected AClass targetClass;
    
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
        AClass ftrCls1 = AClassUtils.getPrimitiveAClass(leftFactor.getResultType());
        AClass ftrCls2 = AClassUtils.getPrimitiveAClass(rightFactor.getResultType());
        
        if(ftrCls1.getCastOrder() > ftrCls2.getCastOrder()){
            targetClass = ftrCls1;
        }else{
            targetClass = ftrCls2;
        }
        
        if(leftFactor instanceof Value)
            ((Value)leftFactor).convert(targetClass);
        
        if(rightFactor instanceof Value)
            ((Value)rightFactor).convert(targetClass);
    }
    
    protected final void checkFactorForNumerical(AClass ftrCls){
        if(!ftrCls.isPrimitive() || 
           ftrCls.equals(AClassFactory.getType(boolean.class))){
            throw new ASMSupportException("this operator " + getOperatorSymbol().getSymbol() + " cannot support for type " + ftrCls );
        }
    }
    
    @Override
    public void loadToStack(KernelProgramBlock block) {
        this.execute();
    }

    @Override
    public void execute() {
        if(byOtherUsed){
            if(LOG.isPrintEnabled()){
            	LOG.print("run operator " + leftFactor.getResultType() + getOperatorSymbol().getSymbol() + rightFactor.getResultType());
            }
            super.execute();
        }else{
            throw new ASMSupportException("the operator " + leftFactor.getResultType() + getOperatorSymbol().getSymbol() +
                                          rightFactor.getResultType() + " has not been used by other operator.");
        }
    }

    @Override
    public AClass getResultType() {
        return AClassFactory.getType(boolean.class);
    }

    @Override
    public void asArgument() {
        byOtherUsed = true;
        block.removeExe(this);
    }

    protected abstract void factorsToStack();

	@Override
    protected void doExecute() {
		instructionGenerate();
        defaultStackOperator();
    }
	
	protected void instructionGenerate(){
		factorsToStack();
		
        negativeCmp(falseLbl);

        MethodVisitor mv = insnHelper.getMv();
        
        //push true to stack
        mv.visitInsn(Opcodes.ICONST_0 + 1);
        mv.visitJumpInsn(Opcodes.GOTO, trueLbl);
        mv.visitLabel(falseLbl);
        
        //push false to stack
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitLabel(trueLbl);
	}
	
	protected void defaultStackOperator(){
		block.getMethod().getStack().pop();
        block.getMethod().getStack().pop();
        block.getMethod().getStack().push(Type.INT_TYPE);
	}
	
    @Override
    public void jumpPositive(KernelParam from, Label posLbl, Label negLbl) {
        factorsToStack();
        positiveCmp(posLbl);
    }

    @Override
    public void jumpNegative(KernelParam from, Label posLbl, Label negLbl) {
        factorsToStack();
        negativeCmp(negLbl);
    }
    
    protected abstract void negativeCmp(Label lbl);

    protected abstract void positiveCmp(Label lbl);
    
}
