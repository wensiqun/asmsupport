package jw.asmsupport.exception;

import jw.asmsupport.clazz.AClass;
import jw.asmsupport.entity.MethodEntity;

public class NoSuchMethod extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public NoSuchMethod(AClass owner, String name, AClass[] argus) {
        super("no such method " + MethodEntity.getMethodString(name, argus) + " in " + owner);
    }

}
