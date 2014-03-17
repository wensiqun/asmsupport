package cn.wensiqun.asmsupport.block.method.init;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.IBlockOperators;
import cn.wensiqun.asmsupport.block.operator.KeywordVariableable;
import cn.wensiqun.asmsupport.operators.method.MethodInvoker;

public interface IInitBody extends IBlockOperators, KeywordVariableable{
	
	/**
	 * 
	 * @param arguments
	 * @return
	 */
	public MethodInvoker invokeSuperConstructor(Parameterized... arguments);
}
