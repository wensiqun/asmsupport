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



import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

public abstract class AbstractNullCompareRelational extends NumericalAndReferenceRelational {

	protected AbstractNullCompareRelational(KernelProgramBlock block, KernelParam leftFactor, KernelParam rightFactor, Operator operator) {
		super(block, leftFactor, rightFactor, operator);
	}

	@Override
	protected void doExecute() {
        //if those two factories are both null value
		//direct push true or false
		MethodVisitor mv = getInstructions().getMv();
		KernelProgramBlock block = getParent();
		if(isNullValue(leftFactor) && isNullValue(rightFactor)){
			if(Operator.EQUAL_TO.equals(getOperatorSymbol())){
				//push true to stack
		        mv.visitInsn(Opcodes.ICONST_0 + 1);
		        block.getMethod().getStack().push(Type.INT_TYPE);
		        return;
			}else if(Operator.NOT_EQUAL_TO.equals(getOperatorSymbol())){
				//push false to stack
		        mv.visitInsn(Opcodes.ICONST_0);
		        block.getMethod().getStack().push(Type.INT_TYPE);
		        return;
			}
		}else if(isNullValue(leftFactor) || isNullValue(rightFactor)){
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
            MethodVisitor mv = getInstructions().getMv();
            switch (mode) {
                case Instructions.EQ:
                    if((isNullValue(leftFactor) && !isNullValue(rightFactor)) ||
                       (!isNullValue(leftFactor) && isNullValue(rightFactor))){
                        mv.visitJumpInsn(Opcodes.IFNULL, label);
                        return;
                    }
                case Instructions.NE:
                    if((isNullValue(leftFactor) && !isNullValue(rightFactor)) ||
                       (!isNullValue(leftFactor) && isNullValue(rightFactor))){
                        mv.visitJumpInsn(Opcodes.IFNONNULL, label);
                        return;
                    }
                }
        }
		getInstructions().ifCmp(type, mode, label);
    }
	
	@Override
	protected void factorsToStack() {
		KernelProgramBlock block = getParent();
		if(isNullValue(leftFactor) && !isNullValue(rightFactor)){
			rightFactor.loadToStack(block);
		}else if(!isNullValue(leftFactor) && isNullValue(rightFactor)){
			leftFactor.loadToStack(block);
		}else{
			super.factorsToStack();
		}
	}

	protected boolean isNullValue(KernelParam val){
    	if(val != null && val instanceof Value && ((Value) val).getValue() == null){
    		return true;
    	}
    	return false;
    }
}
