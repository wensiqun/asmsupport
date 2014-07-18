package cn.wensiqun.asmsupport.block.control;


import org.objectweb.asm.Label;

import cn.wensiqun.asmsupport.Executable;
import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.asm.InstructionHelper;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.block.body.LocalVariableBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.clazz.ArrayClass;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.operators.Jumpable;
import cn.wensiqun.asmsupport.operators.asmdirect.GOTO;
import cn.wensiqun.asmsupport.operators.asmdirect.Marker;
import cn.wensiqun.asmsupport.operators.asmdirect.NOP;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class ForEachLoop extends ProgramBlock implements ILoop, LocalVariableBody{
    
    private ExplicitVariable iteratorVar;
    
    private Parameterized condition;
    
    private Label startLbl = new Label();
    private Label conditionLbl = new Label();
    private Label continueLbl = new Label();
    private Label endLbl = new Label();
    
    public ForEachLoop(ExplicitVariable iteratorVar) {
        super();
        this.iteratorVar = iteratorVar;
        checkMember(iteratorVar);
    }
    
    private void checkMember(ExplicitVariable member){
        AClass cls = member.getParamterizedType();
        if(!cls.isArray() &&
           !cls.isChildOrEqual(AClassFactory.getProductClass(Iterable.class))){
            throw new ASMSupportException("The object must be an array or an object that implements the new Iterable interface.");
        }
    }

    @Override
    public void executing() {
    	
        for(Executable exe : getExecuteQueue()){
            exe.execute();
        }
        
        if(condition instanceof Jumpable){
        	Jumpable jmp = (Jumpable) condition;
        	jmp.setJumpLable(startLbl);
        	jmp.executeAndJump(ControlType.LOOP);
        }else{
            condition.loadToStack(this);
            insnHelper.unbox(condition.getParamterizedType().getType());
            insnHelper.ifZCmp(InstructionHelper.NE, startLbl);
        }
        insnHelper.mark(endLbl);
    }

    @Override
    protected void init() {
    	
    }
    
    @Override
    public final void generateInsn() {
        new NOP(getExecuteBlock());
        if(iteratorVar.getParamterizedType().isArray()){
            final LocalVariable i = createVariable(null, AClass.INT_ACLASS, true, Value.value(0));
            
            new GOTO(getExecuteBlock(), conditionLbl);
            new NOP(getExecuteBlock());
            new Marker(getExecuteBlock(), startLbl);
            new NOP(getExecuteBlock());
            
            LocalVariable obj = createVariable(null, ((ArrayClass)iteratorVar.getParamterizedType()).getNextDimType(), true, arrayLoad(iteratorVar, i) );
            body(obj);

            new Marker(getExecuteBlock(), continueLbl);
            afterInc(i);
            new Marker(getExecuteBlock(), conditionLbl);
            condition = lessThan(i, arrayLength(iteratorVar));
            //((LessThan)condition).setJumpLable(startLbl);
        }else{
        	final LocalVariable itr = createVariable(null, AClass.ITERATOR_ACLASS, true, invoke(iteratorVar, "iterator"));
            new GOTO(getExecuteBlock(), conditionLbl);
        	
        	new Marker(getExecuteBlock(), startLbl);
            new NOP(getExecuteBlock());

            LocalVariable obj = createVariable(null, AClass.OBJECT_ACLASS, true, invoke(itr, "next"));
            body(obj);

            new Marker(getExecuteBlock(), continueLbl);
            new Marker(getExecuteBlock(), conditionLbl);
        	condition = invoke(itr, "hasNext");
        }
        condition.asArgument();
    }
    
    @Override
    public Label getBreakLabel() {
        return endLbl;
    }

    @Override
    public Label getContinueLabel() {
        return continueLbl;
    }

	@Override
	public String toString() {
		return "For Each Block:" + super.toString();
	}
}
