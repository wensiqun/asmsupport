/**
 * 
 */
package cn.wensiqun.asmsupport.definition.value;

import java.lang.reflect.Modifier;

import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.exception.ASMSupportException;


/**
 * 通常一些基本类型的常量值，String类型的值，null，Class类型，这些在编写java代码的时候都是直接可以获取的到，
 * 那么这些值在ASMSupport中我们都要将其封装成ASMSupport内部可识别的对象, 这个类就是对这种内部可识别对象的类型。
 * 
 * @author wensiqun(at)gmail.com
 * 
 */
public class Value implements IValue {

    private AClass aclass;
    private Object value;
    
    @Override
	public String toString() {
		return value + "(type is " + aclass + ")";
	}

    /**
     * boolean值
     * @param value
     */
	private Value(Boolean value) {
        this.aclass = AClass.BOOLEAN_ACLASS;
        setProperites(value);
    }

	/**
	 * byte值
	 * @param value
	 */
	private Value(Byte value) {
        this.aclass = AClass.BYTE_ACLASS;
        setProperites(value);
    }

    /**
     * short value
     * 
     * @param value
     */
	private Value(Short value) {
        this.aclass = AClass.SHORT_ACLASS;
        setProperites(value);
    }

    /**
     * char value
     * 
     * @param value
     */
	private Value(Character value) {
        this.aclass = AClass.CHAR_ACLASS;
        setProperites(value);
    }

    /**
     * int value
     * 
     * @param value
     */
	private Value(Integer value) {
        this.aclass = AClass.INT_ACLASS;
        setProperites(value);
    }

    /**
     * long value
     * 
     * @param value
     */
	private Value(Long value) {
        this.aclass = AClass.LONG_ACLASS;
        setProperites(value);
    }

    /**
     * float value
     * 
     * @param value
     */
	private Value(Float value) {
        this.aclass = AClass.FLOAT_ACLASS;
        setProperites(value);
    }

    /**
     * double value
     * 
     * @param value
     */
	private Value(Double value) {
        this.aclass = AClass.DOUBLE_ACLASS;
        setProperites(value);
    }

    /**
     * string value
     * 
     * @param value
     */
	private Value(String value) {
        this.aclass = AClass.STRING_ACLASS;
        setProperites(value);
    }

    private Value() {
    }

    private Value(AClass aclass) {
        this.aclass = AClass.CLASS_ACLASS;
        setProperites(aclass);
    }

    private void setProperites(Object value) {
        //this.type = aclass.getType();
        this.value = value;
    }

    public Type getType() {
        return this.aclass.getType();
    }

    public AClass getAClass() {
        return aclass;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public AClass getParamterizedType() {
        return aclass;
    }

    /**
     * 传入AClass 根据AClass获取默认值
     * 
     * @param aclass
     * @return
     */
    public static Value defaultValue(AClass aclass) {
        
        if (aclass.getName().equals(int.class.getName())) {
            return new Value(0);
            
        } else if (aclass.getName().equals(short.class.getName())) {
            return new Value((short) 0);
            
        } else if (aclass.getName().equals(byte.class.getName())) {
            return new Value((byte) 0);
            
        } else if (aclass.getName().equals(boolean.class.getName())) {
            return new Value(false);
            
        } else if (aclass.getName().equals(long.class.getName())) {
            return new Value((long) 0);
            
        } else if (aclass.getName().equals(double.class.getName())) {
            return new Value(0d);
            
        } else if (aclass.getName().equals(char.class.getName())) {
           return new Value((char)0);
            
        } else if (aclass.getName().equals(float.class.getName())) {
            return new Value(0f);
            
        } else {
            Value v = new Value();
            v.aclass = aclass;
            v.value = null;
            return v;
        }
    }

    /**
     * 获取Null值
     * 
     * @param type
     * @return
     */
    public static Value getNullValue(AClass type) {
        if(type.isPrimitive()){
            throw new IllegalArgumentException("the type of null value cannot be primitive type!");
        }
        return defaultValue(type);
    }

    /**
     * 
     * 
     * @param obj
     * @return
     */
    public static Value value(Object obj) {
        if(obj == null){
        	throw new NullPointerException("cannot support null value for this method, use nullValue(AClass type) method to get null value if you want get null!");
        }
        if (obj instanceof Integer) {

            return new Value((Integer) obj);

        } else if (obj instanceof Short) {

            return new Value((Short) obj);
            
        } else if (obj instanceof Byte) {

            return new Value((Byte) obj);

        } else if (obj instanceof Boolean) {

            return new Value((Boolean) obj);

        } else if (obj instanceof Long) {

            return new Value((Long) obj);

        } else if (obj instanceof Double) {

            return new Value((Double) obj);

        } else if (obj instanceof Character) {

            return new Value((Character) obj);

        } else if (obj instanceof Float) {

            return new Value((Float) obj);

        } else if (obj instanceof AClass) {

            return new Value((AClass) obj);

        } else if (obj instanceof String) {

            return new Value((String) obj);

        } else if (obj instanceof Class) {
        	
        	return new Value(AClassFactory.getProductClass((Class<?>) obj));
        	
        }
        throw new ASMSupportException("cannot support type " + obj.getClass() + " for this method!");
    }

    @Override
    public void loadToStack(ProgramBlock block) {
        if (value == null) {
            block.getInsnHelper().push(this.getType());
            return;
        }

        if (this.aclass.getName().equals(int.class.getName())) {

            block.getInsnHelper().push((Integer) value);

        } else if (this.aclass.getName().equals(short.class.getName())) {

            block.getInsnHelper().push((Short) value);

        } else if (this.aclass.getName().equals(byte.class.getName())) {

            block.getInsnHelper().push((Byte) value);

        } else if (this.aclass.getName().equals(boolean.class.getName())) {

            block.getInsnHelper().push((Boolean) value);

        } else if (this.aclass.getName().equals(long.class.getName())) {

            block.getInsnHelper().push((Long) value);

        } else if (this.aclass.getName().equals(double.class.getName())) {

            block.getInsnHelper().push((Double) value);

        } else if (this.aclass.getName().equals(char.class.getName())) {

            block.getInsnHelper().push((Character) value);

        } else if (this.aclass.getName().equals(float.class.getName())) {

            block.getInsnHelper().push((Float) value);

        } else if (this.aclass.getName().equals(String.class.getName())) {

            block.getInsnHelper().push(value.toString());

        } else if (this.aclass.getName().equals(Class.class.getName())) {

            block.getInsnHelper().pushClass(((AClass) value).getType());

        }
    }

    @Override
    public void asArgument() {
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
        AClass curtyp = getParamterizedType();
        AClass objtyp = objval.getParamterizedType();

        if (value == null && objval.value == null) {
            if (curtyp.isChildOrEqual(objtyp)
                    || objtyp.isChildOrEqual(curtyp)) {
                return true;
            } else if (Modifier.isAbstract(curtyp.getModifiers())
                    && Modifier.isFinal(objtyp.getModifiers())) {
                return false;
            } else if (Modifier.isAbstract(objtyp.getModifiers())
                    && Modifier.isFinal(curtyp.getModifiers())) {
                return false;
            } else {
                return true;
            }
        } else if (value == null && objval.value != null
                || objval.value == null && value != null) {
            return false;
        } else {
            return value.equals(objval);
        }
    }

}
