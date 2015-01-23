/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.numerical.ternary;


import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.classes.control.ControlType;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;


/**
 * @author 温斯群(Joe Wen)
 *
 */
public class TernaryOperator extends AbstractOperator implements Parameterized{
    
    private Parameterized exp1;
    private Parameterized exp2;
    private Parameterized exp3;
    
    private boolean byOtherUsed;
    
    private AClass resultClass;
    
    protected TernaryOperator(ProgramBlockInternal block, Parameterized expression1,
            Parameterized expression2, Parameterized expression3) {
        super(block);
        this.exp1 = expression1;
        this.exp2 = expression2;
        this.exp3 = expression3;
    }

    @Override
    protected void verifyArgument() {
        AClass expCls1 = exp1.getParamterizedType();
        AClass expCls2 = exp2.getParamterizedType();
        AClass expCls3 = exp3.getParamterizedType();
        
        if(!expCls1.equals(AClass.BOOLEAN_ACLASS) && !expCls1.equals(AClass.BOOLEAN_WRAP_ACLASS)){
            throw new ASMSupportException("the first expression type of ternary operator must by boolean or Boolean!");
        }
        
        if(!checkExpression(expCls2, expCls3)){
            throw new ASMSupportException("cannot convert!");
        }
    }

    @Override
    protected void checkAsArgument() {
         exp1.asArgument();
         exp2.asArgument();
         exp3.asArgument();
    }

    private boolean checkExpression(AClass expCls1, AClass expCls2){
        AClass expPrimCls1 = AClassUtils.getPrimitiveAClass(expCls1);
        AClass expPrimCls2 = AClassUtils.getPrimitiveAClass(expCls2);
        
        if(expPrimCls1.equals(expPrimCls2)){
            resultClass = expPrimCls1;
            return true;
        }else if(expPrimCls1.isPrimitive() && expPrimCls2.isPrimitive()){
            if(expPrimCls1.equals(AClass.BOOLEAN_ACLASS) || expPrimCls2.equals(AClass.BOOLEAN_ACLASS)){
                return false;
            }else{
                if(expPrimCls1.getCastOrder() > expPrimCls2.getCastOrder()){
                    resultClass = expPrimCls1;
                }else{
                    resultClass = expPrimCls2;
                }
                return true;
            }
        }else if(expPrimCls1.isChildOrEqual(expPrimCls2)){
            resultClass = expPrimCls2;
            return true;
        }else if(expPrimCls2.isChildOrEqual(expPrimCls1)){
            resultClass = expPrimCls1;
            return true;
        }
        return false;
    }
    
    @Override
    protected void doExecute() {
        Label l1 = new Label();
        Label l2 = new Label();
    	if(exp1 instanceof Jumpable){
        	Jumpable jmp = (Jumpable) exp1;
        	jmp.setJumpLable(l1);
        	jmp.executeAndJump(ControlType.IF);
        }else{
        	exp1.loadToStack(block);
            insnHelper.unbox(exp1.getParamterizedType().getType());
            insnHelper.ifZCmp(InstructionHelper.EQ, l1);
        }
    	
    	exp2.loadToStack(block);
        block.getMethod().getStack().pop();
        insnHelper.goTo(l2);
    	insnHelper.mark(l1);
    	
        exp3.loadToStack(block);
        insnHelper.mark(l2);
    }
    
    @Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new RuntimeException("the logical ternary operator has not been used by other operator.");
        }
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        this.execute();
    }

    @Override
    public AClass getParamterizedType() {
        return resultClass;
    }

    @Override
    public void asArgument() {
        byOtherUsed = true;
        block.removeExe(this);
    }


}
