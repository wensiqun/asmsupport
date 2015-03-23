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

import cn.wensiqun.asmsupport.core.block.control.exception.TryInternal;
import cn.wensiqun.asmsupport.standard.excep.ITry;

public abstract class Try extends ProgramBlock<TryInternal> implements ITry<Catch, Finally> {

    public Try() {
        target = new TryInternal() {

            @Override
            public void body() {
                Try.this.body();
            }

        };
    }

    @Override
    public Catch catch_(Catch catchBlock) {
        target.catch_(catchBlock.target);
        return catchBlock;
    }

    @Override
    public Finally finally_(Finally finallyClient) {
        target.finally_(finallyClient.target);
        return finallyClient;
    }

}
