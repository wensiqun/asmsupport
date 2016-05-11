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
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.method.AbstractKernelMethodBody;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.asmdirect.DUP;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Marker;
import cn.wensiqun.asmsupport.core.operator.common.KernelReturn;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.block.sync.ISynchronized;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

/**
 * @author wensiqun at 163.com(Joe Wen)
 * 
 */
public abstract class KernelSync extends KernelProgramBlock implements ISynchronized<KernelParam> {

	private KernelParam lock;
	private LocalVariable dupSynArgument;
	
	private Label monitorenter;
	private Label monitorexit;
	private Label excetpionStart;
	private Label excetpionEnd;
	private Label returnLbl;
	
	private Marker flag1;

	public KernelSync(KernelParam lock) {
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
		if (lock.getResultType() == null ||
		    lock.getResultType().isPrimitive() || 
		    lock.getResultType().getType().equals(Type.VOID_TYPE)) {
			throw new IllegalArgumentException(
					lock
							+ " is not a valid type's argument for the synchronized statement");
		}
		lock.asArgument();

        AbstractKernelMethodBody mb = getMethodBody();
        mb.addExceptionTableEntry(monitorenter, monitorexit, excetpionStart, null);
        mb.addExceptionTableEntry(excetpionStart, excetpionEnd, excetpionStart, null);
	}

	@Override
	public void doExecute() {
        Executable returnInsn = null;
        
		lock.loadToStack(this);
		for (Executable e : getQueue()) {
			if(e.equals(flag1)){
				//e.execute();
				instructionHelper.monitorEnter();
				instructionHelper.mark(monitorenter);
				continue;
			}else if(e instanceof KernelReturn){
				returnInsn = e;
				break;
			}
			
			e.execute();
		}

		dupSynArgument.loadToStack(this);
		instructionHelper.monitorExit();
		instructionHelper.mark(monitorexit);
		instructionHelper.goTo(returnLbl);
		
        //for exception
		instructionHelper.nop();
		instructionHelper.mark(excetpionStart);
		dupSynArgument.loadToStack(this);
		instructionHelper.monitorExit();
		instructionHelper.getMv().getStack().push(getClassHolder().getType(Throwable.class).getType());
		instructionHelper.mark(excetpionEnd);
		instructionHelper.throwException();
		
		instructionHelper.mark(returnLbl);
		if(returnInsn != null){
			returnInsn.execute();
		}
	}
	
	@Override
	public void generate() {

        DUP dup = OperatorFactory.newOperator(DUP.class, 
                new Class<?>[]{KernelProgramBlock.class, IClass.class}, 
                this, lock.getResultType());
        
		dupSynArgument = var(lock.getResultType(), dup);
		
		flag1 = OperatorFactory.newOperator(Marker.class, 
                new Class<?>[]{KernelProgramBlock.class, Label.class}, 
                this, new Label());
		body(lock);
	}

}
