package cn.wensiqun.asmsupport.client.operations;

import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.TestConstant;
import cn.wensiqun.asmsupport.client.block.MethodBody;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Var;

public class NumberSampleTest {

    @Test
    public void test() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        DummyClass dummy = new DummyClass().public_().name("cn.wensiqun.asmsupport.client.test.NumberSampleGenerated")
                .setClassOutPutPath(TestConstant.classOutPutPath);

        dummy.newMethod("excepted").public_().static_().return_(String.class)
        .argTypes(int.class, int.class, int.class, int.class, int.class, int.class, long.class)
        .argNames("t1", "t2", "t3", "t4", "t5", "t6", "t7")
        .body(new MethodBody() {

			@Override
			public void body(LocVar... args) {
				Var sb = var("sb", StringBuilder.class, new_(StringBuilder.class));
				Var ta = var("ta", double.class, call("getTriangleAcreage", val(10), val(9), val(3)));
				sb.call("append", 
						ta)//.add(call("getSquareAcreage", val(8), val(5))).add(call("getTixingArea", val(5), val((short)4), val((byte)2))))
						.call("append", val('|'));
				sb.call("append", ta.gt(val(10)).and(val(20).gt(ta))).call("append", val('|'));
				sb.call("append", call("getSquareAcreage", val(8), val(5)).lt(val(20)).and(val(10).lt(ta))).call("append", val('|'));
				Var tixingArea = var("tixingArea", float.class, call("getTixingArea", val(6), val((short)3), val((byte)2)));
                sb.call("append", tixingArea.eq(val(9)).and(val(10).ne(tixingArea))).call("append", val('|'));
                sb.call("append", tixingArea.ne(val(9)).and(val(10).eq(tixingArea))).call("append", val('|'));
                sb.call("append", 
                		call("getSquareAcreage", val(2), val(4)) 
						.shl(args[0])//.shl(val(2)) 
						.bor(args[1])//.bor(val(5))
						.bxor(args[2])//.bxor(val(2))
						.shr(args[3])//.shr(val(1))
						.band(args[4])//.band(val(7))
						.ushr(args[5])//.ushr(val(3))
                	).call("append", val('|'));
                sb.call("append", args[6].shr(val(1)));
                return_(sb.call("toString"));
                
			}
        	
        });
        
        dummy.newMethod("getResult").public_().argTypes(int.class, int.class, int.class).argNames("x", "y", "z").static_().return_(int.class)
        .body(new MethodBody() {

            @Override
            public void body(LocVar... args) {
                return_(args[2].add(add(val(8), val(2))).mul(val(3)).mul(args[0]).div(args[1]).mod(val(2)).sub(sub(val(5), val(3))).mul(val(4)).div(val(2)));
            }
             
         });
        
        dummy.newMethod("getTriangleAcreage").public_().static_().return_(double.class)
        .argTypes(double.class, double.class, double.class)
        .argNames("a", "b", "c").body(new MethodBody() {
			@Override
			public void body(LocVar... args) {
				Var a = args[0], b = args[1], c = args[2];
				Var m = var("m", double.class, div(a.add(b).add(c), val(2)));
				return_(val(Math.class).call("sqrt", m.mul(sub(m, a)).mul(sub(m, b)).mul(sub(m, c))));
			}
        });
        
        dummy.newMethod("getSquareAcreage").public_().static_().return_(long.class).argTypes(int.class, long.class)
        .argNames("a", "b")
        .body(new MethodBody() {

			@Override
			public void body(LocVar... args) {
				return_(args[0].mul(args[1]));
			}
        	
        });
        
        dummy.newMethod("getTixingArea").public_().static_().return_(float.class).argTypes(int.class, short.class, byte.class)
        .body(new MethodBody() {

			@Override
			public void body(LocVar... args) {
				return_(mul(args[0].add(args[1]), args[2]).div(val(2)));
			}
        	
        });
        
        
        Class<?> clazz = dummy.build();
        int x = 4, y = 8, z = 100;
        Assert.assertEquals(NumberSample.getResult(x, y, z), clazz.getMethod("getResult", int.class, int.class, int.class).invoke(clazz, x, y, z));
        Assert.assertEquals(NumberSample.excepted(2, 5, 2, 1, 7, 3, 12), clazz.getMethod("excepted", int.class, int.class, int.class, int.class, int.class, int.class, long.class).invoke(clazz, 2, 5, 2, 1, 7, 3, 12));
    }

}
