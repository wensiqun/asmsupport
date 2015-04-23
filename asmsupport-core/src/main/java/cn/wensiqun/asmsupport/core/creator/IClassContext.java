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
/**1
 * 
 */
package cn.wensiqun.asmsupport.core.creator;


import cn.wensiqun.asmsupport.core.clazz.MutableClass;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;


/**
 * The class create or modify context
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface IClassContext {
	
    /**
     * get class visitor
     * 
     * @return
     */
    ClassVisitor getClassVisitor();
    
    
    /**
     * 
     * @return
     */
    MutableClass getCurrentClass();
    
    /**
     * 
     * @param classOutPutPath
     */
	public void setClassOutPutPath(String classOutPutPath);
    
    
    /**
     * start create/modify class
     * 
     * @return
     */
    Class<?> startup();
	
}
