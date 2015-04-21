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

import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.operator.AbstractParameterizedOperator;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.operator.Operators;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.exception.ASMSupportException;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractRelational extends AbstractParameterizedOperator implements Jumpable  {

    private static final Log LOG = LogFactory.getLog(AbstractRelational.class);
    
    /**算数因子1 */
    protected KernelParameterized factor1;

    /**算数因子2 */
    protected KernelParameterized factor2;
    
    /**该操作是否被其他操作引用 */
    private boolean byOtherUsed;
    
    protected AClass targetClass;
    
    protected Label trueLbl;
    protected Label falseLbl;
    
    protected AbstractRelational(ProgramBlockInternal block, KernelParameterized factor1, KernelParameterized factor2, Operators operator) {
        super(block, operator);
        this.factor1 = factor1;
        this.factor2 = factor2;
        falseLbl = new Label();
        trueLbl = new Label();
    }

    @Override
    protected void initAdditionalProperties() {
        //replace Value object
        
        AClass ftrCls1 = AClassUtils.getPrimitiveAClass(factor1.getResultType());
        AClass ftrCls2 = AClassUtils.getPrimitiveAClass(factor2.getResultType());
        
        if(ftrCls1.getCastOrder() > ftrCls2.getCastOrder()){
            targetClass = ftrCls1;
        }else{
            targetClass = ftrCls2;
        }
        
        if(factor1 instanceof Value)
            ((Value)factor1).convert(targetClass);
        
        if(factor2 instanceof Value)
            ((Value)factor2).convert(targetClass);
    }
    
    protected final void checkFactorForNumerical(AClass ftrCls){
        if(!ftrCls.isPrimitive() || 
           ftrCls.equals(AClassFactory.getType(boolean.class))){
            throw new ASMSupportException("this operator " + getOperatorSymbol().getSymbol() + " cannot support for type " + ftrCls );
        }
    }
    
    @Override
    public void loadToStack(ProgramBlockInternal block) {
        this.execute();
    }

    @Override
    public void execute() {
        if(byOtherUsed){
            if(LOG.isPrintEnabled()){
            	LOG.print("run operator " + factor1.getResultType() + getOperatorSymbol().getSymbol() + factor2.getResultType());
            }
            super.execute();
        }else{
            throw new ASMSupportException("the operator " + factor1.getResultType() + getOperatorSymbol().getSymbol() +
                                          factor2.getResultType() + " has not been used by other operator.");
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

    /*@Override
	public void setJumpLable(Label lbl) {
		this.falseLbl = lbl;
	}*/

	@Override
    protected void doExecute() {
		instructionGenerate();
        defaultStackOperator();
    }
	
	/**
	 * 
	 */
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
	
	/**
	 * 
	 */
	protected void defaultStackOperator(){
		block.getMethod().getStack().pop();
        block.getMethod().getStack().pop();
        block.getMethod().getStack().push(Type.INT_TYPE);
	}
	
	/*@Override
	public final void executeJump(int cmpType, Label lbl){
        factorsToStack();
        switch(cmpType) {
        case Opcodes.JUMP_POSITIVE :
            positiveCmp(lbl);break;
        case Opcodes.JUMP_NEGATIVE : 
            negativeCmp(lbl);break;
        }
	}*/
	
    @Override
    public void jumpPositive(KernelParameterized from, Label posLbl, Label negLbl) {
        factorsToStack();
        positiveCmp(posLbl);
    }

    @Override
    public void jumpNegative(KernelParameterized from, Label posLbl, Label negLbl) {
        factorsToStack();
        negativeCmp(negLbl);
    }
    
    protected abstract void negativeCmp(Label lbl);

    protected abstract void positiveCmp(Label lbl);
    
}
