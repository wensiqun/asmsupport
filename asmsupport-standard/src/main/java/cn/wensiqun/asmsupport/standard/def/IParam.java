package cn.wensiqun.asmsupport.standard.def;

import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;


/**
 * The interface indicate a operand among operator, such as a variable, a constant, 
 * a method invoke or arithmetic operator and so on.
 * 
 * @author sqwen
 */
public interface IParam {
    
    /**
     * Get the result type of current operand. 
     * 
     * @return
     */
    AClass getResultType();

    /**
     * Get a field from a {@link IParam}
     * 
     * @param name The field name
     * @return T The sub type of {@link IFieldVar}
     */
    IFieldVar field(String name);
}
