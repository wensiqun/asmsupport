package cn.wensiqun.asmsupport.issues.y2020.m11.com.github.i8;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.DummyMethod;
import cn.wensiqun.asmsupport.client.block.MethodBody;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.issues.IssuesConstant;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * BooleanUnboxTest
 * create at 2020/11/18 21:04
 *
 * @author ComFan
 * @email comfanchina@gmail.com
 */
public class BooleanUnBoxTest {
    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DummyClass dummyClass = new DummyClass(BooleanUnBoxTest.class.getPackage().getName() + ".BooleanUnBoxTestASM");
        dummyClass.public_().setClassOutPutPath(IssuesConstant.classOutPutPath);
        DummyMethod dummyMethod = dummyClass.newMethod("booleanUnBox")
                .public_().static_().return_(boolean.class).argTypes(Boolean.class).argNames("boxBoolArg");
        dummyMethod.body(new MethodBody() {
            @Override
            public void body(LocVar... args) {
                return_(args[0]);
            }
        });
        Class<?> clazz = dummyClass.build();
        Object booleanUnBox = clazz.getMethod("booleanUnBox", Boolean.class).invoke(null, Boolean.TRUE);
        Assert.assertTrue((boolean)booleanUnBox);
    }

}
