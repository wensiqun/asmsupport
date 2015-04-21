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

import cn.wensiqun.asmsupport.core.block.control.condition.ElseIFInternal;
import cn.wensiqun.asmsupport.standard.branch.IElseIF;

public abstract class ElseIF extends ProgramBlock<ElseIFInternal> implements IElseIF<ElseIF, Else> {
	
	public ElseIF(Param condition) {
		target = new ElseIFInternal(condition.target) {
			@Override
			public void body() {
				ElseIF.this.body();
			}
		};
	}

	@Override
	public ElseIF elseif(ElseIF elseif) {
		target.elseif(elseif.target);
		return elseif;
	}
	
	@Override
	public Else else_(Else els) {
		target.else_(els.target);
		return els;
	}
}
