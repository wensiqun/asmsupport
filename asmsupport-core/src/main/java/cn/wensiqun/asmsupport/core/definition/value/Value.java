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
/**
 * 
 */
package cn.wensiqun.asmsupport.core.definition.value;

import java.lang.reflect.Modifier;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.StaticGlobalVariable;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 * Represent a basic value(Can store in constant pool.), such as {@code String}, {@code Class} and all of primitive type,
 * 
 * @author wensiqun(at)163.com
 * 
 */
public class Value implements IValue {

    private IClass cls;
    private Object value;

    /**
     * boolean value
     */
    private Value(ClassHolder holder, Boolean value) {
        this.cls = holder.getType(boolean.class);
        setProperites(value);
    }

    /**
     * byte value
     */
    private Value(ClassHolder holder, Byte value) {
        this.cls = holder.getType(byte.class);
        setProperites(value);
    }

    /**
     * short value
     */
    private Value(ClassHolder holder, Short value) {
        this.cls = holder.getType(short.class);
        setProperites(value);
    }

    /**
     * char value
     */
    private Value(ClassHolder holder, Character value) {
        this.cls = holder.getType(char.class);
        setProperites(value);
    }

    /**
     * int value
     */
    private Value(ClassHolder holder, Integer value) {
        this.cls = holder.getType(int.class);
        setProperites(value);
    }

    /**
     * long value
     */
    private Value(ClassHolder holder, Long value) {
        this.cls = holder.getType(long.class);
        setProperites(value);
    }

    /**
     * float value
     */
    private Value(ClassHolder holder, Float value) {
        this.cls = holder.getType(float.class);
        setProperites(value);
    }

    /**
     * double value
     */
    private Value(ClassHolder holder, Double value) {
        this.cls = holder.getType(double.class);
        setProperites(value);
    }

    /**
     * string value
     */
    private Value(ClassHolder holder, String value) {
        this.cls = holder.getType(String.class);
        setProperites(value);
    }

    private Value() {
    }

    private Value(IClass aclass) {
        this.cls = aclass.getClassLoader().getType(Class.class);
        setProperites(aclass);
    }

    private void setProperites(Object value) {
        this.value = value;
    }

    /**
     * Get a default value according class type.
     * 
     * @param aclass the value type that's you want to create
     */
    public static Value defaultValue(IClass aclass) {
        
    	ClassHolder holder = aclass.getClassLoader();
        if (aclass.getName().equals(int.class.getName())) {
            return new Value(holder, 0);

        } else if (aclass.getName().equals(short.class.getName())) {
            return new Value(holder, (short) 0);

        } else if (aclass.getName().equals(byte.class.getName())) {
            return new Value(holder, (byte) 0);

        } else if (aclass.getName().equals(boolean.class.getName())) {
            return new Value(holder, false);

        } else if (aclass.getName().equals(long.class.getName())) {
            return new Value(holder, (long) 0);

        } else if (aclass.getName().equals(double.class.getName())) {
            return new Value(holder, 0d);

        } else if (aclass.getName().equals(char.class.getName())) {
            return new Value(holder, (char) 0);

        } else if (aclass.getName().equals(float.class.getName())) {
            return new Value(holder, 0f);

        } else {
            Value v = new Value();
            v.cls = aclass;
            v.value = null;
            return v;
        }
    }

    /**
     * Get {@code null} value
     * 
     * @param type
     * @return
     */
    public static Value getNullValue(ClassHolder holder, Class<?> type) {
        return getNullValue(holder.getType(type));
    }

    /**
     * Get {@code null} value
     * 
     * @param type
     * @return
     */
    public static Value getNullValue(IClass type) {
        if (type.isPrimitive()) {
            throw new IllegalArgumentException("the type of null value cannot be primitive type!");
        }
        return defaultValue(type);
    }
    
    /**
     * Get {@code int} value
     * 
     * @param val
     * @return
     */
    public static Value value(ClassHolder holder, Integer obj) {
        return value(holder, (Object) obj);
    }

    /**
     * Get {@code short} value
     * 
     * @param val
     * @return
     */
    public static Value value(ClassHolder holder, Short obj) {
        return value(holder, (Object) obj);
    }

    /**
     * Get {@code byte} value
     * 
     * @param val
     * @return
     */
    public static Value value(ClassHolder holder, Byte obj) {
        return value(holder, (Object) obj);
    }

    /**
     * Get {@code boolean} value
     * 
     * @param val
     * @return
     */
    public static Value value(ClassHolder holder, Boolean obj) {
        return value(holder, (Object) obj);
    }

    /**
     * Get {@code long} value
     * 
     * @param val
     * @return
     */
    public static Value value(ClassHolder holder, Long obj) {
        return value(holder, (Object) obj);
    }

    /**
     * Get {@code double} value
     * 
     * @param val
     * @return
     */
    public static Value value(ClassHolder holder, Double obj) {
        return value(holder, (Object) obj);
    }

    /**
     * Get {@code char} value
     * 
     * @param val
     * @return
     */
    public static Value value(ClassHolder holder, Character obj) {
        return value(holder, (Object) obj);
    }

    /**
     * Get {@code float} value
     * 
     * @param val
     * @return
     */
    public static Value value(ClassHolder holder, Float obj) {
        return value(holder, (Object) obj);
    }

    /**
     * Get {@code class} value
     * 
     * @param val
     * @return
     */
    public static Value value(ClassHolder holder, IClass obj) {
        return value(holder, (Object) obj);
    }

    /**
     * Get {@code class} value
     * 
     * @param val
     * @return
     */
    public static Value value(ClassHolder holder, Class<?> obj) {
        return value(holder, (Object) obj);
    }

    /**
     * Get {@code null} value
     * 
     * @param val
     * @return
     */
    public static Value value(ClassHolder holder, String obj) {
        return value(holder, (Object) obj);
    }

    private static Value value(ClassHolder holder, Object obj) {
        if (obj == null) {
            throw new NullPointerException(
                    "Cannot support null value for this method, use getNullValue method to get null value if you want get null!");
        }
        if (obj instanceof Integer) {

            return new Value(holder, (Integer) obj);

        } else if (obj instanceof Short) {

            return new Value(holder, (Short) obj);

        } else if (obj instanceof Byte) {

            return new Value(holder, (Byte) obj);

        } else if (obj instanceof Boolean) {

            return new Value(holder, (Boolean) obj);

        } else if (obj instanceof Long) {

            return new Value(holder, (Long) obj);

        } else if (obj instanceof Double) {

            return new Value(holder, (Double) obj);

        } else if (obj instanceof Character) {

            return new Value(holder, (Character) obj);

        } else if (obj instanceof Float) {

            return new Value(holder, (Float) obj);

        } else if (obj instanceof IClass) {

            return new Value((IClass) obj);

        } else if (obj instanceof String) {

            return new Value(holder, (String) obj);

        } else if (obj instanceof Class) {

            return new Value(holder.getType((Class<?>) obj));

        }
        throw new ASMSupportException("Cannot support type " + obj.getClass() + " for this method!");
    }

    /**
     * get a Value object, according arguments
     * 
     * for examples:
     * 
     * number(AClassFactory.defType(double.class), 1) will return a Value object that value is
     * 1D, and type is Double
     * 
     * @param type
     * @param val
     * @return
     */
    public static Value number(IClass type, int val) {
    	ClassHolder holder = type.getClassLoader();
        switch (type.getType().getSort()) {
        case Type.CHAR:
            return value(holder, (char) val);
        case Type.BYTE:
            return value(holder, (byte) val);
        case Type.SHORT:
            return value(holder, (short) val);
        case Type.INT:
            return value(holder, val);
        case Type.FLOAT:
            return value(holder, (float) val);
        case Type.LONG:
            return value(holder, (long) val);
        case Type.DOUBLE:
            return value(holder, (double) val);
        }
        return null;
    }

    /**
     * convert current value to new type.
     * 
     * @param type
     */
    public void convert(IClass type) {
        convert(this, type);
    }

    /**
     * get converted value
     * 
     * @param type
     */
    public Value getConvert(IClass type) {
        Value newVal = value(type.getClassLoader(), value);
        convert(newVal, type);
        return newVal;
    }

    /**
     * convert value to new type
     * 
     * @param val
     * @param type
     */
    private void convert(Value val, IClass type) {
        if (cls.equals(type)) {
            return;
        }
        String valStr = value.toString();
        switch (type.getType().getSort()) {
        case Type.BOOLEAN:
            val.refactor(type, Boolean.parseBoolean(valStr));
            break;
        case Type.CHAR:
            val.refactor(type, (char) Integer.parseInt(valStr));
            break;
        case Type.BYTE:
            val.refactor(type, Byte.parseByte(valStr));
            break;
        case Type.SHORT:
            val.refactor(type, Short.parseShort(valStr));
            break;
        case Type.INT:
            val.refactor(type, Integer.parseInt(valStr));
            break;
        case Type.FLOAT:
            val.refactor(type, Float.parseFloat(valStr));
            break;
        case Type.LONG:
            val.refactor(type, Long.parseLong(valStr));
            break;
        case Type.DOUBLE:
            val.refactor(type, Double.parseDouble(valStr));
            break;
        }
    }

    /**
     * 
     * @param type
     * @param val
     */
    private void refactor(IClass type, Object val) {
        this.cls = type;
        this.setProperites(val);
    }

    /**
     * 
     * 
     * @param val
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public int numberCompare(Value comp) {
        if (!isNumber())
            throw new ASMSupportException(this + " is not a number value.");
        if (!comp.isNumber())
            throw new ASMSupportException(comp + " is not a number value.");

        int compOrder = cls.getCastOrder() - comp.cls.getCastOrder();
        Object v1;
        Object v2;

        if (compOrder == 0) {
            v1 = value;
            v2 = comp.value;
        } else if (compOrder > 0) {
            v1 = value;
            v2 = comp.getConvert(cls).value;
        } else {
            v1 = getConvert(comp.cls).value;
            v2 = comp.value;
        }

        return ((Comparable) v1).compareTo(v2);
    }

    public boolean isNumber() {
        int castOrder = cls.getType().getSort();
        if (castOrder >= Type.BYTE && castOrder <= Type.DOUBLE) {
            return true;
        }
        return false;
    }

    public boolean isBoolean() {
        return cls.getType().getSort() == Type.SHORT;
    }

    @Override
    public void loadToStack(KernelProgramBlock block) {
        if (value == null) {
            block.getInsnHelper().push(this.getType());
            return;
        }

        if (this.cls.getName().equals(int.class.getName())) {

            block.getInsnHelper().push((Integer) value);

        } else if (this.cls.getName().equals(short.class.getName())) {

            block.getInsnHelper().push((Short) value);

        } else if (this.cls.getName().equals(byte.class.getName())) {

            block.getInsnHelper().push((Byte) value);

        } else if (this.cls.getName().equals(boolean.class.getName())) {

            block.getInsnHelper().push((Boolean) value);

        } else if (this.cls.getName().equals(long.class.getName())) {

            block.getInsnHelper().push((Long) value);

        } else if (this.cls.getName().equals(double.class.getName())) {

            block.getInsnHelper().push((Double) value);

        } else if (this.cls.getName().equals(char.class.getName())) {

            block.getInsnHelper().push((Character) value);

        } else if (this.cls.getName().equals(float.class.getName())) {

            block.getInsnHelper().push((Float) value);

        } else if (this.cls.getName().equals(String.class.getName())) {

            block.getInsnHelper().push(value.toString());

        } else if (this.cls.getName().equals(Class.class.getName())) {

            block.getInsnHelper().pushClass(((IClass) value).getType());

        }
    }

    @Override
    public void asArgument() {
        // Do nothing
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }

        if (!(obj instanceof Value)) {
            return false;
        }

        Value objval = (Value) obj;
        IClass curtyp = getResultType();
        IClass objtyp = objval.getResultType();

        if (value == null && objval.value == null) {
            if (curtyp.isChildOrEqual(objtyp) || objtyp.isChildOrEqual(curtyp)) {
                return true;
            } else if (Modifier.isAbstract(curtyp.getModifiers()) && Modifier.isFinal(objtyp.getModifiers())) {
                return false;
            } else {
                return !((Modifier.isAbstract(objtyp.getModifiers()) && Modifier.isFinal(curtyp.getModifiers())));
            }
        } else if (value == null && objval.value != null || objval.value == null && value != null) {
            return false;
        } else {
            return value.equals(objval);
        }
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }

    public Type getType() {
        return this.cls.getType();
    }

    public Object getValue() {
        return value;
    }

    @Override
    public IClass getResultType() {
        return cls;
    }

    @Override
    public String toString() {
        return value + " [type:" + cls + "]";
    }

    @Override
    public GlobalVariable field(String name) {
        if(value != null && value instanceof IClass) {
            Field field = ((IClass)value).getField(name);
            if(ModifierUtils.isStatic(field.getModifiers())) {
                return new StaticGlobalVariable((IClass)value, field);
            } else {
                throw new ASMSupportException("No such field " + name);
            }
        }
        return null;
    }
}
