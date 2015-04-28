package cn.wensiqun.asmsupport.client.operations;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.TestConstant;
import cn.wensiqun.asmsupport.client.block.MethodBody;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Var;

public class ObjectSampleTest {

    @Test
    public void test() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        DummyClass dummy = new DummyClass().public_().name("cn.wensiqun.asmsupport.client.test.ObjectSampleGenerated")
                .setClassOutPutPath(TestConstant.classOutPutPath);

        dummy.newMethod("excepted").public_().static_().return_(String.class)
        .body(new MethodBody() {
            
			@Override
			public void body(LocVar... args) {
			    Var list = var("list", List.class, new_(ArrayList.class));
			    list.call("add", val("100"));
                list.call("add", val("200"));
                Var arrayList = var("arraylist", ArrayList.class, list.cast(ArrayList.class));
                Var str = var("str", String.class, val("Test Str : ")
                        .stradd(list.eq(arrayList))
                        .stradd(list.call("size"))
                        .stradd(list.instanceof_(Serializable.class)));
			    return_(str);
			}
        	
        });
        
        Class<?> clazz = dummy.build();
        Assert.assertEquals(ObjectSample.excepted(), clazz.getMethod("excepted").invoke(clazz));
    }

}
