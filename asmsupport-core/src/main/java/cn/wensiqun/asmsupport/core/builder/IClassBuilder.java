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
package cn.wensiqun.asmsupport.core.builder;


import cn.wensiqun.asmsupport.core.loader.AsmsupportClassLoader;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.standard.def.clazz.MutableClass;


/**
 * The class create or modify context
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public interface IClassBuilder {
    
    /**
     * First phase, asmsupport will create all of meta information such 
     * as "Field information", "Method information" etc.
     */
    void create();
    
    /**
     * Second phase, asmsupport will verify the code you want's to generated.
     * such as : check the method call is legally.  must call {@link #create()}
     * before call this method
     */
    void prepare();
    
    
    /**
     * Third phase, asmsupport will generate byte code. and 
     * will return the byte array of class. must call {@link #prepare()}
     * before call this method
     */
    byte[] execute();

	/**
	 * Start create/modify class, this method will integrate the three phase. and get the
	 * byte[] as result.
	 * 
	 * @return
	 */
	byte[] toClassBytes();
    
    /**
     * Start create/modify class, this method will integrate the three phase. and get the
	 * class as result.
     * 
     * @return
     */
    Class<?> startup();

	
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
     * @return
     */
    AsmsupportClassLoader getClassLoader();
    
    /**
     * 
     * @param classOutPutPath
     */
	void setClassOutPutPath(String classOutPutPath);
}
