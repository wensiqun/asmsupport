package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;

public class DummyEnumConstructor {

    /** The constructor argument types.*/
    private AClass[] argTypes;

    /** The constructor argument names.*/
    private String[] argNames;
    
    /** The constructor body.*/
    private EnumConstructorBody body;
    
    /**
     * Set the argument types.
     * 
     * @param argus
     * @return
     */
    public DummyEnumConstructor argTypes(AClass... argus){
        argTypes = argus;
        return this;
    }

    /**
     * Set the argument types.
     * 
     * @param argus
     * @return
     */
    public DummyEnumConstructor argTypes(Class<?>... argus){
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
    public DummyEnumConstructor argNames(String... argNames){
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
	 * 
	 * @param body
	 * @return
	 */
    public DummyEnumConstructor body(EnumConstructorBody body){
        this.body = body;
        return this;
    }
    
    /**
     * Get constructor body.
     * 
     * @return
     */
    public EnumConstructorBody getConstructorBody() {
        return body;
    }
}
