/**
 * 
 */
package jw.asmsupport.utils;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;






/**
 * 
 * @author 温斯群(Joe Wen)
 */
public class Scope extends Component {

    /** variable and scope */
    private List<Component> components;
    // Scope的范围边界
    private Label start;
    private Label innerEnd;
    private Label outerEnd;

    public Scope(LocalVariables locals, Scope parent) {
        super(locals);
        this.start = new Label();
        this.innerEnd = new Label();
        this.outerEnd = new Label();
        this.setParent(parent);
        if(parent != null){
            parent.addComponent(this);
        }
        this.components = new ArrayList<Component>();
    }

    public Component addComponent(Component component) {
        if (component == null) {
            throw new NullPointerException("component must be non-null");
        }
        components.add(component);
        return component;
    }

    public List<Component> getComponents() {
        return components;
    }

    public Label getStart() {
        return start;
    }

    public Label innerEnd() {
        return innerEnd;
    }

    public Label outerEnd() {
        return outerEnd;
    }

}
