package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class DummyField extends DummyAccessControl<DummyField> {

    /** Field type */
    private AClass type;
    
    /** Field name */
    private String name;

    /**
     * Set to static
     * 
     * @return
     */
    public DummyField _static() {
        modifiers = (modifiers | ~Opcodes.ACC_STATIC) + Opcodes.ACC_STATIC;
        return this;
    }

    /**
     * Check the access whether or not static.
     * 
     * @return
     */
    public boolean isStatic() {
        return (modifiers & Opcodes.ACC_STATIC) != 0;
    }
    
    /**
     * Set the field to transient.
     * 
     * @return
     */
    public DummyField _transient() {
        modifiers = (modifiers | ~Opcodes.ACC_TRANSIENT) + Opcodes.ACC_TRANSIENT;
        return this;
    } 
    
    /**
     * Check the field whether or not transient.
     * 
     * @return
     */
    public boolean isTransient() {
        return (modifiers & Opcodes.ACC_TRANSIENT) != 0;
    }
    
    /**
     * Set the field to volatile.
     * 
     * @return
     */
    public DummyField _volatile() {
        modifiers = (modifiers | ~Opcodes.ACC_VOLATILE) + Opcodes.ACC_VOLATILE;
        return this;
    }
    
    /**
     * Check the field whether or not transient.
     * 
     * @return
     */
    public boolean isVolatile() {
        return (modifiers & Opcodes.ACC_VOLATILE) != 0;
    }
    
    /**
     * Set the field type.
     * 
     * @param type
     * @return
     */
	public DummyField _type(AClass type){
	    this.type = type;
	    return this;
	}	
	
	/**
	 * Set the field type.
	 * 
	 * @param type
	 * @return
	 */
	public DummyField _type(Class<?> type) {
        this.type = AClassFactory.getProductClass(type);
        return this;
	}

	/**
	 * Get the field type.
	 * 
	 * @return
	 */
	public AClass getType() {
	    return type;
	}
	
	/**
	 * Set the name.
	 * 
	 * @param name
	 * @return
	 */
	public DummyField _name(String name) {
	    this.name = name;
        return this;
	}
	
	/**
	 * Get the field name.
	 * 
	 * @return
	 */
	public String getName() {
	    return this.name;
	}
}
