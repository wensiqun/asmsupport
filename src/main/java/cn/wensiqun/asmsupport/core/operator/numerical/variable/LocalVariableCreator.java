package cn.wensiqun.asmsupport.core.operator.numerical.variable;


import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.lang.StringUtils;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeLogicVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


public class LocalVariableCreator extends AbstractOperator {

	private ScopeLogicVariable slv;
	
	protected LocalVariableCreator(ProgramBlockInternal block, String name, Type declareClass,
            Type actuallyClass){
	    super(block);	
		block.removeExe(this);
		if(StringUtils.isBlank(name)){
	        this.slv = new ScopeLogicVariable(block.getScope(), declareClass, actuallyClass, true);
		}else{
	        this.slv = new ScopeLogicVariable(name, block.getScope(), declareClass, actuallyClass);
		}
	}

	public ScopeLogicVariable getScopeLogicVariable(){
		return slv;
	}
	
	@Override
	protected void doExecute() {}

}
