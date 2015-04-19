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

import java.util.List;

import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.standard.clazz.AClass;
import cn.wensiqun.asmsupport.standard.var.Field;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractVariable implements IVariable {

    @Override
    public final void asArgument() {
        //don't do anything if this class don't extends AbstractExecuteable
    }

    @Override
    public final GlobalVariable field(String name) {
        if(this.getResultType() instanceof ArrayClass){
            throw new ASMSupportException("Cannot get global variable from array type variable : " + this);
        }
        
        List<Field> fields = getFormerType().getGlobalVariableMeta(name);
        if(fields.isEmpty()) {
            throw new ASMSupportException("No such field " + name);
        }
        
        Field found = null;
        StringBuilder errorSuffix = new StringBuilder();
        for(Field field : fields) {
            if(found != null) {
                errorSuffix.append(found.getActuallyOwnerType()).append(',')
                           .append(field.getActuallyOwnerType());
                throw new ASMSupportException("The field '" + name + "' is ambiguous, found it in class [" + errorSuffix + "]");
            }
            found = field;
        }
        
        if(found == null) {
            throw new ASMSupportException("No such field " + name);
        }

        if(ModifierUtils.isStatic(found.getModifiers())){
            return new StaticGlobalVariable(found.getActuallyOwnerType(), found.getDeclaringClass(), found.getActuallyOwnerType(),
            		found.getFormerType(), found.getModifiers(), found.getName());
        } else {
            return new NonStaticGlobalVariable(this, found.getDeclaringClass(), found.getActuallyOwnerType(),
            		found.getFormerType(), found.getModifiers(), found.getName());
        }
    }
    
    @Override
    public final AClass getResultType() {
        return this.getFormerType();
    }
    
    @Override
    public final String toString() {
        return ASConstant.THIS;
    }
    
}
