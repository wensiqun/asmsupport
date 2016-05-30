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
/**
 * 
 */
package cn.wensiqun.asmsupport.core.build.resolver;


import cn.wensiqun.asmsupport.core.block.method.clinit.KernelStaticBlockBody;
import cn.wensiqun.asmsupport.core.build.FieldBuilder;
import cn.wensiqun.asmsupport.core.build.MethodBuilder;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;


public class InterfaceResolver extends ClassBuildResolver {

	public InterfaceResolver(int version, String name, IClass[] itfs) {
		this(version, name, itfs, CachedThreadLocalClassLoader.getInstance());
	}
	
	/**
	 * Interface Creator
	 * 
	 * @param version JDK version.  
	 * @param name Interface qualified name.
	 * @param interfaces super interfaces.
	 */
	public InterfaceResolver(int version, String name, IClass[] interfaces, ASMSupportClassLoader classLoader) {
		super(version, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE, name, null, interfaces, classLoader);
	}

	/**
	 * Declare an non-varargs interface method
	 *
	 * @param name
	 * @param argTypes
	 * @param returnType
	 * @param exceptions
	 * @return
	 * @see #declareMethod(String, IClass[], IClass, IClass[], boolean)
	 */
	public MethodBuilder declareMethod(String name, IClass[] argTypes, IClass returnType, IClass[] exceptions) {
		return this.declareMethod(name, argTypes, returnType, exceptions, false);
    }

	/**
	 * Declare an interface method
	 *
	 * @param name method name
	 * @param argTypes method argument types
	 * @param returnType method return type, if null than indicate void
	 * @param exceptions what exception you want explicit throw.
	 * @see #declareMethod(String, IClass[], IClass, IClass[])
	 */
	public MethodBuilder declareMethod(String name, IClass[] argTypes, IClass returnType, IClass[] exceptions, boolean isVarargs) {
		String[] argNames = new String[argTypes.length];
		for(int i=0; i<argNames.length; i++){
			argNames[i] = "arg" + i;
		}
		return createMethodInternal(name, argTypes, argNames, returnType, exceptions,
				Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + (isVarargs ? Opcodes.ACC_VARARGS : 0), null);
    }
	
	/**
     * <P>
     *     create a global variable. the modifiers "public static final".</br>
     *     The default value is same to JVM</br>
     * </p>
     * 
     * <p>
     *     if you want assign special value to this variable, <br>
     *     you need do it at static block.
     * </p>
     * 
	 * @param name
	 * @param type
	 * @return
	 */
	public FieldBuilder createField(String name, IClass type) {
	    return createField(name, type, null);
	}
    
	/**
	 * <P>
	 *     create a global variable with special value. the modifiers "public static final".</br>
	 *     The default value is same to JVM</br>
	 * </p>
	 * 
	 * <p>
	 *     if you want assign special value to this variable, <br>
	 *     you need do it at static block.
	 * </p>
	 * 
	 * @param name variable name
	 * @param type
	 * @param value The initial value, this value is only support static field, 
     *              otherwise will ignored.This parameter, which may be null 
     *              if the field does not have an initial value, 
     *              must be an Integer, a Float, a Long, a Double or a 
     *              String (for int, float, long or String fields respectively). 
     *              This parameter is only used for static fields. Its value is 
     *              ignored for non static fields, which must be initialized 
     *              through bytecode instructions in constructors or methods.
	 */
    public FieldBuilder createField(String name, IClass type, Object value){
        return createFieldInternal(name, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, type, value);
    }
	
    /**
     * <p>
     *     create a static block. <br>
     *     you can call this method just on time.<br>
     *     this is different write java code. we can write any java cod
     * </p>
     * 
     * @param body Method Body
     */
    public MethodBuilder createStaticBlock(KernelStaticBlockBody body) {
		return createStaticBlockInternal(body);
    }
	
	@Override
	protected final void createDefaultConstructor() {
        // Do nothing
	}

}
