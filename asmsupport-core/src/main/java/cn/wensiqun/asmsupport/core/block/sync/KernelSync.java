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


import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.method.AbstractMethodBody;
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
			throw new IllegalArgumentException(lock + " is not a valid type's argument for the synchronized statement");
		}
		lock.asArgument();
        AbstractMethodBody methodBody = getMethod().getBody();
        methodBody.addExceptionTableEntry(monitorenter, monitorexit, excetpionStart, null);
        methodBody.addExceptionTableEntry(excetpionStart, excetpionEnd, excetpionStart, null);
	}

	@Override
	public void doExecute(MethodContext context) {
        Executable returnInsn = null;
		Instructions instructions = getMethod().getInstructions();
		lock.loadToStack(context);
		for (Executable e : getChildren()) {
			if(e.equals(flag1)){
				//e.execute();
				instructions.monitorEnter();
				instructions.mark(monitorenter);
				continue;
			}else if(e instanceof KernelReturn){
				returnInsn = e;
				break;
			}
			
			e.execute(context);
		}

		dupSynArgument.loadToStack(context);
		instructions.monitorExit();
		instructions.mark(monitorexit);
		instructions.goTo(returnLbl);
		
        //for exception
		instructions.nop();
		instructions.mark(excetpionStart);
		dupSynArgument.loadToStack(context);
		instructions.monitorExit();
		instructions.getMv().getStack().push(getType(Throwable.class).getType());
		instructions.mark(excetpionEnd);
		instructions.throwException();
		
		instructions.mark(returnLbl);
		if(returnInsn != null){
			returnInsn.execute(context);
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
