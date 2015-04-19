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

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.operator.Operators;
import cn.wensiqun.asmsupport.standard.clazz.AClass;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class BinaryBitwise extends AbstractBitwise {

    private static final Log LOG = LogFactory.getLog(BinaryBitwise.class);
    
    protected InternalParameterized factor1;
    protected InternalParameterized factor2;
    
    protected BinaryBitwise(ProgramBlockInternal block, InternalParameterized factor1, InternalParameterized factor2) {
        super(block);
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
        
        if(factor1 instanceof Value)
            ((Value)factor1).convert(targetClass);
        
        if(factor2 instanceof Value)
            ((Value)factor2).convert(targetClass);
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
        
        LOG.print("push the second arithmetic factor to stack");
        factor2.loadToStack(block);
        
        if(LOG.isPrintEnabled()){
            if(!factor2.getResultType().equals(targetClass)){
                LOG.print("cast arithmetic factor from " + factor2.getResultType() + " to " + targetClass);
            }
        }
        
        insnHelper.unbox(factor2.getResultType().getType());
        
        if(operator.equals(Operators.LEFT_SHIFT) ||
           operator.equals(Operators.RIGHT_SHIFT) ||
           operator.equals(Operators.UNSIGNED_RIGHT_SHIFT) ){
            insnHelper.cast(factor2.getResultType().getType(), AClassFactory.getType(int.class).getType());
        }else{
            insnHelper.cast(factor2.getResultType().getType(), targetClass.getType());
        }
    }
    
    @Override
    public final void doExecute() {
        LOG.print("prepare operator " + operator);
        factorToStack();
        LOG.print("execute operator " + operator);
        innerRunExe();
    }

    protected abstract void innerRunExe();

}
