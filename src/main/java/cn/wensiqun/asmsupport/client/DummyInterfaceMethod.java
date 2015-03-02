package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

public class DummyInterfaceMethod {

    /** The return type of method. */
    private AClass returnType;
    
    /** The name of method */
    private String name;

    /** The constructor argument types.*/
    private AClass[] argTypes;
    
    /** The constructor throws exception types.*/
    private AClass[] exceptionTypes;
    
    /**
     * Set the method return type.
     * 
     * @param ret
     * @return
     */
    public DummyInterfaceMethod _return(AClass ret) {
        this.returnType = ret;
        return this;
    }
    
    /**
     * Set the method return type.
     * 
     * @param ret
     * @return
     */
    public DummyInterfaceMethod _return(Class<?> ret) {
        this.returnType = AClassFactory.getProductClass(ret);
        return this;
    }
    
    /**
     * Get the method return type.
     * 
     * @return
     */
    public AClass getReturnType() {
        return returnType;
    }
    
    /**
     * Set the name of the method.
     * 
     * @param name
     * @return
     */
    public DummyInterfaceMethod _name(String name) {
        this.name = name;
        return this;
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
     * Set the argument types.
     * 
     * @param argus
     * @return
     */
    public DummyInterfaceMethod _argumentTypes(AClass... argus){
        argTypes = argus;
        return this;
    }

    /**
     * Set the argument types.
     * 
     * @param argus
     * @return
     */
    public DummyInterfaceMethod _argumentTypes(Class<?>... argus){
        this.argTypes = AClassUtils.convertToAClass(argus);
        return this;
    }
    
    /**
     * Get argument types.
     * 
     * @return
     */
    public AClass[] getArgumentTypes() {
        if(argTypes == null) {
            return new AClass[0];
        }
        AClass[] copy = new AClass[argTypes.length];
        System.arraycopy(argTypes, 0, copy, 0, copy.length);
        return copy;
    }
    
    /**
     * Set the method throws exception types.
     * 
     * @param exceptionTypes
     * @return
     */
    public DummyInterfaceMethod _throws(Class<?>... exceptionTypes){
        this.exceptionTypes = AClassUtils.convertToAClass(exceptionTypes);
        return this;
    }
    
    /**
     * Set the method throws exception types.
     * 
     * @param exceptionTypes
     * @return
     */
    public DummyInterfaceMethod _throws(AClass... exceptionTypes){
        this.exceptionTypes = exceptionTypes;
        return this;
    }
    
    /**
     * Get the method throws exception types.
     * 
     * @return
     */
    public AClass[] getThrows() {
        if(exceptionTypes == null) {
            return new AClass[0];
        }
        AClass[] copy = new AClass[exceptionTypes.length];
        System.arraycopy(exceptionTypes, 0, copy, 0, copy.length);
        return copy;
    }
    
}
