/**
 * 
 */
package cn.wensiqun.asmsupport.core.definition.variable;

import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

/**
 * 全局变量。这个class只用于方法体内操作变量
 * @author 温斯群(Joe Wen)
 */
public class GlobalVariable extends ExplicitVariable implements Crementable{

    private static Log log = LogFactory.getLog(GlobalVariable.class);
    
    private GlobalVariableMeta globalVariableMeta;
    
    /**如果当前全局变量是静态变量，那么staticOwner表示静态变量的所属Class */
    private AClass staticOwner;
    
    /**如果当前全局变量所属与某一个变量中，那么variableOwner表示当前全局变量所属的变量*/
    private IVariable variableOwner;
    
    /**
     * 
     * 
     * 通过Class获取的全局变量
     * @param owner 变量拥有者
     * @param actuallyOwner 变量实际有用者
     * @param declareClass 变量声明类型
     * @param actuallyClass 变量真实类型
     * @param modifiers 变量的修饰符
     * @param name 变量名
     * 
     */
    public GlobalVariable(AClass owner, AClass actuallyOwner, AClass declareClass,int modifiers,
            String name) {
    	globalVariableMeta = new GlobalVariableMeta(owner, actuallyOwner, declareClass,modifiers, name);
        staticOwner = owner;
    }
    
    /**
     * 
     * @param owner
     * @param gve
     */
    public GlobalVariable(AClass owner, GlobalVariableMeta gve){
        this.globalVariableMeta = gve;
        staticOwner = owner;
    }
    
    /**
     * 通过Variable获取的全局变量
     * @param var 变量
     * @param actuallyOwner 变量实际有用者
     * @param varClass 变量类型
     * @param modifiers 变量修饰符
     * @param name 变量名
     */
    public GlobalVariable(IVariable var, AClass actuallyOwner, AClass declareClass, int modifiers,
            String name) {
    	globalVariableMeta = new GlobalVariableMeta(var.getParamterizedType(), actuallyOwner, declareClass, modifiers, name);
        variableOwner = var;
    }
    
    /**
     * 
     * @param var
     * @param gve
     */
    public GlobalVariable(IVariable var, GlobalVariableMeta gve){
        this.globalVariableMeta = gve;
        variableOwner = var;
    }

    @Override
    public AClass getParamterizedType() {
        return globalVariableMeta.getDeclareClass();
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
    	
    	if(!AClassUtils.visible(block.getMethodOwner(), globalVariableMeta.getOwner(), 
    			globalVariableMeta.getActuallyOwner(), globalVariableMeta.getModifiers()))
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
                    globalVariableMeta.getName(), globalVariableMeta.getDeclareClass().getType());
        }else{
            if(log.isDebugEnabled()){
                log.debug("get field " + globalVariableMeta.getName() + " from variable " + globalVariableMeta.getName() + " and push to stack!");
            }
            variableOwner.loadToStack(block);
            block.getInsnHelper().getField(globalVariableMeta.getOwner().getType(), globalVariableMeta.getName(), globalVariableMeta.getDeclareClass().getType());
        }
    }
    
    /**
     * staticOwner的get方法
     * 获取当前变量的拥有者
     * @return
     */
    public AClass getStaticOwner() {
        return staticOwner;
    }

    /**
     * variableOwner的get方法
     * 获取当前变量的拥有者
     * @return
     */
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
        return getGlobalVariable(globalVariableMeta.getDeclareClass(), name);
    }
    
    @Override
    public String toString() {
        return globalVariableMeta.getName();
    }

}
