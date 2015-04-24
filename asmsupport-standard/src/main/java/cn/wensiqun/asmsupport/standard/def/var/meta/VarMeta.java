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
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

/**
 * The variable meta informations. 
 */
public class VarMeta {

    private String name;
    private int modifiers;
    private AClass type;

    public VarMeta(String name, int modifiers, AClass type) {
        super();
        this.name = name;
        this.modifiers = modifiers;
        this.type = type;
    }

    /**
     * Get the variable name;
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get the variable modifier, it's an integer value which 
     * is a sum value of same field from {@link Opcodes}, the field 
     * is start with 'ACC_', for exampel : "public static" modifiers,
     * the value is  {@link Opcodes#ACC_PUBLIC} + {@link Opcodes#ACC_STATIC}
     * 
     * @return
     */
    public int getModifiers() {
        return modifiers;
    }

    /**
     * Get the variable type.
     * 
     * @return
     */
    public AClass getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        VarMeta other = (VarMeta) obj;
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type))
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
