package cn.wensiqun.asmsupport.core.context;

import cn.wensiqun.asmsupport.core.definition.method.AMethod;

/**
 * Created by sqwen on 2016/7/5.
 */
public class MethodContext implements Context {

    private AMethod method;

    public AMethod getMethod() {
        return method;
    }

    public void setMethod(AMethod method) {
        this.method = method;
    }

}
