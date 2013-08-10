/**
 * 
 */
package jw.asmsupport.operators.numerical.crement;

import jw.asmsupport.Crementable;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.definition.value.Value;
import jw.asmsupport.operators.assign.Assigner;
import jw.asmsupport.operators.numerical.AbstractNumerical;
import jw.asmsupport.utils.AClassUtils;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractCrement extends AbstractNumerical {

    protected Crementable factor;
    
    protected Assigner assigner;
    
    protected AbstractCrement(ProgramBlock block, Crementable factor) {
        super(block);
        this.factor = factor;
    }
    
    protected Value getValue(){
        AClass type = factor.getParamterizedType();
        if(type.equals(AClass.DOUBLE_ACLASS)){
            return Value.value(1d);
        }else if(type.equals(AClass.FLOAT_ACLASS)){
            return Value.value(1f);
        }else if(type.equals(AClass.LONG_ACLASS)){
            return Value.value(1l);
        }else{
            return Value.value(1);
        }
    }
    
    public abstract void after();
    
    public abstract void before();
    

    @Override
    public void loadToStack(ProgramBlock block) {
        factor.loadToStack(block);
    }

    @Override
    public void asArgument() {
        block.removeExe(this);
    }

    @Override
    protected void factorToStack() {
        factor.loadToStack(block);
        insnHelper.unbox(factor.getParamterizedType().getType());
    }

    @Override
    protected void verifyArgument() {
        AClass fatCls = factor.getParamterizedType();
        if(!AClassUtils.arithmetical(fatCls)){
            throw new ArithmeticException("cannot execute arithmetic operator whit " + fatCls);
        }
    }

    @Override
    protected void checkOutCrement() {
        if(factor instanceof AbstractCrement){
            allCrement.add((AbstractCrement) factor);
        }
    }

    @Override
    protected void checkAsArgument() {
        factor.asArgument();
    }

    @Override
    protected void afterInitProperties() {
        AClass fatCls = factor.getParamterizedType();
        resultClass = AClassUtils.getPrimitiveAClass(fatCls);
    }

}
