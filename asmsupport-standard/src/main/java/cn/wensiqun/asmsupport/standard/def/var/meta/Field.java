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

import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

/**
 * The Field of class
 */
public class Field extends VarMeta {

    private IClass directOwnerType;

    private IClass declaringClass;

    /**
     * @param directOwnerType The direct owner see {@link #getDirectOwnerType()}
     * @param declaringClass The field see {@link #getDeclaringClass()}
     * @param type Field type see {@link VarMeta#getType()}
     * @param modifiers Field modifiers see {@link VarMeta#getModifiers()}
     * @param name Field name see {@link VarMeta#getName()}
     */
    public Field(IClass directOwnerType, IClass declaringClass, IClass type, int modifiers, String name) {
        super(name, modifiers, type);
        this.directOwnerType = directOwnerType;
        this.declaringClass = declaringClass;
    }

    /**
     * <p>
     * Get the direct owner of current {@link Field}, what's the different to {@link #getDeclaringClass()},
     * the direct owner is a enter class. For example :
     * <p>
     * 
     * <pre>
     * public class Foo {
     *     public String field; 
     * }
     * 
     * public class SubFoo extends Foo{
     * 
     * }
     * </pre>
     * 
     * <p>
     * If you get the field via SubFoo class, such as following asmsupport code :
     * <pre>
     * AClass subFoo = getType(SubFoo.class);
     * Field field = subFoo.field("field");
     * </pre>
     * Than the {@link #getDirectOwnerType()} is <code>SubFoo<code> and {@link #getDeclaringClass()} is <code>Foo<code>
     * </p>
     */
    public IClass getDirectOwnerType() {
        return directOwnerType;
    }

    /**
     * <p>
     * Returns the <code>{@link IClass}</code> object representing the class or interface
     * that declares the field represented by this <code>{@link Field}</code> object.
     * For example :
     * </p>
     * 
     * <p>
     * <pre>
     * public class Foo {
     *     public String field; 
     * }
     * </pre>
     * 
     * The declaring class is Foo of Field "field"
     * </p> 
     */
    public IClass getDeclaringClass() {
        return declaringClass;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((declaringClass == null) ? 0 : declaringClass.hashCode());
        result = prime * result + ((directOwnerType == null) ? 0 : directOwnerType.hashCode());
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
        if (declaringClass == null) {
            if (other.declaringClass != null) {
                return false;
            }
        } else if (!declaringClass.equals(other.declaringClass)) {
            return false;
        }
        if (directOwnerType == null) {
            if (other.directOwnerType != null) {
                return false;
            }
        } else if (!directOwnerType.equals(other.directOwnerType)) {
            return false;
        }
        return true;
    }

}
