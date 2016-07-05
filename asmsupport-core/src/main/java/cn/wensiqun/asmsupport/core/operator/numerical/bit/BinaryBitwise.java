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
package cn.wensiqun.asmsupport.core.operator.numerical.bit;

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class BinaryBitwise extends AbstractBitwise {

    private static final Log LOG = LogFactory.getLog(BinaryBitwise.class);
    
    protected KernelParam leftFactor;
    protected KernelParam rightFactor;
    
    protected BinaryBitwise(KernelProgramBlock block, KernelParam leftFactor, KernelParam rightFactor, Operator operator) {
        super(block, operator);
        this.leftFactor = leftFactor;
        this.rightFactor = rightFactor;
    }

    @Override
    protected void verifyArgument() {
    	IClass ftrCls1 = leftFactor.getResultType();
    	IClass ftrCls2 = rightFactor.getResultType();
        
        checkFactor(ftrCls1);
        checkFactor(ftrCls2);
    }

    @Override
    protected void checkAsArgument() {
        leftFactor.asArgument();
        rightFactor.asArgument();
    }

    @Override
    protected void initAdditionalProperties() {
    	IClass ftrCls1 = leftFactor.getResultType();
    	IClass ftrCls2 = rightFactor.getResultType();
        
        if(ftrCls2.getCastOrder() < ftrCls1.getCastOrder()){
            targetClass = ftrCls1;
        }else{
            targetClass = ftrCls2;
        }
        
        if(leftFactor instanceof Value) {
            ((Value)leftFactor).convert(targetClass);
        }
        
        if(rightFactor instanceof Value) {
        	if(getOperatorSymbol().equals(Operator.SHIFT_LEFT) ||
        	   getOperatorSymbol().equals(Operator.SHIFT_RIGHT) ||
        	   getOperatorSymbol().equals(Operator.UNSIGNED_SHIFT_RIGHT) ){
        		((Value)rightFactor).convert(getType(int.class));
        	} else {
        		((Value)rightFactor).convert(targetClass);	
        	}
            
        }
    }

    @Override
    protected final void factorToStack(MethodContext context) {
        Instructions instructions = context.getInstructions();
        LOG.print("push the first arithmetic factor to stack");
        leftFactor.loadToStack(context);
        if(LOG.isPrintEnabled()){
            if(!leftFactor.getResultType().equals(targetClass)){
                LOG.print("cast arithmetic factor from " + leftFactor.getResultType() + " to " + targetClass);
            }
        }
        instructions.unbox(leftFactor.getResultType().getType());
        instructions.cast(leftFactor.getResultType().getType(), targetClass.getType());
        
        if(LOG.isPrintEnabled()) {
            LOG.print("push the second arithmetic factor to stack");	
        }
        rightFactor.loadToStack(context);
        
        if(LOG.isPrintEnabled()){
            if(!rightFactor.getResultType().equals(targetClass)){
                LOG.print("cast arithmetic factor from " + rightFactor.getResultType() + " to " + targetClass);
            }
        }

        instructions.unbox(rightFactor.getResultType().getType());
        
        if(getOperatorSymbol().equals(Operator.SHIFT_LEFT) ||
           getOperatorSymbol().equals(Operator.SHIFT_RIGHT) ||
           getOperatorSymbol().equals(Operator.UNSIGNED_SHIFT_RIGHT) ){
            instructions.cast(rightFactor.getResultType().getType(), getType(int.class).getType());
        }else{
            instructions.cast(rightFactor.getResultType().getType(), targetClass.getType());
        }
    }
    
    @Override
    public final void doExecute(MethodContext context) {
        if(LOG.isPrintEnabled()) {
            LOG.print("prepare operator " + getOperatorSymbol());
        }
        factorToStack(context);
        if(LOG.isPrintEnabled()) {
            LOG.print("execute operator " + getOperatorSymbol());
        }
        innerRunExe(context);
    }

    protected abstract void innerRunExe(MethodContext context);

}
