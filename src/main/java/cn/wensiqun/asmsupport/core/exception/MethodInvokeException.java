package cn.wensiqun.asmsupport.core.exception;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class MethodInvokeException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MethodInvokeException(String message) {
        super(message);
    }

	public MethodInvokeException() {
		super();
	}

	public MethodInvokeException(String message, Throwable cause) {
		super(message, cause);
	}

	public MethodInvokeException(Throwable cause) {
		super(cause);
	}

}
