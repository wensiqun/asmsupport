package cn.wensiqun.asmsupport.core.definition.value;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.utils.AsmsupportClassLoader;

public class ValueTest
{

    @Test
    public void testGetConvert(){
    	AsmsupportClassLoader classLoader = CachedThreadLocalClassLoader.getInstance();
        Value value = Value.value(classLoader, 1);
        Value newValue = value.getConvert(classLoader.getType(double.class));
        Assert.assertEquals(Type.DOUBLE_TYPE, newValue.getType());
        Assert.assertTrue(newValue.getValue() instanceof Double);
    }
    
    @Test
    public void testNumberCompare()
    {
    	AsmsupportClassLoader classLoader = CachedThreadLocalClassLoader.getInstance();
        Value[] values = new Value[14];
        
        values[0] = Value.value(classLoader, (byte)1);
        values[1] = Value.value(classLoader, (byte)2);
        
        values[2] = Value.value(classLoader, (short)1);
        values[3] = Value.value(classLoader, (short)2);
        
        values[4] = Value.value(classLoader, 1);
        values[5] = Value.value(classLoader, 2);
        
        values[6] = Value.value(classLoader, 1F);
        values[7] = Value.value(classLoader, 2F);
        
        values[8] = Value.value(classLoader, 1L);
        values[9] = Value.value(classLoader, 2L);
        
        values[10] = Value.value(classLoader, 1D);
        values[11] = Value.value(classLoader, 2D);
       
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
