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
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;

public class NonStaticGlobalVariable extends GlobalVariable {

    private static final Log LOG = LogFactory.getLog(NonStaticGlobalVariable.class);
    
    private IVariable owner;
    
    /**
     * 
     * @param var
     * @param gve
     */
    public NonStaticGlobalVariable(IVariable owner, Field meta){
        super(meta);
        this.owner = owner;
    }
    
    public IVariable getOwner() {
        return owner;
    }
    
    @Override
    public AClass getResultType() {
        return meta.getDeclareType();
    }

    @Override
    public void loadToStack(KernelProgramBlock block) {
        if(!AClassUtils.visible(block.getMethodOwner(), meta.getOwner(), 
                meta.getActuallyOwnerType(), meta.getModifiers())){
            throw new IllegalArgumentException("cannot access field " +
                    meta.getActuallyOwnerType() + "#" + meta.getName()
                    + " from " + block.getMethodOwner());
        }
        
        if(LOG.isPrintEnabled()){
            LOG.print("Get field " + meta.getName() + " from variable " + meta.getName() + " and push to stack!");
        }
        owner.loadToStack(block);
        block.getInsnHelper().getField(meta.getOwner().getType(), meta.getName(), meta.getDeclareType().getType());
    }

    @Override
    public boolean availableFor(AbstractOperator operator) {
        return true;
    }


}
