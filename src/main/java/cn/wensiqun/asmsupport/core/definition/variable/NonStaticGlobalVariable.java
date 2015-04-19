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

import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.standard.clazz.AClass;

public class NonStaticGlobalVariable extends GlobalVariable {

    private static final Log LOG = LogFactory.getLog(NonStaticGlobalVariable.class);
    
    private IVariable owner;
    
    /**
     * 
     * @param var
     * @param gve
     */
    public NonStaticGlobalVariable(IVariable owner, AClass declaringClass, 
    		AClass actuallyOwnerType, AClass formerType, int modifiers, String name){
    	super(declaringClass, actuallyOwnerType, formerType, modifiers, name);
        this.owner = owner;
    }
    
    public IVariable getOwner() {
        return owner;
    }
    
    @Override
    public void loadToStack(ProgramBlockInternal block) {
        if(!AClassUtils.visible(block.getMethodOwner(), getDeclaringClass(), 
                getActuallyOwnerType(), getModifiers())){
            throw new IllegalArgumentException("Cannot access field " +
                    getActuallyOwnerType() + "#" + getName()
                    + " from " + block.getMethodOwner());
        }
        
        if(LOG.isPrintEnabled()){
            LOG.print("Get field " + getName() + " from variable " + block.getMethodOwner()
            		+ " and push to stack!");
        }
        owner.loadToStack(block);
        block.getInsnHelper().getField(getDeclaringClass().getType(), getName(), getFormerType().getType());
    }

}
