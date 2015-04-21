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

import cn.wensiqun.asmsupport.core.block.sync.SynchronizedInternal;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.standard.sync.ISynchronized;

public abstract class Sync extends ProgramBlock<SynchronizedInternal> implements ISynchronized<Param> {

	public Sync(Param lock) {
		target = new SynchronizedInternal(lock.getTarget()) {

			@Override
			public void body(KernelParameterized e) {
				Sync.this.body(new DummyParam(e));
			}
			
		};
	}

}
