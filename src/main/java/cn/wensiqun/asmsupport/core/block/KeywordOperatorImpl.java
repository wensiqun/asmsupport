package cn.wensiqun.asmsupport.core.block;

import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.core.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.standard.operator.KeywordOperator;

public class KeywordOperatorImpl implements KeywordOperator {

    private ProgramBlockInternal executor;
    
    public KeywordOperatorImpl(ProgramBlockInternal executor) {
        this.executor = executor;
    }

    @Override
    public final ThisVariable _this() {
        if(executor.getMethod().isStatic()){
            throw new ASMSupportException("cannot use \"this\" keyword in static block");
        }
        return executor.getMethodOwner().getThisVariable();
    }
    
    @Override
    public final GlobalVariable _this(String name) {
        return _this().getGlobalVariable(name);
    }
    
    @Override
    public final SuperVariable _super() {
        if(executor.getMethod().isStatic()){
            throw new ASMSupportException("cannot use \"super\" keyword in static block");
        }
        return executor.getMethodOwner().getSuperVariable();
    }

}
