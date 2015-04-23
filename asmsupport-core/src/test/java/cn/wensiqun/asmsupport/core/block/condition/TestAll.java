package cn.wensiqun.asmsupport.core.block.condition;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;

public class TestAll
{
    
    public static void main(String[] args) throws InterruptedException
    {
    	ConditionBlockGeneratorSample.main(args);
        ConditionBlockGenerator.main(args);
        Assert.assertEquals(TesterStatics.EXPECTED.toString(), TesterStatics.ACTUALLY.toString());
        TesterStatics.clear();
    }
    
    @Test
    public void test() throws InterruptedException
    {
        main(null);
    }
}
