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
package cn.wensiqun.asmsupport.core.definition.variable;


import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;


/**
 * 全局变量。这个class只用于方法体内操作变量
 * @author 温斯群(Joe Wen)
 */
public class SuperVariable extends ImplicitVariable{

    private GlobalVariableMeta globalVariableMeta;
    
    /**
     * 通过Class获取的全局变量
     * @param insnHelper
     */
    public SuperVariable(AClass aclass) {
        this.globalVariableMeta = new GlobalVariableMeta(
                AClassFactory.deftype(aclass.getSuperClass()), 
                AClassFactory.deftype(aclass.getSuperClass()), 
                AClassFactory.deftype(aclass.getSuperClass()), 
                Opcodes.ACC_FINAL, ASConstant.SUPER);
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        block.getInsnHelper().loadThis();
    }

    @Override
    public boolean availableFor(AbstractOperator operator) {
        return true;
    }


    @Override
    public AClass getParamterizedType() {
        return globalVariableMeta.getDeclareType();
    }

    @Override
    public VariableMeta getVariableMeta() {
        return globalVariableMeta;
    }

    /*@Override
    public GlobalVariable getGlobalVariable(String name) {
        return getGlobalVariable(globalVariableMeta.getDeclareType(), name);
    }*/

    @Override
    public String toString() {
        return ASConstant.SUPER;
    }
   
}
