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

import cn.wensiqun.asmsupport.core.context.MethodExecuteContext;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.exception.ClassException;
import cn.wensiqun.asmsupport.core.operator.AbstractParamOperator;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.ArrayClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;

/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class AbstractArrayOperator extends AbstractParamOperator {

    private static final Log LOG = LogFactory.getLog(AbstractArrayOperator.class);
    
    protected KernelParam arrayRef;
    
    protected KernelParam[] parDims;
    
    protected AbstractArrayOperator(KernelProgramBlock block, KernelParam arrayVar) {
        super(block, Operator.COMMON);
        this.arrayRef = arrayVar;
    }

    @Override
	protected void checkAsArgument() {
        arrayRef.asArgument();
        if(parDims != null){
            for(KernelParam par : parDims){
                par.asArgument();
            }
        }
	}
    
	@Override
	protected void verifyArgument() {
		if(!(arrayRef.getResultType() instanceof ArrayClass)){
        	throw new ClassException(toString() + " : the declare class of " + arrayRef.toString() + " is not a array type");
        }
		
		if(ArrayUtils.isNotEmpty(parDims)){
			for(KernelParam par : parDims){
				if(!IClassUtils.checkAssignable(par.getResultType(), getParent().getType(int.class))) {
					throw new IllegalArgumentException("Type mismatch: cannot convert from " + par.getResultType() + " to " + getParent().getType(int.class));
				}
			}
		}
		
	}

	protected void getValue(MethodExecuteContext context){
        IClass cls = arrayRef.getResultType();
        if(LOG.isPrintEnabled()){
            LOG.print("load the array reference to stack");
        }
        KernelProgramBlock block = getParent();
        arrayRef.push(context);

        for(int i=0; i<parDims.length; i++){
            cls = cls.getNextDimType();
            parDims[i].push(context);
            autoCast(context, parDims[i].getResultType(), block.getType(int.class), false);
            context.getInstructions().arrayLoad(cls.getType());
        }
        
    }
}
