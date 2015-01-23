package cn.wensiqun.asmsupport.core.exception;

import cn.wensiqun.asmsupport.core.utils.memory.Stack;

public class InstructionException extends RuntimeException {

	private int insn;
	private Stack copyOfStack;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2822269556587915571L;

	public InstructionException(String message, int insn, Stack stackCopy) {
		super(message);
		this.insn = insn;
		this.copyOfStack = stackCopy;
	}

	public int getInsn() {
		return insn;
	}

	public Stack getCopyOfStack() {
		return copyOfStack;
	}

}
