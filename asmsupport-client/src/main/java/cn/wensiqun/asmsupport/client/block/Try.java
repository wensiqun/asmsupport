/**
 * Asmsupport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.client.block;

import cn.wensiqun.asmsupport.core.block.control.exception.KernelTry;
import cn.wensiqun.asmsupport.standard.block.exception.ITry;

public abstract class Try extends ProgramBlock<KernelTry> implements ITry<Catch, Finally> {

    public Try() {
        kernelBlock = new KernelTry() {

            @Override
            public void body() {
                Try.this.body();
            }

        };
    }

    @Override
    public Catch catch_(Catch catchBlock) {
        catchBlock.parent = parent;
        kernelBlock.catch_(catchBlock.kernelBlock);
        return catchBlock;
    }

    @Override
    public Finally finally_(Finally finallyClient) {
        finallyClient.parent = parent;
        kernelBlock.finally_(finallyClient.kernelBlock);
        return finallyClient;
    }

}
