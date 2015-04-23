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
package cn.wensiqun.asmsupport.standard.def.var.meta;

import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class Field extends VarMeta {

    private AClass owner;

    private AClass actuallyOwnerType;

    /**
     * @param owner
     *            变量所属的Class
     * @param actuallyOwnerType
     *            变量实际有用者
     * @param variableClass
     *            变量类型
     * @param modifiers
     *            变量修饰符
     * @param name
     *            变量名
     */
    public Field(AClass owner, AClass actuallyOwnerType, AClass declareClass, int modifiers, String name) {
        super(name, modifiers, declareClass);
        this.owner = owner;
        this.actuallyOwnerType = actuallyOwnerType;
    }

    /**
     * getter of owner
     * 
     * @return
     */
    public AClass getOwner() {
        return owner;
    }

    /**
     * 
     * @return
     */
    public AClass getActuallyOwnerType() {
        return actuallyOwnerType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((actuallyOwnerType == null) ? 0 : actuallyOwnerType.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Field other = (Field) obj;
        if (actuallyOwnerType == null) {
            if (other.actuallyOwnerType != null) {
                return false;
            }
        } else if (!actuallyOwnerType.equals(other.actuallyOwnerType)) {
            return false;
        }
        if (owner == null) {
            if (other.owner != null) {
                return false;
            }
        } else if (!owner.equals(other.owner)) {
            return false;
        }
        return true;
    }

}
