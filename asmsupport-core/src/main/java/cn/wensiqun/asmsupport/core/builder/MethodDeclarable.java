package cn.wensiqun.asmsupport.core.builder;

import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

/**
 * Created by sqwen on 2016/5/27.
 */
public interface MethodDeclarable {

    void declareMethod(String name, IClass[] argClasses, IClass returnClass, IClass[] exceptions);

    /**
     * declare an interface method
     *
     * @param name method name
     * @param argTypes method argument types
     * @param returnType method return type, if null than indicate void
     * @param exceptions what exception you want explicit throw.
     */
    void declareMethod(String name, IClass[] argTypes, IClass returnType, IClass[] exceptions, boolean varargs);

}
