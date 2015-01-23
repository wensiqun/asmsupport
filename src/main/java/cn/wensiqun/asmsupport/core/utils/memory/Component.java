/**
 * 
 */
package cn.wensiqun.asmsupport.core.utils.memory;

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
    
    /** 该Component的出现顺序, 比如在第一层第二个出现则值为1.2, 第一层中的第二个中的第三个出现则为1.2.3 */
    protected String componentOrder;

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
        	this.componentOrder = "1";
    	}else{
    		this.componentOrder = parent.componentOrder + "." + (parent.getComponents().size() + 1);
    	}
    	/*if(parent == null){
        	this.componentOrder = "1";
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
                    lastCom = scope.getComponents().get(scope.getCTomponents().size() - 1);
                }
            }
            this.componentOrder = lastCom.componentOrder + 1;
        }*/
    }
    
    /**
     * order1 great than order2 return 1;
     * order1 less than order2 return -1;
     * order1 equeas order2 return 0;
     * 
     * @param order1
     * @param order2
     * @return
     */
    protected int compareComponentOrder(String order1, String order2){
    	String[] split1 = order1.split("\\.");
    	String[] split2 = order2.split("\\.");

    	int maxLen = split1.length > split2.length ? split1.length : split2.length;
    	
    	int[] orderSplit1 = new int[maxLen];
    	int[] orderSplit2 = new int[maxLen];
    	
    	for(int i=0; i<maxLen; i++){
    		if(i < split1.length){
    			orderSplit1[i] = Integer.parseInt(split1[i]);
    		}
    		if(i < split2.length){
    			orderSplit2[i] = Integer.parseInt(split2[i]);
    		}
    	}
    	
    	//compare
    	for(int i=0; i<maxLen; i++){
    		int comp1 = orderSplit1[i];
    		int comp2 = orderSplit2[i];
    		if(comp1 > comp2){
    			return 1;
    		}
    		if (comp1 < comp2){
    			return -1;
    		}
    	}
    	return 0;
    }
    
    public LocalVariables getLocals() {
        return locals;
    }

}
