package cn.wensiqun.asmsupport.block.method.clinit;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.IBlockOperators;

public interface IEnumClinitBody extends IBlockOperators{

	/**
	 * 
	 * @param name
	 * @param argus
	 * @return
	 */
    public void newEnum(String name, Parameterized... argus);
}
