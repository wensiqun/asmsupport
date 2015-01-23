package cn.wensiqun.asmsupport.core.block.exception;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;
import example.AbstractExample;

public class TryCatchBlockGeneratorSample extends AbstractExample
{
    private static void runtimeException(){
        throw new RuntimeException();
    }
    private static void exception() throws Exception{
        throw new Exception();
    }
    
    private static void tryCatch_errorBeforePrintInTry()
    {
        try
        {
            exception();
            TesterStatics.expectedPrintln("    try");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatch_errorAfterPrintInTry()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
            exception();
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatch_runtimeExceptionBeforePrintInCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
        }
        catch(Exception e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatch_runtimeExceptionAfterPrintInCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
            runtimeException();
        }
    }
    
    private static void tryCatch_exceptionBeforePrintInTry_runtimeExceptionBeforePrintInCatch()
    {
        try
        {
            exception();
            TesterStatics.expectedPrintln("    try");
        }
        catch(Exception e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatch_exceptionBeforePrintInTry_runtimeExceptionAfterPrintInCatch()
    {
        try
        {
            exception();
            TesterStatics.expectedPrintln("    try");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
            runtimeException();
        }
    }
    
    private static void tryCatch_exceptionAfterPrintInTry_runtimeExceptionBeforePrintInCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
            exception();
        }
        catch(Exception e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatch_exceptionAfterPrintInTry_runtimeExceptionAfterPrintInCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
            exception();
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
            runtimeException();
        }
    }
    
    
    //============================================================
    

    
    private static void tryCatchTwo_runtimeBeforePrintInTry()
    {
        try
        {
            runtimeException();
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_runtimeAfterPrintInTry()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
            runtimeException();
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_exceptionBeforePrintInTry()
    {
        try
        {
            exception();
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_exceptionAfterPrintInTry()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
            exception();
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_runtimeBeforePrintInRuntimeCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_runtimeAfterPrintInRuntimeCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
            runtimeException();
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_runtimeBeforePrintInExceptionCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_runtimeAfterPrintInExceptionCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
            runtimeException();
        }
    }
    
    //{
    
    private static void tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch()
    {
        try
        {
            exception();
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch()
    {
        try
        {
            exception();
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
            runtimeException();
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
            exception();
        }
        catch(RuntimeException e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
            exception();
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
            runtimeException();
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    //}
    
    //{
    
    private static void tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInRuntimeCatch()
    {
        try
        {
            exception();
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInRuntimeCatch()
    {
        try
        {
            exception();
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
            runtimeException();
        }
    }
    
    private static void tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInRuntimeCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");
            exception();
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInRuntimeCatch()
    {
        try
        {
            TesterStatics.expectedPrintln("    try");            exception();
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
            runtimeException();
        }
    }
    
    //}
    
    //{
    
    private static void tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch()
    {
        try
        {
            exception();
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch()
    {
        try
        {
            exception();
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
            runtimeException();
        }
    }
    
    private static void tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch()
    {
        try
        {
            exception();
            TesterStatics.expectedPrintln("    try");
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
            runtimeException();
        }
        catch(Exception e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch()
    {
        try
        {   
            exception();
            TesterStatics.expectedPrintln("    try");            
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
            runtimeException();
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
            runtimeException();
        }
    }
    
    private static void tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch()
    {
        try
        {   
            TesterStatics.expectedPrintln("    try");   
            exception();         
        }
        catch(RuntimeException e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch()
    {
        try
        {   
            TesterStatics.expectedPrintln("    try");   
            exception();         
        }
        catch(RuntimeException e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    runtime exception");
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
            runtimeException();
        }
    }
    
    private static void tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch()
    {
        try
        {   
            TesterStatics.expectedPrintln("    try");   
            exception();         
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
            runtimeException();
        }
        catch(Exception e)
        {
            runtimeException();
            TesterStatics.expectedPrintln("    exception");
        }
    }
    
    private static void tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch()
    {
        try
        {   
            TesterStatics.expectedPrintln("    try");   
            exception();         
        }
        catch(RuntimeException e)
        {
            TesterStatics.expectedPrintln("    runtime exception");
            runtimeException();
        }
        catch(Exception e)
        {
            TesterStatics.expectedPrintln("    exception");
            runtimeException();
        }
    }
    
    //}
    
    
    public static void main(String[] args) throws InterruptedException
    {
        TesterStatics.expectedPrintln("=======tryCatch_errorBeforePrintInTry");
        try{tryCatch_errorBeforePrintInTry();}catch(Exception e){}

        TesterStatics.expectedPrintln("=======tryCatch_errorAfterPrintInTry");
        try{tryCatch_errorAfterPrintInTry();}catch(Exception e){}

        TesterStatics.expectedPrintln("=======tryCatch_runtimeExceptionBeforePrintInCatch");
        try{tryCatch_runtimeExceptionBeforePrintInCatch();}catch(Exception e){}

        TesterStatics.expectedPrintln("=======tryCatch_runtimeExceptionAfterPrintInCatch");
        try{tryCatch_runtimeExceptionAfterPrintInCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatch_exceptionBeforePrintInTry_runtimeExceptionBeforePrintInCatch");
        try{tryCatch_exceptionBeforePrintInTry_runtimeExceptionBeforePrintInCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatch_exceptionBeforePrintInTry_runtimeExceptionAfterPrintInCatch");
        try{tryCatch_exceptionBeforePrintInTry_runtimeExceptionAfterPrintInCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatch_exceptionAfterPrintInTry_runtimeExceptionBeforePrintInCatch");
        try{tryCatch_exceptionAfterPrintInTry_runtimeExceptionBeforePrintInCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatch_exceptionAfterPrintInTry_runtimeExceptionAfterPrintInCatch");
        try{tryCatch_exceptionAfterPrintInTry_runtimeExceptionAfterPrintInCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_runtimeBeforePrintInTry");
        try{tryCatchTwo_runtimeBeforePrintInTry();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_runtimeAfterPrintInTry");
        try{tryCatchTwo_runtimeAfterPrintInTry();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionBeforePrintInTry");
        try{tryCatchTwo_exceptionBeforePrintInTry();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionAfterPrintInTry");
        try{tryCatchTwo_exceptionAfterPrintInTry();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_runtimeBeforePrintInRuntimeCatch");
        try{tryCatchTwo_runtimeBeforePrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_runtimeAfterPrintInRuntimeCatch");
        try{tryCatchTwo_runtimeAfterPrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_runtimeBeforePrintInExceptionCatch");
        try{tryCatchTwo_runtimeBeforePrintInExceptionCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_runtimeAfterPrintInExceptionCatch");
        try{tryCatchTwo_runtimeAfterPrintInExceptionCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch");
        try{tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch");
        try{tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch");
        try{tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch");
        try{tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInRuntimeCatch");
        try{tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInRuntimeCatch");
        try{tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInRuntimeCatch");
        try{tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInRuntimeCatch");
        try{tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch");
        try{tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch");
        try{tryCatchTwo_exceptionBeforePrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch");
        try{tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch");
        try{tryCatchTwo_exceptionBeforePrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch");
        try{tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch");
        try{tryCatchTwo_exceptionAfterPrintInTry_runtimeBeforePrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch");
        try{tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeBeforePrintInRuntimeCatch();}catch(Exception e){}
        
        TesterStatics.expectedPrintln("=======tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch");
        try{tryCatchTwo_exceptionAfterPrintInTry_runtimeAfterPrintInExceptionCatch_runtimeAfterPrintInRuntimeCatch();}catch(Exception e){}
    }
    
}
