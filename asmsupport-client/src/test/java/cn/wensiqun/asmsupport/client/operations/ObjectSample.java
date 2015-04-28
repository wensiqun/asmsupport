package cn.wensiqun.asmsupport.client.operations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjectSample {
    
    public static String excepted() {
        List list = new ArrayList();
        list.add("100");
        list.add("200");
        ArrayList arrayList = (ArrayList) list;
        String str = "Test Str : " + (list == arrayList) + list.size() + (list instanceof Serializable);
        return str;
    }

}
