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

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.method.clinit.EnumStaticBlockBodyInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.method.IEnumStaticBlockBody;

public abstract class EnumStaticBlockBody extends ProgramBlock<EnumStaticBlockBodyInternal> implements IEnumStaticBlockBody {

	public EnumStaticBlockBody() {
		target = new EnumStaticBlockBodyInternal() {

			@Override
			public void body(LocalVariable... argus) {
				EnumStaticBlockBody.this.body(argus);
			}

			@Override
			public void constructEnumConsts() {
				EnumStaticBlockBody.this.constructEnumConsts();
			}
		};
	}
	
	@Override
	public void constructEnumConst(String name, InternalParameterized... argus) {
		target.constructEnumConst(name, argus);
	}
	
}
