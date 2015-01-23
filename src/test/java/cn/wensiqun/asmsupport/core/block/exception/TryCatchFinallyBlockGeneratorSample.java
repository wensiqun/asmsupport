package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import example.AbstractExample;

public class TryCatchFinallyBlockGeneratorSample extends AbstractExample
{
    private static void runtimeException(){
        throw new RuntimeException();
    }
    private static void exception() throws Exception{
        throw new Exception();
    }
    
    private static void tryCatchFinally_tryDirectException(){
    	TesterStatics.expectedPrintln("Root");
    	try
    	{
        	TesterStatics.expectedPrintln("    |-Try");
        	throw new Exception();
    	}
    	catch(Exception e)
    	{
        	TesterStatics.expectedPrintln("    |-Catch");
    	}
    	finally
    	{
        	TesterStatics.expectedPrintln("    |-Finally");
    	}
    }
    
    private static void tryCatchFinally_runtimeInTryNoSuitableCatch(){
    	TesterStatics.expectedPrintln("Root");
    	try
    	{
        	TesterStatics.expectedPrintln("    |-Try");
        	throw new RuntimeException();
    	}
    	catch(NullPointerException e)
    	{
        	TesterStatics.expectedPrintln("    |-Catch");
    	}
    	finally
    	{
        	TesterStatics.expectedPrintln("    |-Finally");
    	}
    }
    
    private static void tryCatchFinally_nestedTryCatchInFinally()
    {
    	TesterStatics.expectedPrintln("Root");
    	try
    	{
        	TesterStatics.expectedPrintln("    |-Try");
        	throw new RuntimeException();
    	}
    	catch(NullPointerException e)
    	{
        	TesterStatics.expectedPrintln("    |-Catch");
    	}
    	finally
    	{
        	TesterStatics.expectedPrintln("    |-Finally");
        	try
        	{
            	TesterStatics.expectedPrintln("        |-Try");
            	exception();
        	}
        	catch(Exception e)
        	{
            	TesterStatics.expectedPrintln("        |-Catch");
        	}
    	}
    }
    
    private static void tryCatchFinally_tryMethodException(){
    	TesterStatics.expectedPrintln("Root");
    	try
    	{
        	TesterStatics.expectedPrintln("    |-Try");
        	runtimeException();
    	}
    	catch(Exception e)
    	{
        	TesterStatics.expectedPrintln("    |-Catch");
    	}
    	finally
    	{
        	TesterStatics.expectedPrintln("    |-Finally");
    	}
    }
    
    private static void tryCatchFinally_catchDirectException(){
    	TesterStatics.expectedPrintln("Root");
    	try
    	{
        	TesterStatics.expectedPrintln("    |-Try");
        	throw new Exception();
    	}
    	catch(Exception e)
    	{
        	TesterStatics.expectedPrintln("    |-Catch");
        	try {
            	TesterStatics.expectedPrintln("        |-Try");
				throw new Exception();
			} catch (Exception e1) {
            	TesterStatics.expectedPrintln("        |-Catch");
            	try
            	{
                	TesterStatics.expectedPrintln("            |-Try");
                	exception();
            	}
            	catch(RuntimeException e2)
        		{
                	TesterStatics.expectedPrintln("            |-Catch(RuntimeException)");
        		}
        		catch(Exception e2)
        		{
                	TesterStatics.expectedPrintln("            |-Catch(Exception)");
                	throw new RuntimeException();
        		}
			}
    	}
    	finally
    	{
        	TesterStatics.expectedPrintln("    |-Finally");
    		try
    		{
            	TesterStatics.expectedPrintln("        |-Try");
            	throw new RuntimeException();
    		}
    		catch(RuntimeException e)
    		{
            	TesterStatics.expectedPrintln("        |-Catch(RuntimeException)");
    		}
    		catch(Exception e)
    		{
            	TesterStatics.expectedPrintln("        |-Catch(Exception)");
    		}
    	}
    }
    
    private static void complexTryCatchFinally()
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
                return;
        	}
        	catch(Exception e)
        	{
                TesterStatics.expectedPrintln("        |-Catch(Exception)");
                runtimeException();
        	}
        	finally
        	{
                TesterStatics.expectedPrintln("        |-Finally");
        	}
    	}
    	catch(RuntimeException e)
    	{
            TesterStatics.expectedPrintln("    |-Catch(RuntimeException)");
    		try
        	{
                TesterStatics.expectedPrintln("        |-Try");
        	}
        	catch(RuntimeException e1)
        	{
                TesterStatics.expectedPrintln("        |-Catch(RuntimeException)");
        	}
        	catch(Exception e1)
        	{
                TesterStatics.expectedPrintln("        |-Catch(Exception)");
        		
        	}
        	finally
        	{
                TesterStatics.expectedPrintln("        |-Finally");
        	}
    	}
    	catch(Exception e)
    	{
            TesterStatics.expectedPrintln("    |-Catch(Exception)");
    	}
    	finally
    	{
            TesterStatics.expectedPrintln("    |-Finally");
    		try
    		{
                TesterStatics.expectedPrintln("        |-Try");
    			try
    			{
                    TesterStatics.expectedPrintln("            |-Try");
    			}
                catch(RuntimeException e)
    			{
                    TesterStatics.expectedPrintln("            |-Catch(RuntimeException)");
    			}
    			throw new Exception();
    		}
    		catch(Exception e)
    		{
                TesterStatics.expectedPrintln("        |-Exception(Exception)");
    		}
            TesterStatics.expectedPrintln("    ====");
    		try
            {
                TesterStatics.expectedPrintln("        |-Try");
                try
                {
                    TesterStatics.expectedPrintln("            |-Try");
                    throw new RuntimeException();
                }
                catch(RuntimeException e)
                {
                    TesterStatics.expectedPrintln("            |-Catch(RuntimeException)");
                    throw e;
                }
                catch(Exception e)
                {
                    TesterStatics.expectedPrintln("            |-Catch(Exception)");
                }
            }
    		catch(RuntimeException e)
    		{
                TesterStatics.expectedPrintln("        |-Catch(RuntimeException)");
                return;
    		}
            catch(Exception e)
            {
                TesterStatics.expectedPrintln("        |-Catch(Exception)");
                try
                {
                    TesterStatics.expectedPrintln("            |-Try");
                }
                catch(RuntimeException e1)
                {
                    TesterStatics.expectedPrintln("            |-Catch(RuntimeException)");
                }
                catch(Exception e1)
                {
                    TesterStatics.expectedPrintln("            |-Catch(Exception)");
                }
                finally
                {
                    TesterStatics.expectedPrintln("            |-Finally");
                }
            }
    		finally
    		{
                TesterStatics.expectedPrintln("        |-Finally");
    		}
    	}
    }
    
    
    public static void main(String[] args) throws InterruptedException
    {
        TesterStatics.expectedPrintln("=======tryCatchFinally_tryDirectException");
        try{tryCatchFinally_tryDirectException();}catch(Exception e){}

        TesterStatics.expectedPrintln("=======tryCatchFinally_runtimeInTryNoSuitableCatch");
        try{tryCatchFinally_runtimeInTryNoSuitableCatch();}catch(Exception e){}

        TesterStatics.expectedPrintln("=======tryCatchFinally_nestedTryCatchInFinally");
        try{tryCatchFinally_nestedTryCatchInFinally();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchFinally_tryMethodException");
        try{tryCatchFinally_tryMethodException();}catch(Exception e){}

        TesterStatics.expectedPrintln("=======tryCatchFinally_catchDirectException");
        try{tryCatchFinally_catchDirectException();}catch(Exception e){}

        TesterStatics.expectedPrintln("=======complexTryCatchFinally");
        try{complexTryCatchFinally();}catch(Exception e){}
    }
    
}
