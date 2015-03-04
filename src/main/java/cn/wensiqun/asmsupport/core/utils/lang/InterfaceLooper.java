package cn.wensiqun.asmsupport.core.utils.lang;

public abstract class InterfaceLooper {

    public final boolean loop(Class<?>[] clazzs){
        for(Class<?> clazz : clazzs) {
            if(clazz.isInterface()) {
                if(process(clazz)) {
                    return true;
                }
            }
        }
        
        for(Class<?> clazz : clazzs) {
            if(loop(clazz.getInterfaces())) {
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * return true stop loop, false continue loop
     * 
     * @param inter
     * @return
     */
    protected abstract boolean process(Class<?> inter);
    
}
