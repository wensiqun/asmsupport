package cn.wensiqun.asmsupport.core.definition.method.meta;

import java.lang.reflect.Method;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
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
        super();
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
                    "different length between argClasses and argNames");
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
            arguments[i] = AClassFactory.getProductClass(argCls[i]);
            argNames[i] = "arg" + i;
        }

        Class<?>[] exceptionTypes = m.getExceptionTypes();
        AClass[] exceptionAclasses = AClassUtils.convertToAClass(exceptionTypes);
        
        AMethodMeta me = new AMethodMeta(m.getName(), owner,
                AClassFactory.getProductClass(m.getDeclaringClass()), arguments,
                argNames, AClassFactory.getProductClass(m.getReturnType()),
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