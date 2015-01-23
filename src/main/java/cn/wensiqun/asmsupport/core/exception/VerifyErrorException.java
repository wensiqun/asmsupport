package cn.wensiqun.asmsupport.core.exception;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class VerifyErrorException extends RuntimeException {

    private static final long serialVersionUID = 498375918516850394L;

    public VerifyErrorException(String str) {
        super(str);
    }
}