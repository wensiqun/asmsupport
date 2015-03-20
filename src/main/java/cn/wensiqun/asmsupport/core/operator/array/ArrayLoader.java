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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.exception.ArrayOperatorException;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class ArrayLoader extends AbstractArrayOperator implements Parameterized {

    private static Log log = LogFactory.getLog(ArrayLoader.class);
    
    private AClass valueClass;
    
    private boolean useByOther;

    private void init(Parameterized pardim, Parameterized... parDims){
    	this.parDims = new Parameterized[1 + parDims.length];
        this.parDims[0] = pardim;
        System.arraycopy(parDims, 0, this.parDims, 1, parDims.length);
        
        valueClass = arrayReference.getParamterizedType();
        for(int i=0; i<this.parDims.length; i++){
            valueClass = ((ArrayClass) valueClass).getNextDimType();
        }
    }
    
    protected ArrayLoader(ProgramBlockInternal block, Parameterized arrayReference, Parameterized pardim, Parameterized... parDims) {
        super(block, arrayReference);
        init(pardim, parDims);
    }
    
    
	@Override
    public void doExecute() {
		if(!useByOther){
            throw new RuntimeException(this.toString() + " not use by other operator");
        }
        ArrayClass cls = (ArrayClass) arrayReference.getParamterizedType();
        if(parDims != null && parDims.length > cls.getDimension()){
            throw new ArrayOperatorException(toString() + " dimension not enough!");
        }
        log.debug("start load array value");
        getValue();
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        this.execute();
    }

    @Override
    public AClass getParamterizedType() {
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
		for(Parameterized p : parDims){
			toString.append("[").append(p).append("]");
		}
		return toString.toString();
	}

}
