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

import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.operator.AbstractParameterizedOperator;
import cn.wensiqun.asmsupport.core.operator.Operators;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractLogical extends AbstractParameterizedOperator {

    protected boolean byOtherUsed;
    
    protected AbstractLogical(ProgramBlockInternal block, Operators operator) {
        super(block, operator);
    }
    
    @Override
    public void loadToStack(ProgramBlockInternal block) {
        this.execute();
    }

    @Override
    public AClass getResultType() {
        return AClassFactory.getType(boolean.class);
    }

    @Override
    public void asArgument() {
        byOtherUsed = true;
        block.removeExe(this);
    }
    
    protected abstract void factorToStack();

    @Override
    protected void doExecute() {
        factorToStack();
        executing();
    }
    
    protected abstract void executing();

}
