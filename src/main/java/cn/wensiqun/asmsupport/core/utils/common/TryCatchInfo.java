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

public class TryCatchInfo {
    private Label start;
    private Label end;
    private Label hander;
    private Type exception;

    public TryCatchInfo(Label start, Label end, Label hander, Type exception) {
        this.start = start;
        this.end = end;
        this.hander = hander;
        this.exception = exception;
    }

    public Label getStart() {
        return start;
    }

    public Label getEnd() {
        return end;
    }

    public Label getHander() {
        return hander;
    }

    public Type getException() {
        return exception;
    }

}