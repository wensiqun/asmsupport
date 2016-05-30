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
package cn.wensiqun.asmsupport.standard.def.method;

import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.ClassHolder;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;


/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class AMethodMeta implements Cloneable {

    /** Method Name */
    private String name;

    /** Method Owner */
    private IClass owner;

    /** Method Actually Owner */
    private IClass actuallyOwner;

    /** The parameter type list of method */
    private Type[] argTypes;
    
    /** The parameter type list of method */
    private IClass[] argClasses;
    
    /** The parameter name list of method */
    private String[] argNames;
    
    /** The method return type */
    private Type returnType;
    
    /** The method modifier */
    private int modifier;

    private IClass[] exceptions;
    
    private IClass returnClass;

    private String methodStr;
    
    public AMethodMeta(ClassHolder classHolder, String name, IClass owner, IClass actuallyOwner,
    		IClass[] argClasses, String[] argNames, IClass returnClass,
    		IClass[] exceptions, int modifier) {
        if(ArrayUtils.isNotEmpty(argClasses) && ArrayUtils.isEmpty(argNames)) {
            argNames = new String[argClasses.length];
            for(int i=0; i<argNames.length; i++) {
                argNames[i] = "arg" + i;
            }
        }
        
        if (argClasses == null) {
            argClasses = new IClass[0];
        }
        if (argNames == null) {
            argNames = new String[0];
        }
        if (exceptions == null) {
            exceptions = new IClass[0];
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
            this.returnClass = classHolder.getType(void.class);
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

    public static String getMethodString(String name, IClass[] arguments) {

        if (arguments == null) {
            arguments = new IClass[0];
        }

        StringBuilder str = new StringBuilder(name).append("(");
        for(IClass arg : arguments) {
            str.append(arg.getName()).append(", ");
        }
        if (arguments.length > 0) {
            str.delete(str.length() - 2, str.length());
        }
        str.append(")");
        return str.toString();
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

	public String getDescription() {
		if (ArrayUtils.isEmpty(argClasses)) {
			return Type.getMethodDescriptor(returnType);
		} else {
			Type[] argTypes = new Type[argClasses.length];
			for (int i = 0; i < argTypes.length; i++) {
				argTypes[i] = argClasses[i].getType();
			}
			return Type.getMethodDescriptor(returnType, argTypes);
		}
	}

	public String getName() {
        return name;
    }
    
    public void setName(String name){
    	this.name = name;
    }

    public IClass getOwner() {
        return owner;
    }

    public Type[] getParameterAsmTypes() {
        return argTypes;
    }

    public IClass[] getParameterTypes() {
        return argClasses;
    }

    public String[] getParameterNames() {
        return argNames;
    }

    public Type getReturnType() {
        return returnType;
    }

    public int getModifiers() {
        return modifier;
    }

    public IClass getReturnClass() {
        return returnClass;
    }

    public IClass getActuallyOwner() {
        return actuallyOwner;
    }

    public IClass[] getExceptions() {
        return exceptions;
    }

}