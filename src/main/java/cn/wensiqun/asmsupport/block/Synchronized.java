/**
 * 
 */
package cn.wensiqun.asmsupport.block;


import org.objectweb.asm.Label;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.Executable;
import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.body.ParameterizedBody;
import cn.wensiqun.asmsupport.block.method.GenericMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.operators.Return;
import cn.wensiqun.asmsupport.operators.asmdirect.DUP;
import cn.wensiqun.asmsupport.operators.asmdirect.Marker;


/**
 * @author 温斯群(Joe Wen)
 * 
 */
public abstract class Synchronized extends ProgramBlock implements ParameterizedBody {

	private Parameterized lock;
	private LocalVariable dupSynArgument;
	
	private Label monitorenter;
	private Label monitorexit;
	private Label excetpionStart;
	private Label excetpionEnd;
	private Label returnLbl;
	
	private Marker flag1;

	public Synchronized(Parameterized lock) {
		super();
		this.lock = lock;
		monitorenter = new Label();
		monitorexit = new Label();
		excetpionStart = new Label();
		excetpionEnd = new Label();
		returnLbl = new Label();
	}
	
	@Override
	public void setReturned(boolean returned) {
		super.setReturned(returned);
		getOwnerBlock().setReturned(returned);
	}

	@Override
	protected void init() {
		if (lock.getParamterizedType() == null ||
		    lock.getParamterizedType().isPrimitive() || 
		    lock.getParamterizedType().getType().equals(Type.VOID_TYPE)) {
			throw new IllegalArgumentException(
					lock
							+ " is not a valid type's argument for the synchronized statement");
		}
		lock.asArgument();

        GenericMethodBody mb = getMethodBody();
        mb.addTryCatchInfo(monitorenter, monitorexit, excetpionStart, null);
        mb.addTryCatchInfo(excetpionStart, excetpionEnd, excetpionStart, null);
	}

	@Override
	public void executing() {
        Executable returnInsn = null;
        
		lock.loadToStack(this);
		for (Executable e : getExecuteQueue()) {
			if(e.equals(flag1)){
				//e.execute();
				insnHelper.monitorEnter();
				insnHelper.mark(monitorenter);
				continue;
			}else if(e instanceof Return){
				returnInsn = e;
				break;
			}
			
			e.execute();
		}

		dupSynArgument.loadToStack(this);
		insnHelper.monitorExit();
		insnHelper.mark(monitorexit);
		insnHelper.goTo(returnLbl);
		
        //for exception
		insnHelper.nop();
		insnHelper.mark(excetpionStart);
		dupSynArgument.loadToStack(this);
		insnHelper.monitorExit();
		insnHelper.getMv().getStack().push(AClass.THROWABLE_ACLASS.getType());
		insnHelper.mark(excetpionEnd);
		insnHelper.throwException();
		
		insnHelper.mark(returnLbl);
		if(returnInsn != null){
			returnInsn.execute();
		}
	}
	
	@Override
	public void generateInsn() {
		dupSynArgument = createVariable(null, lock.getParamterizedType(), true, new DUP(this, lock.getParamterizedType()));
		flag1 = new Marker(this, new Label());
        body(lock);
	}

}
