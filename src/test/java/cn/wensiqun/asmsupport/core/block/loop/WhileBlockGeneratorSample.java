package cn.wensiqun.asmsupport.core.block.loop;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;


public class WhileBlockGeneratorSample {

	  private static void test()
	  {
	    int intVar1 = 10;
	    while (intVar1-- > 0)
	      TesterStatics.expectedPrintln(String.valueOf(intVar1));
	    
	    int intVar2 = 10;
	    while (--intVar2 > 0)
	      TesterStatics.expectedPrintln(String.valueOf(intVar2));
	    
	    byte byteVar = 10;
	    while (byteVar-- > 0)
	      TesterStatics.expectedPrintln(String.valueOf(byteVar));
	    
	    double doubleVar = 10.0D;
	    while (--doubleVar > 0.0D)
	      TesterStatics.expectedPrintln(String.valueOf(doubleVar));
	    
	    Short shortObj = 10;
	    while (shortObj-- > 0)
	      TesterStatics.expectedPrintln(String.valueOf(shortObj));
	    
	    Long longObj = 10L;
	    while (--longObj > 0.0D)
	      TesterStatics.expectedPrintln(String.valueOf(longObj));
	    
	  }

	  public static void main(String[] args)
	  {
	    test();
	  }
	
	
}
