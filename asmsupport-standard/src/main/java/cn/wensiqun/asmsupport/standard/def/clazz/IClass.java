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
package cn.wensiqun.asmsupport.standard.def.clazz;

import java.util.Collection;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;


/**
 * Indicate a java class.
 * 
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public interface IClass { 
	
    /**
     * Check the class is array type.
     * 
     * @return true if current class is array.
     */
    boolean isArray();
    
    /**
     * Get the dimension number of current class. 
     * 
     * @return return dimension number if current class is array, otherwise return -1
     */
    int getDimension();

    /**
     * Get the package name.
     * 
     * @return String package name
     */
    String getPackage();

    /**
     * Get the qualified name of current class.
     * 
     * @return class qualified name
     */
    String getName();

    /**
     * Get the class modifier, it's an integer value which 
     * is a sum value of same field from {@link Opcodes}, the field 
     * is start with 'ACC_', for exampel : "public final class" modifiers,
     * the value is  {@link Opcodes#ACC_PUBLIC} + {@link Opcodes#ACC_FINAL}
     * 
     * @return int the modifiers
     */
    int getModifiers();

    /**
     * Get the class version, is should be (see {@link Opcodes#V1_1} ~ {@link Opcodes#V_MAX})
     * 
     * @return 
     */
    int getVersion();

    /**
     * Get the super class.
     * 
     * @return Class super class
     * rename to getSuperclass
     */
    IClass getSuperclass();

    /**
     * Get all interfaces
     * 
     * @return all interface
     */
    Class<?>[] getInterfaces();

    /**
     * Get the class description, the description is the same to {@link Type#getDescriptor()},
     * It's the indication of class in jvm.
     * 
     * @return String class description
     */
    String getDescription();
    
    /**
     * Get all field of current class, it's possible contain static and non-static field.
     * 
     * @param name
     * @return
     */
    Field getField(String name);
    
    /**
     * Returns an array of <code>Constructor</code> objects reflecting all the
     * constructors declared by the class represented by this
     * <code>Class</code> object. These are public, protected, default
     * (package) access, and private constructors.  The elements in the array
     * returned are not sorted and are not in any particular order.  If the
     * class has a default constructor, it is included in the returned array.
     * This method returns an array of length 0 if this <code>Class</code>
     * object represents an interface, a primitive type, an array class, or
     * void.
     * 
     * @return
     */
    Collection<AMethodMeta> getDeclaredConstructors();
    
    /**
     * Returns a <code>Constructor</code> object that reflects the specified
     * constructor of the class or interface represented by this
     * <code>Class</code> object.  The <code>parameterTypes</code> parameter is
     * an array of <code>Class</code> objects that identify the constructor's
     * formal parameter types, in declared order.
     * 
     * @param parameterTypes
     * @return
     */
    AMethodMeta getDeclaredConstructor(IClass... parameterTypes);
    
    /**
     * Returns an array of <code>Method</code> objects reflecting all the
     * methods declared by the class or interface represented by this
     * <code>Class</code> object. This includes public, protected, default
     * (package) access, and private methods, but excludes inherited methods.
     * The elements in the array returned are not sorted and are not in any
     * particular order.  This method returns an array of length 0 if the class
     * or interface declares no methods, or if this <code>Class</code> object
     * represents a primitive type, an array class, or void.  The class
     * initialization method <code>&lt;clinit&gt;</code> is not included in the
     * returned array. If the class declares multiple public member methods
     * with the same parameter types, they are all included in the returned
     * array.
     */
    Collection<AMethodMeta> getDeclaredMethods();
    
    /**
     * Returns a <code>Method</code> object that reflects the specified
     * declared method of the class or interface represented by this
     * <code>Class</code> object. The <code>name</code> parameter is a
     * <code>String</code> that specifies the simple name of the desired
     * method, and the <code>parameterTypes</code> parameter is an array of
     * <code>Class</code> objects that identify the method's formal parameter
     * types, in declared order.  If more than one method with the same
     * parameter types is declared in a class, and one of these methods has a
     * return type that is more specific than any of the others, that method is
     * returned; otherwise one of the methods is chosen arbitrarily.  If the
     * name is "&lt;init&gt;"or "&lt;clinit&gt;" a <code>NoSuchMethodException</code>
     * is raised.
     * 
     * @param name the name of the method
     * @param parameterTypes the parameter array
     * @return if not found method return null
     */
    AMethodMeta getDeclaredMethod(String name, IClass... parameterTypes);
    
    /**
     * Get the asm 
     * @return
     */
    Type getType();

    /**
     * Check the class is primitive
     * 
     */
    boolean isPrimitive();

    
    /**
     * 
     * Check the class is interface
     * 
     */
    boolean isInterface();

    /**
     * Check the class is abstract class
     * 
     */
    boolean isAbstract();
    
    /**
     * When do cast will get the most specially type according the cast order.
     * 
     */
    int getCastOrder();
    
    
    /**
     * Check the class exist static init block.
     * 
     */
    boolean existStaticInitBlock();

    /**
     * Check current class is child or equal other type.
     * 
     * @param otherType
     * @return boolean
     */
    boolean isChildOrEqual(IClass otherType);
    
    /**
     * if the class is array type, get the next dim type.
     * 
     */
    public IClass getNextDimType();
    
    /**
     * IF current class is array type, than get the basic type, for example : 
     * <pre>
     * String[][][] str;
     * </pre>
     * 
     * The root component class is String in preceding code.
     */
    public IClass getRootComponentClass();
    
    
    public ClassHolder getClassLoader();
}
