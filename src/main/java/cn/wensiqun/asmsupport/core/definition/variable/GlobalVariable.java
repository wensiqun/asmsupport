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
package cn.wensiqun.asmsupport.core.definition.variable;

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;

/**
 * 全局变量。这个class只用于方法体内操作变量
 * @author 温斯群(Joe Wen)
 */
public abstract class GlobalVariable extends ExplicitVariable implements Crementable{

    protected GlobalVariableMeta meta;

    public GlobalVariable(GlobalVariableMeta meta) {
        this.meta = meta;
    }
    
    @Override
    public final GlobalVariableMeta getVariableMeta(){
        return meta;
    }
    
   /* private static final Log LOG = LogFactory.getLog(GlobalVariable.class);
    
    private GlobalVariableMeta globalVariableMeta;
    
    private AClass staticOwner;
    
    private IVariable variableOwner;
    

    public GlobalVariable(AClass owner, AClass actuallyOwner, AClass declareClass,int modifiers,
            String name) {
    	globalVariableMeta = new GlobalVariableMeta(owner, actuallyOwner, declareClass,modifiers, name);
        staticOwner = owner;
    }
    

    public GlobalVariable(AClass owner, GlobalVariableMeta gve){
        this.globalVariableMeta = gve;
        staticOwner = owner;
    }
    

    public GlobalVariable(IVariable var, AClass actuallyOwner, AClass declareClass, int modifiers,
            String name) {
    	globalVariableMeta = new GlobalVariableMeta(var.getParamterizedType(), actuallyOwner, declareClass, modifiers, name);
        variableOwner = var;
    }
    

    public GlobalVariable(IVariable var, GlobalVariableMeta gve){
        this.globalVariableMeta = gve;
        variableOwner = var;
    }

    @Override
    public AClass getParamterizedType() {
        return globalVariableMeta.getDeclareType();
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
    	
    	if(!AClassUtils.visible(block.getMethodOwner(), globalVariableMeta.getOwner(), 
    			globalVariableMeta.getActuallyOwnerType(), globalVariableMeta.getModifiers()))
    	{
    		throw new IllegalArgumentException("cannot access field " +
    				globalVariableMeta.getOwner() + "." + globalVariableMeta.getName()
    				+ " from " + block.getMethodOwner());
    	}
    	
        //如果是静态
        if(Modifier.isStatic(globalVariableMeta.getModifiers())){
            if(log.isDebugEnabled()){
                log.debug("get field " + globalVariableMeta.getName() + " from class " + globalVariableMeta.getOwner().getName() + " and push to stack!");
            }
            block.getInsnHelper().getStatic(staticOwner.getType(),
                    globalVariableMeta.getName(), globalVariableMeta.getDeclareType().getType());
        }else{
            if(log.isDebugEnabled()){
                log.debug("Get field " + globalVariableMeta.getName() + " from variable " + globalVariableMeta.getName() + " and push to stack!");
            }
            variableOwner.loadToStack(block);
            block.getInsnHelper().getField(globalVariableMeta.getOwner().getType(), globalVariableMeta.getName(), globalVariableMeta.getDeclareType().getType());
        }
    }
    

    public AClass getStaticOwner() {
        return staticOwner;
    }


    public IVariable getVariableOwner() {
        return variableOwner;
    }

    @Override
    public boolean availableFor(AbstractOperator operator) {
        return true;
    }

    public GlobalVariableMeta getGlobalVariableMeta(){
        return globalVariableMeta;
    }
    
    @Override
    public VariableMeta getVariableMeta() {
        return globalVariableMeta;
    }

    @Override
    public GlobalVariable getGlobalVariable(String name) {
        return getGlobalVariable(globalVariableMeta.getDeclareType(), name);
    }
    
    @Override
    public String toString() {
        return globalVariableMeta.getName();
    }*/

}
