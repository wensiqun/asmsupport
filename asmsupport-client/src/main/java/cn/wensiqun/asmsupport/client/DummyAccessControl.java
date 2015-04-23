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
package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

abstract class DummyAccessControl<T extends DummyAccessControl<?>> {

    /**
     * The modifiers
     */
    protected int modifiers;
    
    /**
     * Set the access to private.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public T private_() {
        modifiers = (modifiers & ~(Opcodes.ACC_PRIVATE + Opcodes.ACC_PUBLIC + Opcodes.ACC_PROTECTED))
                + Opcodes.ACC_PRIVATE;
        return (T) this;
    }
    
    /**
     * Check the access whether or not private.
     * 
     * @return
     */
    public boolean isPrivate() {
        return (modifiers & Opcodes.ACC_PRIVATE) != 0;
    }

    /**
     * Set the access to private.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public T public_() {
        modifiers = (modifiers & ~(Opcodes.ACC_PRIVATE + Opcodes.ACC_PUBLIC + Opcodes.ACC_PROTECTED))
                + Opcodes.ACC_PUBLIC;
        return (T) this;
    }

    /**
     * Check the access whether or not public.
     * 
     * @return
     */
    public boolean isPublic() {
        return (modifiers & Opcodes.ACC_PUBLIC) != 0;
    }

    /**
     * Set the access to private.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public T protected_() {
        modifiers = (modifiers & ~(Opcodes.ACC_PRIVATE + Opcodes.ACC_PUBLIC + Opcodes.ACC_PROTECTED))
                + Opcodes.ACC_PROTECTED;
        return (T) this;
    }

    /**
     * Check the access whether or not protected.
     * 
     * @return
     */
    public boolean isProtected() {
        return (modifiers & Opcodes.ACC_PROTECTED) != 0;
    }
    
    /**
     * Set the access to default.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public T default_() {
        modifiers = (modifiers & ~(Opcodes.ACC_PRIVATE + Opcodes.ACC_PUBLIC + Opcodes.ACC_PROTECTED));
        return (T) this;
    }

    /**
     * Check the access whether or not default.
     * 
     * @return
     */
    public boolean isDefault() {
        return !isPublic() && !isPrivate() && !isProtected();
    }

    /**
     * 
     * Set to final.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public T final_() {
        modifiers = (modifiers & ~Opcodes.ACC_FINAL) + Opcodes.ACC_FINAL;
        return (T) this;
    }
    
    /**
     * Check the access whether or not abstract.
     * 
     * @return
     */
    public boolean isFinal() {
        return (modifiers & Opcodes.ACC_FINAL) != 0;
    }
    
    /**
     * Get modifiers.
     * 
     * @return
     */
    public int getModifiers() {
        return modifiers;
    }
}
