package cn.wensiqun.asmsupport.core.utils;

import org.junit.Ignore;

import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.AClassFactory;

@Ignore
public class TesterStatics
{
    public static final boolean printable = true;
    
    public static final StringBuilder EXPECTED = new StringBuilder();
    
    public static final StringBuilder ACTUALLY = new StringBuilder();
    
    public static final AClass ATesterStatics = AClassFactory.getType(TesterStatics.class);
    
    /*public static final GlobalVariable GV_EXPECTED = ATesterStatics.field("EXPECTED");
    
    public static final GlobalVariable GV_ACTUALLY = ATesterStatics.field("ACTUALLY");*/
    
    
    public static void expectedPrintln(String str)
    {
        EXPECTED.append(str).append("|");
        if(printable)
        	System.out.println(str + "|");
    }

    public static void actuallyPrintln(String str)
    {
        ACTUALLY.append(str).append("|");
        if(printable)
        	System.out.println(str + "|");
    }
    
    public static void clear()
    {
        EXPECTED.delete(0, EXPECTED.length());
        ACTUALLY.delete(0, ACTUALLY.length());
    }
}
