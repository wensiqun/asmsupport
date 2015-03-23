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
package cn.wensiqun.asmsupport.core.operator.checkcast;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class CheckCast extends AbstractOperator implements Parameterized {

    private static final Log LOG = LogFactory.getLog(CheckCast.class);
    private AClass to;
    private Parameterized orginal;
    
    protected CheckCast(ProgramBlockInternal block, Parameterized orgi, AClass to) {
        super(block);
        this.orginal = orgi;
        this.to = to;
    }
    
    @Override
    protected void verifyArgument() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void checkAsArgument() {
        orginal.asArgument();
    }

    @Override
    public void doExecute() {
        orginal.loadToStack(block);
        AClass from = orginal.getParamterizedType();
        if(to.equals(from)){
            return;
        }
        
        if(from.isPrimitive() && to.isPrimitive()){
            LOG.debug("checkcast from " + from + " to " + to );
            if(from.getCastOrder() > to.getCastOrder() ||
               (from.equals(AClass.CHAR_ACLASS) && to.equals(AClass.SHORT_ACLASS)) || 
               (to.equals(AClass.CHAR_ACLASS) && from.equals(AClass.SHORT_ACLASS))){
                insnHelper.cast(from.getType(), to.getType());
                return;
            }
        }
        LOG.debug("checkcast from " + from + " to " + to );
        insnHelper.checkCast(to.getType());
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        this.execute();
    }

    @Override
    public AClass getParamterizedType() {
        return to;
    }

    @Override
    public void asArgument() {
        block.removeExe(this);
    }



}
