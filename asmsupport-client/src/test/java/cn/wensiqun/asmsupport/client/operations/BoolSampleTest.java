package cn.wensiqun.asmsupport.client.operations;

import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.TestConstant;
import cn.wensiqun.asmsupport.client.block.MethodBody;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Var;

public class BoolSampleTest {

    @Test
    public void test() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        DummyClass dummy = new DummyClass().public_().name("cn.wensiqun.asmsupport.client.test.BoolSampleGenerated")
                .setClassOutPutPath(TestConstant.classOutPutPath);

        dummy.newMethod("excepted").public_().static_().return_(String.class)
        .argTypes(boolean.class, boolean.class, boolean.class, boolean.class)
        .argNames("b1", "b2", "b3", "b4")
        .body(new MethodBody() {
			@Override
			public void body(LocVar... args) {
				Var sb = var("sb", StringBuilder.class, new_(StringBuilder.class));
				LocVar b1 = args[0];
				LocVar b2 = args[1];
				LocVar b3 = args[2];
				LocVar b4 = args[3];
				//sb.append(      b1 &&  b2  || b3       |  b4  &&  b2);          
				//sb.call("append", b1.and(b2).or(b3).and(b2));      
				sb.call("append", or(and(b1, b2), b3));
				//                b1  && b2  || b3        | b4   && b2         & b3         ^ b1   && b1
				//sb.call("append", b1.and(b2).or(b3).logicOr(b4).and(b2).logicAnd(b3).logicXor(b1).and(b1));
				return_(sb.call("toString"));
                
			}
        	
        });
        Class<?> clazz = dummy.build();
        Assert.assertEquals(BoolSample.excepted(true, false, false, true), clazz.getMethod("excepted", boolean.class, boolean.class, boolean.class, boolean.class)
        		.invoke(clazz, true, false, false, true));
    }

}
