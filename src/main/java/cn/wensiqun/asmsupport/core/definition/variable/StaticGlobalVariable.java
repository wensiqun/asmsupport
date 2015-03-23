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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

public class StaticGlobalVariable extends GlobalVariable {

    private static final Log LOG = LogFactory.getLog(StaticGlobalVariable.class);

    /** 如果当前全局变量是静态变量，那么staticOwner表示静态变量的所属Class */
    private AClass owner;

    /**
     * 
     * @param owner
     * @param gve
     */
    public StaticGlobalVariable(AClass owner, GlobalVariableMeta meta) {
        super(meta);
        this.owner = owner;
    }

    public AClass getOwner() {
        return owner;
    }

    @Override
    public AClass getParamterizedType() {
        return meta.getDeclareType();
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        if (!AClassUtils.visible(block.getMethodOwner(), meta.getOwner(), meta.getActuallyOwnerType(),
                meta.getModifiers())) {
            throw new IllegalArgumentException("Cannot access field " + meta.getActuallyOwnerType() + "#"
                    + meta.getName() + " from " + block.getMethodOwner());
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("get field " + meta.getName() + " from class " + meta.getOwner().getName()
                    + " and push to stack!");
        }
        block.getInsnHelper().getStatic(owner.getType(), meta.getName(), meta.getDeclareType().getType());
    }

    @Override
    public boolean availableFor(AbstractOperator operator) {
        return true;
    }

}
