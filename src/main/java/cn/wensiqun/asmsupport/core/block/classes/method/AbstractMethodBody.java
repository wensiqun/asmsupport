package cn.wensiqun.asmsupport.core.block.classes.method;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AnyException;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.meta.LocalVariableMeta;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.operator.numerical.variable.LocalVariableCreator;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.common.TryCatchInfo;
import cn.wensiqun.asmsupport.core.utils.memory.Component;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeLogicVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

public abstract class AbstractMethodBody extends ProgramBlockInternal {

    private static Log log = LogFactory.getLog(AbstractMethodBody.class);
    
    private List<TryCatchInfo> tryCatches;

    protected LocalVariable[] argments;
    
    public AbstractMethodBody() {
		super();
        tryCatches = new ArrayList<TryCatchInfo>();
	}

	public LocalVariable[] getMethodArguments(){
		return argments;
	}

	@Override
	public final void generate() {
		generateBody();
	}
	
	/**
	 * generate the method body
	 */
	public abstract void generateBody();

	@Override
    protected void init() {
	    AMethod method = getMethod();
    	AMethodMeta me = method.getMethodMeta(); 
        if (!method.isStatic()) {
            OperatorFactory.newOperator(LocalVariableCreator.class, 
            		new Class<?>[]{ProgramBlockInternal.class, String.class, Type.class, Type.class}, 
            		getExecutor(), ASConstant.THIS, me.getOwner().getType(), method.getMethodMeta().getOwner().getType());
        }

        String[] argNames = me.getArgNames();
        AClass[] argClsses = me.getArgClasses();
        argments = new LocalVariable[argNames.length];
        for (int i = 0; i < argNames.length; i++) {
            ScopeLogicVariable slv = new ScopeLogicVariable(argNames[i], getScope(), argClsses[i].getType(),
                    argClsses[i].getType());
            LocalVariableMeta lve = new LocalVariableMeta(argNames[i], 0, argClsses[i]);
            LocalVariable lv = new LocalVariable(lve);
            lv.setScopeLogicVar(slv);
            argments[i] = lv;
        }
        method.setArguments(argments);
    }
    
    @Override
    public final void doExecute() {
        AMethod method = getMethod();
        if (log.isDebugEnabled()) {
            StringBuilder str = new StringBuilder("create method: ------------");
            str.append(method.getMethodMeta().getMethodString());
            log.debug(str);
        }
        
        for(Executable exe : getQueue()){
            exe.execute();
        }
        
        for(TryCatchInfo tci : tryCatches){
        	if(tci.getEnd().getOffset() - tci.getStart().getOffset() > 0)
        	{
        		Type type = tci.getException();
                insnHelper.tryCatchBlock(tci.getStart(), tci.getEnd(), tci.getHander(), type == null || type == AnyException.ANY.getType() ? null : type);
        	}
        }
        
        
    }
    

    /**
     * 
     */
    public void endMethodBody() {
        checkNoReturnBlock();
        declarationVariable(getScope());
        int s = getMethod().getStack().getMaxSize();
        int l = getScope().getLocals().getSize();
        insnHelper.maxs(s, l);
    }

    /**
     * 检测时候有未返回的分支
     */
    private void checkNoReturnBlock() {

    };

    /**
     * local variable declaration.
     */
    private void declarationVariable(Scope parent) {
        List<Component> coms = parent.getComponents();
        Component com;
        Scope lastBrotherScope = null;
        for (int i = 0; i < coms.size(); i++) {
            com = coms.get(i);
            if (com instanceof ScopeLogicVariable) {
                ScopeLogicVariable slv = (ScopeLogicVariable) com;
                if(slv.isAnonymous()){
                    continue;
                }
                insnHelper.declarationVariable(slv.getName(), slv
                        .getDeclareType().getDescriptor(), null, slv.getSpecifiedStartLabel(), parent
                        .getEnd(), slv.getInitStartPos());
            } else {
                lastBrotherScope = (Scope) com;
                declarationVariable(lastBrotherScope);
            }
        }
    }
    
    public void addTryCatchInfo(Label start, Label end, Label handler, Type exception){
        addTryCatchInfo(new TryCatchInfo(start, end, handler, exception));
    }
    
    public void addTryCatchInfo(TryCatchInfo info)
    {
        tryCatches.add(info);	
    }
    
}
