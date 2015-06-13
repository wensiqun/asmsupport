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
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class UnaryLogical extends AbstractLogical {
    
    private static final Log LOG = LogFactory.getLog(BinaryBitwise.class);
    
    protected KernelParam factor;
    
    protected Label trueLbl;
    protected Label falseLbl;
    
    protected UnaryLogical(KernelProgramBlock block, KernelParam factor, Operator operator) {
        super(block, operator);
        this.factor = factor;
        falseLbl = new Label();
        trueLbl = new Label();
    }

    @Override
	protected void verifyArgument() {
    	AClass ftrCls = factor.getResultType();
        if(!(ftrCls.equals(AClassFactory.getType(boolean.class)) && !ftrCls.equals(AClassFactory.getType(Boolean.class)))){
            throw new ASMSupportException("the factor type must be boolean or Boolean for logical operator!");
        }
	}

    @Override
	protected void checkAsArgument() {
        factor.asArgument();
	}
    
    @Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ASMSupportException("the logical operator " + getOperatorSymbol() + " " + 
                    factor.getResultType() + " has not been used by other operator.");
        }
    }

	@Override
    protected void factorToStack() {
        LOG.print("factors to stack");
        factor.loadToStack(block);
        insnHelper.unbox(factor.getResultType().getType());
    }

}
