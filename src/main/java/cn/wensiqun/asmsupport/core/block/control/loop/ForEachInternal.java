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


import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
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
    
    private ExplicitVariable iteratorVar;
    private Parameterized condition;
    private AClass elementType;
    
    private Label startLbl = new Label();
    private Label conditionLbl = new Label();
    private Label continueLbl = new Label();
    private Label endLbl = new Label();
    
    public ForEachInternal(ExplicitVariable iteratorVar) {
        this(iteratorVar, null);
    }
    
    public ForEachInternal(ExplicitVariable iteratorVar, AClass elementType) {
        super();
        this.iteratorVar = iteratorVar;
        this.elementType = elementType;
        
        AClass type = iteratorVar.getParamterizedType();
        if(!type.isArray() &&
           !type.isChildOrEqual(AClassFactory.deftype(Iterable.class))){
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
        //?
        //?new NOP(getExecutor());
        if(iteratorVar.getParamterizedType().isArray()){
            final LocalVariable i = var(null, AClass.INT_ACLASS, true, Value.value(0));
            
            OperatorFactory.newOperator(GOTO.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), conditionLbl);
            //new GOTO(getExecutor(), conditionLbl);
            
            //?new NOP(getExecutor());
            

            OperatorFactory.newOperator(Marker.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), startLbl);
            //new Marker(getExecutor(), startLbl);
            
            //?new NOP(getExecutor());
            
            LocalVariable obj = var(((ArrayClass)iteratorVar.getParamterizedType()).getNextDimType(), arrayLoad(iteratorVar, i) );
            body(obj);

            OperatorFactory.newOperator(Marker.class, 
                    new Class[]{ProgramBlockInternal.class, Label.class}, 
                    getExecutor(), conditionLbl);
            postinc(i);
            
            condition = lt(i, arrayLength(iteratorVar));
        }else{
        	final LocalVariable itr = var(null, AClass.ITERATOR_ACLASS, true, call(iteratorVar, "iterator"));
        	
            OperatorFactory.newOperator(GOTO.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), conditionLbl);
        	
            OperatorFactory.newOperator(Marker.class, 
            		new Class[]{ProgramBlockInternal.class, Label.class}, 
            		getExecutor(), startLbl);

            LocalVariable obj = elementType == null ? 
                                var(AClass.OBJECT_ACLASS, call(itr, "next")) :
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
