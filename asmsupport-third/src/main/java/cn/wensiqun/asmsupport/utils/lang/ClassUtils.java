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
package cn.wensiqun.asmsupport.utils.lang;


/**
 * Class Helper Class
 * 
 * @author wensiqun at 163.com(Joe Wen)
 */
public class ClassUtils { 

    /**
     * The inner class separator character: <code>'$' == {@value}</code>.
     */
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
    
    /**
     * 
     * @param cls
     * @return
     */
    public static Class<?> getRootComponentType(Class<?> cls) {
        if (cls.isArray()) {
            return getRootComponentType(cls.getComponentType());
        } else {
            return cls;
        }
    }

    /**
     * 
     * @param arrayClass
     * @return
     */
    public static int getDimension(Class<?> arrayClass) {
        if (arrayClass.isArray()) {
            return StringUtils.findAllIndexes(arrayClass.getName(), "[").length;
        } else {
            return 0;
        }
    }

    /**
     * determine cls1 is super of cls2
     * 
     * @param cls1
     * @param cls2
     * @return
     */
    public static boolean isSuper(Class<?> cls1, Class<?> cls2) {
        if (cls1.equals(cls2.getSuperclass())) {
            return true;
        } else {
            return isSuper(cls1, cls2.getSuperclass());
        }
    }

    public static Class<?> getMethodOwner(Class<?> owner, String name, Class<?> arguments) {
        for (; !owner.equals(Object.class); owner = owner.getSuperclass()) {
        }
        return owner;
    }

    /**
     * 
     * @param owner
     * @param innerCls
     * @return
     */
    public static boolean isDirectInnerClass(Class<?> owner, Class<?> innerCls) {

        String[] clses = StringUtils.split(innerCls.getName(), INNER_CLASS_SEPARATOR_CHAR);

        if (clses.length > 1) {
            if (clses[clses.length - 2].equals(owner.getName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check a class {@code innerClas} whether or not a inner class of an {@code owner}.
     */
    public static boolean isInnerClass(Class<?> owner, Class<?> innerCls) {
        int ownerIndex = innerCls.getName().indexOf(owner.getName());

        int separatorIndex = innerCls.getName().indexOf(INNER_CLASS_SEPARATOR_CHAR);

        if (ownerIndex >= 0 && separatorIndex > 0 && ownerIndex < separatorIndex) {
            return true;
        }

        return false;
    }

    /**
     * Get class from a class name, the name also support description, internal name in jvm,
     * and primitive name such int, char.
     * 
     * @param className class name, such java.lang.String, [java.lang.String, int, [java/lang/String etc... 
     * @return Class the java class
     * @throws ClassNotFoundException
     */
	public static Class<?> forName(String className) throws ClassNotFoundException {
		className = getClassname(className);
		Class<?> clazz = primitiveToClass(className);
		if (clazz != null) {
			return clazz;
		}
		return Class.forName(className);
	}

    /**
     * Convert a primitive name or description to java class.
     * @param nameOrDesc primitive name or description 
     * @return Class java class
     */
    public static Class<?> primitiveToClass(String nameOrDesc) {
        if ("boolean".equals(nameOrDesc) || "Z".equals(nameOrDesc)) {
            return boolean.class;
        }
        if ("byte".equals(nameOrDesc) || "B".equals(nameOrDesc)) {
            return byte.class;
        }
        if ("char".equals(nameOrDesc) || "C".equals(nameOrDesc)) {
            return char.class;
        }
        if ("short".equals(nameOrDesc) || "S".equals(nameOrDesc)) {
            return short.class;
        }
        if ("int".equals(nameOrDesc) || "I".equals(nameOrDesc)) {
            return int.class;
        }
        if ("long".equals(nameOrDesc) || "J".equals(nameOrDesc)) {
            return long.class;
        }
        if ("float".equals(nameOrDesc) || "F".equals(nameOrDesc)) {
            return float.class;
        }
        if ("double".equals(nameOrDesc) || "D".equals(nameOrDesc)) {
            return double.class;
        }
        if ("void".equals(nameOrDesc) ||"V".equals(nameOrDesc)) {
            return void.class;
        }
        return null;
    }

    
    
	/**
	 * The possible may be a class name, description.
	 * 
	 * <ul>
	 *     <li>Primitive type name : int</li>
	 *     <li>Primitive type description : I</li>
	 *     <li>Primitive array type name : [I</li>
	 *     <li>Primitive array type description : [I</li>
	 *     <li>Primitive array declared type name : int[][]</li>
	 *     <li>Object type name : java.lang.Object</li>
	 *     <li>Object type description : Ljava/lang/Object;</li>
	 *     <li>Object type internal name : java/lang/Object</li>
	 *     <li>Object array type name : [Ljava.lang.Object;</li>
	 *     <li>Object array type description : [Ljava/lang/Object;</li>
	 *     <li>Object array declared type name : java.lang.Object[][]</li>
	 * </ul>
	 * 
	 * @param possible
	 * @return
	 */
    public static String getClassname(String possible) {
    	if ("V".equals(possible)) {
			return "void";
		}
		if ("Z".equals(possible)) {
			return "boolean";
		}
		if ("C".equals(possible)) {
			return "char";
		}
		if ("B".equals(possible)) {
			return "byte";
		}
		if ("S".equals(possible)) {
			return "short";
		}
		if ("I".equals(possible)) {
			return "int";
		}
		if ("F".equals(possible)) {
			return "float";
		}
		if ("J".equals(possible)) {
			return "long";
		}
		if ("D".equals(possible)) {
			return "double";
		}
		if ("E".equals(possible)) {
			return "ANY_EXCEPTION";
		}
		if(possible.startsWith("[")) {
			return possible.replace('/', '.');
		}
		if(possible.endsWith(";")) {
			return possible.substring(1, possible.length() - 1).replace('/', '.');
		}
		if(possible.endsWith("[]")) {
			String elementType = getDescription(possible.substring(0, possible.indexOf('['))).replace('/', '.');
			String dims = possible.substring(possible.indexOf('[')).replace("]", "");
			return dims + elementType;
		}
		return possible.replace('/', '.');
    }
    
	/**
	 * The possible may be a class name, description.
	 * 
	 * <ul>
	 *     <li>Primitive type name : int</li>
	 *     <li>Primitive type description : I</li>
	 *     <li>Primitive array type name : [I</li>
	 *     <li>Primitive array type description : [I</li>
	 *     <li>Primitive array declared type name : int[][]</li>
	 *     <li>Object type name : java.lang.Object</li>
	 *     <li>Object type description : Ljava/lang/Object;</li>
	 *     <li>Object type internal name : java/lang/Object</li>
	 *     <li>Object array type name : [Ljava.lang.Object;</li>
	 *     <li>Object array type description : [Ljava/lang/Object;</li>
	 *     <li>Object array declared type name : java.lang.Object[][]</li>
	 * </ul>
	 * 
	 * @param possible
	 * @return
	 */
	public static String getDescription(String possible) {
		if (possible.endsWith(";") || "V".equals(possible)
				|| "Z".equals(possible) || "C".equals(possible)
				|| "B".equals(possible) || "S".equals(possible)
				|| "I".equals(possible) || "F".equals(possible)
				|| "J".equals(possible) || "D".equals(possible)
				|| "E".equals(possible)) {
			return possible;
		}

		if ("void".equals(possible)) {
			return "V";
		}

		if ("boolean".equals(possible)) {
			return "Z";
		}

		if ("char".equals(possible)) {
			return "C";
		}

		if ("byte".equals(possible)) {
			return "B";
		}

		if ("short".equals(possible)) {
			return "S";
		}

		if ("int".equals(possible)) {
			return "I";
		}

		if ("float".equals(possible)) {
			return "F";
		}

		if ("long".equals(possible)) {
			return "J";
		}

		if ("double".equals(possible)) {
			return "D";
		}

		if ("ANY_EXCEPTION".equals(possible)) {
			return "E";
		}

		if (possible.startsWith("[")) {
			return possible.replace('.', '/');
		}

		if (possible.endsWith("]")) {
			int splitIdx = possible.indexOf('[');
			String componentDesc = getDescription(possible.substring(0,
					splitIdx));
			String dimension = possible.substring(splitIdx).replace("]", "");
			return dimension + componentDesc;
		}

		return "L" + possible.replace('.', '/') + ";";
	}
}
