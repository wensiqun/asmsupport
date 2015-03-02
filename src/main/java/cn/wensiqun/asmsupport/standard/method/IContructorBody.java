package cn.wensiqun.asmsupport.standard.method;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.standard.body.LocalVariablesBody;

public interface IContructorBody extends LocalVariablesBody {
	
    /**
     * Call super constructor, corresponding to following java code : 
     * <pre>
     *     super(arg1, arg2...);
     * </pre>
     * @param arguments
     * @return
     */
	MethodInvoker _supercall(Parameterized... arguments);

}
