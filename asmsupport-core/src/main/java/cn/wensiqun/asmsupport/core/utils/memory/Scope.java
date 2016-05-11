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
/**
 * 
 */
package cn.wensiqun.asmsupport.core.utils.memory;

import cn.wensiqun.asmsupport.org.objectweb.asm.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a scope, the scope could be contain {@link Scope} and {@link ScopeLogicVariable}
 * 
 * @author wensiqun at 163.com(Joe Wen)
 */
public class Scope extends ScopeComponent {

    /** variable and scope */
    private List<ScopeComponent> components;
    private Label start;
    private Label end;

    public Scope(LocalVariables locals, Scope parent) {
        super(locals);
        this.start = new Label("block start");
        this.end = new Label("block end");
        this.setParent(parent);
        if(parent != null){
            parent.addComponent(this);
        }
        this.components = new ArrayList<ScopeComponent>();
    }

    public ScopeComponent addComponent(ScopeComponent component) {
        if (component == null) {
            throw new NullPointerException("component must be non-null");
        }
        components.add(component);
        return component;
    }

    public List<ScopeComponent> getComponents() {
        return components;
    }

    /**
     * Get the scope start label
     */
    public Label getStart() {
        return start;
    }

    /**
     * Get the scope end label
     */
    public Label getEnd() {
        return end;
    }


}
