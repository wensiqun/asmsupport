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

import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public interface ASConstant {

    public final static String CLINIT = "<clinit>";
    public final static String CLINIT_PROXY = "&clinit&";
    
    public final static String INIT = "<init>";
    public final static String INIT_PROXY = "&init&";
    public final static String SUPER = "super";
    public final static String THIS = "this";
    public final static String METHOD_PROXY_SUFFIX = "@original";
    public final static String CLASS_PROXY_SUFFIX = "";
    public final static String STRING_EMPTY = "";

    public final static int METHOD_CREATE_MODE_ADD = 0;
    public final static int METHOD_CREATE_MODE_MODIFY = 1;
    
    public final static int ASM_VERSION = Opcodes.ASM5;

    public final static String ACCESS_TOKEN_PUBLIC = "public";
    
    public final static String ACCESS_TOKEN_PRIVATE = "private";

    public final static String ACCESS_TOKEN_PROTECTED = "protected";
    
    public final static String ACCESS_TOKEN_STATIC = "static";
    
    public final static String ACCESS_TOKEN_FINAL = "final";
    
    public final static String ACCESS_TOKEN_SYNCHRONIZED = "synchronized";
    
    public final static String ACCESS_TOKEN_VOLATILE = "volatile";
    
    public final static String ACCESS_TOKEN_ABSTRACT = "abstract";
    
    public final static ThreadLocal<LogFactory> LOG_FACTORY_LOCAL = new ThreadLocal<LogFactory>();
}
