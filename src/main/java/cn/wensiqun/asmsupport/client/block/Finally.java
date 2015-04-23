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

import cn.wensiqun.asmsupport.core.block.control.exception.KernelFinally;
import cn.wensiqun.asmsupport.standard.excep.IFinally;

public abstract class Finally extends ProgramBlock<KernelFinally> implements IFinally {

	public Finally() {
		targetBlock = new KernelFinally() {

			@Override
			public void body() {
				Finally.this.body();
			}
		};
	}
	
}
