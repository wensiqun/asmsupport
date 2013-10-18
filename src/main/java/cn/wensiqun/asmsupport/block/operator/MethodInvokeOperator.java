package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.operators.method.MethodInvoker;

public interface MethodInvokeOperator {
    
    /**
     * <p>invoke a method by other method invoker, for example:</p>
     * 
     * <pre>
     * <B>java code:</B>
     * "String".getClass().toString();
     * </pre>
     * 
     * <pre>
     * <B>asmsupport code: </B>
     * MethodInvoker getClass = invoke(Value.value("String"), "getClass()");
     * invoke(getClass, "toString");
     * <pre>
     *
     * @param objRef
     * @param methodName
     * @param arguments
     * @return
     */
    public MethodInvoker invoke(Parameterized objRef, String methodName, Parameterized... arguments);

    
    /**
     * 
     * @param owner
     * @param methodName
     * @param arguments
     * @return
     */
    public MethodInvoker invokeStatic(AClass owner, String methodName, Parameterized... arguments);
    
    /**
     * 
     * @param owner
     * @param arguments
     * @return
     */
    public MethodInvoker invokeConstructor(AClass owner, Parameterized... arguments);
    
    /**
     * invoke by proxy method
     * @return
     */
    public MethodInvoker invokeOriginalMethod();
    
}
