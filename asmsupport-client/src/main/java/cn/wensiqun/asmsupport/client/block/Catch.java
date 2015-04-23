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

import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.control.exception.KernelCatch;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.excep.ICatch;

public abstract class Catch extends ProgramBlock<KernelCatch> implements ICatch<LocVar, Catch, Finally> {

    public Catch(AClass exceptionType) {
        targetBlock = new KernelCatch(exceptionType) {

            @Override
            public void body(LocalVariable e) {
                Catch.this.body(new LocVar(this, e));
            }
        };
    }
    
    public Catch(Class<?> exceptionType) {
        targetBlock = new KernelCatch(getType(exceptionType)) {

            @Override
            public void body(LocalVariable e) {
                Catch.this.body(new LocVar(this, e));
            }
        };
    }

    public Catch catch_(Catch catchBlock) {
        targetBlock.catch_(catchBlock.targetBlock);
        return catchBlock;
    }

    public Finally finally_(Finally finallyClient) {
        targetBlock.finally_(finallyClient.targetBlock);
        return finallyClient;
    }

}
