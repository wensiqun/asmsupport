package jw.asmsupport.block.operator;

import jw.asmsupport.definition.variable.SuperVariable;
import jw.asmsupport.definition.variable.ThisVariable;

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
