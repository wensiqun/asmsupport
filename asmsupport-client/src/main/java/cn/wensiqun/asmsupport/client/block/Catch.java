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
import cn.wensiqun.asmsupport.standard.block.exception.ICatch;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public abstract class Catch extends ProgramBlock<KernelCatch> implements ICatch<LocVar, Catch, Finally> {

	/**
	 * Constructor
	 * 
	 * @param exceptionType exception type
	 */
    public Catch(IClass exceptionType) {
        targetBlock = new KernelCatch(exceptionType) {
            @Override
            public void body(LocalVariable e) {
                Catch.this.body(new LocVar(cursor, e));
            }
        };
    }
    
    /**
     * Constructor
     * 
     * @param exceptionType exception type
     */
    public Catch(Class<?> exceptionType) {
        targetBlock = new KernelCatch(exceptionType) {
            @Override
            public void body(LocalVariable e) {
                Catch.this.body(new LocVar(cursor, e));
            }

            @Override
            public void prepare() {
                cursor.push(this);
                super.prepare();
                cursor.pop();
            }
        };
    }

    @Override
    public Catch catch_(Catch catchBlock) {
        catchBlock.cursor = cursor;
        catchBlock.parent = parent;
        targetBlock.catch_(catchBlock.targetBlock);
        return catchBlock;
    }

    @Override
    public Finally finally_(Finally finallyClient) {
        finallyClient.cursor = cursor;
        finallyClient.parent = parent;
        targetBlock.finally_(finallyClient.targetBlock);
        return finallyClient;
    }

}
