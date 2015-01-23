package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import example.AbstractExample;

public class NestedTryCatchBlockGeneratorSample extends AbstractExample
{
    private static void runtimeException(){
        throw new RuntimeException();
    }
    private static void exception() throws Exception{
        throw new Exception();
    }
    
    private static void fullTryCatch1()
    {
        TesterStatics.expectedPrintln("Root");
        try
        {
            TesterStatics.expectedPrintln("    |-Try");
            try
            {
                TesterStatics.expectedPrintln("        |-Try");
                throw new Exception();
            }
            catch(Exception e)
            {
                TesterStatics.expectedPrintln("        |-Catch");
                throw e;
            }
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    |-Catch");
        }
        TesterStatics.expectedPrintln("End");
    }
    
    private static void fullTryCatch2()
    {
        TesterStatics.expectedPrintln("Root");
        try
        {
            TesterStatics.expectedPrintln("    |-Try");
            try
            {
                TesterStatics.expectedPrintln("        |-Try");
                throw new Exception();
            }
            catch(RuntimeException e)
            {
                TesterStatics.expectedPrintln("        |-Catch(RuntimeException)");
            }
            catch(Exception e)
            {
                TesterStatics.expectedPrintln("        |-Catch(Exception)");
                throw new RuntimeException();
            }
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    |-Catch(RuntimeException)");
            try
            {
                TesterStatics.expectedPrintln("        |-Try");
                throw new Exception();
            }
            catch(Exception e1)
            {
                TesterStatics.expectedPrintln("        |-Catch(Exception)");
            }
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    |-Catch(Exception)");
        }
        TesterStatics.expectedPrintln("End");
    }
    
    
    public static void main(String[] args) throws InterruptedException
    {
        TesterStatics.expectedPrintln("=======fullTryCatch1");
        try{fullTryCatch1();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======fullTryCatch2");
        try{fullTryCatch2();}catch(Exception e){}
    }
    
}
