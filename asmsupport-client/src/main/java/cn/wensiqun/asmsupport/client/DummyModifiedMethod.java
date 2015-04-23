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

import cn.wensiqun.asmsupport.client.block.ModifiedMethodBody;


public class DummyModifiedMethod {
    
    private String name;
    
    private Class<?>[] argTypes;
    
    private ModifiedMethodBody body;

    public DummyModifiedMethod(String name, Class<?>[] argTypes, ModifiedMethodBody body) {
        this.name = name;
        this.argTypes = argTypes;
        this.body = body;
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
     * Get argument types.
     * 
     * @return
     */
    public Class<?>[] getArgumentTypes() {
        if(argTypes == null) {
            return new Class[0];
        }
        Class<?>[] copy = new Class[argTypes.length];
        System.arraycopy(argTypes, 0, copy, 0, copy.length);
        return copy;
    }

    /**
     * Get the method body.
     * 
     * @return
     */
    public ModifiedMethodBody getMethodBody() {
        return this.body;
    }
}
