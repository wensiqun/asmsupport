package cn.wensiqun.asmsupport.core.definition.value;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

public class ValueTest
{

    @Test
    public void testGetConvert(){
        Value value = Value.value(1);
        Value newValue = value.getConvert(AClass.DOUBLE_ACLASS);
        Assert.assertEquals(Type.DOUBLE_TYPE, newValue.getType());
        Assert.assertTrue(newValue.getValue() instanceof Double);
    }
    
    @Test
    public void testNumberCompare()
    {
        Value[] values = new Value[14];
        
        values[0] = Value.value((byte)1);
        values[1] = Value.value((byte)2);
        
        values[2] = Value.value((short)1);
        values[3] = Value.value((short)2);
        
        values[4] = Value.value(1);
        values[5] = Value.value(2);
        
        values[6] = Value.value(1F);
        values[7] = Value.value(2F);
        
        values[8] = Value.value(1L);
        values[9] = Value.value(2L);
        
        values[10] = Value.value(1D);
        values[11] = Value.value(2D);
       
        for(int i=0; i<11; i++)
        {
            Value vi = values[i];
            for(int j=0; j<11; j++)
            {
                Value vj = values[j];
                    
                if(i%2 == j%2)
                {
                    Assert.assertEquals(0, vi.numberCompare(vj));
                    Assert.assertEquals(0, vj.numberCompare(vi));
                }
                else
                {
                    if(i%2 == 1)
                    {
                        Assert.assertEquals(1, vi.numberCompare(vj));
                        Assert.assertEquals(-1, vj.numberCompare(vi));
                    }
                    else
                    {
                        Assert.assertEquals(-1, vi.numberCompare(vj));
                        Assert.assertEquals(1, vj.numberCompare(vi));
                    }
                }
            }
        }
        
    }

}
