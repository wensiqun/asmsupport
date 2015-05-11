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

/**
 * Represent a scope component, it's a tree structure, we can simulate
 * a local variable scope like following diagram:
 * 
 * <pre>
 *              ,------------------.                            
 *              |Parent_Scope      |                            
 *              |------------------|                            
 *              |generation : 0    |                            
 *              |componentOrder : 1|                            
 *              `------------------'                            
 *                    /      \                                  
 * ,--------------------.  ,--------------------.               
 * |Sub_Scope_1         |  |Sub_Scope_2         |               
 * |--------------------|  |--------------------|               
 * |generation : 1      |  |generation : 1      |               
 * |componentOrder : 1.1|  |componentOrder : 1.2|               
 * `--------------------'  `--------------------'               
 *                              /           \      
 *           ,----------------------.   ,----------------------.
 *           |Sub_Scope_2_1         |   |Sub_Scope_2_2         |
 *           |----------------------|   |----------------------|
 *           |generation : 2        |   |generation : 2        |
 *           |componentOrder : 1.2.1|   |componentOrder : 1.2.2|
 *           `----------------------'   `----------------------'
 * <pre> 
 * 
 * @author wensiqun at 163.com(Joe Wen)
 */
public abstract class ScopeComponent {

    /** The local variables */
    protected LocalVariables locals;

    /** indicate the generation see diagram in description of {@link ScopeComponent} */
    protected int generation;
    
    /**
     * The order number of current component in parent scope, the format is 'a.b.c'.
     * see diagram in description of {@link ScopeComponent}
     */
    protected String componentOrder;

    private Scope parent;

    public ScopeComponent(LocalVariables locals) {
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
        if(parent == null){
        	this.componentOrder = "1";
    	}else{
    		this.componentOrder = parent.componentOrder + "." + (parent.getComponents().size() + 1);
    	}
    }
    
    /**
     * The compare rule is : 
     * <ul>
     * <li>compare each generation order, for example 1.1.2 > 1.1.1, 1.1 > 1</li>
     * <li>order1 great than order2 return 1;</li>
     * <li>order1 less than order2 return -1;</li>
     * <li>order1 equal to order2 return 0;</li>
     * </ul>
     * 
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
    
    /**
     * Get @{link LocalVariables}
     */
    public LocalVariables getLocals() {
        return locals;
    }

}
