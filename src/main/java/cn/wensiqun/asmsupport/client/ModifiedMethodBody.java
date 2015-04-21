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
package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.method.common.ModifiedMethodBodyInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.method.IModifiedMethodBody;

public abstract class ModifiedMethodBody extends ProgramBlock<ModifiedMethodBodyInternal> implements
    IModifiedMethodBody<LocVar> {

	public ModifiedMethodBody() {
	     target = new ModifiedMethodBodyInternal() {

			@Override
			public void body(LocalVariable... args) {
				ModifiedMethodBody.this.body(internalVar2ClientVar(args));
			}
	    	 
	     };
	}

	@Override
	public AClass getOrigReturnType(){
		return target.getOrigReturnType();
	}

}
