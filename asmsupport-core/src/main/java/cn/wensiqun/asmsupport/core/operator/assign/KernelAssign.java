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
package cn.wensiqun.asmsupport.core.operator.assign;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.operator.AbstractParameterizedOperator;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * @author 温斯群(Joe Wen)
 */
public abstract class KernelAssign extends AbstractParameterizedOperator {

    protected KernelParame value;
    
    private IVariable var;
    
    /**该操作是否被其他操作引用 */
    protected boolean byOtherUsed;
    
    protected KernelAssign(KernelProgramBlock block, IVariable var, KernelParame value) {
        super(block, Operator.ASSIGN);
        this.value = value;
        this.var = var;
    }

	@Override
	public String toString() {
		return var + " = " + value;
	}

	@Override
	protected void verifyArgument() {
		if(!AClassUtils.checkAssignable(value.getResultType(), var.getResultType())) {
			throw new IllegalArgumentException("Type mismatch: cannot convert from " + value.getResultType() + " to " + var.getResultType() + "");
		}
	}

	@Override
	protected void checkAsArgument() {
        value.asArgument();
	}

	/**
     * auto cast
     */
    protected void autoCast(){
        autoCast(value.getResultType(), var.getResultType(), false);
    }
    
    
    @Override
	public void loadToStack(KernelProgramBlock block) {
        this.execute();
		var.loadToStack(block);
	}

	@Override
	public AClass getResultType() {
		return var.getResultType();
	}

	@Override
	public void asArgument() {
        byOtherUsed = true;
        block.removeExe(this);
	}

	protected static class AssignerException extends RuntimeException{

        private static final long serialVersionUID = 5667984928208743770L;
        
        protected AssignerException(String msg){
            super(msg);
        } 
    }

}
