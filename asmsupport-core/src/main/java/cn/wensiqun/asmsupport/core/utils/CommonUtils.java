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
package cn.wensiqun.asmsupport.core.utils;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class CommonUtils {

    /**
     * Get system jdk version for bytecode indication, 
     * current only support jdk1.6- cause asmsupport 
     * dosn't support frame.
     * 
     * @return
     */
    public static int getSystemJDKVersion() {
        String str = System.getProperty("java.version");
        /*if(str.startsWith("1.7")) {
            return Opcodes.V1_7;
        }*/
        if(str.startsWith("1.6")) {
            return Opcodes.V1_6;
        }
        if(str.startsWith("1.5")) {
            return Opcodes.V1_5;
        }
        if(str.startsWith("1.4")) {
            return Opcodes.V1_4;
        }
        if(str.startsWith("1.3")) {
            return Opcodes.V1_3;
        }
        if(str.startsWith("1.2")) {
            return Opcodes.V1_2;
        }
        if(str.startsWith("1.1")) {
            return Opcodes.V1_1;
        }
        return Opcodes.V1_6;
    }
    
}
