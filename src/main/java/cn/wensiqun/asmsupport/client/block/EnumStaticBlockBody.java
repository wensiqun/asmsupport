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
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.method.clinit.KernelEnumStaticBlockBody;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.method.IEnumStaticBlockBody;

public abstract class EnumStaticBlockBody extends ProgramBlock<KernelEnumStaticBlockBody> implements IEnumStaticBlockBody<Param, LocVar> {

	public EnumStaticBlockBody() {
		targetBlock = new KernelEnumStaticBlockBody() {

			@Override
			public void body(LocalVariable... argus) {
				EnumStaticBlockBody.this.body(internalVar2ClientVar(argus));
			}

			@Override
			public void constructEnumConsts() {
				EnumStaticBlockBody.this.constructEnumConsts();
			}
		};
	}
	
	@Override
	public void constructEnumConst(String name, Param... argus) {
		targetBlock.constructEnumConst(name, ParamPostern.getTarget(argus));
	}
	
}
