package cn.wensiqun.asmsupport.block.method.cinit;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.IBlockOperators;

public interface IEnumCInitBody extends IBlockOperators{

	/**
	 * 
	 * @param name
	 * @param argus
	 * @return
	 */
    public void newEnum(String name, Parameterized... argus);
}
