package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

public class DummyConstructor extends DummyAccessControl<DummyConstructor> {

    /** The constructor argument types.*/
    private AClass[] argTypes;

    /** The constructor argument names.*/
    private String[] argNames;
    
    /** The constructor throws exception types.*/
    private AClass[] exceptionTypes;
    
    /** The constructor body.*/
    private ConstructorBody body;
    
    /**
     * Set the argument types.
     * 
     * @param argus
     * @return
     */
    public DummyConstructor _argumentTypes(AClass... argus){
        argTypes = argus;
        return this;
    }

    /**
     * Set the argument types.
     * 
     * @param argus
     * @return
     */
    public DummyConstructor _argumentTypes(Class<?>... argus){
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
     * Set argument names.
     * 
     * @param argNames
     * @return
     */
    public DummyConstructor _argumentNames(String... argNames){
        this.argNames = argNames;
        return this;
    }

    
    /**
     * Get argument names.
     * 
     * @param argNames
     * @return
     */
    public String[] getArgumentNames(){
        if(argNames == null) {
            return new String[0];
        }
        String[] copy = new String[argNames.length];
        System.arraycopy(argNames, 0, copy, 0, copy.length);
        return copy;
    }

    
    /**
     * Set the constructor throws exception types.
     * 
     * @param exceptionTypes
     * @return
     */
    public DummyConstructor _throws(Class<?>... exceptionTypes){
        this.exceptionTypes = AClassUtils.convertToAClass(exceptionTypes);
        return this;
    }
	
	/**
	 * Set the constructor throws exception types.
	 * 
	 * @param exceptionTypes
	 * @return
	 */
    public DummyConstructor _throws(AClass[] exceptionTypes){
        this.exceptionTypes = exceptionTypes;
        return this;
    }
    

    
    /**
     * Get the constructor throws exception types.
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
	
	/**
	 * 
	 * @param body
	 * @return
	 */
    public DummyConstructor _body(ConstructorBody body){
        this.body = body;
        return this;
    }
    
    /**
     * Get constructor body.
     * 
     * @return
     */
    public ConstructorBody getConstructorBody() {
        return body;
    }
}
