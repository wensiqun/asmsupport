package cn.wensiqun.asmsupport.operators.asmdirect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;

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
