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
package cn.wensiqun.asmsupport.core.definition.variable.meta;

import cn.wensiqun.asmsupport.standard.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class VariableMeta {

    private String name;
    private int modifiers;
    private AClass formerType;

    public VariableMeta(String name, int modifiers, AClass formerType) {
        this.name = name;
        this.modifiers = modifiers;
        this.formerType = formerType;
    }

    public String getName() {
        return name;
    }

    public int getModifiers() {
        return modifiers;
    }

    public AClass getFormerType() {
        return formerType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((formerType == null) ? 0 : formerType.hashCode());
        result = prime * result + modifiers;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        VariableMeta other = (VariableMeta) obj;
        if (formerType == null) {
            if (other.formerType != null) {
                return false;
            }
        } else if (!formerType.equals(other.formerType))
            return false;
        if (modifiers != other.modifiers)
            return false;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
