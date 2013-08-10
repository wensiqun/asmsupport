/**
 * 
 */
package jw.asmsupport.operators.numerical.crement;

import jw.asmsupport.Crementable;
import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.definition.variable.GlobalVariable;
import jw.asmsupport.definition.variable.LocalVariable;
import jw.asmsupport.definition.variable.MemberVariable;
import jw.asmsupport.operators.Operators;
import jw.asmsupport.operators.numerical.arithmetic.AbstractArithmetic;
import jw.asmsupport.operators.numerical.arithmetic.Addition;
import jw.asmsupport.operators.util.OperatorFactory;

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
            
            MemberVariable mvar = (MemberVariable) factor;
            assigner = block.assign(mvar, arithOperator);
            
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
