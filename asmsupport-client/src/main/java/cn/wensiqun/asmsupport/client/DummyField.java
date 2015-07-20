/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.AsmsupportClassLoader;

public class DummyField extends DummyAccessControl<DummyField> {
    
    /** Field type */
    private IClass type;
    
    /** Field name */
    private String name;

    private Object value;
    
    DummyField(AsmsupportClassLoader classLoader) {
    	this(null, null, classLoader);
    }
    
    DummyField(AClass type, String name, AsmsupportClassLoader classLoader) {
    	super(classLoader);
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
        this.type = getClassLoader().getType(type);
        return this;
	}

	/**
	 * Get the field type.
	 * 
	 * @return
	 */
	public IClass getType() {
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
