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
package cn.wensiqun.asmsupport.core.operator.numerical.arithmetic;

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.operator.numerical.AbstractNumerical;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.standard.clazz.AClass;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractArithmetic extends AbstractNumerical implements
        InternalParameterized {

    /**算数因子1 */
    protected InternalParameterized factor1;

    /**算数因子2 */
    protected InternalParameterized factor2;
    
    /**该操作是否被其他操作引用 */
    private boolean byOtherUsed;

    protected AbstractArithmetic(ProgramBlockInternal block, InternalParameterized factor1, InternalParameterized factor2) {
        super(block);
        this.factor1 = factor1;
        this.factor2 = factor2;
    }
    
    @Override
    public void loadToStack(ProgramBlockInternal block) {
        this.execute();
    }
    
    @Override
    protected void verifyArgument() {
        AClass f1cls = factor1.getResultType();
        AClass f2cls = factor2.getResultType();
        if(!AClassUtils.isArithmetical(f1cls) || !AClassUtils.isArithmetical(f2cls)){
            throw new ArithmeticException("cannot execute arithmetic operator whit " + f1cls + " and " + f2cls);
        }
    }

    @Override
    protected void checkAsArgument() {
        factor1.asArgument();
        factor2.asArgument();
    }

    @Override
    protected void initAdditionalProperties() {
        
        targetClass = AClassUtils.getArithmeticalResultType(factor1.getResultType(), factor2.getResultType());
        
        if(factor1 instanceof Value)
            ((Value)factor1).convert(targetClass);
        
        if(factor2 instanceof Value)
            ((Value)factor2).convert(targetClass);
    }
    
    @Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ArithmeticException("the arithmetic operator " + factor1.getResultType() + " " + operator + " " + 
                                          factor2.getResultType() + " has not been used by other operator.");
        }
    }

    @Override
    protected void factorToStack() {
        pushFactorToStack(factor1);
        pushFactorToStack(factor2);
        
        /*AClass ftrCls1 = factor1.getParamterizedType();
        AClass ftrCls2 = factor2.getParamterizedType();
        
        log.debug("push the first arithmetic factor to stack");
        factor1.loadToStack(block);
        
        if(!ftrCls1.isPrimitive()){
            log.debug("unbox " + ftrCls1);
            insnHelper.unbox(ftrCls1.getType());
        }
        
        if(!ftrCls1.equals(resultClass) &&
            resultClass.getCastOrder() > AClassFactory.defType(int.class).getCastOrder() ){
            log.debug("cast arithmetic factor from " + ftrCls1 + " to " + resultClass);
            insnHelper.cast(ftrCls1.getType(), resultClass.getType());    
        }

        log.debug("push the second arithmetic factor to stack");
        factor2.loadToStack(block);
        
        if(!ftrCls2.isPrimitive()){
            log.debug("unbox " + ftrCls2);
            insnHelper.unbox(ftrCls2.getType());
        }
        
        if(!ftrCls2.equals(resultClass) &&
            resultClass.getCastOrder() > AClassFactory.defType(int.class).getCastOrder() ){
            log.debug("cast arithmetic factor from " + ftrCls2 + " to " + resultClass);
            insnHelper.cast(ftrCls2.getType(), resultClass.getType());    
        }*/
    }
    
    @Override
    public void asArgument() {
        //由参数使用者调用
        block.removeExe(this);
        //指明是被其他操作引用
        byOtherUsed = true;
    }
    
}
