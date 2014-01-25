/**
 * 
 */
package cn.wensiqun.asmsupport;

import cn.wensiqun.asmsupport.clazz.AClass;


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
     * 
     * <p>如果当前操作或者变量被其他操作所引用，那么就需要调用当前的这个操作对象或者变量的asArgument方法.</p>
     * <p>大多数情况下，重写这个方法是为了将当前操作从栈中移除。</p>
     */
    public void asArgument();
    
}
