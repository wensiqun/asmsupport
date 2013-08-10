package jw.asmsupport.utils;

public interface ASConstant {

    public final static String CLINIT = "<clinit>";
    public final static String CLINIT_PROXY = "&clinit&";
    
    public final static String INIT = "<init>";
    public final static String INIT_PROXY = "&init&";
    public final static String SUPER = "super";
    public final static String THIS = "this";
    public final static String METHOD_PROXY_SUFFIX = "@ByProxy";
    public final static String CLASS_PROXY_SUFFIX = "";//"ByProxy" + System.currentTimeMillis();
    public final static String STRING_EMPTY = "";

    public final static int METHOD_CREATE_MODE_ADD = 0;
    public final static int METHOD_CREATE_MODE_MODIFY = 1;
}
