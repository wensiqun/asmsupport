package cn.wensiqun.asmsupport.core.utils.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SuperTypeLopper {

    public final Object found(List<Class> types){
        Object obj = null;
        for(Class<?> t : types) {
            if(obj == null) {
                obj = process(t);
            } else {
                if(process(t) != null) {
                    //throw
                }
            }
        }
        if(obj != null) {
            return obj;
        }
        
        for(Class<?> t : types) {
            List<Class> superTypes = new ArrayList<Class>();
            if(t.getSuperclass() != null) {
                superTypes.add(t.getSuperclass());
            }
            Collections.addAll(superTypes, t.getInterfaces());
            
            if(obj == null) {
                obj = found(superTypes);
            } else {
                if(found(superTypes) != null) {
                    //throw
                }
            }
        }
        
        return obj;
        
    }
    
    
    
    
    /**
     * return true stop loop, false continue loop
     * 
     * @param inter
     * @return
     */
    protected abstract Object process(Class<?> inter);
    
}
