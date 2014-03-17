package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.definition.variable.ThisVariable;

/**
 * 
 * This和Super关键字操作
 * 
 * @author wensiqun(at)gmail
 *
 */
public interface KeywordVariableable {
	
	/**
	 * 
	 * 在程序块中获取this关键字. 需要注意的是，和java代码一样，这个变量只能够在非静态方法或者程序块中调用，否则会抛异常。
	 * 
	 * @return {@link ThisVariable}
	 * @see #getSuper()
	 */
	public ThisVariable getThis();
	
	
	/**
	 * 获取父类super关键字。 需要注意的是，和java代码一样，这个变量只能够在非静态方法或者程序块中调用，否则会抛异常。
	 * 
	 * @return {@link SuperVariable}
	 * @see #getThis()
	 */
	public SuperVariable getSuper();
}
