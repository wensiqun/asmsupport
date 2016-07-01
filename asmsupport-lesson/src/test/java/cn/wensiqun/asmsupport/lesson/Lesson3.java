package cn.wensiqun.asmsupport.lesson;

import cn.wensiqun.asmsupport.client.DummyEnum;
import cn.wensiqun.asmsupport.client.block.EnumConstructorBody;
import cn.wensiqun.asmsupport.client.block.EnumStaticBlockBody;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import org.junit.Test;

/**
 * Created by woate on 2016/5/16.
 * 这节课讲解如何创建枚举
 */
public class Lesson3 {
    static final String LESSON = "Lesson3";
    static final String PACKAGE = "lesson3";
    static final String OUTPUT_PATH = "./target/asmsupport-generated";
    /**
     * 创一个含有周一，周二的枚举
     */
    @Test
    public void test1(){
        DummyEnum dc = new DummyEnum().package_(PACKAGE).name(LESSON + "test1").setClassOutPutPath(OUTPUT_PATH);
        dc.newEnum("Monday");
        dc.newEnum("Tuesday");
        Class cls = dc.build();
    }

    @Test
    public void test2(){
        DummyEnum dc = new DummyEnum().package_(PACKAGE).name(LESSON + "test2").setClassOutPutPath(OUTPUT_PATH);
        dc.newField(String.class, "name");
        dc.newField(int.class, "code");
        dc.newEnum("FIRST");
        dc.newEnum("SECOND");
        dc.newConstructor().argTypes(String.class, int.class).argNames("name", "code").body(new EnumConstructorBody() {
            @Override
            public void body(LocVar... args) {
                FieldVar name = this_().field("name");
                FieldVar size = this_().field("code");
                assign(name, args[0]);
                assign(size, args[1]);
            }
        });
        dc.newStaticBlock(new EnumStaticBlockBody() {
            @Override
            public void constructEnumConsts() {
                constructEnumConst("FIRST", val("Monday Name Here"), val(10));
                constructEnumConst("SECOND", val("Monday Name Here1"), val(11));
            }

            @Override
            public void body(LocVar... args) {
                //Undo other codes.
            }
        });
        //TODO 这里怎么使用构造函数呢？？？
        Class cls = dc.build();
    }
}
