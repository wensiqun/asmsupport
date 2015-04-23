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

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.core.exception.ArrayOperatorException;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class KernelArrayLoad extends AbstractArrayOperator implements KernelParameterized {

    private static final Log LOG = LogFactory.getLog(KernelArrayLoad.class);
    
    private AClass valueClass;
    
    private boolean useByOther;

    private void init(KernelParameterized pardim, KernelParameterized... parDims){
    	this.parDims = new KernelParameterized[1 + parDims.length];
        this.parDims[0] = pardim;
        System.arraycopy(parDims, 0, this.parDims, 1, parDims.length);
        
        valueClass = arrayReference.getResultType();
        for(int i=0; i<this.parDims.length; i++){
            valueClass = ((ArrayClass) valueClass).getNextDimType();
        }
    }
    
    protected KernelArrayLoad(KernelProgramBlock block, KernelParameterized arrayReference, KernelParameterized pardim, KernelParameterized... parDims) {
        super(block, arrayReference);
        init(pardim, parDims);
    }
    
    
	@Override
    public void doExecute() {
		if(!useByOther){
            throw new RuntimeException(this.toString() + " not use by other operator");
        }
        ArrayClass cls = (ArrayClass) arrayReference.getResultType();
        if(parDims != null && parDims.length > cls.getDimension()){
            throw new ArrayOperatorException(toString() + " dimension not enough!");
        }
        if(LOG.isPrintEnabled()) {
            LOG.print("start load array value");
        }
        getValue();
    }

    @Override
    public void loadToStack(KernelProgramBlock block) {
        this.execute();
    }

    @Override
    public AClass getResultType() {
        return valueClass;
    }

    @Override
    public void asArgument() {
        useByOther = true;
        block.removeExe(this);
    }

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder(arrayReference.toString());
		for(KernelParameterized p : parDims){
			toString.append("[").append(p).append("]");
		}
		return toString.toString();
	}

}
