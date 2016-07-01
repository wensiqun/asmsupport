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
package cn.wensiqun.asmsupport.core.definition.variable;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

public class NonStaticGlobalVariable extends GlobalVariable {

    private static final Log LOG = LogFactory.getLog(NonStaticGlobalVariable.class);
    
    private KernelParam owner;
    
    /**
     * 
     * @param owner
     * @param meta
     */
    public NonStaticGlobalVariable(KernelParam owner, Field meta){
        super(meta);
        this.owner = owner;
    }
    
    public KernelParam getOwner() {
        return owner;
    }
    
    @Override
    public IClass getResultType() {
        return meta.getType();
    }

    @Override
    public void loadToStack(KernelProgramBlock block) {
        if(!IClassUtils.visible(block.getMethodDeclaringClass(), meta.getDirectOwnerType(), 
                meta.getDeclaringClass(), meta.getModifiers())){
            throw new IllegalArgumentException("Cannot access field " +
                    meta.getDeclaringClass() + "#" + meta.getName()
                    + " from " + block.getMethodDeclaringClass());
        }
        
        if(LOG.isPrintEnabled()){
            LOG.print("Get field " + meta.getName() + " from variable " + meta.getName() + " and push to stack!");
        }
        owner.loadToStack(block);
        block.getMethod().getInstructions().getField(meta.getDirectOwnerType().getType(), meta.getName(), meta.getType().getType());
    }

    @Override
    public boolean availableFor(AbstractOperator operator) {
        return true;
    }


}
