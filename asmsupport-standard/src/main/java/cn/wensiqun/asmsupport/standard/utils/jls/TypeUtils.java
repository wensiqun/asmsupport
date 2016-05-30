package cn.wensiqun.asmsupport.standard.utils.jls;

import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.ArrayClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

import java.io.Serializable;


/**
 * 
 * Support Types, Values, and Variables according chapter 4 of jls.
 * 
 */
public abstract class TypeUtils {

	/**
	 * <strong>4.10 Subtyping</strong>
	 * <p>The subtype and supertype relations are binary relations on types.</p>
	 * <p>check is subtyping</p>
	 * @param subType
	 * @param superType
	 * @return
	 */
    public static boolean isSubtyping(IClass subType, IClass superType) {
		if (superType.getName().equals(Object.class.getName()) || 
			subType.equals(superType)) {
            return true;
        }
		IClass[] directSuperTypes = TypeUtils.directSuperType(subType);
        if (directSuperTypes == null) {
            return false;
        }
        
        for (IClass directSuperType : directSuperTypes) {
            if (directSuperType.isChildOrEqual(superType)) {
                return true;
            }
        }
        return false;
	}
	
	/**
	 * <strong>4.10 Subtyping</strong>
	 * <p>The subtype and supertype relations are binary relations on types.</p>
	 * <p>get direct super type</p>
	 * @param subType
	 * @return
	 */
	public static IClass[] directSuperType(IClass subType) {
		if(subType.isPrimitive()) {
		    IClass type = directSuperAmongPrimitiveType(subType);
			if(type == null) {
				return null;
			} else {
				return new IClass[]{type};
			}
		} else if (subType.isArray()) {
			return directSuperAmongArrayType(subType);
		} else {
			return directSuperAmongClassAndInterfaceType(subType);
		}
	}
	
	/**
	 * <strong>4.10.1 Subtyping among Primitive Types</strong>
	 * 
	 * <p>The following rules define the direct supertype relation among the primitive types:</p>
	 * <ul>
	 * <li>double &gt;1 float</li>
	 * <li>float &gt;1 long</li>
	 * <li>long &gt;1 int</li>
	 * <li>int &gt;1 char</li>
	 * <li>int &gt;1 short</li>
	 * <li>short &gt;1 byte</li>
	 * </ul>
	 * 
	 * @param subType
	 * @return
	 */
	public static IClass directSuperAmongPrimitiveType(IClass subType) {
        switch(subType.getType().getSort()) {
        case Type.CHAR  : return subType.getClassHolder().getType(int.class);
        case Type.BYTE  : return subType.getClassHolder().getType(short.class);
        case Type.SHORT : return subType.getClassHolder().getType(int.class);
        case Type.INT   : return subType.getClassHolder().getType(long.class);
        case Type.FLOAT : return subType.getClassHolder().getType(double.class);
        case Type.LONG  : return subType.getClassHolder().getType(float.class);
        default : return null;
        }
	}
	
	
	/**
	 * <strong>4.10.2 Subtyping among Class and Interface Types</strong>
	 * 
	 * Given a generic type declaration C<F1,...,Fn>, the direct supertypes of the
	 * parameterized type C<T1,...,Tn> are all of the following:
	 * <ul>
	 * <li>The direct superclasses of C.</li>
	 * <li>The direct superinterfaces of C.</li>
	 * <li>The type Object, if C is an interface type with no direct superinterfaces.</li>
	 * <li>The raw type C.</li>
	 * </ul>
	 * The direct supertypes of the parameterized type C<T1,...,Tn>, where Ti (1 ≤ i ≤ n)
	 * is a type, are all of the following:
	 * <ul>
	 * <li>D<U1 θ,...,Uk θ>, where D<U1,...,Uk> is a direct supertype of C<T1,...,Tn> and θ is
	 * the substitution [F1:=T1,...,Fn:=Tn].</li>
	 * <li>C<S1,...,Sn>, where Si contains Ti (1 ≤ i ≤ n) (§4.5.1).</li>
	 * </ul>
	 * 
	 * The direct supertypes of the parameterized type C<R1,...,Rn>, where at least one of
	 * the Ri (1 ≤ i ≤ n) is a wildcard type argument, are the direct supertypes of C<X1,...,Xn>
	 * which is the result of applying capture conversion (§5.1.10) to C<R1,...,Rn>.
	 * The direct supertypes of an intersection type T1 & ... & Tn are Ti (1 ≤ i ≤ n).
	 * The direct supertypes of a type variable are the types listed in its bound.
	 * A type variable is a direct supertype of its lower bound.
	 * The direct supertypes of the null type are all reference types other than the null
	 * type itself.
	 * 
	 * @param subType
	 * @return
	 */
	public static IClass[] directSuperAmongClassAndInterfaceType(IClass subType) {
		if (subType.isInterface()) {
            IClass[] interfaces = subType.getInterfaces();
            if (interfaces.length > 0) {
                return interfaces;
            } else {
                return new IClass[] { subType.getClassHolder().getType(Object.class) };
            }
		} else {
			IClass superType = subType.getSuperclass();
            IClass[] interfaces = subType.getInterfaces();
            IClass[] superTypes;
            if(superType == null) {
            	superTypes = new IClass[interfaces.length];
            } else {
            	superTypes = new IClass[interfaces.length + 1];
            	superTypes[0] = superType;
            }
            
            if(interfaces.length > 0){
                System.arraycopy(interfaces, 0, superTypes, 1, interfaces.length);
            }
            
            return superTypes;
		}
	}
	
	/**
	 * <strong>4.10.3 Subtyping among Array Types</strong>
	 * 
	 * <p>The following rules define the direct supertype relation among array types:</p>
	 * 
	 * <ul>
	 * <li>If S and T are both reference types, then S[] >1 T[] iff S >1 T.</li>
	 * <li>Object &gt;1 Object[]</li>
	 * <li>Cloneable &gt;1 Object[]</li>
	 * <li>java.io.Serializable &gt;1 Object[]</li>
	 * <li>If P is a primitive type, then:</li>
	 * <ul>
	 * <li>Object &gt;1 Object[]</li>
	 * <li>Cloneable &gt;1 Object[]</li>
	 * <li>java.io.Serializable &gt;1 Object[]</li>
	 * </ul>
	 * </ul>
	 * 
	 * @param subType
	 * @return
	 */
	public static IClass[] directSuperAmongArrayType(IClass subType) {
		ArrayClass arrayType = (ArrayClass) subType;
		IClass basicElementType = arrayType.getRootComponentClass();
        IClass[] superTypes;
        if (basicElementType.isPrimitive()) {
            superTypes = new IClass[2];
            superTypes[0] = subType.getClassHolder().getType(Cloneable.class);
            superTypes[1] = subType.getClassHolder().getType(Serializable.class);
        } else {
            IClass[] basicElementSuperTypes = directSuperType(basicElementType);
            if (basicElementSuperTypes != null) {
                superTypes = new IClass[basicElementSuperTypes.length];
                for (int i = 0; i < superTypes.length; i++) {
                    superTypes[i] = subType.getClassHolder().getArrayType(basicElementSuperTypes[i], arrayType.getDimension());
                }
            } else {
                superTypes = new IClass[2];
                superTypes[0] = subType.getClassHolder().getType(Cloneable.class);
                superTypes[1] = subType.getClassHolder().getType(Serializable.class);
            }
        }
        return superTypes;
	}
}
