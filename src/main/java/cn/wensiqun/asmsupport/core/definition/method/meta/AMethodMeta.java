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
package cn.wensiqun.asmsupport.core.definition.method.meta;

import java.lang.reflect.Method;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.ArrayUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class AMethodMeta implements Cloneable {

    /** 方法名 */
    private String name;

    /** 方法所属的类 */
    private AClass owner;

    private AClass actuallyOwner;

    /** 方法参数类型 */
    private Type[] argTypes;
    /** 参数Class类型 */
    private AClass[] argClasses;
    /** 方法参数名 */
    private String[] argNames;
    /** 方法的返回类型 */
    private Type returnType;
    /** 方法的修饰符 */
    private int modifier;

    private AClass[] exceptions;
    
    private AClass returnClass;

    private String methodStr;

    public AMethodMeta(String name, AClass owner, AClass actuallyOwner,
            AClass[] argClasses, String[] argNames, AClass returnClass,
            AClass[] exceptions, int modifier) {
        
        if(ArrayUtils.isNotEmpty(argClasses) && ArrayUtils.isEmpty(argNames)) {
            argNames = new String[argClasses.length];
            for(int i=0; i<argNames.length; i++) {
                argNames[i] = "arg" + i;
            }
        }
        
        if (argClasses == null) {
            argClasses = new AClass[0];
        }
        if (argNames == null) {
            argNames = new String[0];
        }
        if (exceptions == null) {
            exceptions = new AClass[0];
        }
        this.exceptions = exceptions;
        this.name = name;
        this.owner = owner;
        this.actuallyOwner = actuallyOwner;
        this.argNames = argNames;
        this.modifier = modifier;
        this.argClasses = argClasses;

        if (argClasses.length != argNames.length) {
            throw new ASMSupportException(
                    "Different length between argClasses and argNames when create method : " + name);
        }

        this.argTypes = new Type[argClasses.length];
        for (int i = 0; i < argClasses.length; i++) {
            this.argTypes[i] = argClasses[i].getType();
        }

        if (returnClass == null) {
            this.returnType = Type.VOID_TYPE;
            this.returnClass = AClass.VOID_ACLASS;
        } else {
            this.returnType = returnClass.getType();
            this.returnClass = returnClass;
        }
    }

    public String getMethodString() {
        if (methodStr == null) {
        	methodStr = actuallyOwner.toString() + "." + getMethodString(name, argClasses);
        }
        return methodStr;
    }

    public static String getMethodString(String name, AClass[] arguments) {

        if (arguments == null) {
            arguments = new AClass[0];
        }

        StringBuilder str = new StringBuilder(name).append("(");
        for (int i = 0; i < arguments.length; i++) {
            str.append(arguments[i].getName()).append(", ");
        }
        if (arguments.length > 0) {
            str.delete(str.length() - 2, str.length());
        }
        str.append(")");
        return str.toString();
    }

    public static AMethodMeta methodToMethodEntity(AClass owner, Method m) {
        Class<?>[] argCls = m.getParameterTypes();
        AClass[] arguments = new AClass[argCls.length];
        String[] argNames = new String[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = AClassFactory.defType(argCls[i]);
            argNames[i] = "arg" + i;
        }

        Class<?>[] exceptionTypes = m.getExceptionTypes();
        AClass[] exceptionAclasses = AClassUtils.convertToAClass(exceptionTypes);
        
        AMethodMeta me = new AMethodMeta(m.getName(), owner,
                AClassFactory.defType(m.getDeclaringClass()), arguments,
                argNames, AClassFactory.defType(m.getReturnType()),
                exceptionAclasses, m.getModifiers());
        
        return me;
    }
    
    @Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new ASMSupportException("cannot clone this");
		}
	}

	@Override
    public String toString() {
        return getMethodString();
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name){
    	this.name = name;
    }

    public AClass getOwner() {
        return owner;
    }

    public Type[] getArgTypes() {
        return argTypes;
    }

    public AClass[] getArgClasses() {
        return argClasses;
    }

    public String[] getArgNames() {
        return argNames;
    }

    public Type getReturnType() {
        return returnType;
    }

    public int getModifier() {
        return modifier;
    }

    public AClass getReturnClass() {
        return returnClass;
    }

    public AClass getActuallyOwner() {
        return actuallyOwner;
    }

    public AClass[] getExceptions() {
        return exceptions;
    }

}