/**
 * 
 */
package cn.wensiqun.asmsupport.core.definition.variable;

import java.util.List;

import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractVariable implements IVariable {

    @Override
    public final void asArgument() {
        //don't do anything if this class don't extends AbstractExecuteable
    }

    /*
     * 通过名字和类获取全局变量
     * @param aclass
     * @param name
     * @return
     */
    /*protected final GlobalVariable getGlobalVariable(AClass aclass, String name){
        if(this.getParamterizedType() instanceof ArrayClass){
            throw new ASMSupportException("Cannot get global variable from array type variable : " + this);
        }
    	
        List<GlobalVariableMeta> metas = aclass.getGlobalVariableMeta(name);
        if(metas.isEmpty()) {
            throw new ASMSupportException("No such field " + name);
        }
        
        GlobalVariableMeta found = null;
        StringBuilder errorSuffix = new StringBuilder();
        for(GlobalVariableMeta meta : metas) {
            if(found != null) {
                errorSuffix.append(found.getActuallyOwnerType()).append(',')
                           .append(meta.getActuallyOwnerType());
                throw new ASMSupportException("The field '" + name + "' is ambiguous, found it in class [" + errorSuffix + "]");
            }
            found = meta;
        }
        
        if(found == null) {
            throw new ASMSupportException("No such field " + name);
        }
        
        if(ModifierUtils.isStatic(found.getModifiers())){
            return new StaticGlobalVariable(found.getDeclareType(), found);
        } else {
            return new NonStaticGlobalVariable(this, found);
        }
    }*/

    @Override
    public final GlobalVariable field(String name) {
        if(this.getParamterizedType() instanceof ArrayClass){
            throw new ASMSupportException("Cannot get global variable from array type variable : " + this);
        }
        
        List<GlobalVariableMeta> metas = getVariableMeta().getDeclareType().getGlobalVariableMeta(name);
        if(metas.isEmpty()) {
            throw new ASMSupportException("No such field " + name);
        }
        
        GlobalVariableMeta found = null;
        StringBuilder errorSuffix = new StringBuilder();
        for(GlobalVariableMeta meta : metas) {
            if(found != null) {
                errorSuffix.append(found.getActuallyOwnerType()).append(',')
                           .append(meta.getActuallyOwnerType());
                throw new ASMSupportException("The field '" + name + "' is ambiguous, found it in class [" + errorSuffix + "]");
            }
            found = meta;
        }
        
        if(found == null) {
            throw new ASMSupportException("No such field " + name);
        }
        
        if(ModifierUtils.isStatic(found.getModifiers())){
            return new StaticGlobalVariable(found.getActuallyOwnerType(), found);
        } else {
            return new NonStaticGlobalVariable(this, found);
        }
    }

    
}
