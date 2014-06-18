package cn.wensiqun.asmsupport.operators.relational;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.asm.InstructionHelper;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.block.control.ControlType;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.operators.AbstractOperator;
import cn.wensiqun.asmsupport.operators.Jumpable;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractRelational extends AbstractOperator implements
     Jumpable  {

    private static Log log = LogFactory.getLog(AbstractRelational.class);
    
    /**算数因子1 */
    protected Parameterized factor1;

    /**算数因子2 */
    protected Parameterized factor2;
    
    /**该操作是否被其他操作引用 */
    private boolean byOtherUsed;
    
    protected String operator;
    
    protected AClass targetClass;
    
    protected Label trueLbl;
    protected Label falseLbl;
    
    protected AbstractRelational(ProgramBlock block, Parameterized factor1, Parameterized factor2) {
        super(block);
        this.factor1 = factor1;
        this.factor2 = factor2;

        falseLbl = new Label();
        trueLbl = new Label();
    }

    protected final void checkFactorForNumerical(AClass ftrCls){
        if(!ftrCls.isPrimitive() || 
           ftrCls.equals(AClass.BOOLEAN_ACLASS)){
            throw new ASMSupportException("this operator " + operator + " cannot support for type " + ftrCls );
        }
    }
    
    @Override
    public void loadToStack(ProgramBlock block) {
        this.execute();
    }

    @Override
    public void execute() {
        if(byOtherUsed){
            if(log.isDebugEnabled()){
            	log.debug("run operator " + factor1.getParamterizedType() + " " + operator + " " + factor2.getParamterizedType());
            }
            super.execute();
        }else{
            throw new ASMSupportException("the operator " + factor1.getParamterizedType() + " " + operator + " " + 
                                          factor2.getParamterizedType() + " has not been used by other operator.");
        }
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

    protected abstract void factorsToStack();

    @Override
	public void setJumpLable(Label lbl) {
		this.falseLbl = lbl;
	}

	@Override
    protected void executing() {
		instructionGenerate();
        defaultStackOperator();
    }
	
	/**
	 * 
	 */
	protected void instructionGenerate(){
		factorsToStack();
        relationalOperator();

        MethodVisitor mv = insnHelper.getMv();
        //push true to stack
        mv.visitInsn(Opcodes.ICONST_0 + 1);
        mv.visitJumpInsn(Opcodes.GOTO, trueLbl);
        mv.visitLabel(falseLbl);
        
        //push false to stack
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitLabel(trueLbl);
	}
	
	/**
	 * 
	 */
	protected void defaultStackOperator(){
		block.getMethod().getStack().pop();
        block.getMethod().getStack().pop();
        block.getMethod().getStack().push(Type.INT_TYPE);
	}
	
	@Override
	public final void executeAndJump(ControlType ctl){
        factorsToStack();
        if(ctl.equals(ControlType.IF)){
        	relationalOperator();
        }else if(ctl.equals(ControlType.LOOP)){
        	relationalOperatorWithInLoopCondition();
        }
	}
	
	protected abstract void relationalOperator();
	
	protected abstract void relationalOperatorWithInLoopCondition();
    
	/*private void stackOperator(){
        Stack stack = insnHelper.getStack();
        stack.pop();
        stack.printState();
        stack.pop();
        stack.printState();
        stack.push(Type.INT_TYPE);
        stack.printState();
	}*/
	
    protected void ifCmp(final Type type, final int mode, final Label label) {
        MethodVisitor mv = insnHelper.getMv();
        //Stack stack = insnHelper.getStack();
        switch (type.getSort()) {
            case Type.LONG:
                mv.visitInsn(Opcodes.LCMP);
                //stackOperator();
                break;
            case Type.DOUBLE:
                mv.visitInsn(mode == InstructionHelper.GE || mode == InstructionHelper.GT ? Opcodes.DCMPG : Opcodes.DCMPL);
                //stackOperator();
                break;
            case Type.FLOAT:
                mv.visitInsn(mode == InstructionHelper.GE || mode == InstructionHelper.GT ? Opcodes.FCMPG : Opcodes.FCMPL);
                //stackOperator();
                break;
            case Type.ARRAY:
            case Type.OBJECT:
                switch (mode) {
                    case InstructionHelper.EQ:
                        mv.visitJumpInsn(Opcodes.IF_ACMPEQ, label);
                        /*stack.pop();
                        stack.printState();
                        stack.pop();
                        stack.printState();*/
                        return;
                    case InstructionHelper.NE:
                        mv.visitJumpInsn(Opcodes.IF_ACMPNE, label);
                        /*stack.pop();
                        stack.printState();
                        stack.pop();
                        stack.printState();*/
                        return;
                }
                throw new IllegalArgumentException("Bad comparison for type "
                        + type);
            default:
                int intOp = -1;
                switch (mode) {
                    case InstructionHelper.EQ:
                        intOp = Opcodes.IF_ICMPEQ;
                        break;
                    case InstructionHelper.NE:
                        intOp = Opcodes.IF_ICMPNE;
                        break;
                    case InstructionHelper.GE:
                        intOp = Opcodes.IF_ICMPGE;
                        break;
                    case InstructionHelper.LT:
                        intOp = Opcodes.IF_ICMPLT;
                        break;
                    case InstructionHelper.LE:
                        intOp = Opcodes.IF_ICMPLE;
                        break;
                    case InstructionHelper.GT:
                        intOp = Opcodes.IF_ICMPGT;
                        break;
                }
                mv.visitJumpInsn(intOp, label);
                /*stack.pop();
                stack.printState();
                stack.pop();
                stack.printState();*/
                return;
        }
        mv.visitJumpInsn(mode, label);
        /*stack.pop();
        stack.printState();*/
    }
}
