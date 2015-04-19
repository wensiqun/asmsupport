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

import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.standard.clazz.AClass;

public class DummyEnumConstructor {

    /** The constructor argument types.*/
    private AClass[] argTypes;

    /** The constructor argument names.*/
    private String[] argNames;
    
    /** The constructor body.*/
    private EnumConstructorBody body;
    
    /**
     * Set the argument types.
     * 
     * @param argus
     * @return
     */
    public DummyEnumConstructor argTypes(AClass... argus){
        argTypes = argus;
        return this;
    }

    /**
     * Set the argument types.
     * 
     * @param argus
     * @return
     */
    public DummyEnumConstructor argTypes(Class<?>... argus){
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
     * Set argument names.
     * 
     * @param argNames
     * @return
     */
    public DummyEnumConstructor argNames(String... argNames){
        this.argNames = argNames;
        return this;
    }

    
    /**
     * Get argument names.
     * 
     * @param argNames
     * @return
     */
    public String[] getArgumentNames(){
        if(argNames == null) {
            return new String[0];
        }
        String[] copy = new String[argNames.length];
        System.arraycopy(argNames, 0, copy, 0, copy.length);
        return copy;
    }
	
	/**
	 * 
	 * @param body
	 * @return
	 */
    public DummyEnumConstructor body(EnumConstructorBody body){
        this.body = body;
        return this;
    }
    
    /**
     * Get constructor body.
     * 
     * @return
     */
    public EnumConstructorBody getConstructorBody() {
        return body;
    }
}
