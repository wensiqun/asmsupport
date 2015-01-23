package cn.wensiqun.asmsupport.generic.method;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.generic.body.LocalVariablesBody;

public interface IContructorBody extends LocalVariablesBody {
	
	MethodInvoker invokeSuperConstructor(Parameterized... arguments);

}
