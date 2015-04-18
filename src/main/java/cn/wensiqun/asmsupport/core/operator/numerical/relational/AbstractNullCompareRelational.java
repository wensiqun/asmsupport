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
package cn.wensiqun.asmsupport.core.operator.numerical.relational;



import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.operator.Operators;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

public abstract class AbstractNullCompareRelational extends NumericalAndReferenceRelational {

	protected AbstractNullCompareRelational(ProgramBlockInternal block, InternalParameterized factor1, InternalParameterized factor2) {
		super(block, factor1, factor2);
	}
	
	

	@Override
	protected void doExecute() {
        //if those two factories are both null value
		//direct push true or false
		MethodVisitor mv = insnHelper.getMv();
		if(isNullValue(factor1) && isNullValue(factor2)){
			if(Operators.EQUAL_TO.equals(operator)){
				//push true to stack
		        mv.visitInsn(Opcodes.ICONST_0 + 1);
		        block.getMethod().getStack().push(Type.INT_TYPE);
		        return;
			}else if(Operators.NOT_EQUAL_TO.equals(operator)){
				//push false to stack
		        mv.visitInsn(Opcodes.ICONST_0);
		        block.getMethod().getStack().push(Type.INT_TYPE);
		        return;
			}
		}else if(isNullValue(factor1) || isNullValue(factor2)){
			instructionGenerate();
	        block.getMethod().getStack().pop();
	        block.getMethod().getStack().push(Type.INT_TYPE);
		}else{
			super.doExecute();
		}
	}
	
	protected void ifCmp(Type type, int mode, Label label) {
        //check null
        int sort = type.getSort();
        if(sort == Type.OBJECT || sort == Type.ARRAY){
            MethodVisitor mv = insnHelper.getMv();
            switch (mode) {
                case InstructionHelper.EQ:
                    if((isNullValue(factor1) && !isNullValue(factor2)) ||
                       (!isNullValue(factor1) && isNullValue(factor2))){
                        mv.visitJumpInsn(Opcodes.IFNULL, label);
                        return;
                    }
                case InstructionHelper.NE:
                    if((isNullValue(factor1) && !isNullValue(factor2)) ||
                       (!isNullValue(factor1) && isNullValue(factor2))){
                        mv.visitJumpInsn(Opcodes.IFNONNULL, label);
                        return;
                    }
                }
        }
        insnHelper.ifCmp(type, mode, label);
    }
	
	@Override
	protected void factorsToStack() {
		if(isNullValue(factor1) && !isNullValue(factor2)){
			factor2.loadToStack(block);
		}else if(!isNullValue(factor1) && isNullValue(factor2)){
			factor1.loadToStack(block);
		}else{
			super.factorsToStack();
		}
	}

	protected boolean isNullValue(InternalParameterized val){
    	if(val != null && val instanceof Value && ((Value) val).getValue() == null){
    		return true;
    	}
    	return false;
    }
}
