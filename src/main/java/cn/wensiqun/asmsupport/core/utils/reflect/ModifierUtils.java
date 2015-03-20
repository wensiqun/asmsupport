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
/**
 * 
 */
package cn.wensiqun.asmsupport.core.utils.reflect;

import java.lang.reflect.Modifier;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;





/**
 * @author 温斯群(Joe Wen)
 *
 */
public class ModifierUtils extends Modifier {
    
    public static boolean isDefault(int mod){
        return !Modifier.isPrivate(mod) && 
           !Modifier.isProtected(mod) && 
           !Modifier.isProtected(mod);
    }
    
    public static boolean isEnum(int mod){
        return (mod & Opcodes.ACC_ENUM) != 0;	
    }
    
    public static boolean isVarargs(int mod){
    	return (mod & Opcodes.ACC_VARARGS) != 0;
    }
    
    public static boolean isBridge(int mod){
    	return (mod & Opcodes.ACC_BRIDGE) != 0;
    }
}
