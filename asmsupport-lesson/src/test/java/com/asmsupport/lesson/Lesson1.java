package com.asmsupport.lesson;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.block.StaticBlockBody;
import org.junit.Test;

/**
 * Created by woate on 2016/5/9.
 * 测试创建一个实现类
 */
//@Ignore
public class Lesson1 {
    static final String LESSON = "Lesson1";
    static final String PACKAGE = "lesson1";
    static final String OUTPUT_PATH = ".";
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
     * 创建一个默认访问域的抽象类Test1test4
     */
    @Test
    public void test4(){
        DummyClass dc = new DummyClass().package_(PACKAGE).name(LESSON + "test4").setClassOutPutPath(OUTPUT_PATH);
        dc.newStaticBlock(new StaticBlockBody() {
            @Override
            public void body() {
//                Field out = getType(System.class).getField("out");
//                call(out.getDeclaringClass(), "println", val("Hello ASMSupport"));
//
//                return_();
            }
        });
        Class cls = dc.build();
    }
}