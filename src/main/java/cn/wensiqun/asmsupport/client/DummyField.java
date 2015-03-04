package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class DummyField extends DummyAccessControl<DummyField> {
    
    /** Field type */
    private AClass type;
    
    /** Field name */
    private String name;

    private Object value;
    
    public DummyField() {
    }
    
    public DummyField(AClass type) {
        this.type = type;
    }
    
    public DummyField(String name) {
        this.name = name;
    }

    public DummyField(AClass type, String name) {
        this.type = type;
        this.name = name;
    }
    
    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */
    public DummyField _value(Boolean val) {
        return _value((Object)val);
    }

    
    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */
    public DummyField _value(Character val) {
        return _value((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */
    public DummyField _value(Byte val) {
        return _value((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */   
    public DummyField _value(Short val) {
        return _value((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */    
    public DummyField _value(Integer val) {
        return _value((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */   
    public DummyField _value(Float val) {
        return _value((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */    
    public DummyField _value(Long val) {
        return _value((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */    
    public DummyField _value(Double val) {
        return _value((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */    
    public DummyField _value(String val) {
        return _value((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */    
    private DummyField _value(Object val) {
        value = val;
        return this;
    }
    
    /**
     * Get the field initial value
     * 
     * @return
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Set to static
     * 
     * @return
     */
    public DummyField _static() {
        modifiers = (modifiers & ~Opcodes.ACC_STATIC) + Opcodes.ACC_STATIC;
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
        modifiers = (modifiers & ~Opcodes.ACC_TRANSIENT) + Opcodes.ACC_TRANSIENT;
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
        modifiers = (modifiers & ~Opcodes.ACC_VOLATILE) + Opcodes.ACC_VOLATILE;
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
