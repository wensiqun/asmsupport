package cn.wensiqun.asmsupport.core.definition.variable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

public class StaticGlobalVariable extends GlobalVariable  {

    private static Log LOG = LogFactory.getLog(StaticGlobalVariable.class);

    /**如果当前全局变量是静态变量，那么staticOwner表示静态变量的所属Class */
    private AClass owner;
    
    /**
     * 
     * @param owner
     * @param gve
     */
    public StaticGlobalVariable(AClass owner, GlobalVariableMeta meta){
        super(meta);
        this.owner = owner;
    }

    public AClass getOwner() {
        return owner;
    }

    @Override
    public AClass getParamterizedType() {
        return meta.getDeclareType();
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        if(!AClassUtils.visible(block.getMethodOwner(), meta.getOwner(), 
                meta.getActuallyOwnerType(), meta.getModifiers()))
        {
            throw new IllegalArgumentException("Cannot access field " +
                    meta.getOwner() + "." + meta.getName()
                    + " from " + block.getMethodOwner());
        }
        
        if(LOG.isDebugEnabled()){
            LOG.debug("get field " + meta.getName() + " from class " + meta.getOwner().getName() + " and push to stack!");
        }
        block.getInsnHelper().getStatic(owner.getType(),
                meta.getName(), meta.getDeclareType().getType());
    }

    @Override
    public boolean availableFor(AbstractOperator operator) {
        return true;
    }

}
