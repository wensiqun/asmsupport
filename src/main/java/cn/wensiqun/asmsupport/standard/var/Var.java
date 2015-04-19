package cn.wensiqun.asmsupport.standard.var;

import cn.wensiqun.asmsupport.standard.Parameterized;
import cn.wensiqun.asmsupport.standard.clazz.AClass;

public interface Var extends Parameterized {

	/**
	 * Get variable name
	 * 
	 * @return
	 */
	String getName();

	/**
	 * The method is same to call {@link #getResultType()}
	 * 
	 * @return AClass
	 */
	AClass getFormerType();
	
	/**
	 * Get variable modifier
	 * 
	 * @return
	 */
	int getModifiers();
}
