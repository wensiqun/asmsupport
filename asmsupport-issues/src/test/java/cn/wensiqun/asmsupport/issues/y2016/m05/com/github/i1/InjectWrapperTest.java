package cn.wensiqun.asmsupport.issues.y2016.m05.com.github.i1;

import cn.wensiqun.asmsupport.client.DummyModifiedClass;
import cn.wensiqun.asmsupport.client.block.*;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.issues.IssuesConstant;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by sqwen on 2016/5/4.
 */
public class InjectWrapperTest {

    @Test
    public void test() throws Exception {
        main();
    }

    public static void main(String... args) throws Exception {
        DummyModifiedClass dummy = new DummyModifiedClass(MyActivity.class)
                .setClassOutPutPath(IssuesConstant.classOutPutPath);
        dummy.modify("test", new Class[]{StringBuilder.class}, new ModifiedMethodBody() {

            @Override
            public void body(final LocVar... args) {

                //Map context = null;
                final LocVar context = var("context", Map.class, null_(Map.class));
                //boolean success = false;
                final LocVar success = var("success", boolean.class, val(false));
                //boolean wrapper = null;
                final LocVar wrapper = var("wrapper", MethodWrapper.class, null_(MethodWrapper.class));
                try_(new Try() {
                    @Override
                    public void body() {
                        Param wrapperInvoke =
                                call(SingletonFactory.class, "getInjectLogic",
                                        val(MyMethodWrapper.class));
                        assign(wrapper,
                                var(MethodWrapper.class,
                                        checkcast(wrapperInvoke, MethodWrapper.class)
                                )
                        );
                    }
                }).catch_(new Catch(getType(Throwable.class)) {
                    @Override
                    public void body(LocVar e) {
                    }
                });

                //if (wrapper != null)
                if_(new IF(ne(null_(MethodWrapper.class), wrapper)) {

                    @Override
                    public void body() {
                        try_(new Try() {
                            @Override
                            public void body() {
                                Param[] arguments = new Param[args.length + 1];
                                arguments[0] = this_();
                                System.arraycopy(args, 0, arguments, 1, args.length);
                                //context = wrapper.before(arg...)
                                assign(context, wrapper.call("before", arguments));
                                //success = true
                                assign(success, val(true));
                            }
                        }).catch_(new Catch(Throwable.class) {
                            @Override
                            public void body(LocVar e) {
                                e.call("printStackTrace");
                            }
                        }).finally_(new Finally() {
                            @Override
                            public void body() {
                                args[0].call("append", val("Here"));
                                call(args[0], "append", val("Now"));
                            }
                        });
                    }
                });

                final LocVar result = var(getOrigReturnType(), callOrig());
                //if(wrapper != null && success)
                if_(new IF(ne(null_(MethodWrapper.class), wrapper).and(success)) {
                    @Override
                    public void body() {
                        try_(new Try() {
                            @Override
                            public void body() {
                                Param[] arguments = new Param[args.length + 3];
                                arguments[0] = context;
                                arguments[1] = result;
                                arguments[2] = this_();
                                System.arraycopy(args, 0, arguments, 3, args.length);
                                wrapper.call("after", arguments);
                            }
                        }).catch_(new Catch(getType(Throwable.class)) {
                            @Override
                            public void body(LocVar e) {
                            }
                        });
                    }
                });

                //return result;
                return_(result);
            }
        });
        Class<?> injectedClass = dummy.build();
        Activity activity = (Activity) injectedClass.newInstance();
        StringBuilder sb = new StringBuilder();
        activity.test(sb);
        Assert.assertEquals("HelloHereNow***Bye", sb.toString());
    }

}
