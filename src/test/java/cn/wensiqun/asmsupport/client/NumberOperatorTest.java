package cn.wensiqun.asmsupport.client;

import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.client.def.var.LocVar;

public class NumberOperatorTest {

    @Test
    public void test() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        DummyClass dummy = new DummyClass().public_().name("cn.wensiqun.asmsupport.client.test.NumberOperatorTestGenerated")
                .setClassOutPutPath("./target/test-generated");
        dummy.newMethod("getResult").public_().static_().return_(int.class)
             .body(new MethodBody() {

                @Override
                public void body(LocVar... args) {
                    return_(add(val(8), val(2)).mul(val(3)).mul(val(4)).div(val(8)).mod(val(2)).sub(val(5)).sub(val(3)).mul(val(4)).div(val(2)));
                }
                 
             });
        Class<?> clazz = dummy.build();
        Assert.assertEquals(8 + 2*3*4/8%2-5-3*4/2, clazz.getMethod("getResult").invoke(clazz));
    }

}
