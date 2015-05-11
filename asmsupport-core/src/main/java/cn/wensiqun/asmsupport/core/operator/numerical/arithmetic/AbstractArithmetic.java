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
package cn.wensiqun.asmsupport.core.operator.numerical.arithmetic;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.numerical.AbstractNumerical;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public abstract class AbstractArithmetic extends AbstractNumerical {

	/** the left factor of arithmetic */
    protected KernelParam leftFactor;
    
    /** the right factor of arithmetic */
    protected KernelParam rightFactor;
    
    private boolean byOtherUsed;

    protected AbstractArithmetic(KernelProgramBlock block, KernelParam leftFactor, KernelParam rightFactor, Operator operator) {
        super(block, operator);
        this.leftFactor = leftFactor;
        this.rightFactor = rightFactor;
    }
    
    @Override
    public void loadToStack(KernelProgramBlock block) {
        this.execute();
    }
    
    @Override
    protected void verifyArgument() {
        AClass f1cls = leftFactor.getResultType();
        AClass f2cls = rightFactor.getResultType();
        if(!AClassUtils.isArithmetical(f1cls) || !AClassUtils.isArithmetical(f2cls)){
            throw new ArithmeticException("cannot execute arithmetic operator whit " + f1cls + " and " + f2cls);
        }
    }

    @Override
    protected void checkAsArgument() {
        leftFactor.asArgument();
        rightFactor.asArgument();
    }

    @Override
    protected void initAdditionalProperties() {
        
        targetClass = AClassUtils.getArithmeticalResultType(leftFactor.getResultType(), rightFactor.getResultType());
        
        if(leftFactor instanceof Value)
            ((Value)leftFactor).convert(targetClass);
        
        if(rightFactor instanceof Value)
            ((Value)rightFactor).convert(targetClass);
    }
    
    @Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ArithmeticException("the arithmetic operator " + leftFactor.getResultType() + " " + getOperatorSymbol() + " " + 
                                          rightFactor.getResultType() + " has not been used by other operator.");
        }
    }

    @Override
    protected void factorToStack() {
        pushFactorToStack(leftFactor);
        pushFactorToStack(rightFactor);
    }
    
    @Override
    public void asArgument() {
        block.removeExe(this);
        byOtherUsed = true;
    }
    
}
