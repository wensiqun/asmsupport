/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package cn.wensiqun.asmsupport.core.block.sync;


import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.Return;
import cn.wensiqun.asmsupport.core.operator.asmdirect.DUP;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Marker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.sync.ISynchronized;


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
		insnHelper.getMv().getStack().push(AClassFactory.getType(Throwable.class).getType());
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
        
		dupSynArgument = var(lock.getParamterizedType(), dup);
		
		flag1 = OperatorFactory.newOperator(Marker.class, 
                new Class<?>[]{ProgramBlockInternal.class, Label.class}, 
                this, new Label());
		body(lock);
	}

}
