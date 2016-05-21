package com.asmsupport.lesson;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.DummyEnum;
import cn.wensiqun.asmsupport.client.block.*;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by woate on 2016/5/16.
 * 这节课讲解如何创建逻辑
 */
public class Lesson4 {
    static final String LESSON = "Lesson4";
    static final String PACKAGE = "lesson4";
    static final String OUTPUT_PATH = "./target/asmsupport-generated";
    /**
     * 创建一个判断输入字符串是否为test,如果为test，输出内容和1，2，3
     */
    @Test
    public void test1(){
        DummyClass dc = new DummyClass().package_(PACKAGE).name(LESSON + "test1").setJavaVersion(Opcodes.V1_7).setClassOutPutPath(OUTPUT_PATH);
        dc.newConstructor().body(new ConstructorBody() {
            @Override
            public void body(LocVar... args) {
                return_();
            }
        });
        dc.newMethod("fun").argTypes(String.class).argNames("name").return_(void.class).body(new MethodBody() {
            @Override
            public void body(final LocVar... args) {
                if_(new IF(call(args[0], "equals", val("this"))) {
                    @Override
                    public void body() {
                        FieldVar out = val(System.class).field("out");
                        out.call("println", args[0]);
                        out.call("println",val("1"));
                        out.call("println", val("2"));
                        out.call("println", val("3"));
                    }
                });
            }
        });
        Class cls = dc.build();
    }

    /**
     * 创建一个判断输入字符串是否为test,如果为test，输出内容和1，2，3，如果不是，则输出错误
     */
    @Test
    public void test2(){
        DummyClass dc = new DummyClass().package_(PACKAGE).name(LESSON + "test2").setJavaVersion(Opcodes.V1_7).setClassOutPutPath(OUTPUT_PATH);
        dc.newConstructor().body(new ConstructorBody() {
            @Override
            public void body(LocVar... args) {
                return_();
            }
        });
        dc.newMethod("fun").argTypes(String.class).argNames("name").return_(void.class).body(new MethodBody() {
            @Override
            public void body(final LocVar... args) {
                if_(new IF(call(args[0], "equals", val("test"))) {
                    @Override
                    public void body() {
                        FieldVar out = val(System.class).field("out");
                        out.call("println", args[0]);
                        out.call("println",val("1"));
                        out.call("println", val("2"));
                        out.call("println", val("3"));
                    }
                }).else_(new Else() {
                    @Override
                    public void body() {
                        FieldVar out = val(System.class).field("out");
                        out.call("println", val("error"));
                    }
                });
            }
        });
        Class cls = dc.build();
    }


    /**
     * 创建一个判断输入字符串是否为test,如果为test，输出内容和1，2，3，；如果是demo，直接结束函数，如果不是，则输出错误
     */
    @Test
    public void test3(){
        DummyClass dc = new DummyClass().package_(PACKAGE).public_().name(LESSON + "test3").setJavaVersion(Opcodes.V1_7).setClassOutPutPath(OUTPUT_PATH);
        dc.newConstructor().public_().body(new ConstructorBody() {
            @Override
            public void body(LocVar... args) {
                return_();
            }
        });
        dc.newMethod("fun").public_().argTypes(String.class).argNames("name").return_(void.class).body(new MethodBody() {
            @Override
            public void body(final LocVar... args) {
                if_(new IF(call(args[0], "equals", val("test"))) {
                    @Override
                    public void body() {
                        FieldVar out = val(System.class).field("out");
                        out.call("println", args[0]);
                        out.call("println",val("1"));
                        out.call("println", val("2"));
                        out.call("println", val("3"));
                        return_();
                    }
                }).elseif(new ElseIF(call(args[0], "equals", val("demo"))) {
                    @Override
                    public void body() {
                        return_();
                    }
                }).else_(new Else() {
                    @Override
                    public void body() {
                        FieldVar out = val(System.class).field("out");
                        out.call("println", val("error"));
                        return_();
                    }
                });
                return_();
            }
        });
        Class cls = dc.build();
        //测试使用新创建的类
        try {
            Object obj = cls.newInstance();
            Method fun = cls.getMethod("fun", String.class);
            fun.invoke(obj, "test");
            fun.invoke(obj, "demo2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 创建一个循环结构输出1-10
     */
    @Test
    public void test4(){
        DummyClass dc = new DummyClass().package_(PACKAGE).name(LESSON + "test4").setJavaVersion(Opcodes.V1_7).setClassOutPutPath(OUTPUT_PATH);
        dc.newConstructor().body(new ConstructorBody() {
            @Override
            public void body(LocVar... args) {
                return_();
            }
        });
        dc.newMethod("fun").argTypes(String.class).argNames("name").return_(void.class).body(new MethodBody() {
            @Override
            public void body(final LocVar... args) {
                final LocVar i = var("i", int.class, val(0));
                //i<= 10;
                //while(i <= 10){...}
                while_(new While(le(i, val(10))) {
                    @Override
                    public void body() {
                        //System.out.println(i)
                        FieldVar out = val(System.class).field("out");
                        out.call("println", i);
                        //++i;
                        preinc(i);
                    }
                });
            }
        });
        Class cls = dc.build();
    }
}
