package com.asmsupport.lesson;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.DummyInterface;
import cn.wensiqun.asmsupport.client.block.ConstructorBody;
import cn.wensiqun.asmsupport.client.block.MethodBody;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by woate on 2016/5/16.
 * 这节课讲解如何实现继承，实现接口
 */
public class Lesson5 {
    static final String LESSON = "Lesson5";
    static final String PACKAGE = "lesson5";
    static final String OUTPUT_PATH = "./target/asmsupport-generated";

    /**
     * 创一个空接口
     */
    @Test
    public void test1() throws Exception {
        DummyInterface di = new DummyInterface().public_().package_(PACKAGE).name("I" + LESSON + "test1").setClassOutPutPath(OUTPUT_PATH);
        di.newMethod("fun1").argTypes(String.class).return_(void.class);
        Class interfaceCls = di.build();
        DummyClass dc = new DummyClass().public_().package_(PACKAGE).name(LESSON + "test1Impl").setClassOutPutPath(OUTPUT_PATH);
        dc.implements_(interfaceCls);
        dc.newConstructor().body(new ConstructorBody() {
            @Override
            public void body(LocVar... args) {
                return_();
            }
        });
        dc.newMethod("fun1").argTypes(String.class).argNames("name").public_().return_(void.class).body(new MethodBody() {
            @Override
            public void body(LocVar... args) {
                FieldVar out = val(System.class).field("out");
                out.call("println", args[0]);
                out.call("println", val("fun1 called"));
            }
        });
        Class implCls = dc.build();
        Object instance = implCls.newInstance();
        Method m = implCls.getMethod("fun1", String.class);
        m.invoke(instance, "this is a test");
    }
}
