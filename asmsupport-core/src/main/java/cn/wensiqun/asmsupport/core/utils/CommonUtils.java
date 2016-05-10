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

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;

public class CommonUtils {

    public static final Pattern JAVA_CLASS_NAME_PART_PATTERN = Pattern.compile("([A-Za-z_$]+[a-zA-Z0-9_$]*)");
	
    public static final Set<String> JAVA_KEYWORDS = new TreeSet<>(Arrays.asList(
	    "abstract",     "assert",        "boolean",      "break",           "byte",
	    "case",         "catch",         "char",         "class",           "const",
	    "continue",     "default",       "do",           "double",          "else",
	    "enum",         "extends",       "false",        "final",           "finally",
	    "float",        "for",           "goto",         "if",              "implements",
	    "import",       "instanceof",    "int",          "interface",       "long",
	    "native",       "new",           "null",         "package",         "private",
	    "protected",    "public",        "return",       "short",           "static",
	    "strictfp",     "super",         "switch",       "synchronized",    "this",
	    "throw",        "throws",        "transient",    "true",            "try",
	    "void",         "volatile",      "while"
    ));

    private CommonUtils(){}
    
    /**
     * Get system jdk version for bytecode indication, 
     * current only support jdk1.6- cause asmsupport 
     * dons't support frame.
     * 
     * @return
     */
    public static int getJDKVersion() {
        String str = System.getProperty("java.version");
        if(str.startsWith("1.7")) {
            return Opcodes.V1_7;
        }
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
	
    public static void toLocal(byte[] b, String location, String name){
        
        if (!StringUtils.isBlank(location)) {
        	String fileSeparator = System.getProperty("file.separator");
        	StringBuilder path = new StringBuilder(location);
        	if(!location.endsWith(fileSeparator)){
        		path.append(fileSeparator);
        	}
        	
            // optional: stores the adapted class on disk
            try {
                File f = new File(path.append(name.replace(".", fileSeparator)).append(".class").toString());
                f.getParentFile().mkdirs();
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(b);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                throw new ASMSupportException(e);
            }
        }
    }
    
    public static boolean isJavaClassName(String text) {
        for (String part : text.split("\\.")) {
            if (JAVA_KEYWORDS.contains(part) || !JAVA_CLASS_NAME_PART_PATTERN.matcher(part).matches()) {
                return false;
            }           
        }
        return text.length() > 0;
    }
    
    public static void validateJavaClassName(String text) {
    	if(StringUtils.isBlank(text)) {
    		throw new ASMSupportException("Class name is blank");
    	}
    	for (String part : text.split("\\.")) {
            if (JAVA_KEYWORDS.contains(part)) {
        		throw new ASMSupportException("The part of class name is '" + part + "', it's a keyword in java.");
            }
            if (!JAVA_CLASS_NAME_PART_PATTERN.matcher(part).matches()) {
        		throw new ASMSupportException("It's invalid class name : " + text);
            }
        }
    }
    
}
