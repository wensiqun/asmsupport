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
import cn.wensiqun.asmsupport.core.block.control.condition.KernelIF;
import cn.wensiqun.asmsupport.standard.branch.IIF;

public abstract class IF extends ProgramBlock<KernelIF> implements IIF<ElseIF, Else> {

	public IF(Param condition) {
		targetBlock = new KernelIF(ParamPostern.getTarget(condition)) {
			@Override
			public void body() {
				IF.this.body();
			}
		};
	}
	
	@Override
	public ElseIF elseif(ElseIF elseif) {
	    elseif.cursor = cursor;
	    elseif.parent = parent;
        cursor.setPointer(elseif.targetBlock);
        
		targetBlock.elseif(elseif.targetBlock);
		
		cursor.setPointer(parent.targetBlock);
		
		return elseif;
	}
	
	@Override
	public Else else_(Else els) {
	    els.cursor = cursor;
	    els.parent = parent;
        cursor.setPointer(els.targetBlock);
        
		targetBlock.else_(els.targetBlock);
        
        cursor.setPointer(parent.targetBlock);
        
		return els;
	}
}
