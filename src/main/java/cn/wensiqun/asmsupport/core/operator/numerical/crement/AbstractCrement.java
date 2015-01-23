/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.numerical.crement;

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.Operators;
import cn.wensiqun.asmsupport.core.operator.numerical.AbstractNumerical;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractCrement extends AbstractNumerical {

    private Crementable factor;
    
    /**
     * indicate the operators position, 
     * 
     * true  : like i++
     * false : like ++i;
     */
    private boolean post;
    
    protected AbstractCrement(ProgramBlockInternal block, Crementable factor, String operator, boolean post) {
        super(block);
        this.factor = factor;
        this.operator = operator;
        this.post = post;
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        execute();
    }

    @Override
    public void asArgument() {
        block.removeExe(this);
    }
    
    @Override
    protected void factorToStack() {
    	
    }

    @Override
    protected void initAdditionalProperties() {
        targetClass = factor.getParamterizedType();
    }

    @Override
    protected void verifyArgument() {
        AClass fatCls = factor.getParamterizedType();
        if(!AClassUtils.isArithmetical(fatCls)){
            throw new ArithmeticException("cannot execute arithmetic operator whit " + fatCls);
        }
    }

    @Override
    protected void checkAsArgument() {
        factor.asArgument();
    }

    @Override
    protected void doExecute()
    {
    	Type type = targetClass.getType();
    	boolean asArgument = !block.getQueue().contains(this); 
    	
    	if(factor instanceof LocalVariable && 
    	   Type.INT_TYPE.equals(targetClass.getType()))
        {
    		if(asArgument && post)
    			factor.loadToStack(block);
    		
            insnHelper.iinc(((LocalVariable)factor).getScopeLogicVar().getInitStartPos(), 
            		Operators.INCREMENT.equals(operator) ? 1 : -1);
            
            if(asArgument && !post)
            	factor.loadToStack(block);
        }
        else
        {
            AClass primitiveClass = AClassUtils.getPrimitiveAClass(targetClass);
            Type   primitiveType  = primitiveClass.getType();
            
            //factor load to stack
            factor.loadToStack(block);
            
            if(asArgument && post)
            	insnHelper.dup(type);
            
            //unbox
            autoCast(targetClass, primitiveClass, true);
            
            //load 1 to stack 
            getIncreaseValue().loadToStack(block);

            //generate xadd/xsub for decrement
            if(Operators.INCREMENT.equals(operator))
            	insnHelper.add(primitiveType);
            else
                insnHelper.sub(primitiveType);
            
            //box and cast
            autoCast(primitiveType.getSort() <= Type.INT ? AClass.INT_ACLASS : primitiveClass, targetClass, true);
            
            if(asArgument && !post)
            	insnHelper.dup(type);
         
             //将栈内的值存储到全局变量中
            //判读如果是静态变量
            insnHelper.commonPutField((ExplicitVariable) factor);
        }
    }
    
    private Value getIncreaseValue(){
        AClass type = factor.getParamterizedType();
        if(type.equals(AClass.DOUBLE_ACLASS) || 
           type.equals(AClass.DOUBLE_WRAP_ACLASS))
        {
            return Value.value(1d);
        }
        else if(type.equals(AClass.FLOAT_ACLASS) || 
                type.equals(AClass.FLOAT_WRAP_ACLASS))
        {
            return Value.value(1f);
        }
        else if(type.equals(AClass.LONG_ACLASS) || 
                type.equals(AClass.LONG_WRAP_ACLASS))
        {
            return Value.value(1l);
        }else{
            return Value.value(1);
        }
    }

}
