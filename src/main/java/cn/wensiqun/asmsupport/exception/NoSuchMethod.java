package cn.wensiqun.asmsupport.exception;

import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.definition.method.meta.AMethodMeta;

public class NoSuchMethod extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public NoSuchMethod(AClass owner, String name, AClass[] argus) {
        super("no such method " + AMethodMeta.getMethodString(name, argus) + " in " + owner);
    }

}
