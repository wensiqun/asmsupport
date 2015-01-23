package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import example.AbstractExample;

public class TryFinallyBlockGeneratorSample extends AbstractExample
{
    private static void tryFinally()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
        }
    }
    
    private static void nestedTryFinally()
    {
        TesterStatics.expectedPrintln("start");
        try
        {
            TesterStatics.expectedPrintln("    try{");
            try
            {
                TesterStatics.expectedPrintln("        try_inner");
            }
            finally
            {
                TesterStatics.expectedPrintln("        finally_inner");
            }
            TesterStatics.expectedPrintln("    }");
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
        }
        TesterStatics.expectedPrintln("end");
    }
    
    private static void tryFinally_TryError()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
            throw new RuntimeException();
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
        }
    }
    
    private static void nestedTryFinally_InnerTryError()
    {
        try
        {
            
            try
            {
                TesterStatics.expectedPrintln("        try_inner");
                throw new RuntimeException();
            }
            finally
            {
                TesterStatics.expectedPrintln("        finally_inner");
            }
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
        }
    }
    
    private static void nestedTryFinally_InnerFinallyError()
    {
        try
        {
            
            try
            {
                TesterStatics.expectedPrintln("        try_inner");
            }
            finally
            {
                TesterStatics.expectedPrintln("        finally_inner");
                throw new RuntimeException();
            }
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
        }
    }
    
    private static void nestedTryFinally_OutterTryError()
    {
        try
        {
            
            try
            {
                TesterStatics.expectedPrintln("        try_inner");
            }
            finally
            {
                TesterStatics.expectedPrintln("        finally_inner");
            }
            throw new RuntimeException();
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
        }
    }
    
    private static void nestedTryFinally_OutterFinallyError()
    {
        try
        {
            
            try
            {
                TesterStatics.expectedPrintln("        try_inner");
            }
            finally
            {
                TesterStatics.expectedPrintln("        finally_inner");
            }
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
            throw new RuntimeException();
        }
    }
    
    private static void nestedTryFinally_InnerBothError()
    {
        try
        {
            
            try
            {
                TesterStatics.expectedPrintln("        try_inner");
                throw new RuntimeException();
            }
            finally
            {
                TesterStatics.expectedPrintln("        finally_inner");
                throw new RuntimeException();
            }
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
        }
    }
    
    private static void nestedTryFinally_OutterBothError()
    {
        try
        {
            
            try
            {
                TesterStatics.expectedPrintln("        try_inner");
            }
            finally
            {
                TesterStatics.expectedPrintln("        finally_inner");
            }
            throw new RuntimeException();
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
            throw new RuntimeException();
        }
    }
    
    private static void nestedTryFinally_InnerBothOuterFinallyError()
    {
        try
        {
            
            try
            {
                TesterStatics.expectedPrintln("        try_inner");
                throw new RuntimeException();
            }
            finally
            {
                TesterStatics.expectedPrintln("        finally_inner");
                throw new RuntimeException();
            }
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
            throw new RuntimeException();
        }
    }
    
    public static void main(String[] args)
    {
        TesterStatics.expectedPrintln("=======tryFinally");
        try{tryFinally();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally");
        try{nestedTryFinally();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======tryFinally_TryError");
        try{tryFinally_TryError();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_InnerTryError");
        try{nestedTryFinally_InnerTryError();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_InnerFinallyError");
        try{nestedTryFinally_InnerFinallyError();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_OutterTryError");
        try{nestedTryFinally_OutterTryError();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_OutterFinallyError");
        try{nestedTryFinally_OutterFinallyError();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_InnerBothError");
        try{nestedTryFinally_InnerBothError();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_OutterBothError");
        try{nestedTryFinally_OutterBothError();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_InnerBothOuterFinallyError");
        try{nestedTryFinally_InnerBothOuterFinallyError();}catch(Exception e){}
    }
    
}
