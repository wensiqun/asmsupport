package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.standard.Parameterized;

public class ClientParameterized<P extends InternalParameterized> implements Parameterized {

    protected P target;
    
    public ClientParameterized(P target) {
        this.target = target;
    }

    @Override
    public final AClass getResultType() {
        return target.getResultType();
    }

    protected P getTarget() {
        return target;
    }
}
