package cn.wensiqun.asmsupport.core.block.loop;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;


public class DoWhileBlockGeneratorSample {

	  private static void test()
	  {
	    int intVar1 = 10;
	    do
	      TesterStatics.expectedPrintln(String.valueOf(intVar1));
	    while (intVar1-- > 0);
	    
	    int intVar2 = 10;
	    do {
	      TesterStatics.expectedPrintln(String.valueOf(intVar2));
	    } while (--intVar2 > 0);
		    
	    
	    byte byteVar = 10;
	    do
	        TesterStatics.expectedPrintln(String.valueOf(byteVar));
	    while (byteVar-- > 0);
		    
	    double doubleVar = 10.0D;
	    do
	      TesterStatics.expectedPrintln(String.valueOf(doubleVar));
	    while (--doubleVar > 0.0D);
	    
	    Short shortObj = 10;
	    do
	        TesterStatics.expectedPrintln(String.valueOf(shortObj));
	    while (shortObj-- > 0);
		    
	    Long longObj = 10L;
	    do
	      TesterStatics.expectedPrintln(String.valueOf(longObj));
	    while (--longObj > 0.0D);
	    
	  }

	  public static void main(String[] args)
	  {
	    test();
	  }
	
	
}
