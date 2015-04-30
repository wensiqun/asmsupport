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
package cn.wensiqun.asmsupport.core.clazz;

import java.util.LinkedList;

import cn.wensiqun.asmsupport.core.utils.lang.InterfaceLooper;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class SemiClass extends MutableClass {

	SemiClass(int version, int access, String name, Class<?> superCls,
            Class<?>[] interfaces) {
        this.version = version;
        this.name = name;
        this.mod = access;
        this.superClass = superCls;
        this.interfaces = interfaces;

        if(!ModifierUtils.isInterface(mod)){
            this.mod += Opcodes.ACC_SUPER;
        }
        
    }

    @Override
    public String getDescription() {
        return new StringBuilder("L").append(getName().replace(".", "/"))
                .append(";").toString();
    }
    
    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public int getDimension() {
        return -1;
    }

    @Override
    public Field getField(final String name) {
        
        final LinkedList<Field> found = new LinkedList<Field>();
        
        for(Field gv : getFields()){
            if(gv.getName().equals(name)){
                found.add(gv);
            }
        }
        
        if(found.isEmpty()) {
            Class<?> fieldOwner = getSuperClass();
            for(;!fieldOwner.equals(Object.class); fieldOwner = fieldOwner.getSuperclass()){
                try {
                    java.lang.reflect.Field field = fieldOwner.getDeclaredField(name);
                    found.add(new Field(this,
                            AClassFactory.getType(fieldOwner),
                            AClassFactory.getType(field.getType()), field.getModifiers(), name));
                    break;
                } catch (NoSuchFieldException e) {
                }
            }
        }
        
        new InterfaceLooper() {
            @Override
            protected boolean process(Class<?> inter) {
                try {
                    java.lang.reflect.Field f = inter.getDeclaredField(name);
                    found.add(new Field(SemiClass.this,
                            AClassFactory.getType(inter),
                            AClassFactory.getType(f.getType()), f.getModifiers(), name));
                    return true;
                } catch (NoSuchFieldException e) {
                    return false;
                }
            }
        }.loop(getInterfaces());
        
        if(found.size() == 0) {
            throw new ASMSupportException("Not found field " + name);
        } else if(found.size() == 1) {
            return found.getFirst();
        } 

        StringBuilder errorSuffix = new StringBuilder();
        for(Field field : found) {
            errorSuffix.append(field.getDeclaringClass()).append(',');
        }
        throw new ASMSupportException("The field '" + name + "' is ambiguous, found it in class [" + errorSuffix + "]");
    }

    @Override
    public AClass getNextDimType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IClass getRootComponentClass() {
        throw new UnsupportedOperationException();
    }
    
}
