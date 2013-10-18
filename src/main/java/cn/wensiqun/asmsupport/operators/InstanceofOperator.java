/**
 * 
 */
package cn.wensiqun.asmsupport.operators;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.exception.ASMSupportException;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class InstanceofOperator extends AbstractOperator implements Parameterized{

    private AClass type; 
    private Parameterized obj;
    private boolean byOtherUsed;
    
    protected InstanceofOperator(ProgramBlock block, Parameterized obj, AClass type) {
        super(block);
        this.obj = obj;
        this.type = type;
    }

    @Override
    protected void verifyArgument() {
        if(obj.getParamterizedType().isPrimitive()){
            throw new ASMSupportException("Incompatible conditional operand types " + obj.getParamterizedType() + " int and " + type);
        }
    }

    @Override
    protected void checkOutCrement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void checkAsArgument() {
        obj.asArgument();
    }

    @Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ASMSupportException("the instanceof operator has not been used by other operator.");
        }
    }

    @Override
    protected void executing() {
        obj.loadToStack(block);
        insnHelper.instanceOf(type.getType());
    }

    @Override
    public void loadToStack(ProgramBlock block) {
        this.execute();
    }

    @Override
    public AClass getParamterizedType() {
        return AClass.BOOLEAN_ACLASS;
    }

    @Override
    public void asArgument() {
        byOtherUsed = true;
        block.removeExe(this);
    }

}
