package cn.wensiqun.asmsupport.block.method.init;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.IBlockOperators;
import cn.wensiqun.asmsupport.block.operator.ThisVariableable;
import cn.wensiqun.asmsupport.operators.method.MethodInvoker;

public interface IInitBody extends IBlockOperators, ThisVariableable{
	
	/**
	 * 
	 * @param arguments
	 * @return
	 */
	public MethodInvoker invokeSuperConstructor(Parameterized... arguments);
}
