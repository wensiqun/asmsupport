package jw.asmsupport.operators.exception;

import jw.asmsupport.definition.method.Method;

public class ReturnException extends RuntimeException{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Method m;

	public ReturnException(Method m, String msg) {
		super("exception throw when create method " + m.toString() + " "  + msg);
		this.m = m;
	}

	public Method getMethod() {
		return m;
	}
	
}
