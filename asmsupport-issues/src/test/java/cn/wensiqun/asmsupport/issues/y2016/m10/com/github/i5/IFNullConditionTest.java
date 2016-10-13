package cn.wensiqun.asmsupport.issues.y2016.m10.com.github.i5;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.DummyMethod;
import cn.wensiqun.asmsupport.client.block.IF;
import cn.wensiqun.asmsupport.client.block.MethodBody;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.issues.IssuesConstant;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class IFNullConditionTest {
    @Test
    public void test() throws Exception {
        DummyClass dummy = new DummyClass(IFNullConditionTest.class.getPackage().getName() + ".IFNullConditionTest_");
        dummy.public_().setClassOutPutPath(IssuesConstant.classOutPutPath);
        DummyMethod dummyMethod = dummy.newMethod("ifNullReturn")
                .public_().static_().return_(int.class);

        dummyMethod.body(new MethodBody() {

            @Override
            public void body(LocVar... args) {
				if_(new IF(eq(null_(Double.class), val(Double.class).call("valueOf", val(2.2D)))) {
					@Override
					public void body() {
						return_(val(0));
					}
				});
				return_(val(1));
            }
        });
        Class<?> clazz = dummy.build();
        Method method = clazz.getMethod("ifNullReturn");
        Integer value = (Integer) method.invoke(clazz);
        Assert.assertEquals(1, value.intValue());
    }
}
