/**
 * 
 */
package cn.wensiqun.asmsupport.core.block.classes.common;


import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.Return;
import cn.wensiqun.asmsupport.core.operator.asmdirect.DUP;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Marker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.generic.ISynchronized;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


/**
 * @author 温斯群(Joe Wen)
 * 
 */
public abstract class SynchronizedInternal extends ProgramBlockInternal implements ISynchronized {

	private Parameterized lock;
	private LocalVariable dupSynArgument;
	
	private Label monitorenter;
	private Label monitorexit;
	private Label excetpionStart;
	private Label excetpionEnd;
	private Label returnLbl;
	
	private Marker flag1;

	public SynchronizedInternal(Parameterized lock) {
		super();
		this.lock = lock;
		monitorenter = new Label();
		monitorexit = new Label();
		excetpionStart = new Label();
		excetpionEnd = new Label();
		returnLbl = new Label();
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

        AbstractMethodBody mb = getMethodBody();
        mb.addTryCatchInfo(monitorenter, monitorexit, excetpionStart, null);
        mb.addTryCatchInfo(excetpionStart, excetpionEnd, excetpionStart, null);
	}

	@Override
	public void doExecute() {
        Executable returnInsn = null;
        
		lock.loadToStack(this);
		for (Executable e : getQueue()) {
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
	public void generate() {

        DUP dup = OperatorFactory.newOperator(DUP.class, 
                new Class<?>[]{ProgramBlockInternal.class, AClass.class}, 
                this, lock.getParamterizedType());
        
		dupSynArgument = _createVariable(null, lock.getParamterizedType(), true, dup);
		
		flag1 = OperatorFactory.newOperator(Marker.class, 
                new Class<?>[]{ProgramBlockInternal.class, Label.class}, 
                this, new Label());
		body(lock);
	}

}
