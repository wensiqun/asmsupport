package jw.asmsupport.operators.asmdirect;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DUP extends ASMDirect implements Parameterized {

	private static Log log = LogFactory.getLog(DUP.class);
	
	private AClass type;
	
	public DUP(ProgramBlock block, AClass stackTopType) {
		super(block);
		type = stackTopType;
	}

	@Override
	public void loadToStack(ProgramBlock block) {
		this.execute();
	}

	@Override
	public AClass getParamterizedType() {
		return type;
	}

	@Override
	public void asArgument() {
        block.removeExe(this);
	}

	@Override
	protected void executing() {
		if(log.isDebugEnabled()){
			log.debug("duplicate the top of stack and push it to stack");
		}
        block.getInsnHelper().dup();
	}

}
