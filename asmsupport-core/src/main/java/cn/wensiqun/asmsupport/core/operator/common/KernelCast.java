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
package cn.wensiqun.asmsupport.core.operator.common;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.operator.AbstractParameterizedOperator;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class KernelCast extends AbstractParameterizedOperator {

    private static final Log LOG = LogFactory.getLog(KernelCast.class);
    private AClass to;
    private KernelParame orginal;
    
    protected KernelCast(KernelProgramBlock block, KernelParame orgi, AClass to) {
        super(block, Operator.COMMON);
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
        AClass from = orginal.getResultType();
        if(to.equals(from)){
            return;
        }
        
        if(from.isPrimitive() && to.isPrimitive()){
            if(LOG.isPrintEnabled()) { 
                LOG.print("checkcast from " + from + " to " + to );
            }
            if(from.getCastOrder() > to.getCastOrder() ||
               (from.equals(AClassFactory.getType(char.class)) && to.equals(AClassFactory.getType(short.class))) || 
               (to.equals(AClassFactory.getType(char.class)) && from.equals(AClassFactory.getType(short.class)))){
                insnHelper.cast(from.getType(), to.getType());
                return;
            }
        }

        if(LOG.isPrintEnabled()) { 
            LOG.print("checkcast from " + from + " to " + to );
        }
        insnHelper.checkCast(to.getType());
    }

    @Override
    public void loadToStack(KernelProgramBlock block) {
        this.execute();
    }

    @Override
    public AClass getResultType() {
        return to;
    }

    @Override
    public void asArgument() {
        block.removeExe(this);
    }



}
