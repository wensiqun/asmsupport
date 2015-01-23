package cn.wensiqun.asmsupport.core.block.exception;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;

public class TestAll
{
    
    public static void main(String[] args) throws InterruptedException
    {
        TryFinallyBlockGeneratorSample.main(args);
        TryFinallyBlockGenerator.main(args);
        Assert.assertEquals(TesterStatics.EXPECTED.toString(), TesterStatics.ACTUALLY.toString());
        TesterStatics.clear();

        
        TryFinallyWithReturnBlockGeneratorSample.main(args);
        TryFinallyWithReturnBlockGenerator.main(args);
        Assert.assertEquals(TesterStatics.EXPECTED.toString(), TesterStatics.ACTUALLY.toString());
        TesterStatics.clear();
        

        TryCatchBlockGeneratorSample.main(args);
        TryCatchBlockGenerator.main(args);
        Assert.assertEquals(TesterStatics.EXPECTED.toString(), TesterStatics.ACTUALLY.toString());
        TesterStatics.clear();
        

        NestedTryCatchBlockGeneratorSample.main(args);
        NestedTryCatchBlockGenerator.main(args);
        Assert.assertEquals(TesterStatics.EXPECTED.toString(), TesterStatics.ACTUALLY.toString());
        TesterStatics.clear();
        

        TryCatchFinallyBlockGeneratorSample.main(args);
        TryCatchFinallyBlockGenerator.main(args);
        Assert.assertEquals(TesterStatics.EXPECTED.toString(), TesterStatics.ACTUALLY.toString());
        TesterStatics.clear();
        
        
    }
    
    @Test
    public void test() throws InterruptedException
    {
        main(null);
    }
}
