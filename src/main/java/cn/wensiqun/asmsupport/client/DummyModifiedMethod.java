package cn.wensiqun.asmsupport.client;


public class DummyModifiedMethod {
    
    private String name;
    
    private Class<?>[] argTypes;
    
    private ModifiedMethodBody body;

    public DummyModifiedMethod(String name, Class<?>[] argTypes, ModifiedMethodBody body) {
        this.name = name;
        this.argTypes = argTypes;
        this.body = body;
    }

    /**
     * Get the name of the method.
     * 
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get argument types.
     * 
     * @return
     */
    public Class<?>[] getArgumentTypes() {
        if(argTypes == null) {
            return new Class[0];
        }
        Class<?>[] copy = new Class[argTypes.length];
        System.arraycopy(argTypes, 0, copy, 0, copy.length);
        return copy;
    }

    /**
     * Get the method body.
     * 
     * @return
     */
    public ModifiedMethodBody getMethodBody() {
        return this.body;
    }
}
