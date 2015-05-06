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

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class BinaryBitwise extends AbstractBitwise {

    private static final Log LOG = LogFactory.getLog(BinaryBitwise.class);
    
    protected KernelParam factor1;
    protected KernelParam factor2;
    
    protected BinaryBitwise(KernelProgramBlock block, KernelParam factor1, KernelParam factor2, Operator operator) {
        super(block, operator);
        this.factor1 = factor1;
        this.factor2 = factor2;
    }

    @Override
    protected void verifyArgument() {
        AClass ftrCls1 = factor1.getResultType();
        AClass ftrCls2 = factor2.getResultType();
        
        checkFactor(ftrCls1);
        checkFactor(ftrCls2);
    }

    @Override
    protected void checkAsArgument() {
        factor1.asArgument();
        factor2.asArgument();
    }

    @Override
    protected void initAdditionalProperties() {
        AClass ftrCls1 = factor1.getResultType();
        AClass ftrCls2 = factor2.getResultType();
        
        if(ftrCls2.getCastOrder() < ftrCls1.getCastOrder()){
            targetClass = ftrCls1;
        }else{
            targetClass = ftrCls2;
        }
        
        if(factor1 instanceof Value) {
            ((Value)factor1).convert(targetClass);
        }
        
        if(factor2 instanceof Value) {
        	if(getOperatorSymbol().equals(Operator.SHIFT_LEFT) ||
        	   getOperatorSymbol().equals(Operator.SHIFT_RIGHT) ||
        	   getOperatorSymbol().equals(Operator.UNSIGNED_SHIFT_RIGHT) ){
        		((Value)factor2).convert(AClassFactory.getType(int.class));
        	} else {
        		((Value)factor2).convert(targetClass);	
        	}
            
        }
    }

    @Override
    protected final void factorToStack() {
        LOG.print("push the first arithmetic factor to stack");
        factor1.loadToStack(block);
        if(LOG.isPrintEnabled()){
            if(!factor1.getResultType().equals(targetClass)){
                LOG.print("cast arithmetic factor from " + factor1.getResultType() + " to " + targetClass);
            }
        }
        insnHelper.unbox(factor1.getResultType().getType());
        insnHelper.cast(factor1.getResultType().getType(), targetClass.getType());    
        
        if(LOG.isPrintEnabled()) {
            LOG.print("push the second arithmetic factor to stack");	
        }
        factor2.loadToStack(block);
        
        if(LOG.isPrintEnabled()){
            if(!factor2.getResultType().equals(targetClass)){
                LOG.print("cast arithmetic factor from " + factor2.getResultType() + " to " + targetClass);
            }
        }
        
        insnHelper.unbox(factor2.getResultType().getType());
        
        if(getOperatorSymbol().equals(Operator.SHIFT_LEFT) ||
           getOperatorSymbol().equals(Operator.SHIFT_RIGHT) ||
           getOperatorSymbol().equals(Operator.UNSIGNED_SHIFT_RIGHT) ){
            insnHelper.cast(factor2.getResultType().getType(), AClassFactory.getType(int.class).getType());
        }else{
            insnHelper.cast(factor2.getResultType().getType(), targetClass.getType());
        }
    }
    
    @Override
    public final void doExecute() {
        if(LOG.isPrintEnabled()) {
            LOG.print("prepare operator " + getOperatorSymbol());
        }
        factorToStack();
        if(LOG.isPrintEnabled()) {
            LOG.print("execute operator " + getOperatorSymbol());
        }
        innerRunExe();
    }

    protected abstract void innerRunExe();

}
