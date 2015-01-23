package cn.wensiqun.asmsupport.core.exception;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;

public class UnreachableCodeException extends ASMSupportException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6866532878000484233L;

	@SuppressWarnings("unused")
	private ProgramBlockInternal block;
	
	@SuppressWarnings("unused")
	private AbstractOperator unreachableOperator;
	
	public UnreachableCodeException(ProgramBlockInternal block, AbstractOperator unreachableOperator) {
		super();
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}

	public UnreachableCodeException(String message, Throwable cause, ProgramBlockInternal block, AbstractOperator unreachableOperator) {
		super(message, cause);
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}

	public UnreachableCodeException(String message, ProgramBlockInternal block, AbstractOperator unreachableOperator) {
		super(message);
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}

	public UnreachableCodeException(Throwable cause, ProgramBlockInternal block, AbstractOperator unreachableOperator) {
		super(cause);
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}
	
	

}
