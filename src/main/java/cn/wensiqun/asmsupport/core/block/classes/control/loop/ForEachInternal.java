package cn.wensiqun.asmsupport.core.block.classes.control.loop;


import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.classes.control.ControlType;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.operator.asmdirect.GOTO;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Marker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.generic.loop.IForEach;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class ForEachInternal extends ProgramBlockInternal implements Loop, IForEach {
    
    private ExplicitVariable iteratorVar;
    
    private Parameterized condition;
    
    private Label startLbl = new Label();
    private Label conditionLbl = new Label();
    private Label continueLbl = new Label();
    private Label endLbl = new Label();
    
    public ForEachInternal(ExplicitVariable iteratorVar) {
        super();
        this.iteratorVar = iteratorVar;
        checkMember(iteratorVar);
    }
    
    private void checkMember(ExplicitVariable member){
        AClass cls = member.getParamterizedType();
        if(!cls.isArray() &&
           !cls.isChildOrEqual(AClassFactory.getProductClass(Iterable.class))){
            throw new ASMSupportException("Can only iterate over an array or an instance of java.lang.Iterable.");
        }
    }

    @Override
    public void doExecute() {
    	
        for(Executable exe : getQueue()){
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
    public final void generate() {
        //?
        //?new NOP(getExecutor());
        if(iteratorVar.getParamterizedType().isArray()){
            final LocalVariable i = _createVariable(null, AClass.INT_ACLASS, true, Value.value(0));
            
            OperatorFactory.newOperator(GOTO.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), conditionLbl);
            //new GOTO(getExecutor(), conditionLbl);
            
            //?new NOP(getExecutor());
            

            OperatorFactory.newOperator(Marker.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), startLbl);
            //new Marker(getExecutor(), startLbl);
            
            //?new NOP(getExecutor());
            
            LocalVariable obj = _createVariable(null, ((ArrayClass)iteratorVar.getParamterizedType()).getNextDimType(), true, _arrayLoad(iteratorVar, i) );
            body(obj);

            //?new Marker(getExecutor(), continueLbl);
            
            _postInc(i);
            
            //?new Marker(getExecutor(), conditionLbl);
            condition = _lessThan(i, _arrayLength(iteratorVar));
            //((LessThan)condition).setJumpLable(startLbl);
        }else{
        	final LocalVariable itr = _createVariable(null, AClass.ITERABLE_ACLASS, true, _invoke(iteratorVar, "iterator"));
        	
            OperatorFactory.newOperator(GOTO.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), conditionLbl);
            //new GOTO(getExecutor(), conditionLbl);
        	
            OperatorFactory.newOperator(Marker.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), startLbl);
        	//new Marker(getExecutor(), startLbl);
            //?new NOP(getExecutor());

            LocalVariable obj = _createVariable(null, AClass.OBJECT_ACLASS, true, _invoke(itr, "next"));
            body(obj);

            //?new Marker(getExecutor(), continueLbl);
            
        	condition = _invoke(itr, "hasNext");
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
