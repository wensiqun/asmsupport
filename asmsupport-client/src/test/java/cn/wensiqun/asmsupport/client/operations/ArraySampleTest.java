package cn.wensiqun.asmsupport.client.operations;

import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.TestConstant;
import cn.wensiqun.asmsupport.client.block.MethodBody;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Var;

public class ArraySampleTest {

    @Test
    public void test() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        DummyClass dummy = new DummyClass().public_().name("cn.wensiqun.asmsupport.client.test.ArraySampleGenerated")
                .setClassOutPutPath(TestConstant.classOutPutPath);

        dummy.newMethod("excepted").public_().static_().return_(String.class)
        .body(new MethodBody() {
            
			@Override
			public void body(LocVar... args) {
			    Var sb = new_(StringBuilder.class).asVar("sb");
			    Var strs = newarray(String[][].class, 
			    				new Param[][]{
			    			        new Param[]{val("Hello"), val("ASMSupport")}, 
			    			        new Param[]{val("Hi"), val("ASM")}}).asVar("strs", String[][].class);
			    sb.call("append", 
			    		strs.length())
			    		.call("append", 
			    				strs.load(val(0))
			    				.length());
			    strs.store(newarray(String[].class, new Param[]{val("Hey"), val("Java")}), val(1));
			    strs.load(val(1)).store(val("ByteCode"), val(1));
			    sb.call("append", strs.load(val(0)).length()).call("append", strs.load(val(0), val(1)));
			    return_(sb.call("toString"));
			}
        	
        });
        
        Class<?> clazz = dummy.build();
        Assert.assertEquals(ArraySample.excepted(), clazz.getMethod("excepted").invoke(clazz));
    }

}
