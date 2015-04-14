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

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.exception.ClassException;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.core.utils.lang.ArrayUtils;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractArrayOperator extends AbstractOperator {

    private static final Log LOG = LogFactory.getLog(AbstractArrayOperator.class);
    
    protected Parameterized arrayReference;
    
    protected Parameterized[] parDims;
    
    protected AbstractArrayOperator(ProgramBlockInternal block, Parameterized arrayVar) {
        super(block);
        this.arrayReference = arrayVar;
    }

    @Override
	protected void checkAsArgument() {
        arrayReference.asArgument();
        if(parDims != null){
            for(Parameterized par : parDims){
                par.asArgument();
            }
        }
	}
    
	@Override
	protected void verifyArgument() {
		if(!(arrayReference.getParamterizedType() instanceof ArrayClass)){
        	throw new ClassException(toString() + " : the declare class of " + arrayReference.toString() + " is not a array type");
        }
		
		if(ArrayUtils.isNotEmpty(parDims)){
			for(Parameterized par : parDims){
				if(!AClassUtils.checkAssignable(par.getParamterizedType(), AClassFactory.defType(int.class))) {
					throw new IllegalArgumentException("Type mismatch: cannot convert from " + par.getParamterizedType() + " to " + AClassFactory.defType(int.class) + "");
				}
			}
		}
		
	}

	protected void getValue(){
        InstructionHelper ih = block.getInsnHelper();
        AClass cls = arrayReference.getParamterizedType();
        if(LOG.isPrintEnabled()){
            LOG.print("load the array reference to stack");
        }
        arrayReference.loadToStack(block);
        
        for(int i=0; i<parDims.length; i++){
            cls = ((ArrayClass) cls).getNextDimType();
            parDims[i].loadToStack(block);
            autoCast(parDims[i].getParamterizedType(), AClassFactory.defType(int.class), false);
            ih.arrayLoad(cls.getType());
        }
        
    }
}
