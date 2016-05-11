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
package cn.wensiqun.asmsupport.client.block;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.UncertainParam;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.method.init.KernelConstructorBody;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.block.method.IConstructorBody;

public abstract class ConstructorBody extends ProgramBlock<KernelConstructorBody> implements IConstructorBody<Param, LocVar> {

	public ConstructorBody() {
		targetBlock = new KernelConstructorBody(){

			@Override
			public void body(LocalVariable... args) {
				ConstructorBody.this.body(internalVar2ClientVar(args));
			}

			@Override
			public void prepare() {
				cursor.push(this);
				super.prepare();
				cursor.pop();
			}
			
		};
		cursor = new KernelProgramBlockCursor(targetBlock);
	}

	@Override
	public UncertainParam supercall(Param... arguments) {
    	return new UncertainParam(cursor, targetBlock.supercall(ParamPostern.getTarget(arguments)));
	}
}
