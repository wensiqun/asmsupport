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
/**
 * 
 */
package cn.wensiqun.asmsupport.core.definition.variable;

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.standard.clazz.AClass;
import cn.wensiqun.asmsupport.standard.var.Field;

/**
 * 全局变量。这个class只用于方法体内操作变量
 * @author 温斯群(Joe Wen)
 */
public abstract class GlobalVariable extends ExplicitVariable implements Crementable, Field{

    private String name;
    
    private int modifiers;
    
    private AClass formerType;

    private AClass declaringClass;

    public GlobalVariable(AClass declaringClass, AClass formerType, int modifiers, String name) {
        this.declaringClass = declaringClass;
        this.formerType = formerType;
        this.modifiers = modifiers;
    	this.name = name;
    }

    @Override
    public final boolean availableFor(AbstractOperator operator) {
        return true;
    }

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final AClass getFormerType() {
		return formerType;
	}

	@Override
	public final int getModifiers() {
		return modifiers;
	}

	@Override
	public final AClass getDeclaringClass() {
		return declaringClass;
	}
	
}
