package cn.wensiqun.asmsupport.core.utils.jls15_12_2;

import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;


/**
 * Support conversions and promotions according chapter 5 of jls.
 * 
 */
public class ConversionsPromotionsUtils {

    /**
     * 
     * <strong>5.1.1 Identity Conversion</strong>
     * 
     * <p>A conversion from a type to that same type is permitted for any type.</p>
     * <p>This may seem trivial, but it has two practical consequences. First, it is always permitted
     * for an expression to have the desired type to begin with, thus allowing the simply stated rule
     * that every expression is subject to conversion, if only a trivial identity conversion. Second,
     * it implies that it is permitted for a program to include redundant cast operators for the sake
     * of clarity.</p>
     * 
     * @param from
     * @param to
     * @return
     */
    public static boolean checkIdentityConversion(AClass from, AClass to) {
        return from.equals(to);
    }
    
    /**
     * <strong>5.1.2 Widening Primitive Conversion</strong>
     * 19 specific conversions on primitive types are called the widening primitive conversions:
     * <ul>
     * <li>{@code byte} to {@code short}, {@code int}, {@code long}, {@code float}, or {@code double}</li>
     * <li>{@code short} to {@code int}, {@code long}, {@code float}, or {@code double}</li>
     * <li>{@code char} to {@code int}, {@code long}, {@code float}, or {@code double}</li>
     * <li>{@code int} to {@code long}, {@code float}, or {@code double}</li>
     * <li>{@code long} to {@code float} or {@code double}</li>
     * <li>{@code float} to {@code double}</li>
     * </ul>
     * @param from
     * @param to
     * @return
     */
    public static boolean checkWideningPrimitiveConversion(AClass from, AClass to) {
        int fromSort = from.getType().getSort();
        int toSort = from.getType().getSort();
        if(fromSort == Type.BOOLEAN || toSort == Type.BOOLEAN) {
            return false;
        }
        switch(fromSort) {
        case Type.CHAR :
            switch(toSort) {
            case Type.INT :
            case Type.FLOAT :
            case Type.LONG :
            case Type.DOUBLE : return true;
            default : return false;
            }
        case Type.BYTE :
            switch(toSort) {
            case Type.SHORT :
            case Type.INT :
            case Type.FLOAT :
            case Type.LONG :
            case Type.DOUBLE : return true;
            default : return false;
            }
        case Type.SHORT :
            switch(toSort) {
            case Type.INT :
            case Type.FLOAT :
            case Type.LONG :
            case Type.DOUBLE : return true;
            default : return false;
            }
        case Type.INT :
            switch(toSort) {
            case Type.FLOAT :
            case Type.LONG :
            case Type.DOUBLE : return true;
            default : return false;
            }
        case Type.FLOAT :
            switch(toSort) {
            case Type.DOUBLE : return true;
            default : return false;
            }
        case Type.LONG :
            switch(toSort) {
            case Type.FLOAT :
            case Type.DOUBLE : return true;
            default : return false;
            }
        case Type.DOUBLE :
        default : return false;
        }
    }
    
    /**
     * <strong>5.1.3 Narrowing Primitive Conversion</strong>
     * 
     * <p>22 specific conversions on primitive types are called the narrowing primitive conversions:</p>
     * <ul>
     * <li>short to byte or char</li>
     * <li>char to byte or short</li>
     * <li>int to byte, short, or char</li>
     * <li>long to byte, short, char, or int</li>
     * <li>float to byte, short, char, int, or long</li>
     * <li>double to byte, short, char, int, long, or float</li>
     * </ul>
     * @param from
     * @param to
     * @return
     */
    public static boolean checkNarrowingPrimitiveConversion(AClass from, AClass to) {
        int fromSort = from.getType().getSort();
        int toSort = from.getType().getSort();
        if(fromSort == Type.BYTE || fromSort == Type.BOOLEAN || toSort == Type.BOOLEAN) {
            return false;
        }
        switch(fromSort) {
        case Type.CHAR :
            switch(toSort) {
            case Type.BYTE : 
            case Type.SHORT : return true;
            default : return false;
            }
        case Type.SHORT :
            switch(toSort) {
            case Type.CHAR : 
            case Type.BYTE : return true;
            default : return false;
            }
        case Type.INT :
            switch(toSort) {
            case Type.CHAR :
            case Type.BYTE : 
            case Type.SHORT : return true;
            default : return false;
            }
        case Type.FLOAT :
            switch(toSort) {
            case Type.CHAR :
            case Type.BYTE : 
            case Type.SHORT :
            case Type.INT :
            case Type.LONG : return true;
            default : return false;
            }
        case Type.LONG :
            switch(toSort) {
            case Type.CHAR :
            case Type.BYTE : 
            case Type.SHORT :
            case Type.INT : return true;
            default : return false;
            }
        case Type.DOUBLE :
            switch(toSort) {
            case Type.CHAR :
            case Type.BYTE : 
            case Type.SHORT :
            case Type.INT :
            case Type.FLOAT :
            case Type.LONG : return true;
            default : return false;
            }
        default : return false;
        }
    }
    
    
    /**
     * <strong>5.1.4 Widening and Narrowing Primitive Conversion</strong>
     * 
     * <p>The following conversion combines both widening and narrowing primitive conversions:</p>
     * 
     * <p>byte to char</p>
     * 
     * <p>First, the byte is converted to an int via widening primitive conversion (§5.1.2), 
     * and then the resulting int is converted to a char by narrowing primitive conversion (§5.1.3).</p>
     * 
     * @param from
     * @param to
     * @return
     */
    public static boolean checkWideningAndNarrowingPrimitiveConversion(AClass from, AClass to) {
        return from.getType().getSort() == Type.BYTE && to.getType().getSort() == Type.CHAR;
    }
    
    /**
     * <p>5.1.5 Widening Reference Conversion</p>
     * 
     * <p>A widening reference conversion exists from any reference type S to any reference 
     * type T, provided S is a subtype (§4.10) of T.</p>
     * 
     * </p>Widening reference conversions never require a special action at run time and 
     * therefore never throw an exception at run time. They consist simply in regarding 
     * a reference as having some other type in a manner that can be proved correct at 
     * compile time.</p>
     * 
     * @param from
     * @param to
     * @return
     */
    public static boolean checkWideningReferenceConversion(AClass s, AClass t) {
        return s.isChildOrEqual(t);
    }
    
    /**
     * <p>5.1.7 Boxing Conversion</p>
     * 
     * <p>Boxing conversion converts expressions of primitive type to corresponding
     * expressions of reference type. Specifically, the following nine conversions are
     * called the boxing conversions:</p>
     * 
     * <ul>
     * <li>From type boolean to type Boolean</li>
     * <li>From type byte to type Byte</li>
     * <li>From type short to type Short</li>
     * <li>From type char to type Character</li>
     * <li>From type int to type Integer</li>
     * <li>From type long to type Long</li>
     * <li>From type float to type Float</li>
     * <li>From type double to type Double</li>
     * <li>From the null type to the null type</li>
     * </ul>
     * @param s
     * @param t
     * @return
     */
    public static boolean checkBoxingConversion(AClass s, AClass t) {
        return s.isPrimitive() && t.equals(AClassUtils.getPrimitiveWrapAClass(s));
    }
    
    /**
     * <strong>5.1.8 Unboxing Conversion<strong>
     * 
     * <p>Unboxing conversion converts expressions of reference type to corresponding
     * expressions of primitive type. Specifically, the following eight conversions are
     * called the unboxing conversions:</p>
     * 
     * <ul>
     * <li>From type Boolean to type boolean</li>
     * <li>From type Byte to type byte</li>
     * <li>From type Short to type short</li>
     * <li>From type Character to type char</li>
     * <li>From type Integer to type int</li>
     * <li>From type Long to type long</li>
     * <li>From type Float to type float</li>
     * <li>From type Double to type double</li>
     * </ul>
     * 
     * @param s
     * @param t
     * @return
     */
    public static boolean checkUnboxingConversion(AClass s, AClass t) {
        return t.isPrimitive() && s.equals(AClassUtils.getPrimitiveWrapAClass(t));
    }
    
    public static boolean checkMethodInvocatioConversion(AClass s, AClass t) {
    	return checkIdentityConversion(s, t) || 
    		   checkWideningPrimitiveConversion(s, t) ||
    		   checkWideningReferenceConversion(s, t) ||
    		   checkBoxingConversion(s, t) ||
    		   checkUnboxingConversion(s, t);
    }
    
}
