package cn.wensiqun.asmsupport.core.context;

import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;

/**
 * Created by sqwen on 2016/7/5.
 */
public class MethodExecuteContext implements Context {

    private AMethod method;

    private Instructions instructions;

    public AMethod getMethod() {
        return method;
    }

    public void setMethod(AMethod method) {
        this.method = method;
    }

    public Instructions getInstructions() {
        return instructions;
    }

    public void setInstructions(Instructions instructions) {
        this.instructions = instructions;
    }

}
