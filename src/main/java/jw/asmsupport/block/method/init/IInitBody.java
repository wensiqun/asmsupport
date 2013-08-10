package jw.asmsupport.block.method.init;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.IBlockOperators;
import jw.asmsupport.block.operator.ThisVariableable;
import jw.asmsupport.operators.method.MethodInvoker;

public interface IInitBody extends IBlockOperators, ThisVariableable{
	
	/**
	 * 
	 * @param arguments
	 * @return
	 */
	public MethodInvoker invokeSuperConstructor(Parameterized... arguments);
}
