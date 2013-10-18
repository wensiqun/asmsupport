package cn.wensiqun.asmsupport.exception;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.operators.AbstractOperator;

public class UnreachableCode extends ASMSupportException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6866532878000484233L;

	@SuppressWarnings("unused")
	private ProgramBlock block;
	
	@SuppressWarnings("unused")
	private AbstractOperator unreachableOperator;
	
	public UnreachableCode(ProgramBlock block, AbstractOperator unreachableOperator) {
		super();
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}

	public UnreachableCode(String message, Throwable cause, ProgramBlock block, AbstractOperator unreachableOperator) {
		super(message, cause);
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}

	public UnreachableCode(String message, ProgramBlock block, AbstractOperator unreachableOperator) {
		super(message);
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}

	public UnreachableCode(Throwable cause, ProgramBlock block, AbstractOperator unreachableOperator) {
		super(cause);
		this.block = block;
		this.unreachableOperator = unreachableOperator;
	}
	
	

}
