package com.asmsupport.lesson;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.block.ConstructorBody;
import cn.wensiqun.asmsupport.client.block.StaticBlockBody;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import org.junit.Test;

/**
 * Created by woate on 2016/5/9.
 * 这节课讲解如何创建类
 */
//@Ignore
public class Lesson1 {
    static final String LESSON = "Lesson1";
    static final String PACKAGE = "lesson1";
    static final String OUTPUT_PATH = "./target/asmsupport-generated";
    /**
     * 创建一个默认访问域的类Test1test1
     */
    @Test
    public void test1(){
        DummyClass dc = new DummyClass().package_(PACKAGE).name(LESSON + "test1").setClassOutPutPath(OUTPUT_PATH);
        Class cls = dc.build();
    }
    /**
     * 创建一个公开访问域的类Test1test2
     */
    @Test
    public void test2(){
        DummyClass dc = new DummyClass().package_(PACKAGE).name(LESSON + "test2").public_().setClassOutPutPath(OUTPUT_PATH);
        Class cls = dc.build();
    }
    /**
     * 创建一个默认访问域的抽象类Test1test3
     */
    @Test
    public void test3(){
        DummyClass dc = new DummyClass().package_(PACKAGE).name(LESSON + "test3").abstract_().setClassOutPutPath(OUTPUT_PATH);
        Class cls = dc.build();
    }

    /**
     * 创建一个静态初始块打印文本信息Test1test4
     */
    @Test
    public void test4(){
        DummyClass dc = new DummyClass().package_(PACKAGE).name(LESSON + "test4").setClassOutPutPath(OUTPUT_PATH);
        dc.newStaticBlock(new StaticBlockBody() {
            @Override
            public void body() {
                FieldVar out = val(System.class).field("out");
                out.call("println", val("Hello ASMSupport"));
                return_();
            }
        });
        Class cls = dc.build();
    }

    /**
     * 创建一个无参构造函数打印文本信息Test1test4
     */
    @Test
    public void test5(){
        DummyClass dc = new DummyClass().package_(PACKAGE).name(LESSON + "test5").setClassOutPutPath(OUTPUT_PATH);
        dc.newConstructor().body(new ConstructorBody() {
            @Override
            public void body(LocVar... args) {
                FieldVar out = val(System.class).field("out");
                out.call("println", val("Hello ASMSupport"));
                return_();
            }
        });
        Class cls = dc.build();
    }

    /**
     * 创建一个有参构造函数打印文本信息Test1test4
     */
    @Test
    public void test6(){
        DummyClass dc = new DummyClass().package_(PACKAGE).name(LESSON + "test6").setClassOutPutPath(OUTPUT_PATH);
        dc.newConstructor()
                .argTypes(String.class, int.class)
                .argNames("name", "count")
                .body(new ConstructorBody() {
                    @Override
                    public void body(LocVar... args) {
                        LocVar name = args[0];
                        LocVar count = args[1];
                        FieldVar out = val(System.class).field("out");
                        out.call("println", name);
                        out.call("println", count);
                        return_();
                    }
                });
        Class cls = dc.build();
    }

    /**
     * 创建一个有参构造函数，并且重载两个参数的构造函数打印文本信息Test1test4
     */
    @Test
    public void test7(){
        DummyClass dc = new DummyClass()
                .package_(PACKAGE)
                .name(LESSON + "test7")
                .setClassOutPutPath(OUTPUT_PATH);


        dc.newConstructor()
                .argTypes(String.class, int.class)
                .argNames("name", "count")
                .body(new ConstructorBody() {
                    @Override
                    public void body(LocVar... args) {
                        LocVar name = args[0];
                        LocVar count = args[1];
                        FieldVar out = val(System.class).field("out");
                        out.call("println", name);
                        out.call("println", count);
                        return_();
                    }
                });

        //构造函函数重载调用两个参数的构造函数
        dc.newConstructor()
                .argTypes(String.class)
                .argNames("name")
                .body(new ConstructorBody() {
                    @Override
                    public void body(LocVar... args) {
                        new_(getMethodOwner(), args[0], val(1));
                    }
                });
        Class cls = dc.build();
    }
}