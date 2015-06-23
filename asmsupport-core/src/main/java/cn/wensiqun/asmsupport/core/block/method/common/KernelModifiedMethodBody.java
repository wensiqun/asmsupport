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
package cn.wensiqun.asmsupport.core.block.method.common;

import java.util.List;

import cn.wensiqun.asmsupport.core.asm.adapter.VisitXInsnAdapter;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.block.method.IModifiedMethodBody;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.utils.AsmsupportConstant;

/**
 * @author
 * 
 */
public abstract class KernelModifiedMethodBody extends KernelMethodBody implements IModifiedMethodBody<LocalVariable> {
    
	private List<VisitXInsnAdapter> superConstructorOperators;

    @Override
    public IClass getOrigReturnType(){
    	return getMethod().getMeta().getReturnClass();
    }
    
	public void setSuperConstructorOperators(
			List<VisitXInsnAdapter> superConstructorOperators) {
		this.superConstructorOperators = superConstructorOperators;
	}

	@Override
    public void generateBody() {
		AMethodMeta me = getMethod().getMeta();
		if(me.getName().equals(AsmsupportConstant.INIT)){
			if(superConstructorOperators != null){
			    for(VisitXInsnAdapter visitXInsnAdapter : superConstructorOperators){
			    	visitXInsnAdapter.newVisitXInsnOperator(getExecutor());
			    }
			}
		}
		super.generateBody();
    }
}
