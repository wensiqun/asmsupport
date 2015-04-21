package cn.wensiqun.asmsupport.client.operations;

import cn.wensiqun.asmsupport.client.Param;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.core.operator.Operators;
import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;

public class NumberOperator implements Param{

    private KernelParameterized target; 
    
    private ArrayStack<Operators> symbolStack = new ArrayStack<Operators>();
    private ArrayStack<Param> operandStack = new ArrayStack<Param>();
    
    public NumberOperator(Operators symbol, Param... operands) {
        symbolStack.push(symbol);
        for(Param param : operands) {
            operandStack.push(param);
        }
    }
    
    @Override
    public AClass getResultType() {
        return getTarget().getResultType();
    }

    @Override
    public IFieldVar field(String name) {
        return getTarget().field(name);
    }

    @Override
    public KernelParameterized getTarget() {
        return null;
    }
    
    /*public NumberOperator add(Param para) {
        Operators top = symbolStack.peek();
        if(Operators.ADD.getPriority() > top.getPriority()) {
            
        }
    }
    
    public NumberOperator mul(Param para) {
        
    }*/

}
