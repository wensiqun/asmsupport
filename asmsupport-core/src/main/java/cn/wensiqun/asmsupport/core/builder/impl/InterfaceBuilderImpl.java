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
package cn.wensiqun.asmsupport.core.builder.impl;


import cn.wensiqun.asmsupport.core.block.method.clinit.KernelStaticBlockBody;
import cn.wensiqun.asmsupport.core.builder.FieldBuilder;
import cn.wensiqun.asmsupport.core.builder.MethodDeclarable;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.ASConstants;


public class InterfaceBuilderImpl extends ClassCreator implements MethodDeclarable {

	private int clinitNum = 0;

	public InterfaceBuilderImpl(int version, String name, IClass[] itfs) {
		this(version, name, itfs, CachedThreadLocalClassLoader.getInstance());
	}
	
	/**
	 * Interface Creator
	 * 
	 * @param version JDK version.  
	 * @param name Interface qualified name.
	 * @param itfs super interfaces.
	 */
	public InterfaceBuilderImpl(int version, String name, IClass[] itfs, ASMSupportClassLoader classLoader) {
		super(version, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE, name, null, itfs, classLoader);
	}
	
	/**
	 * Create an interface method
	 * 
	 * @param name method name
	 * @param argClasses
	 * @param returnClass
	 * @param exceptions
	 */
	@Override
	public void declareMethod(String name, IClass[] argClasses, IClass returnClass, IClass[] exceptions) {
		this.declareMethod(name, argClasses, returnClass, exceptions, false);
    }

	@Override
	public void declareMethod(String name, IClass[] argClasses, IClass returnClass, IClass[] exceptions, boolean isVarargs) {
		String[] argNames = new String[argClasses.length];
		for(int i=0; i<argNames.length; i++){
			argNames[i] = "arg" + i;
		}
        methods.add(
        		DefaultMethodBuilder.buildForNew(name, argClasses, argNames,
                returnClass, exceptions, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + (isVarargs ? Opcodes.ACC_VARARGS : 0), null));
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
    	FieldBuildImpl fc = new FieldBuildImpl(name, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, type, value);
        fields.add(fc);
        return fc;
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
    public InterfaceBuilderImpl createStaticBlock(KernelStaticBlockBody body) {
		DefaultMethodBuilder creator;
		if(clinitNum > 0) {
			creator = DefaultMethodBuilder.buildForDelegate((DefaultMethodBuilder) methods.get(0), body);
		} else {
			creator = DefaultMethodBuilder.buildForNew(ASConstants.CLINIT, null, null, null, null,
					Opcodes.ACC_STATIC, body);
		}
		methods.add(clinitNum++, creator);
		return this;
    }
	
	@Override
	protected final void createDefaultConstructor() {
        // Do nothing
	}

}
