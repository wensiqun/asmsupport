package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.definition.variable.ThisVariable;

public interface ThisVariableable {
	/**
	 * get "this". like use this key word in java
	 * @return
	 */
	public ThisVariable getThis();
	
	/**
	 * get "super". like use super key word in java
	 * @return
	 */
	public SuperVariable getSuper();
}
