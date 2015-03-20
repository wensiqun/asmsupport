package cn.wensiqun.asmsupport.standard.action;

import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.core.definition.variable.ThisVariable;

/**
 * 
 * This和Super关键字操作
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface KeywordAction {
	
	/**
	 * 
	 * 在程序块中获取this关键字. 需要注意的是，和java代码一样，这个变量只能够在非静态方法或者程序块中调用，否则会抛异常。
	 * 
	 * @return {@link ThisVariable}
	 * @see #super_()
	 */
	public ThisVariable this_();
	
	/**
	 * Get global variable of current class according the passed name. 
	 * this method is equivalence to : 
	 * <pre>
	 *     _this().getGlobalVariable(name);
	 * <pre>
	 * @param name
	 * @return {@link GlobalVariable}
	 */
	public GlobalVariable this_(String name);
	
	/**
	 * 获取父类super关键字。 需要注意的是，和java代码一样，这个变量只能够在非静态方法或者程序块中调用，否则会抛异常。
	 * 
	 * @return {@link SuperVariable}
	 * @see #this_()
	 */
	public SuperVariable super_();
}
