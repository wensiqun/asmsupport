package cn.wensiqun.asmsupport.standard.def.clazz;

import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.FieldVarGetter;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;


/**
 * Java Class的抽象
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public interface IClass<F extends IFieldVar> extends FieldVarGetter<F>{
	
    /**
     * 判断当前Class是否是数组
     * @return 如果当前Class是数组返回true, 否则返回false
     */
    boolean isArray();
    
    /**
     * 如果当前Class是数组返回当前数组的维度
     * 
     * @return 如果当前Class是数组返回当前数组的维度, 否则返回-1
     */
    int getDimension();

    /**
     * 获取包名
     * 
     * @return 包的全名
     */
    String getPackage();

    /**
     * 获取当前Class的名字
     * 
     * @return Class全名
     */
    String getName();

    /**
     * 获取当前Class的修饰符, 修饰符表示使用ASM的方法表示(see {@link Opcodes})
     * @return 修饰符所表示的值
     */
    int getModifiers();

    /**
     * 获取当前Class的java版本, 版本的表示使用ASM的方法表示(see {@link Opcodes})
     * 
     * @return 
     */
    int getVersion();

    /**
     * 获取父类
     * @return 父类
     */
    Class<?> getSuperClass();

    /**
     * 所实现的直接接口
     * @return 接口Class的数组
     */
    Class<?>[] getInterfaces();

    String getDescription();
    
    /**
     * Get all global variable metadata, that possible contain static and non-static field.
     * 
     * @param name
     * @return
     */
    Field getField(String name);
    
    
    /**
     * 获取当前Class对应的ASM中的Type
     * @return
     */
    Type getType();

    /**
     * 
     * @return 
     */
    boolean isPrimitive();

    boolean isInterface();

    boolean isAbstract();
    
    /**
     * 返回类型转换优先级顺序
     * @return
     */
    int getCastOrder();
    
    /**
     * 获取默认值
     * @return
     */
    Value getDefaultValue();
    
    boolean existStaticInitBlock();
    
}
