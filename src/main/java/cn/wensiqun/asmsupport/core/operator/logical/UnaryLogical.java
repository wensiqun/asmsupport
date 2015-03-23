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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BinaryBitwise;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class UnaryLogical extends AbstractLogical {
    
    private static final Log LOG = LogFactory.getLog(BinaryBitwise.class);
    
    protected Parameterized factor;
    
    protected Label trueLbl;
    protected Label falseLbl;
    
    protected UnaryLogical(ProgramBlockInternal block, Parameterized factor) {
        super(block);
        this.factor = factor;
        falseLbl = new Label();
        trueLbl = new Label();
    }

    @Override
	protected void verifyArgument() {
    	AClass ftrCls = factor.getParamterizedType();
        if(!(ftrCls.equals(AClass.BOOLEAN_ACLASS) && !ftrCls.equals(AClass.BOOLEAN_WRAP_ACLASS))){
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
            throw new ASMSupportException("the logical operator " + operator + " " + 
                    factor.getParamterizedType() + " has not been used by other operator.");
        }
    }

	@Override
    protected void factorToStack() {
        LOG.debug("factors to stack");
        factor.loadToStack(block);
        insnHelper.unbox(factor.getParamterizedType().getType());
    }

}
