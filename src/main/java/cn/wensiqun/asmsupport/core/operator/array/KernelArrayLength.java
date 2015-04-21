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
package cn.wensiqun.asmsupport.core.operator.array;

import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class KernelArrayLength extends AbstractArrayOperator implements KernelParameterized {


    private static final Log LOG = LogFactory.getLog(KernelArrayLength.class);
    
    private boolean useByOther;
    
    /**
     * 
     * @param block
     * @param arrayReference
     * @param parDims
     */
    protected KernelArrayLength(ProgramBlockInternal block, KernelParameterized arrayReference, KernelParameterized... parDims) {
        super(block, arrayReference);
        this.parDims = parDims;
    }
    

    @Override
    public void doExecute() {
		if(!useByOther){
            throw new RuntimeException(this.toString() + " not use by other operator");
        }
        InstructionHelper ih = block.getInsnHelper();
        if(LOG.isPrintEnabled()) {
            LOG.print("start get length of array");
        }
        getValue();
        if(LOG.isPrintEnabled()) {
            LOG.print("got length and push to stack");
        }
        ih.arrayLength();
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        this.execute();
    }

    @Override
    public AClass getResultType() {
        return AClassFactory.getType(int.class);
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
		toString.append(".length");
		return toString.toString();
	}
}
