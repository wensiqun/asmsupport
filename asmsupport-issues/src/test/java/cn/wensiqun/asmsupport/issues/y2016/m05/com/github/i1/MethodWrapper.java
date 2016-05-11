package cn.wensiqun.asmsupport.issues.y2016.m05.com.github.i1;

import java.util.Map;

/**
 * Created by sqwen on 2016/5/3.
 */
public interface MethodWrapper {

    Map<String, Object> before(Object owner, Object... arguments);

    void after(Map<String, Object> injectContext, Object result, Object owner, Object... arguments);

}
