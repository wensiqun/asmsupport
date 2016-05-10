package com.asmsupport.lesson;

import cn.wensiqun.asmsupport.client.DummyInterface;
import cn.wensiqun.asmsupport.client.block.StaticBlockBody;
import cn.wensiqun.asmsupport.client.def.var.This;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by woate on 2016/5/10.
 * 这节课讲解如何创建接口
 */
@Ignore
public class Lesson2 {
    static final String LESSON = "Lesson2";
    static final String PACKAGE = "lesson2";
    static final String OUTPUT_PATH = "./target/asmsupport-generated";

    /**
     * 创一个空接口
     */
    @Test
    public void test1(){
        DummyInterface dc = new DummyInterface().package_(PACKAGE).name(LESSON + "test1").setClassOutPutPath(OUTPUT_PATH);
        Class cls = dc.build();
    }

    /**
     * 创一个有两个常量字段的接口
     */
    @Test
    public void test2(){
        DummyInterface dc = new DummyInterface().package_(PACKAGE).name(LESSON + "test2").setClassOutPutPath(OUTPUT_PATH);
        dc.newField(String.class, "ASM_NAME").val("This is a test");
        dc.newField(int.class, "ASM_COUNT").val(1);
        Class cls = dc.build();
    }

    /**
     * 创一个有两个常量字段的接口,并且用静态初始块进行初始化
     */
    @Test
    public void test3(){
        DummyInterface dc = new DummyInterface().package_(PACKAGE).name(LESSON + "test3").setClassOutPutPath(OUTPUT_PATH);
        dc.newField(String.class, "ASM_NAME");
        dc.newField(int.class, "ASM_COUNT");
        dc.newStaticBlock(new StaticBlockBody() {
            @Override
            public void body() {
                GlobalVariable FIELD =
                assign(FIELD, call(String.class, "valueOf", val(100)));
                return_();
            }
        });
        Class cls = dc.build();
    }
}
