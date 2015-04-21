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
package cn.wensiqun.asmsupport.standard.def.clazz;

import cn.wensiqun.asmsupport.core.utils.jls.TypeUtils;
import cn.wensiqun.asmsupport.core.utils.lang.ClassUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


/**
 * Java Class的抽象
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AClass implements IClass {
	
    /**
     * Class name
     */
    protected String name;

    /** Class对应的java的版本 */
    protected int version;

    /** Class的修饰符值 */
    protected int mod;

    /** Class的父类 */
    protected Class<?> superClass;

    /** Class的接口 */
    protected Class<?>[] interfaces;
    
    /** 当前Class对应的Type类型 */
    protected Type type;
    
    /** 当前的Class的包名 */
    protected String pkg;
    
    @Override
    public String getPackage() {
        if(pkg == null){
            pkg = ClassUtils.getPackageName(name);
        }
        return pkg;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getModifiers() {
        return mod;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Class<?> getSuperClass() {
        return superClass;
    }

    @Override
    public Class<?>[] getInterfaces() {
    	if(interfaces == null){
    		interfaces = new Class[0];
    	}
        return interfaces;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        
        if(obj instanceof AClass){
            if(name.equals(((AClass)obj).name)){
                return true;
            }
        }else{
            return false;
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        return getDescription().hashCode();
    }

    @Override
    public Type getType() {
        if(type == null){
            type = Type.getType(getDescription());
        }
        return type;
    }

    @Override
    public final boolean isInterface() {
        return (getModifiers() & Opcodes.ACC_INTERFACE) != 0;
    }

    @Override
    public final boolean isAbstract() {
        return (getModifiers() & Opcodes.ACC_ABSTRACT) != 0;
    }

    @Override
    public String toString() {
        return getName();
    }

    public boolean isChildOrEqual(AClass otherType) {
        return TypeUtils.isSubtyping(this, otherType);
    }

    @Override
    public abstract AClass getNextDimType();
    
}
