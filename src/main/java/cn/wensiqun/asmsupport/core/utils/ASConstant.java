package cn.wensiqun.asmsupport.core.utils;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public interface ASConstant {

    public final static String CLINIT = "<clinit>";
    public final static String CLINIT_PROXY = "&clinit&";
    
    public final static String INIT = "<init>";
    public final static String INIT_PROXY = "&init&";
    public final static String SUPER = "super";
    public final static String THIS = "this";
    public final static String METHOD_PROXY_SUFFIX = "@original";
    public final static String CLASS_PROXY_SUFFIX = "";
    public final static String STRING_EMPTY = "";

    public final static int METHOD_CREATE_MODE_ADD = 0;
    public final static int METHOD_CREATE_MODE_MODIFY = 1;
    
    public final static int ASM_VERSION = Opcodes.ASM4;

    public final static String ACCESS_TOKEN_PUBLIC = "public";
    
    public final static String ACCESS_TOKEN_PRIVATE = "private";

    public final static String ACCESS_TOKEN_PROTECTED = "protected";
    
    public final static String ACCESS_TOKEN_STATIC = "static";
    
    public final static String ACCESS_TOKEN_FINAL = "final";
    
    public final static String ACCESS_TOKEN_SYNCHRONIZED = "synchronized";
    
    public final static String ACCESS_TOKEN_VOLATILE = "volatile";
    
    
    public final static String ACCESS_TOKEN_ABSTRACT = "abstract";
    
    
}
