package cn.wensiqun.asmsupport.core.operator.asmdirect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;


/**
 * integrated DUP DUP2 instruction according to type of the top element.
 *
 */
public class DUP extends ASMDirect implements Parameterized {

	private static Log log = LogFactory.getLog(DUP.class);
	
	private AClass type;
	
	protected DUP(ProgramBlockInternal block, AClass stackTopType) {
		super(block);
		type = stackTopType;
	}

	@Override
	public void loadToStack(ProgramBlockInternal block) {
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
	protected void doExecute() {
		if(log.isDebugEnabled()){
			log.debug("duplicate the top of stack and push it to stack");
		}
        block.getInsnHelper().dup(type.getType());
	}

}
