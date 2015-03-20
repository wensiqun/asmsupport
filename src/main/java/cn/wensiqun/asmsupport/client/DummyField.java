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
    public DummyField val(Boolean val) {
        return val((Object)val);
    }

    
    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */
    public DummyField val(Character val) {
        return val((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */
    public DummyField val(Byte val) {
        return val((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */   
    public DummyField val(Short val) {
        return val((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */    
    public DummyField val(Integer val) {
        return val((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */   
    public DummyField val(Float val) {
        return val((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */    
    public DummyField val(Long val) {
        return val((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */    
    public DummyField val(Double val) {
        return val((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */    
    public DummyField val(String val) {
        return val((Object)val);
    }

    /**
     * Set initial value, this is only support static field, 
     * otherwise will ignored.
     * 
     * @param val
     * @return
     */    
    private DummyField val(Object val) {
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
    public DummyField static_() {
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
    public DummyField transient_() {
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
    public DummyField volatile_() {
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
	public DummyField type(AClass type){
	    this.type = type;
	    return this;
	}	
	
	/**
	 * Set the field type.
	 * 
	 * @param type
	 * @return
	 */
	public DummyField type(Class<?> type) {
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
	public DummyField name(String name) {
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
