package jw.asmsupport.block.method.cinit;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.IBlockOperators;

public interface IEnumCInitBody extends IBlockOperators{

	/**
	 * 
	 * @param name
	 * @param argus
	 * @return
	 */
    public void newEnum(String name, Parameterized... argus);
}
