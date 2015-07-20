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

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;
import cn.wensiqun.asmsupport.standard.utils.AsmsupportClassLoader;

public class DummyInterfaceMethod extends AbstractDummy {

    /** The return type of method. */
    private IClass returnType;
    
    /** The name of method */
    private String name;

    /** The constructor argument types.*/
    private IClass[] argTypes;
    
    /** The constructor throws exception types.*/
    private IClass[] exceptionTypes;
    
    private boolean varargs = false;
    
    DummyInterfaceMethod(AsmsupportClassLoader classLoader) {
    	super(classLoader);
    }
    
    /**
     * Set the method return type.
     * 
     * @param ret
     * @return
     */
    public DummyInterfaceMethod return_(IClass ret) {
        this.returnType = ret;
        return this;
    }
    
    /**
     * Set the method return type.
     * 
     * @param ret
     * @return
     */
    public DummyInterfaceMethod return_(Class<?> ret) {
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
    public DummyInterfaceMethod name(String name) {
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
    public DummyInterfaceMethod argTypes(IClass... argus){
        argTypes = argus;
        return this;
    }

    /**
     * Set the argument types.
     * 
     * @param argus
     * @return
     */
    public DummyInterfaceMethod argTypes(Class<?>... argus){
        this.argTypes = IClassUtils.convertToAClass(getClassLoader(), argus);
        return this;
    }

    
    /**
     * Varargs
     * 
     * @return
     */
    public DummyInterfaceMethod varargs() {
    	this.varargs = true;
        return this;
    }
    
    /**
     * Check the method whether or not varargs
     */
    public boolean isVarargs() {
        return varargs;
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
     * Unrecommend use it.
     * 
     * @return
     */
    public DummyInterfaceMethod setModifier(int modifiers) {
        if((modifiers & Opcodes.ACC_VARARGS) != 0) {
        	this.varargs = true;
        }
    	return this;
    }
    
    /**
     * Set the method throws exception types.
     * 
     * @param exceptionTypes
     * @return
     */
    public DummyInterfaceMethod throws_(Class<?>... exceptionTypes){
        this.exceptionTypes = IClassUtils.convertToAClass(getClassLoader(), exceptionTypes);
        return this;
    }
    
    /**
     * Set the method throws exception types.
     * 
     * @param exceptionTypes
     * @return
     */
    public DummyInterfaceMethod throws_(IClass... exceptionTypes){
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
    
}
