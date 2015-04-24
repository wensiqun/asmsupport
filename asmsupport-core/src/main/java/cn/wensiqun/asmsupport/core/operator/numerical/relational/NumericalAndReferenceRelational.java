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
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class NumericalAndReferenceRelational extends AbstractRelational {
    
    private static final Log LOG = LogFactory.getLog(NumericalRelational.class);
    
    protected NumericalAndReferenceRelational(KernelProgramBlock block, KernelParame factor1, KernelParame factor2, Operator operator) {
        super(block, factor1, factor2, operator);
    }
    
    @Override
    protected void verifyArgument() {
        AClass ftrCls1 = AClassUtils.getPrimitiveAClass(factor1.getResultType());
        AClass ftrCls2 = AClassUtils.getPrimitiveAClass(factor2.getResultType());
        
        if(ftrCls1.equals(AClassFactory.getType(boolean.class))&&
           ftrCls2.equals(AClassFactory.getType(boolean.class))){
        
        } else if(ftrCls1.isPrimitive() && ftrCls2.isPrimitive()){
            checkFactorForNumerical(ftrCls1);
            checkFactorForNumerical(ftrCls2);
        }
    }

    @Override
    protected void checkAsArgument() {
        factor1.asArgument();
        factor2.asArgument();
    }

    @Override
    protected void factorsToStack() {
        AClass ftrCls1 = factor1.getResultType();
        AClass ftrCls2 = factor2.getResultType();
        
        if(ftrCls1.isPrimitive() || ftrCls2.isPrimitive()){
            
            LOG.print("push the first factor to stack");
            factor1.loadToStack(block);
            
            if(!ftrCls1.isPrimitive()){
                LOG.print("unbox " + ftrCls1);
                insnHelper.unbox(ftrCls1.getType());
            }
            
            boolean isNumerical = (targetClass.getCastOrder() >= AClassFactory.getType(byte.class).getCastOrder() &&
                       targetClass.getCastOrder() <= AClassFactory.getType(double.class).getCastOrder());
            
            if(isNumerical){
                if(!ftrCls1.equals(targetClass) &&
                   targetClass.getCastOrder() > AClassFactory.getType(int.class).getCastOrder()){
                    LOG.print("cast from " + ftrCls1 + " to " + targetClass);
                    insnHelper.cast(ftrCls1.getType(), targetClass.getType());
                }
            }

            LOG.print("push the second factor to stack");
            factor2.loadToStack(block);
            
            if(!ftrCls2.isPrimitive()){
                LOG.print("unbox " + ftrCls1);
                insnHelper.unbox(ftrCls2.getType());
            }
            
            if(isNumerical){
                if(!ftrCls2.equals(targetClass) &&
                   targetClass.getCastOrder() > AClassFactory.getType(int.class).getCastOrder()){
                    LOG.print("cast from " + ftrCls2 + " to " + targetClass);
                    insnHelper.cast(ftrCls2.getType(), targetClass.getType());
                }
            }
        }else{
            LOG.print("push the first factor to stack");
            factor1.loadToStack(block);
            
            LOG.print("push the second factor to stack");
            factor2.loadToStack(block);
        }
    }
    
}
