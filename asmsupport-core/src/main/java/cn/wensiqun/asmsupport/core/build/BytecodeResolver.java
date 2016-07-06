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
package cn.wensiqun.asmsupport.core.build;


import cn.wensiqun.asmsupport.core.InitializedLifeCycle;
import cn.wensiqun.asmsupport.core.context.ClassExecuteContext;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.standard.def.clazz.MutableClass;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;


/**
 * The resolver for create/modify class
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public interface BytecodeResolver {

	/**
	 * Start create/modify class, this method will integrate those three phase({@link #create(ClassExecuteContext)}, {@link #prepare()}, {@link #execute()}). and get the
	 * byte[] as result.
	 * 
	 * @return
	 */
	byte[] toBytes();
    
    /**
     * Call {@link #toBytes()} and translate the {@code byte[]} to {@code Class}
     * 
     * @return
     */
    Class<?> resolve();

    /**
     * Get the operating class.
     * <ol>
     *     <li>Get the new class if current operation is build an new class.</li>
     *     <li>Get the class what's you modify if you are doing a modify class.</li>
     * </ol>
     *
     * @return
     */
    MutableClass getCurrentClass();

    /**
     *
     * @return
     */
    ASMSupportClassLoader getClassLoader();

    /**
     * If you want's the generated class save to local,
     * call this method set the output path.
     *
     * @param classOutPutPath
     */
	void setClassOutputPath(String classOutPutPath);
}
