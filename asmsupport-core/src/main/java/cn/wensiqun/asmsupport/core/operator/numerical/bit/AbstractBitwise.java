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
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.numerical.AbstractNumerical;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class AbstractBitwise extends AbstractNumerical {

    protected boolean byOtherUsed;
    
    protected AbstractBitwise(KernelProgramBlock block, Operator operator) {
        super(block, operator);
    }
    
    protected final void checkFactor(IClass ftrCls){
        if(ftrCls.isPrimitive() ||
           IClassUtils.isPrimitiveWrapAClass(ftrCls)){
           if(ftrCls.equals(getType(boolean.class)) ||
              ftrCls.equals(getType(float.class)) ||
              ftrCls.equals(getType(double.class)) ||
              ftrCls.equals(getType(Boolean.class)) ||
              ftrCls.equals(getType(Float.class)) ||
              ftrCls.equals(getType(Double.class))){
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
