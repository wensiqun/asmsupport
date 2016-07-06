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
import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class NumericalAndReferenceRelational extends AbstractRelational {
    
    private static final Log LOG = LogFactory.getLog(NumericalRelational.class);
    
    protected NumericalAndReferenceRelational(KernelProgramBlock block, KernelParam leftFactor, KernelParam rightFactor, Operator operator) {
        super(block, leftFactor, rightFactor, operator);
    }
    
    @Override
    protected void verifyArgument() {
    	IClass ftrCls1 = IClassUtils.getPrimitiveAClass(leftFactor.getResultType());
    	IClass ftrCls2 = IClassUtils.getPrimitiveAClass(rightFactor.getResultType());
        
        if(ftrCls1.equals(getType(boolean.class))&&
           ftrCls2.equals(getType(boolean.class))){
        
        } else if(ftrCls1.isPrimitive() && ftrCls2.isPrimitive()){
            checkFactorForNumerical(ftrCls1);
            checkFactorForNumerical(ftrCls2);
        }
    }

    @Override
    protected void checkAsArgument() {
        leftFactor.asArgument();
        rightFactor.asArgument();
    }

    @Override
    protected void factorsToStack(MethodExecuteContext context) {
    	IClass ftrCls1 = leftFactor.getResultType();
    	IClass ftrCls2 = rightFactor.getResultType();
        Instructions instructions = context.getInstructions();
        KernelProgramBlock block = getParent();
        if(ftrCls1.isPrimitive() || ftrCls2.isPrimitive()){
            
            LOG.print("push the first factor to stack");
            leftFactor.push(context);
            
            if(!ftrCls1.isPrimitive()){
                LOG.print("unbox " + ftrCls1);
                instructions.unbox(ftrCls1.getType());
            }
            
            boolean isNumerical = (targetClass.getCastOrder() >= getType(byte.class).getCastOrder() &&
                       targetClass.getCastOrder() <= getType(double.class).getCastOrder());
            
            if(isNumerical){
                if(!ftrCls1.equals(targetClass) &&
                   targetClass.getCastOrder() > getType(int.class).getCastOrder()){
                    LOG.print("cast from " + ftrCls1 + " to " + targetClass);
                    instructions.cast(ftrCls1.getType(), targetClass.getType());
                }
            }

            LOG.print("push the second factor to stack");
            rightFactor.push(context);
            
            if(!ftrCls2.isPrimitive()){
                LOG.print("unbox " + ftrCls1);
                instructions.unbox(ftrCls2.getType());
            }
            
            if(isNumerical){
                if(!ftrCls2.equals(targetClass) &&
                   targetClass.getCastOrder() > getType(int.class).getCastOrder()){
                    LOG.print("cast from " + ftrCls2 + " to " + targetClass);
                    instructions.cast(ftrCls2.getType(), targetClass.getType());
                }
            }
        }else{
            LOG.print("push the first factor to stack");
            leftFactor.push(context);
            
            LOG.print("push the second factor to stack");
            rightFactor.push(context);
        }
    }
    
}
