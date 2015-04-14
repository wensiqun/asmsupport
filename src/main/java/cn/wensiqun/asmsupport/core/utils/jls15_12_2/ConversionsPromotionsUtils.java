package cn.wensiqun.asmsupport.core.utils.jls15_12_2;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


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
	public static boolean checkIdentity(AClass from, AClass to) {
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
	public static boolean checkWideningPrimitive(AClass from, AClass to) {
		if(from.isPrimitive() && to.isPrimitive()) {
			int fromSort = from.getType().getSort();
			int toSort = from.getType().getSort();
			if(fromSort == 1 || toSort == 1) {
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
		return false;
	}
	
	/*switch(toSort) {
	case Type.CHAR : return false;
	case Type.BYTE : 
	case Type.SHORT :
	case Type.INT :
	case Type.FLOAT :
	case Type.LONG :
	case Type.DOUBLE : return true;
	default : return false;
	}*/
}
