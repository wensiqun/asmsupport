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
package cn.wensiqun.asmsupport.standard.clazz;

import java.util.List;

import cn.wensiqun.asmsupport.core.FieldGetter;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.StaticGlobalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.jls.TypeUtils;
import cn.wensiqun.asmsupport.core.utils.lang.ClassUtils;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.var.Field;


/**
 * Java Class的抽象
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AClass implements FieldGetter{
	
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
    public boolean isChildOrEqual(AClass otherType) {
        return TypeUtils.isSubtyping(this, otherType);
    }

    /**
     * Get all global variable metadata, that possible contain static and non-static field.
     * 
     * @param name
     * @return
     */
    public abstract List<Field> getGlobalVariableMeta(String name);
    
    @Override
    public final StaticGlobalVariable field(String name) {
        
        List<Field> fields = getGlobalVariableMeta(name);
        if(fields.isEmpty()) {
            throw new ASMSupportException("No such field " + name);
        }
        
        Field found = null;
        StringBuilder errorSuffix = new StringBuilder();
        for(Field field : fields) {
            if(ModifierUtils.isStatic(field.getModifiers())) {
                if(found != null) {
                    errorSuffix.append(found.getActuallyOwnerType()).append(',')
                               .append(field.getActuallyOwnerType());
                    throw new ASMSupportException("The field '" + name + "' is ambiguous, found it in class [" + errorSuffix + "]");
                }
                found = field;
            }
        }
        
        if(found == null) {
            throw new ASMSupportException("No such field " + name);
        }
        
        return new StaticGlobalVariable(this, found.getDeclaringClass(), found.getActuallyOwnerType(),
        		found.getFormerType(), found.getModifiers(), found.getName());
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

    @Override
    public String toString() {
        return getName();
    }
    
    public abstract boolean existStaticInitBlock();
    
}
