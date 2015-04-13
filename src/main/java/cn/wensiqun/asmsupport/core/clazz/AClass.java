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
package cn.wensiqun.asmsupport.core.clazz;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import cn.wensiqun.asmsupport.core.GetGlobalVariabled;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.StaticGlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.lang.ClassUtils;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


/**
 * Java Class的抽象
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AClass implements GetGlobalVariabled{

    /** java.lang.Boolean of AClass */
    public static final AClass BOOLEAN_WRAP_ACLASS = AClassFactory.defType(Boolean.class);

    /** java.lang.Byte of AClass */
    public static final AClass BYTE_WRAP_ACLASS = AClassFactory.defType(Byte.class);

    /** java.lang.Short of AClass */
    public static final AClass SHORT_WRAP_ACLASS = AClassFactory.defType(Short.class);

    /** java.lang.Character of AClass */
    public static final AClass CHARACTER_WRAP_ACLASS = AClassFactory.defType(Character.class);

    /** java.lang.Integer of AClass */
    public static final AClass INTEGER_WRAP_ACLASS = AClassFactory.defType(Integer.class);

    /** java.lang.Long of AClass */
    public static final AClass LONG_WRAP_ACLASS = AClassFactory.defType(Long.class);

    /** java.lang.Float of AClass */
    public static final AClass FLOAT_WRAP_ACLASS = AClassFactory.defType(Float.class);

    /** java.lang.Double of AClass */
    public static final AClass DOUBLE_WRAP_ACLASS = AClassFactory.defType(Double.class);

    /** boolean of AClass */
    public static final AClass BOOLEAN_ACLASS = AClassFactory.defType(boolean.class);

    /** byte of AClass */
    public static final AClass BYTE_ACLASS = AClassFactory.defType(byte.class);

    /** short of AClass */
    public static final AClass SHORT_ACLASS = AClassFactory.defType(short.class);

    /** char of AClass */
    public static final AClass CHAR_ACLASS = AClassFactory.defType(char.class);

    /** int of AClass */
    public static final AClass INT_ACLASS = AClassFactory.defType(int.class);

    /** long of AClass */
    public static final AClass LONG_ACLASS = AClassFactory.defType(long.class);

    /** float of AClass */
    public static final AClass FLOAT_ACLASS = AClassFactory.defType(float.class);

    /** double of AClass */
    public static final AClass DOUBLE_ACLASS = AClassFactory.defType(double.class);

    /** java.lang.Object of AClass */
    public static final AClass OBJECT_ACLASS = AClassFactory.defType(Object.class);

    /** java.lang.Cloneable of AClass */
    public static final AClass CLONEABLE_ACLASS = AClassFactory.defType(Cloneable.class);

    /** java.lang.Serializable of AClass */
    public static final AClass SERIALIZABLE_ACLASS = AClassFactory.defType(Serializable.class);

    /** java.lang.String of AClass */
    public static final AClass STRING_ACLASS = AClassFactory.defType(String.class);

    /** java.lang.Iterator of AClass */
    public static final AClass ITERATOR_ACLASS = AClassFactory.defType(Iterator.class);

    /** java.lang.Exception of AClass */
    public static final AClass EXCEPTION_ACLASS = AClassFactory.defType(Exception.class);

    /** java.lang.Class of AClass */
    public static final AClass CLASS_ACLASS = AClassFactory.defType(Class.class);

    /** java.lang.Throwable of AClass */
    public static final AClass THROWABLE_ACLASS = AClassFactory.defType(Throwable.class);
    
    /** java.lang.Void of AClass */
    public static final AClass VOID_ACLASS = AClassFactory.defType(void.class);
    
    /**
     * Class name
     */
    protected String name;

    /** Class对应的java的版本 */
    protected int version;

    /** Class的修饰符值 */
    protected int mod;

    /** class signature */
    // private String signature;

    /** Class的父类 */
    protected Class<?> superClass;

    /** Class的接口 */
    protected Class<?>[] interfaces;
    
    /** 当前Class对应的Type类型 */
    protected Type type;
    
    /** 当前的Class的包名 */
    protected String pkg;
    
    /**
     * 判断当前Class是否是数组
     * @return 如果当前Class是数组返回true, 否则返回false
     */
    public abstract boolean isArray();
    
    /**
     * 如果当前Class是数组返回当前数组的维度
     * 
     * @return 如果当前Class是数组返回当前数组的维度, 否则返回-1
     */
    public abstract int getDimension();

    /**
     * 获取包名
     * 
     * @return 包的全名
     */
    public String getPackage() {
        if(pkg == null){
            pkg = ClassUtils.getPackageName(name);
        }
        return pkg;
    }

    /**
     * 获取当前Class的名字
     * 
     * @return Class全名
     */
    public String getName() {
        return name;
    }

    /**
     * 获取当前Class的修饰符, 修饰符表示使用ASM的方法表示(see {@link Opcodes})
     * @return 修饰符所表示的值
     */
    public int getModifiers() {
        return mod;
    }

    /**
     * 获取当前Class的java版本, 版本的表示使用ASM的方法表示(see {@link Opcodes})
     * 
     * @return 
     */
    public int getVersion() {
        return version;
    }

    /**
     * 获取父类
     * @return 父类
     */
    public Class<?> getSuperClass() {
        return superClass;
    }

    /**
     * 所实现的直接接口
     * @return 接口Class的数组
     */
    public Class<?>[] getInterfaces() {
    	if(interfaces == null){
    		interfaces = new Class[0];
    	}
        return interfaces;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        
        if(obj instanceof AClass){
            if(name.equals(((AClass)obj).name)){
                return true;
            }
        }else{
            return false;
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        return getDescription().hashCode();
    }

    public abstract String getDescription();
    
    /**
     * 判断当前类是否与另一个类相等或者是另一个类的子类
     * @param cls
     * @return true表示相等或者是其子类, 否则为false
     */
    public boolean isChildOrEqual(AClass cls) {
        String clsName = cls.getName();
        if (getName().equals(clsName)) {
            return true;
        }
        
        if(cls instanceof ArrayClass){
            return false;
        }
        
        if(this.isInterface() && clsName.equals(Object.class.getName())){
        	return true;
        }

        Class<?> superCls = getSuperClass();
        Class<?>[] tempInterfaces = getInterfaces();

        boolean interResult = false;
        
        if(superCls != null){
            interResult = AClassFactory.defType(superCls).isChildOrEqual(cls);
            if(interResult){
                return true;
            }
        }
        
        if(tempInterfaces != null){
            for(Class<?> c : tempInterfaces){
                interResult = AClassFactory.defType(c).isChildOrEqual(cls);
                if(interResult){
                    return true;
                }
            }
        }
        
        return false;
    }

    /**
     * Get all global variable metadata, that possible contain static and non-static field.
     * 
     * @param name
     * @return
     */
    public abstract List<GlobalVariableMeta> getGlobalVariableMeta(String name);
    
    @Override
    public final StaticGlobalVariable field(String name) {
        
        List<GlobalVariableMeta> metas = getGlobalVariableMeta(name);
        if(metas.isEmpty()) {
            throw new ASMSupportException("No such field " + name);
        }
        
        GlobalVariableMeta found = null;
        StringBuilder errorSuffix = new StringBuilder();
        for(GlobalVariableMeta meta : metas) {
            if(ModifierUtils.isStatic(meta.getModifiers())) {
                if(found != null) {
                    errorSuffix.append(found.getActuallyOwnerType()).append(',')
                               .append(meta.getActuallyOwnerType());
                    throw new ASMSupportException("The field '" + name + "' is ambiguous, found it in class [" + errorSuffix + "]");
                }
                found = meta;
            }
        }
        
        if(found == null) {
            throw new ASMSupportException("No such field " + name);
        }
        
        return new StaticGlobalVariable(this, found);
    }
    
    /**
     * 获取当前Class对应的ASM中的Type
     * @return
     */
    public Type getType() {
        if(type == null){
            type = Type.getType(getDescription());
        }
        return type;
    }

    /**
     * 获取相对于某类可见的构造方法
     * @param aclass 需要调用该构造方法的类
     * @param parameterTypes 构造方法的的参数类型
     * @return
     */
    public final AMethodMeta availableConstructor(AClass aclass,
            AClass[] parameterTypes){
    	return availableMethod(aclass, ASConstant.INIT, parameterTypes);
    }

    /**
     * 获取相对于某类可见的方法
     * @param aclass 需要调用该构造方法的类
     * @param name 方法名
     * @param parameterTypes 方法的参数类型
     * @return
     */
    public abstract AMethodMeta availableMethod(AClass where, String name,
            AClass[] parameterTypes);

    /*-****************************************
     *               选择方法                                              *
     ******************************************/
    
    /**
     * 获取父类的方法
     * @param methodName 方法名
     * @param arguments 
     * @return
     */
    public AMethodMeta getSuperMethod(String methodName, AClass[] parameterTypes){
        AClass superCls = AClassFactory.defType(superClass);
        return superCls.availableMethod(null, methodName, parameterTypes);
    }
    
    /**
     * 
     * @param parameterTypes
     * @return
     */
    public AMethodMeta getSuperConstructor(AClass[] parameterTypes){
        AClass superCls = AClassFactory.defType(superClass);
        return superCls.availableConstructor(null, parameterTypes);
    }
    
    /**
     * 
     * @return 
     */
    public abstract boolean isPrimitive();

    public final boolean isInterface() {
        return (getModifiers() & Opcodes.ACC_INTERFACE) != 0;
    }

    public final boolean isAbstract() {
        return (getModifiers() & Opcodes.ACC_ABSTRACT) != 0;
    }
    
    /**
     * 返回类型转换优先级顺序
     * @return
     */
    public abstract int getCastOrder();
    
    /**
     * 获取默认值
     * @return
     */
    public final Value getDefaultValue(){
        return Value.defaultValue(this);
    }
    
    public abstract boolean existStaticInitBlock();

    
    
    @Override
    public String toString() {
        return getName();
    }
    
    
    
    
}
