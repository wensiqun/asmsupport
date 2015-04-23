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
package cn.wensiqun.asmsupport.core.utils.lang;

public abstract class InterfaceLooper {

    public final boolean loop(Class<?>[] clazzs){
        for(Class<?> clazz : clazzs) {
            if(clazz.isInterface()) {
                if(process(clazz)) {
                    return true;
                }
            }
        }
        
        for(Class<?> clazz : clazzs) {
            if(loop(clazz.getInterfaces())) {
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * return true stop loop, false continue loop
     * 
     * @param inter
     * @return
     */
    protected abstract boolean process(Class<?> inter);
    
}
