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
package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.client.block.MethodBody;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

public class DummyMethod extends DummyAccessControl<DummyMethod> {

    /** The return type of method. */
    private IClass returnType;
    
    /** The name of method */
    private String name;

    /** The constructor argument types.*/
    private IClass[] argTypes;

    /** The constructor argument names.*/
    private String[] argNames;
    
    /** The constructor throws exception types.*/
    private IClass[] exceptionTypes;
    
    /** The method body */
    private MethodBody body;
    
    DummyMethod(ASMSupportClassLoader classLoader) {
    	super(classLoader);
    }
    
    /**
     * Set to static
     * 
     * @return
     */
    public DummyMethod static_() {
        modifiers = (modifiers & ~Opcodes.ACC_STATIC) + Opcodes.ACC_STATIC;
        return this;
    }

    /**
     * Check the access whether or not static.
     * 
     * @return
     */
    public boolean isStatic() {
        return (modifiers & Opcodes.ACC_STATIC) != 0;
    }
    
    /**
     * Set method to synchronized
     * 
     * @return
     */
    public DummyMethod synchronized_() {
        modifiers = (modifiers & ~Opcodes.ACC_SYNCHRONIZED) + Opcodes.ACC_SYNCHRONIZED;
        return this;
    }
    
    /**
     * Check the method whether or not synchronized.
     * 
     * @return
     */
    public boolean isSynchronized() {
        return (modifiers & Opcodes.ACC_SYNCHRONIZED) != 0;
    }
    
    /**
     * Set method to abstract
     * 
     * @return
     */
    public DummyMethod abstract_() {
        modifiers = (modifiers & ~Opcodes.ACC_ABSTRACT) + Opcodes.ACC_ABSTRACT;
        return this;
    }
    
    /**
     * Check the method whether or not abstract.
     * 
     * @return
     */    
    public boolean isAbstract() {
        return (modifiers & Opcodes.ACC_ABSTRACT) != 0;
    }
    
    /**
     * Set method to native
     * 
     * @return
     */
    public DummyMethod native_() {
        modifiers = (modifiers & ~Opcodes.ACC_NATIVE) + Opcodes.ACC_NATIVE;
        return this;
    }
    
    /**
     * Check the method whether or not native.
     * 
     * @return
     */
    public boolean isNative() {
        return (modifiers & Opcodes.ACC_NATIVE) != 0;
    }

    
    /**
     * Set method to strictfp
     * 
     * @return
     */
    public DummyMethod strictfp_() {
        modifiers = (modifiers & ~Opcodes.ACC_STRICT) + Opcodes.ACC_STRICT;
        return this;
    }
    
    /**
     * Varargs
     * 
     * @return
     */
    public DummyMethod varargs() {
        modifiers = (modifiers & ~Opcodes.ACC_VARARGS) + Opcodes.ACC_VARARGS;
        return this;
    }
    
    /**
     * Check the method whether or not varargs
     */
    public boolean isVarargs() {
        return (modifiers & Opcodes.ACC_VARARGS) != 0;
    }
    
    /**
     * Check the method whether or not strictfp.
     * 
     * @return
     */
    public boolean isStrictfp() {
        return (modifiers & Opcodes.ACC_STRICT) != 0;
    }
    
    /**
     * Unrecommend use it.
     * 
     * @return
     */
    public DummyMethod setModifier(int modifiers) {
    	this.modifiers = modifiers;
    	return this;
    }
    
    /**
     * Set the method return type.
     * 
     * @param ret
     * @return
     */
    public DummyMethod return_(IClass ret) {
        this.returnType = ret;
        return this;
    }
    
    /**
     * Set the method return type.
     * 
     * @param ret
     * @return
     */
    public DummyMethod return_(Class<?> ret) {
        this.returnType = getClassLoader().getType(ret);
        return this;
    }
    
    /**
     * Get the method return type.
     * 
     * @return
     */
    public IClass getReturnType() {
        return returnType;
    }
    
    /**
     * Set the name of the method.
     * 
     * @param name
     * @return
     */
    public DummyMethod name(String name) {
        this.name = name;
        return this;
    }
    
    /**
     * Get the name of the method.
     * 
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the argument types.
     * 
     * @param argus
     * @return
     */
    public DummyMethod argTypes(IClass... argus){
        argTypes = argus;
        return this;
    }

    /**
     * Set the argument types.
     * 
     * @param argus
     * @return
     */
    public DummyMethod argTypes(Class<?>... argus){
        this.argTypes = IClassUtils.convertToAClass(getClassLoader(), argus);
        return this;
    }
    
    /**
     * Get argument types.
     * 
     * @return
     */
    public IClass[] getArgTypes() {
        if(argTypes == null) {
            return new IClass[0];
        }
        IClass[] copy = new IClass[argTypes.length];
        System.arraycopy(argTypes, 0, copy, 0, copy.length);
        return copy;
    }
    
    /**
     * Set argument names.
     * 
     * @param argNames
     * @return
     */
    public DummyMethod argNames(String... argNames){
        this.argNames = argNames;
        return this;
    }

    
    /**
     * Get argument names.
     * 
     * @param argNames
     * @return
     */
    public String[] getArgNames(){
        if(argNames == null) {
            return new String[0];
        }
        String[] copy = new String[argNames.length];
        System.arraycopy(argNames, 0, copy, 0, copy.length);
        return copy;
    }

    
    /**
     * Set the method throws exception types.
     * 
     * @param exceptionTypes
     * @return
     */
    public DummyMethod throws_(Class<?>... exceptionTypes){
        this.exceptionTypes = IClassUtils.convertToAClass(getClassLoader(), exceptionTypes);
        return this;
    }
    
    /**
     * Set the method throws exception types.
     * 
     * @param exceptionTypes
     * @return
     */
    public DummyMethod throws_(IClass[] exceptionTypes){
        this.exceptionTypes = exceptionTypes;
        return this;
    }
    
    /**
     * Get the method throws exception types.
     * 
     * @return
     */
    public IClass[] getThrows() {
        if(exceptionTypes == null) {
            return new IClass[0];
        }
        IClass[] copy = new IClass[exceptionTypes.length];
        System.arraycopy(exceptionTypes, 0, copy, 0, copy.length);
        return copy;
    }
    
    /**
     * Set the method body.
     * 
     * @param body
     * @return
     */
    public DummyMethod body(MethodBody body) {
        this.body = body;
        return this;
    }
    
    /**
     * Get the method body.
     * 
     * @return
     */
    public MethodBody getMethodBody() {
        return this.body;
    }
}
