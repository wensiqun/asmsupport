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
package cn.wensiqun.asmsupport.core.operator.assign;

import java.lang.reflect.Modifier;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.core.definition.variable.NonStaticGlobalVariable;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class NonStaticGlobalVariableAssigner extends KernelAssign {

    private static final Log LOG = LogFactory.getLog(NonStaticGlobalVariableAssigner.class);
    
    private NonStaticGlobalVariable var;
    
    protected NonStaticGlobalVariableAssigner(KernelProgramBlock block, final NonStaticGlobalVariable var, KernelParame value) {
        super(block, var, value);
        this.var = var;
    }

    @Override
    public void doExecute() {
    	if(LOG.isPrintEnabled()){
            LOG.print("assign value to global variable '" + var.getMeta().getName() + "' from " + value  );
        }
    	if(Modifier.isStatic(block.getMethod().getMeta().getModifier())){
            throw new ASMSupportException("Current method " + block.getMethod() + " is static cannot use non-static field " + var.getMeta().getName() );
        }
        var.getOwner().loadToStack(block);
        
        value.loadToStack(block);
        
        autoCast();
        
        insnHelper.putField(var.getOwner().getResultType().getType(), 
                var.getMeta().getName(),
                var.getMeta().getType().getType());
    }

}
