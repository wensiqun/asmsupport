/**
 * 
 */
package jw.asmsupport.utils;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class Component {

    /** 本地变量的引用 */
    protected LocalVariables locals;

    /** 第几代 */
    protected int generation;
    
    /** 该Component的出现顺序*/
    protected int componentOrder;

    /** 组件的父容器 */
    private Scope parent;

    public Component(LocalVariables locals) {
        super();
        this.locals = locals;
    }

    public Scope getParent() {
        return parent;
    }

    public final void setParent(Scope parent) {
        if (parent == null) {
            generation = 0;
        } else {
            generation = parent.generation + 1;
        }
        this.parent = parent;
        setComponentOrder();
    }

    private final void setComponentOrder(){
        if(parent == null){
            return;
        }
        
        if(parent.getComponents().isEmpty()){
            this.componentOrder = parent.componentOrder + 1;
        }else{
            Component lastCom = parent.getComponents().get(parent.getComponents().size() - 1);
            while(lastCom instanceof Scope){
                Scope scope = (Scope) lastCom;
                if(scope.getComponents().isEmpty()){
                    break;
                }else{
                    lastCom = scope.getComponents().get(scope.getComponents().size() - 1);
                }
            }
            this.componentOrder = lastCom.componentOrder + 1;
        }
    }
    
    public LocalVariables getLocals() {
        return locals;
    }

}
