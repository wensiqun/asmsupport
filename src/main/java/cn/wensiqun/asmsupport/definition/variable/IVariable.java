/**
 * 
 */
package cn.wensiqun.asmsupport.definition.variable;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import cn.wensiqun.asmsupport.GetGlobalVariabled;
import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.operators.AbstractOperator;


/**
 * 
 * 变量的接口
 * @author 温斯群(Joe Wen)
 *
 */
public interface IVariable extends Parameterized, GetGlobalVariabled{
    
    public static final ClassBidiMap PRIMITIVE_WRAP_MAP = new ClassBidiMap()
        .put(AClassFactory.getProductClass(boolean.class), AClassFactory.getProductClass(Boolean.class))
        .put(AClassFactory.getProductClass(byte.class), AClassFactory.getProductClass(Byte.class))
        .put(AClassFactory.getProductClass(int.class), AClassFactory.getProductClass(Integer.class))
        .put(AClassFactory.getProductClass(short.class), AClassFactory.getProductClass(Short.class))
        .put(AClassFactory.getProductClass(float.class), AClassFactory.getProductClass(Float.class))
        .put(AClassFactory.getProductClass(char.class), AClassFactory.getProductClass(Character.class))
        .put(AClassFactory.getProductClass(long.class), AClassFactory.getProductClass(Long.class))
        .put(AClassFactory.getProductClass(double.class), AClassFactory.getProductClass(Double.class));
    
    public static class ClassBidiMap {

        private BidiMap bidiMap = new DualHashBidiMap();

        private ClassBidiMap() {
        }

        private ClassBidiMap put(AClass key, AClass value) {
            bidiMap.put(key, value);
            return this;
        }

        public AClass getKey(AClass value) {
            return (AClass) bidiMap.getKey(value);
        }

        public AClass get(AClass key) {
            return (AClass) bidiMap.get(key);
        }

    }
    
    /**
     * 当前变量对于传入的操作是否可用
     * @param operator
     */
    boolean availableFor(AbstractOperator operator);
    
    /**
     * 获取当前变量的VariableEntity
     * @return
     */
    VariableMeta getVariableEntity();

}
