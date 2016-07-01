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
package cn.wensiqun.asmsupport.core.operator.logical;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BinaryBitwise;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class BinaryLogical extends AbstractLogical {
    
    private static final Log LOG = LogFactory.getLog(BinaryBitwise.class);
    
    protected KernelParam leftFactor;
    protected KernelParam rightFactor;
    
    protected BinaryLogical(KernelProgramBlock block, KernelParam leftFactor, KernelParam rightFactor, Operator operator) {
        super(block, operator);
        this.leftFactor = leftFactor;
        this.rightFactor = rightFactor;
    }

	@Override
	protected void verifyArgument() {
		IClass ftrCls1 = leftFactor.getResultType();
		IClass ftrCls2 = rightFactor.getResultType();
        
		if (!((ftrCls1.equals(getType(boolean.class)) || ftrCls1
				.equals(getType(Boolean.class))) && (ftrCls2
				.equals(getType(boolean.class)) || ftrCls2
				.equals(getType(Boolean.class))))){
            throw new ASMSupportException("the factor type must be boolean or Boolean for logical operator!");
        }
	}

	@Override
	protected void checkAsArgument() {
        leftFactor.asArgument();
        rightFactor.asArgument();
	}

	@Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ArithmeticException("the logical operator " + leftFactor.getResultType() + " " + getOperatorSymbol() + " " + 
                                          rightFactor.getResultType() + " has not been used by other operator.");
        }
    }

    @Override
    protected void factorToStack() {
        if(LOG.isPrintEnabled()) {
            LOG.print("Factors to stack");
        }
        leftFactor.loadToStack(block);
        getInstructions().unbox(leftFactor.getResultType().getType());
        
        rightFactor.loadToStack(block);
        getInstructions().unbox(rightFactor.getResultType().getType());
    }

}
