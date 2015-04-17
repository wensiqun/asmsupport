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
package cn.wensiqun.asmsupport.core.block.control.loop;


import java.util.Iterator;

import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.operator.asmdirect.GOTO;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Marker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.standard.loop.IForEach;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class ForEachInternal extends ProgramBlockInternal implements Loop, IForEach {
    
    private Parameterized iterable;
    private Parameterized condition;
    private AClass elementType;
    
    private Label startLbl = new Label();
    private Label conditionLbl = new Label();
    private Label continueLbl = new Label();
    private Label endLbl = new Label();
    
    public ForEachInternal(Parameterized iterable) {
        this(iterable, null);
    }
    
    public ForEachInternal(Parameterized iterable, AClass elementType) {
        this.iterable = iterable;
        this.elementType = elementType;
        
        AClass type = iterable.getParamterizedType();
        if(!type.isArray() &&
           !type.isChildOrEqual(AClassFactory.getType(Iterable.class))){
            throw new ASMSupportException("Can only iterate over an array or an instance of java.lang.Iterable.");
        }
    }

    @Override
    public void doExecute() {
    	
        for(Executable exe : getQueue()){
            exe.execute();
        }
        if(condition instanceof Jumpable){
        	((Jumpable) condition).jumpPositive(null, startLbl, getEnd());
        }else{
            condition.loadToStack(this);
            insnHelper.unbox(condition.getParamterizedType().getType());
            insnHelper.ifZCmp(InstructionHelper.NE, startLbl);
        }
        insnHelper.mark(endLbl);
    }
    
    @Override
    public final void generate() {
        if(iterable.getParamterizedType().isArray()){
            final LocalVariable i = var(AClassFactory.getType(int.class), Value.value(0));
            final LocalVariable len = var(AClassFactory.getType(int.class), arrayLength(iterable));
            if(!(iterable instanceof LocalVariable)) {
                iterable = arrayvar((ArrayClass) iterable.getParamterizedType(), iterable);
            }
            
            OperatorFactory.newOperator(GOTO.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), conditionLbl);

            OperatorFactory.newOperator(Marker.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), startLbl);
            
            LocalVariable obj = var(((ArrayClass)iterable.getParamterizedType()).getNextDimType(), arrayLoad(iterable, i) );
            body(obj);

            postinc(i);
            
            OperatorFactory.newOperator(Marker.class, 
                    new Class[]{ProgramBlockInternal.class, Label.class}, 
                    getExecutor(), conditionLbl);
            
            condition = lt(i, len);
        }else{
        	final LocalVariable itr = var(AClassFactory.getType(Iterator.class), call(iterable, "iterator"));
        	
            OperatorFactory.newOperator(GOTO.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), conditionLbl);
        	
            OperatorFactory.newOperator(Marker.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), startLbl);

            LocalVariable obj = elementType == null ? 
                                var(AClassFactory.getType(Object.class), call(itr, "next")) :
                                var(elementType, checkcast(call(itr, "next"), elementType));
            body(obj);

            OperatorFactory.newOperator(Marker.class, 
                    new Class[]{ProgramBlockInternal.class, Label.class}, 
                    getExecutor(), conditionLbl);
        	condition = call(itr, "hasNext");
        }
        condition.asArgument();
    }
    
    @Override
    public Label getBreakLabel() {
        return endLbl;
    }

    @Override
    public Label getContinueLabel() {
        return continueLbl;
    }

	@Override
	public String toString() {
		return "For Each Block:" + super.toString();
	}
}
