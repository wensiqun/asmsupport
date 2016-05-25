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
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.control.loop.KernelForEach;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.block.loop.IForEach;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public abstract class ForEach extends ProgramBlock<KernelForEach> implements IForEach<LocVar> {

    public ForEach(Param iteratorVar) {
        setKernelBlock(new KernelForEach(iteratorVar.getTarget()) {

            @Override
            public void body(LocalVariable e) {
                ForEach.this.body(new LocVar(getBlockTracker(), e));
            }

        });
    }

    public ForEach(Param iteratorVar, IClass elementType) {
        setKernelBlock(new KernelForEach(iteratorVar.getTarget(), elementType) {

            @Override
            public void body(LocalVariable e) {
                ForEach.this.body(new LocVar(getBlockTracker(), e));
            }

        });
    }

}
