package cn.wensiqun.asmsupport.issues.y2016.m05.com.github.i1;

import java.util.Map;

/**
 * Created by sqwen on 2016/5/4.
 */
public class MyMethodWrapper implements MethodWrapper {

    @Override
    public Map<String, Object> before(Object owner, Object... arguments) {
        ((StringBuilder)arguments[0]).append("Hello");
        return null;
    }

    @Override
    public void after(Map<String, Object> injectContext, Object result, Object owner, Object... arguments) {
        ((StringBuilder)arguments[0]).append("Bye");
    }

}
