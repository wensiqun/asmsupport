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
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.Jumpable;
import cn.wensiqun.asmsupport.core.operator.asmdirect.GOTO;
import cn.wensiqun.asmsupport.core.operator.asmdirect.Marker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.standard.block.loop.IForEach;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;


/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class KernelForEach extends KernelProgramBlock implements Loop, IForEach<LocalVariable> {
    
    private KernelParam elements;
    private KernelParam condition;
    private AClass elementType;
    
    private Label startLbl = new Label();
    private Label conditionLbl = new Label();
    private Label arrayContinueLbl = new Label();
    private Label endLbl = new Label();
    
    public KernelForEach(KernelParam elements) {
        this(elements, null);
    }
    
    public KernelForEach(KernelParam elements, AClass elementType) {
        this.elements = elements;
        this.elementType = elementType;
        
        AClass type = elements.getResultType();
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
            insnHelper.unbox(condition.getResultType().getType());
            insnHelper.ifZCmp(InstructionHelper.NE, startLbl);
        }
        insnHelper.mark(endLbl);
    }
    
    @Override
    public final void generate() {
        if(elements.getResultType().isArray()){
            final LocalVariable i = var(AClassFactory.getType(int.class), Value.value(0));
            final LocalVariable len = var(AClassFactory.getType(int.class), arrayLength(elements));
            if(!(elements instanceof LocalVariable)) {
                elements = var(elements.getResultType(), elements);
            }
            
            OperatorFactory.newOperator(GOTO.class, 
            		new Class[]{KernelProgramBlock.class, Label.class}, 
            		getExecutor(), conditionLbl);

            OperatorFactory.newOperator(Marker.class, 
            		new Class[]{KernelProgramBlock.class, Label.class}, 
            		getExecutor(), startLbl);
            
            LocalVariable obj = var(((ArrayClass)elements.getResultType()).getNextDimType(), arrayLoad(elements, i) );
            body(obj);
            
            OperatorFactory.newOperator(Marker.class, 
                    new Class[]{KernelProgramBlock.class, Label.class}, 
                    getExecutor(), arrayContinueLbl);
            
            postinc(i);
            
            OperatorFactory.newOperator(Marker.class, 
                    new Class[]{KernelProgramBlock.class, Label.class}, 
                    getExecutor(), conditionLbl);
            
            condition = lt(i, len);
        }else{
        	final LocalVariable itr = var(AClassFactory.getType(Iterator.class), call(elements, "iterator"));
        	
            OperatorFactory.newOperator(GOTO.class, 
            		new Class[]{KernelProgramBlock.class, Label.class}, 
            		getExecutor(), conditionLbl);
        	
            OperatorFactory.newOperator(Marker.class, 
            		new Class[]{KernelProgramBlock.class, Label.class}, 
            		getExecutor(), startLbl);

            LocalVariable obj = elementType == null ? 
                                var(AClassFactory.getType(Object.class), call(itr, "next")) :
                                var(elementType, checkcast(call(itr, "next"), elementType));
            body(obj);

            OperatorFactory.newOperator(Marker.class, 
                    new Class[]{KernelProgramBlock.class, Label.class}, 
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
        return elements.getResultType().isArray() ? arrayContinueLbl : conditionLbl;
    }

	@Override
	public String toString() {
		return "For Each Block:" + super.toString();
	}
}
