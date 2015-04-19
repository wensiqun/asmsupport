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
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.standard.clazz.AClass;
import cn.wensiqun.asmsupport.standard.method.IModifiedMethodBody;

/**
 * @author
 * 
 */
public abstract class ModifiedMethodBodyInternal extends MethodBodyInternal implements IModifiedMethodBody {
    
	private List<VisitXInsnAdapter> superConstructorOperators;

    @Override
    public AClass getOrigReturnType(){
    	return getMethod().getMethodMeta().getReturnClass();
    }
    
	public void setSuperConstructorOperators(
			List<VisitXInsnAdapter> superConstructorOperators) {
		this.superConstructorOperators = superConstructorOperators;
	}

	@Override
    public void generateBody() {
		AMethodMeta me = getMethod().getMethodMeta();
		if(me.getName().equals(ASConstant.INIT)){
			//如果是构造方法，将被修改的构造方法中调用父类构造方法的那段字节码转移到新的构造方法中。
			if(superConstructorOperators != null){
			    for(VisitXInsnAdapter visitXInsnAdapter : superConstructorOperators){
			    	visitXInsnAdapter.newVisitXInsnOperator(getExecutor());
			    }
			}
		}
		super.generateBody();
    }
}
