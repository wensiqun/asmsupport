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
package cn.wensiqun.asmsupport.core.operator.array;

import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.exception.ClassException;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.operator.AbstractParamOperator;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.core.utils.lang.ArrayUtils;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class AbstractArrayOperator extends AbstractParamOperator {

    private static final Log LOG = LogFactory.getLog(AbstractArrayOperator.class);
    
    protected KernelParam arrayReference;
    
    protected KernelParam[] parDims;
    
    protected AbstractArrayOperator(KernelProgramBlock block, KernelParam arrayVar) {
        super(block, Operator.COMMON);
        this.arrayReference = arrayVar;
    }

    @Override
	protected void checkAsArgument() {
        arrayReference.asArgument();
        if(parDims != null){
            for(KernelParam par : parDims){
                par.asArgument();
            }
        }
	}
    
	@Override
	protected void verifyArgument() {
		if(!(arrayReference.getResultType() instanceof ArrayClass)){
        	throw new ClassException(toString() + " : the declare class of " + arrayReference.toString() + " is not a array type");
        }
		
		if(ArrayUtils.isNotEmpty(parDims)){
			for(KernelParam par : parDims){
				if(!AClassUtils.checkAssignable(par.getResultType(), AClassFactory.getType(int.class))) {
					throw new IllegalArgumentException("Type mismatch: cannot convert from " + par.getResultType() + " to " + AClassFactory.getType(int.class) + "");
				}
			}
		}
		
	}

	protected void getValue(){
        InstructionHelper ih = block.getInsnHelper();
        AClass cls = arrayReference.getResultType();
        if(LOG.isPrintEnabled()){
            LOG.print("load the array reference to stack");
        }
        arrayReference.loadToStack(block);
        
        for(int i=0; i<parDims.length; i++){
            cls = ((ArrayClass) cls).getNextDimType();
            parDims[i].loadToStack(block);
            autoCast(parDims[i].getResultType(), AClassFactory.getType(int.class), false);
            ih.arrayLoad(cls.getType());
        }
        
    }
}
