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

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ArrayLength extends AbstractArrayOperator implements Parameterized {


    private static final Log LOG = LogFactory.getLog(ArrayLength.class);
    
    private boolean useByOther;
    
    /**
     * 
     * @param block
     * @param arrayReference
     * @param parDims
     */
    protected ArrayLength(ProgramBlockInternal block, Parameterized arrayReference, Parameterized... parDims) {
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
    public AClass getParamterizedType() {
        return AClass.INT_ACLASS;
    }

    @Override
    public void asArgument() {
        useByOther = true;
        block.removeExe(this);
    }
    
    @Override
	public String toString() {
		StringBuilder toString = new StringBuilder(arrayReference.toString());
		for(Parameterized p : parDims){
			toString.append("[").append(p).append("]");
		}
		toString.append(".length");
		return toString.toString();
	}
}
