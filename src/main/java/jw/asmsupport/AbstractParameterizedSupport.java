/**
 * 
 */
package jw.asmsupport;

import jw.asmsupport.definition.value.Value;
import jw.asmsupport.definition.variable.IVariable;
import jw.asmsupport.operators.method.MethodInvoker;

/**
 * 添加对不同类型Parameterized子类的处理支持
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractParameterizedSupport {

    protected Parameterized para;
    
    public AbstractParameterizedSupport(Parameterized para) {
        this.para = para;
    }

    public void process(){
        if (para instanceof Value) {
            process((Value) para);
        } else if (para instanceof IVariable) {
            process((IVariable) para);
        } else if (para instanceof MethodInvoker) {
            process((MethodInvoker) para);
        }
    }
    
    protected abstract void process(Value val);
    
    protected abstract void process(IVariable var);
    
    protected abstract void process(MethodInvoker mcaller);
}
