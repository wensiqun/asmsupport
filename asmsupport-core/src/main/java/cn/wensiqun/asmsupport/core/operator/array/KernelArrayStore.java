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
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class KernelArrayStore extends AbstractArrayOperator {


    private static final Log LOG = LogFactory.getLog(KernelArrayStore.class);
    
    private KernelParam value;
    
    private IClass storeClass;
    
    private KernelParam lastDim;

    private void init(KernelParam value, KernelParam pardim, KernelParam... parDims){
    	this.value = value;
        this.parDims = new KernelParam[parDims.length];
        
        if(parDims.length != 0){
            this.parDims[0] = pardim;
            System.arraycopy(parDims, 0, this.parDims, 1, parDims.length - 1);
            lastDim = parDims[parDims.length - 1];
        }else{
            lastDim = pardim;
        }
        
        storeClass = arrayRef.getResultType();
        for(int i=0, length = this.parDims.length + 1; i<length; i++){
            storeClass = (storeClass).getNextDimType();
        }
    }

    protected KernelArrayStore(KernelProgramBlock block, KernelParam arrayReference, KernelParam value, KernelParam pardim, KernelParam... parDims) {
        super(block, arrayReference);
        init(value, pardim, parDims);
    }

    
	@Override
	protected void checkAsArgument() {
		super.checkAsArgument();
        value.asArgument();
	}
	
	
    
    @Override
	protected void verifyArgument() {
		super.verifyArgument();
		if(!IClassUtils.checkAssignable(value.getResultType(), storeClass)) {
			throw new IllegalArgumentException("Type mismatch: cannot convert from " + value.getResultType() + " to " + storeClass + "");
		}
        KernelProgramBlock block = getParent();
		if(!IClassUtils.checkAssignable(lastDim.getResultType(), block.getType(int.class))) {
			throw new IllegalArgumentException("Type mismatch: cannot convert from " + lastDim.getResultType() + " to " + block.getType(int.class) + "");
		}
	}

	@Override
    public void doExecute(MethodContext context) {
	    if(LOG.isPrintEnabled()) {
	        LOG.print("start get value for store array");
	    }
        getValue(context);
        LOG.print("push the last dim index to stack");
        KernelProgramBlock block = getParent();
        lastDim.loadToStack(context);
        autoCast(lastDim.getResultType(), block.getType(int.class), false);
        
        value.loadToStack(context);
        autoCast(value.getResultType(), storeClass, false);
        if(LOG.isPrintEnabled()) { 
            LOG.print("store value to corresponse to index of the array");   
        }
        getInstructions().arrayStore(storeClass.getType());
    }

    @Override
    public void asArgument() {
        throw new UnsupportedOperationException("Un imple");
    }

    @Override
    public IClass getResultType() {
        throw new UnsupportedOperationException("Un imple");
    }

    @Override
    public void loadToStack(MethodContext context) {
        throw new UnsupportedOperationException("Un imple");
    }

}
