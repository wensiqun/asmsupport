package json.utils;

import static java.lang.System.arraycopy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionUtils {

    /**
     * This new method 'slightly' outperforms the old method, it was
     * essentially a perfect example of me wasting my time and a
     * premature optimization.  But what the hell...
     *
     * @param s -
     * @return String
     */
    public static String getSetter(String s) {
        char[] chars = new char[s.length() + 3];
        chars[0] = 's';
        chars[1] = 'e';
        chars[2] = 't';
        char[] originChars = s.toCharArray();
        chars[3] = Character.toUpperCase(originChars[0]);
        arraycopy(originChars, 1, chars, 4, originChars.length - 1);
        return new String(chars);
    }


    public static String getGetter(String s) {
        char[] chars = new char[s.length() + 3];
        chars[0] = 'g';
        chars[1] = 'e';
        chars[2] = 't';
        char[] originChars = s.toCharArray();
        chars[3] = Character.toUpperCase(originChars[0]);
        arraycopy(originChars, 1, chars, 4, originChars.length - 1);
        return new String(chars);
    }

    public static String getIsGetter(String s) {
        char[] chars = new char[s.length() + 3];
        chars[0] = 'i';
        chars[1] = 's';
        char[] originChars = s.toCharArray();
        chars[2] = Character.toUpperCase(originChars[0]);
        arraycopy(originChars, 1, chars, 3, originChars.length - 1);
        return new String(chars);
    }
    
    public static Method getGetter(Class<?> clazz, String field) {
        try {
            return clazz.getMethod(getGetter(field));
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
    
    public static Method getSetter(Class<?> clazz, String field) {
        String setter = getSetter(field);
        for (Method meth : clazz.getMethods()) {
            if(setter.equals(meth.getName()) && meth.getParameterTypes().length == 1 && (meth.getModifiers() & Modifier.PUBLIC) != 0) 
                return meth;
        }
        return null;
    }
    
    
}
