package jw.asmsupport.operators.variable;


import org.apache.commons.lang.StringUtils;
import org.objectweb.asm.Type;

import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.operators.AbstractOperator;
import jw.asmsupport.utils.ScopeLogicVariable;


public class LocalVariableCreator extends AbstractOperator {

	private ScopeLogicVariable slv;
	
	/*protected LocalVariableCreator(ProgramBlock block,
			Type declareClass, Type actuallyClass) {
		super(block);
		block.removeExe(this);
        this.slv = new ScopeLogicVariable(block.getScope(), declareClass, actuallyClass, true);
	}*/
	
	protected LocalVariableCreator(ProgramBlock block, String name, Type declareClass,
            Type actuallyClass){
	    super(block);	
		block.removeExe(this);
		if(StringUtils.isBlank(name)){
	        this.slv = new ScopeLogicVariable(block.getScope(), declareClass, actuallyClass, true);
		}else{
	        this.slv = new ScopeLogicVariable(name, block.getScope(), declareClass, actuallyClass);
		}
	}

	
	@Override
	protected void firstPrepareProcess() {
		//slv.store();
	}

	public ScopeLogicVariable getScopeLogicVariable(){
		return slv;
	}
	
	@Override
	protected void executing() {

	}

}
