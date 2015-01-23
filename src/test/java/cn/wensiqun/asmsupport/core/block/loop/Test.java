package cn.wensiqun.asmsupport.core.block.loop;

import org.junit.Assert;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;


public class Test {
	
	public static void main(String[] args) throws InterruptedException
    {
		WhileBlockGeneratorSample.main(args);
    	WhileBlockGenerator.main(args);
       Assert.assertEquals(TesterStatics.EXPECTED.toString(), TesterStatics.ACTUALLY.toString());
       TesterStatics.clear();
       
		DoWhileBlockGeneratorSample.main(args);
    	DoWhileBlockGenerator.main(args);
       Assert.assertEquals(TesterStatics.EXPECTED.toString(), TesterStatics.ACTUALLY.toString());
       TesterStatics.clear();
    }
	
    
    @org.junit.Test
    public void test() throws InterruptedException
    {
        main(null);
    }

}
