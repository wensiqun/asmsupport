/**
 * 
 */
package cn.wensiqun.asmsupport.operators.numerical.crement;

import cn.wensiqun.asmsupport.Crementable;
import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.operators.Operators;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.AbstractArithmetic;
import cn.wensiqun.asmsupport.operators.numerical.arithmetic.Addition;
import cn.wensiqun.asmsupport.operators.util.OperatorFactory;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractIncrement extends AbstractCrement {

    protected AbstractIncrement(ProgramBlock block, Crementable factor) {
        super(block, factor);
        operator = Operators.INCREMENT;
        AClass ftrcls = factor.getParamterizedType();
        
        if(factor instanceof GlobalVariable ||
           ((ftrcls.getCastOrder() <= AClass.BOOLEAN_ACLASS.getCastOrder() || 
            ftrcls.getCastOrder() > AClass.INT_ACLASS.getCastOrder()) &&
            factor instanceof LocalVariable)){
            
        	AbstractArithmetic arithOperator = OperatorFactory.newOperator(Addition.class, 
            		new Class<?>[]{ProgramBlock.class, Parameterized.class, Parameterized.class},
            		block, factor, getValue());
            
            arithOperator.prepare();
            
            ExplicitVariable variable = (ExplicitVariable) factor;
            assigner = block.assign(variable, arithOperator);
            
            assigner.prepare();
            block.removeExe(assigner);
        }
        
    }

    @Override
    public void executing() {
        if(assigner == null){
            LocalVariable lv = (LocalVariable) factor;
            //insnHelper.iinc(lv.getScopeLogicVar().getPosition()[0], 1);
            insnHelper.iinc(lv.getScopeLogicVar().getInitStartPos(), 1);
        } else {
            assigner.execute();
        }
    }
}
