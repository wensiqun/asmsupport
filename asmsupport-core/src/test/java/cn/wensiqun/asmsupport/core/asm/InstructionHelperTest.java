package cn.wensiqun.asmsupport.core.asm;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class InstructionHelperTest
{

    InstructionHelper helper = new InstructionHelper(){};
    
    @Test
    public void testGetReverseCmp()
    {
        Assert.assertEquals(Opcodes.IFEQ, helper.getReverseCmp(Opcodes.IFNE));
        Assert.assertEquals(Opcodes.IFNE, helper.getReverseCmp(Opcodes.IFEQ));
        Assert.assertEquals(Opcodes.IFGE, helper.getReverseCmp(Opcodes.IFLT));
        Assert.assertEquals(Opcodes.IFGT, helper.getReverseCmp(Opcodes.IFLE));
        Assert.assertEquals(Opcodes.IFLE, helper.getReverseCmp(Opcodes.IFGT));
        Assert.assertEquals(Opcodes.IFLT, helper.getReverseCmp(Opcodes.IFGE));
    }

    
}
