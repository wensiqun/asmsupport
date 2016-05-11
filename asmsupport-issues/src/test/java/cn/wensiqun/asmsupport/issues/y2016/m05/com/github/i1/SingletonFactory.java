package cn.wensiqun.asmsupport.issues.y2016.m05.com.github.i1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sqwen on 2016/5/3.
 */
public class SingletonFactory {

    private static final Map<Class<?>, Object> singletonMap = new HashMap<Class<?>, Object>();

    public static <T> T getInjectLogic(Class<T> type) {
        T object = (T) singletonMap.get(type);
        if(object == null) {
            synchronized (singletonMap) {
                object = (T) singletonMap.get(type);
                if(object == null) {
                    try {
                        singletonMap.put(type, object = type.newInstance());
                    } catch (Exception e) {
                    }
                }
            }
        }
        return object;
    }

}
