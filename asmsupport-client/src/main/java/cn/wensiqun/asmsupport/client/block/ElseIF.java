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

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelElseIF;
import cn.wensiqun.asmsupport.standard.block.branch.IElseIF;

public abstract class ElseIF extends ProgramBlock<KernelElseIF> implements IElseIF<ElseIF, Else> {

    public ElseIF(Param condition) {
        setKernelBlock(new KernelElseIF(condition.getTarget()) {
            @Override
            public void body() {
                ElseIF.this.body();
            }
        });
    }

    @Override
    public ElseIF elseif(ElseIF elseif) {
        elseif.parent = parent;
        getDelegate().elseif(elseif.getDelegate());
        return elseif;
    }

    @Override
    public Else else_(Else els) {
        els.parent = parent;
        getDelegate().else_(els.getDelegate());
        return els;
    }
}
