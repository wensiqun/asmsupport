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

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.exception.ArrayOperatorException;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.ArrayClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class KernelArrayLoad extends AbstractArrayOperator implements KernelParam {

    private static final Log LOG = LogFactory.getLog(KernelArrayLoad.class);
    
    private IClass valueClass;
    
    private boolean useByOther;

    private void init(KernelParam pardim, KernelParam... parDims){
    	this.parDims = new KernelParam[1 + parDims.length];
        this.parDims[0] = pardim;
        System.arraycopy(parDims, 0, this.parDims, 1, parDims.length);
        
        valueClass = arrayRef.getResultType();
        for(int i=0; i<this.parDims.length; i++){
            valueClass = ((ArrayClass) valueClass).getNextDimType();
        }
    }
    
    protected KernelArrayLoad(KernelProgramBlock block, KernelParam arrayReference, KernelParam pardim, KernelParam... parDims) {
        super(block, arrayReference);
        init(pardim, parDims);
    }
    
    
	@Override
    public void doExecute(MethodContext context) {
		if(!useByOther){
            throw new RuntimeException(this.toString() + " not use by other operator");
        }
        ArrayClass cls = (ArrayClass) arrayRef.getResultType();
        if(parDims != null && parDims.length > cls.getDimension()){
            throw new ArrayOperatorException(toString() + " dimension not enough!");
        }
        if(LOG.isPrintEnabled()) {
            LOG.print("start load array value");
        }
        getValue(context);
    }

    @Override
    public void loadToStack(MethodContext context) {
        this.execute(context);
    }

    @Override
    public IClass getResultType() {
        return valueClass;
    }

    @Override
    public void asArgument() {
        useByOther = true;
        getParent().removeChild(this);
    }

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder(arrayRef.toString());
		for(KernelParam p : parDims){
			toString.append("[").append(p).append("]");
		}
		return toString.toString();
	}

}
