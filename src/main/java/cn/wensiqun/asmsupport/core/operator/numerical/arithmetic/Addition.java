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

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.operator.Operators;

/**
 * addition operator
 * @author 温斯群(Joe Wen)
 *
 */
public class Addition extends AbstractArithmetic {

    private static final Log LOG = LogFactory.getLog(Addition.class);
    
    protected Addition(ProgramBlockInternal block, InternalParameterized factor1, InternalParameterized factor2) {
        super(block, factor1, factor2);
        operator = Operators.ADD;
    }

    @Override
    public void doExecute() {
        LOG.print("start execute add arithmetic operator");
        factorToStack();
        LOG.print("execute the add instruction");
        insnHelper.add(targetClass.getType());
    }


}
