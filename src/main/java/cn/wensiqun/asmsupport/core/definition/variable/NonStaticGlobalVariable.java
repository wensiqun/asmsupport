package cn.wensiqun.asmsupport.core.definition.variable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

public class NonStaticGlobalVariable extends GlobalVariable {

    private static Log LOG = LogFactory.getLog(NonStaticGlobalVariable.class);
    
    private IVariable owner;
    
    /**
     * 
     * @param var
     * @param gve
     */
    public NonStaticGlobalVariable(IVariable owner, GlobalVariableMeta meta){
        super(meta);
        this.owner = owner;
    }
    
    public IVariable getOwner() {
        return owner;
    }
    
    @Override
    public AClass getParamterizedType() {
        return meta.getDeclareType();
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        if(!AClassUtils.visible(block.getMethodOwner(), meta.getOwner(), 
                meta.getActuallyOwnerType(), meta.getModifiers())){
            throw new IllegalArgumentException("cannot access field " +
                    meta.getOwner() + "." + meta.getName()
                    + " from " + block.getMethodOwner());
        }
        
        if(LOG.isDebugEnabled()){
            LOG.debug("Get field " + meta.getName() + " from variable " + meta.getName() + " and push to stack!");
        }
        owner.loadToStack(block);
        block.getInsnHelper().getField(meta.getOwner().getType(), meta.getName(), meta.getDeclareType().getType());
    }

    @Override
    public boolean availableFor(AbstractOperator operator) {
        return true;
    }

    @Override
    public String toString() {
        return meta.getName();
    }

}
