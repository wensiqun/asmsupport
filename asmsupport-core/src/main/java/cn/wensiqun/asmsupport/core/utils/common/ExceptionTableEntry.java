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
package cn.wensiqun.asmsupport.core.utils.common;

import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 
 * Each method have an exception in class. it's seem like following :
 * 
 *  <pre>
 *  exception table :
 *      [pc: 0, pc: 32] -> 33 when : java.lang.Exception
 *      [pc: 34, pc: 50] -> 51 when : java.lang.RuntimeException
 *  </pre>
 *  
 * Each row indicate a try...catch... information
 * 
 *  <ul>
 *      <li>start : 0</li>
 *      <li>end : 32</li>
 *      <li>handle position : 33</li>
 *      <li>catch exception : RuntimeException</li>
 *  </ul>
 *
 */
public class ExceptionTableEntry {
	
    private Label start;
    private Label end;
    private Label hander;
    private Type exception;

    public ExceptionTableEntry(Label start, Label end, Label handler, Type exception) {
        this.start = start;
        this.end = end;
        this.hander = handler;
        this.exception = exception;
    }

    /**
     * Get try start label
     */
    public Label getStart() {
        return start;
    }

    /**
     * Get try end label
     */
    public Label getEnd() {
        return end;
    }

    /**
     * Get exception handler position
     */
    public Label getHandler() {
        return hander;
    }

    /**
     * Get the what's type to catch
     */
    public Type getException() {
        return exception;
    }

}