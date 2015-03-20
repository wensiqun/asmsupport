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

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

public class DummyInterfaceMethod {

    /** The return type of method. */
    private AClass returnType;
    
    /** The name of method */
    private String name;

    /** The constructor argument types.*/
    private AClass[] argTypes;
    
    /** The constructor throws exception types.*/
    private AClass[] exceptionTypes;
    
    /**
     * Set the method return type.
     * 
     * @param ret
     * @return
     */
    public DummyInterfaceMethod return_(AClass ret) {
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
        this.returnType = AClassFactory.getProductClass(ret);
        return this;
    }
    
    /**
     * Get the method return type.
     * 
     * @return
     */
    public AClass getReturnType() {
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
    public DummyInterfaceMethod argTypes(AClass... argus){
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
        this.argTypes = AClassUtils.convertToAClass(argus);
        return this;
    }
    
    /**
     * Get argument types.
     * 
     * @return
     */
    public AClass[] getArgumentTypes() {
        if(argTypes == null) {
            return new AClass[0];
        }
        AClass[] copy = new AClass[argTypes.length];
        System.arraycopy(argTypes, 0, copy, 0, copy.length);
        return copy;
    }
    
    /**
     * Set the method throws exception types.
     * 
     * @param exceptionTypes
     * @return
     */
    public DummyInterfaceMethod throws_(Class<?>... exceptionTypes){
        this.exceptionTypes = AClassUtils.convertToAClass(exceptionTypes);
        return this;
    }
    
    /**
     * Set the method throws exception types.
     * 
     * @param exceptionTypes
     * @return
     */
    public DummyInterfaceMethod throws_(AClass... exceptionTypes){
        this.exceptionTypes = exceptionTypes;
        return this;
    }
    
    /**
     * Get the method throws exception types.
     * 
     * @return
     */
    public AClass[] getThrows() {
        if(exceptionTypes == null) {
            return new AClass[0];
        }
        AClass[] copy = new AClass[exceptionTypes.length];
        System.arraycopy(exceptionTypes, 0, copy, 0, copy.length);
        return copy;
    }
    
}
