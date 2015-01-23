/**
 * 
 */
package cn.wensiqun.asmsupport.core.definition.variable;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractVariable implements IVariable {

    @Override
    public final void asArgument() {
        //don't do anything if this class don't extends AbstractExecuteable
    }

    /**
     * 通过名字和类获取全局变量
     * @param aclass
     * @param name
     * @return
     */
    protected final GlobalVariable getGlobalVariable(AClass aclass, String name){
        if(this.getParamterizedType() instanceof ArrayClass){
            throw new ASMSupportException("cannot get global variable from array type variable : " + this);
        }
    	
        GlobalVariableMeta ve = aclass.getGlobalVariableMeta(name);
        if(ve == null){
        	throw new IllegalArgumentException("dosn't exist or cannot access \"" + name + "\" property of class " + aclass);
        }
        //return new GlobalVariable(this, ve.getDeclareClass(), ve.getModifiers(), ve.getName());
        return new GlobalVariable(this, ve);
    }

}
