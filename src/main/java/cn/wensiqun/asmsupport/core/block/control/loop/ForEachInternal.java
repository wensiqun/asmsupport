package cn.wensiqun.asmsupport.core.block.control.loop;


import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
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
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.standard.loop.IForEach;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class ForEachInternal extends ProgramBlockInternal implements Loop, IForEach {
    
    private ExplicitVariable iteratorVar;
    private Parameterized condition;
    private AClass elementType;
    
    private Label startLbl = new Label();
    private Label conditionLbl = new Label();
    private Label continueLbl = new Label();
    private Label endLbl = new Label();
    
    public ForEachInternal(ExplicitVariable iteratorVar) {
        this(iteratorVar, null);
    }
    
    public ForEachInternal(ExplicitVariable iteratorVar, AClass elementType) {
        super();
        this.iteratorVar = iteratorVar;
        this.elementType = elementType;
        
        AClass type = iteratorVar.getParamterizedType();
        if(!type.isArray() &&
           !type.isChildOrEqual(AClassFactory.getProductClass(Iterable.class))){
            throw new ASMSupportException("Can only iterate over an array or an instance of java.lang.Iterable.");
        }
    }

    @Override
    public void doExecute() {
    	
        for(Executable exe : getQueue()){
            exe.execute();
        }
        if(condition instanceof Jumpable){
        	((Jumpable) condition).jumpPositive(startLbl, getEnd());
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
            final LocalVariable i = _var(null, AClass.INT_ACLASS, true, Value.value(0));
            
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
            
            LocalVariable obj = _var(((ArrayClass)iteratorVar.getParamterizedType()).getNextDimType(), _arrayLoad(iteratorVar, i) );
            body(obj);

            OperatorFactory.newOperator(Marker.class, 
                    new Class[]{ProgramBlockInternal.class, Label.class}, 
                    getExecutor(), conditionLbl);
            _postInc(i);
            
            condition = _lt(i, _arrayLength(iteratorVar));
        }else{
        	final LocalVariable itr = _var(null, AClass.ITERATOR_ACLASS, true, _invoke(iteratorVar, "iterator"));
        	
            OperatorFactory.newOperator(GOTO.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), conditionLbl);
        	
            OperatorFactory.newOperator(Marker.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), startLbl);

            LocalVariable obj = elementType == null ? 
                                _var(AClass.OBJECT_ACLASS, _invoke(itr, "next")) :
                                _var(elementType, _checkcast(_invoke(itr, "next"), elementType));
            body(obj);

            OperatorFactory.newOperator(Marker.class, 
                    new Class[]{ProgramBlockInternal.class, Label.class}, 
                    getExecutor(), conditionLbl);
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
