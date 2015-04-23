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
package cn.wensiqun.asmsupport.core.operator.numerical.bit;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.numerical.AbstractNumerical;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.exception.ASMSupportException;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractBitwise extends AbstractNumerical {

    /**该操作是否被其他操作引用 */
    protected boolean byOtherUsed;
    
    protected AbstractBitwise(KernelProgramBlock block, Operator operator) {
        super(block, operator);
    }
    
    protected final void checkFactor(AClass ftrCls){
        if(ftrCls.isPrimitive() ||
           AClassUtils.isPrimitiveWrapAClass(ftrCls)){
           if(ftrCls.equals(AClassFactory.getType(boolean.class)) ||
              ftrCls.equals(AClassFactory.getType(float.class)) ||
              ftrCls.equals(AClassFactory.getType(double.class)) ||
              ftrCls.equals(AClassFactory.getType(Boolean.class)) ||
              ftrCls.equals(AClassFactory.getType(Float.class)) ||
              ftrCls.equals(AClassFactory.getType(Double.class))){
               throw new ASMSupportException("this operator " + getOperatorSymbol().getSymbol() + " cannot support for type " + ftrCls );
           }
        }
    }

    @Override
    public final void loadToStack(KernelProgramBlock block) {
        this.execute();
    }
    
    @Override
    public final void asArgument() {
        byOtherUsed = true;
        block.removeExe(this);
    }

    @Override
    public final void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ASMSupportException("the operator " + getOperatorSymbol().getSymbol() + " has not been used by other operator.");
        }
    }
    
    
}
