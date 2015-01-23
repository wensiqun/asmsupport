package cn.wensiqun.asmsupport.core.exception;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ArrayStoreException extends RuntimeException {

    private static final long serialVersionUID = -5812116933256731752L;

    public ArrayStoreException(ArrayClass acls, AClass valueCls) {
        super("cannot store value " + valueCls + " to array " + acls);
    }

}
