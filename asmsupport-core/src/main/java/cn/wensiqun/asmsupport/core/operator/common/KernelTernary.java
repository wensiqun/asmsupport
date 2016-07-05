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
package cn.wensiqun.asmsupport.core.operator.common;


import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.AbstractParamOperator;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;


/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class KernelTernary extends AbstractParamOperator {
    
    private KernelParam exp1;
    private KernelParam exp2;
    private KernelParam exp3;
    
    private boolean byOtherUsed;
    
    private IClass resultClass;
    
    protected KernelTernary(KernelProgramBlock block, KernelParam expression1,
            KernelParam expression2, KernelParam expression3) {
        super(block, Operator.TERNARY);
        this.exp1 = expression1;
        this.exp2 = expression2;
        this.exp3 = expression3;
    }

    @Override
    protected void verifyArgument() {
    	IClass expCls1 = exp1.getResultType();
    	IClass expCls2 = exp2.getResultType();
    	IClass expCls3 = exp3.getResultType();
        
        if(!expCls1.equals(getType(boolean.class)) &&
           !expCls1.equals(getType(Boolean.class))){
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

    private boolean checkExpression(IClass expCls1, IClass expCls2){
    	IClass expPrimCls1 = IClassUtils.getPrimitiveAClass(expCls1);
    	IClass expPrimCls2 = IClassUtils.getPrimitiveAClass(expCls2);
        
        if(expPrimCls1.equals(expPrimCls2)){
            resultClass = expPrimCls1;
            return true;
        }else if(expPrimCls1.isPrimitive() && expPrimCls2.isPrimitive()){
            if(expPrimCls1.equals(getType(boolean.class)) ||
               expPrimCls2.equals(getType(boolean.class))){
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
    protected void doExecute(MethodContext context) {
        Instructions instructions = context.getInstructions();
        Label posLbl = new Label();
        Label l1 = new Label();
        Label l2 = new Label();
    	if(exp1 instanceof Jumpable){
        	Jumpable jmp = (Jumpable) exp1;
        	jmp.jumpNegative(context, this, posLbl, l1);//.executeJump(Opcodes.JUMP_NEGATIVE, l1);
        }else{
        	exp1.loadToStack(context);
            instructions.unbox(exp1.getResultType().getType());
            instructions.ifZCmp(Instructions.EQ, l1);
        }

        instructions.mark(posLbl);
    	exp2.loadToStack(context);
        getParent().getMethod().getStack().pop();
        instructions.goTo(l2);
    	instructions.mark(l1);
    	
        exp3.loadToStack(context);
        instructions.mark(l2);
    }
    
    @Override
    public void execute(MethodContext context) {
        if(byOtherUsed){
            super.execute(context);
        }else{
            throw new RuntimeException("the logical ternary operator has not been used by other operator.");
        }
    }

    @Override
    public void loadToStack(MethodContext context) {
        this.execute(context);
    }

    @Override
    public IClass getResultType() {
        return resultClass;
    }

    @Override
    public void asArgument() {
        byOtherUsed = true;
        getParent().removeChild(this);
    }


}
