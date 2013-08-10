/**
 * 
 */
package jw.asmsupport;

import jw.asmsupport.clazz.AClass;


/**
 * This interface indicate it can be assign to a variable or method as parameter
 * 
 * @author 温斯群(Joe Wen)
 * 
 */
public interface Parameterized extends PushStackable {
    
    /**
     * 获取当前参数化类型的返回参数
     * @return
     */
    public AClass getParamterizedType();
    
    /**
     * 判断当前操作或者变量是否被其他操作引用
     */
    public void asArgument();
    
}
