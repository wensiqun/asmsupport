package cn.wensiqun.asmsupport.operators.exception;

import cn.wensiqun.asmsupport.definition.method.AMethod;

public class ReturnException extends RuntimeException{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AMethod m;

	public ReturnException(AMethod m, String msg) {
		super("exception throw when create method " + m.toString() + " "  + msg);
		this.m = m;
	}

	public AMethod getMethod() {
		return m;
	}
	
}
