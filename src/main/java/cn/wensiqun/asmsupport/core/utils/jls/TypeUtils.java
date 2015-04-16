package cn.wensiqun.asmsupport.core.utils.jls;

import java.io.Serializable;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


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
	 * @param sub
	 * @param sup
	 * @return
	 */
	public static boolean isSubtyping(AClass subType, AClass superType) {
		if (superType.getName().equals(Object.class.getName()) || 
			subType.equals(superType)) {
            return true;
        }
        AClass[] directSuperTypes = TypeUtils.directSuperType(subType);
        if (directSuperTypes == null) {
            return false;
        }
        
        for (AClass directSuperType : directSuperTypes) {
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
	public static AClass[] directSuperType(AClass subType) {
		if(subType.isPrimitive()) {
			AClass type = directSuperAmongPrimitiveType(subType);
			if(type == null) {
				return null;
			} else {
				return new AClass[]{type};
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
	 * @param sub
	 * @return
	 */
	public static AClass directSuperAmongPrimitiveType(AClass subType) {
        switch(subType.getType().getSort()) {
        case Type.CHAR  : return AClassFactory.defType(int.class);
        case Type.BYTE  : return AClassFactory.defType(short.class);
        case Type.SHORT : return AClassFactory.defType(int.class);
        case Type.INT   : return AClassFactory.defType(long.class);
        case Type.FLOAT : return AClassFactory.defType(double.class);
        case Type.LONG  : return AClassFactory.defType(float.class);
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
	public static AClass[] directSuperAmongClassAndInterfaceType(AClass subType) {
		if (subType.isInterface()) {
			AClass[] superTypes;
            Class<?>[] interfaces = subType.getInterfaces();
            if (interfaces != null && interfaces.length > 0) {
                superTypes = new AClass[interfaces.length];
                for (int i = 0; i < superTypes.length; i++) {
                    superTypes[i] = AClassFactory.defType(interfaces[i]);
                }
            } else {
                superTypes = new AClass[] { AClassFactory.defType(Object.class) };
            }
            return superTypes;
		} else {
			Class<?> superType = subType.getSuperClass();
            Class<?>[] intefaces = subType.getInterfaces();
            if(superType == null && intefaces.length == 0) {
            	return null;
            } else if (superType != null && intefaces.length == 0) {
            	return new AClass[]{AClassFactory.defType(superType)};
            } else if (superType == null && intefaces.length > 0) {
            	AClass[] superTypes = new AClass[intefaces.length];
            	for (int i = 0; i < superTypes.length; i++) {
                    superTypes[i] = AClassFactory.defType(intefaces[i]);
                }
            	return superTypes;
            } else {
            	AClass[] superTypes = new AClass[intefaces.length + 1];
                superTypes[0] = AClassFactory.defType(superType);
                for (int i = 1; i < superTypes.length; i++) {
                    superTypes[i] = AClassFactory.defType(intefaces[i - 1]);
                }
                return superTypes;
            }
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
	public static AClass[] directSuperAmongArrayType(AClass subType) {
		ArrayClass arrayType = (ArrayClass) subType;
        AClass basicElementType = arrayType.getRootComponentClass();
        AClass[] superTypes;
        if (basicElementType.isPrimitive()) {
            superTypes = new AClass[2];
            superTypes[0] = AClassFactory.defType(Cloneable.class);
            superTypes[1] = AClassFactory.defType(Serializable.class);
        } else {
            AClass[] basicElementSuperTypes = directSuperType(basicElementType);
            if (basicElementSuperTypes != null) {
                superTypes = new AClass[basicElementSuperTypes.length];
                for (int i = 0; i < superTypes.length; i++) {
                    superTypes[i] = AClassFactory.defArrayType(basicElementSuperTypes[i], arrayType.getDimension());
                }
            } else {
                superTypes = new AClass[2];
                superTypes[0] = AClassFactory.defType(Cloneable.class);
                superTypes[1] = AClassFactory.defType(Serializable.class);
            }
        }
        return superTypes;
	}
}
