package cn.wensiqun.asmsupport.operators.util;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.AbstractOperator;

/**
 * 创建新的操作的监听器
 * @author 
 *
 */
public abstract class NewOperatorListener{
	
	private ProgramBlock executeBlock;
	
	private Class<? extends AbstractOperator> operatorClass;
	
	private Class<?>[] parameterTypes;
	
	private Object[] arguments;
	
	/**
	 * 触发此监听器的条件
	 * @return
	 */
	protected boolean triggerCondition(){
		return false;
	};

	/**
	 * 在创建操作之前的触发的方法
	 */
	protected void beforeNew(){};
	
	/**
	 * 在创建操作之后的触发的方法
	 * @param instance
	 */
	protected void afterNew(AbstractOperator instance){};

	public Class<? extends AbstractOperator> getOperatorClass() {
		return operatorClass;
	}

	public void setOperatorClass(Class<? extends AbstractOperator> operatorClass) {
		this.operatorClass = operatorClass;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}
	
	public ProgramBlock getExecuteBlock() {
		return executeBlock;
	}

	public void setExecuteBlock(ProgramBlock executeBlock) {
		this.executeBlock = executeBlock;
	}
}