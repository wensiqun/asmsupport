package cn.wensiqun.asmsupport.sample.client.helloworld;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.block.MethodBody;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.sample.SampleConstant;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by sqwen on 2016/5/13.
 */
public class HelloWorldMain {

    public static void main(String... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DummyClass dummy = new DummyClass("FirstCase").public_().setClassOutPutPath(SampleConstant.classOutPutPath);
        dummy.newMethod("main").public_().static_().argTypes(String[].class).body(new MethodBody() {
            @Override
            public void body(LocVar... args) {
                val(System.class).field("out").call("println", val("Hello ASMSupport."));
                return_();
            }
        });
        Class<?> FirstCaseClass = dummy.build();
        Method mainMethod = FirstCaseClass.getMethod("main", String[].class);
        mainMethod.invoke(FirstCaseClass, new Object[]{null});
    }
}
