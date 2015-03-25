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
package cn.wensiqun.asmsupport.core.operator.numerical.variable;


import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.lang.StringUtils;
import cn.wensiqun.asmsupport.core.utils.memory.ScopeLogicVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


public class LocalVariableCreator extends AbstractOperator {

	private ScopeLogicVariable slv;
	
	protected LocalVariableCreator(ProgramBlockInternal block, String name, Type declareClass,
            Type actuallyClass){
	    super(block);	
		block.removeExe(this);
		if(StringUtils.isBlank(name)){
	        this.slv = new ScopeLogicVariable(block.getScope(), declareClass, actuallyClass, true);
		}else{
	        this.slv = new ScopeLogicVariable(name, block.getScope(), declareClass, actuallyClass);
		}
	}

	public ScopeLogicVariable getScopeLogicVariable(){
		return slv;
	}
	
	@Override
	protected void doExecute() {
        //Nothing TO DO
	}

}
