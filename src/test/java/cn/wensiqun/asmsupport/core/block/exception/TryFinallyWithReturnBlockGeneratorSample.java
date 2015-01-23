package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import example.AbstractExample;

public class TryFinallyWithReturnBlockGeneratorSample extends AbstractExample
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
    
    private static void tryFinally_TryReturn()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
            return;
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
        }
    }
    
    private static void nestedTryFinally_InnerTryReturn()
    {
        try
        {
            
            try
            {
                TesterStatics.expectedPrintln("        try_inner");
                return;
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
    
    private static void nestedTryFinally_InnerFinallyReturn()
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
                return;
            }
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
        }
    }
    
    private static void nestedTryFinally_OutterTryReturn()
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
            return;
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
        }
    }
    
    private static void nestedTryFinally_OutterFinallyReturn()
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
            return;
        }
    }
    
    private static void nestedTryFinally_InnerBothReturn()
    {
        try
        {
            
            try
            {
                TesterStatics.expectedPrintln("        try_inner");
                return;
            }
            finally
            {
                TesterStatics.expectedPrintln("        finally_inner");
                return;
            }
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
        }
    }
    
    private static void nestedTryFinally_OutterBothReturn()
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
            return;
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
            return;
        }
    }
    
    private static void nestedTryFinally_InnerBothOuterFinallyReturn()
    {
        try
        {
            
            try
            {
                TesterStatics.expectedPrintln("        try_inner");
                return;
            }
            finally
            {
                TesterStatics.expectedPrintln("        finally_inner");
                return;
            }
        }
        finally
        {
            TesterStatics.expectedPrintln("    finally");
            return;
        }
    }
    
    public static void main(String[] args)
    {
        TesterStatics.expectedPrintln("=======tryFinally");
        try{tryFinally();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally");
        try{nestedTryFinally();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======tryFinally_TryReturn");
        try{tryFinally_TryReturn();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_InnerTryReturn");
        try{nestedTryFinally_InnerTryReturn();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_InnerFinallyReturn");
        try{nestedTryFinally_InnerFinallyReturn();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_OutterTryReturn");
        try{nestedTryFinally_OutterTryReturn();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_OutterFinallyReturn");
        try{nestedTryFinally_OutterFinallyReturn();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_InnerBothReturn");
        try{nestedTryFinally_InnerBothReturn();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_OutterBothReturn");
        try{nestedTryFinally_OutterBothReturn();}catch(Exception e){}
        TesterStatics.expectedPrintln("=======nestedTryFinally_InnerBothOuterFinallyReturn");
        try{nestedTryFinally_InnerBothOuterFinallyReturn();}catch(Exception e){}
    }
    
}
