package cn.wensiqun.asmsupport.core.clazz;

import java.lang.reflect.Constructor;
import java.util.List;

import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

public class AnyException extends AClass
{

    public static AClass ANY = new AnyException();
    
    AnyException(){
        try
        {
            Constructor<Type> con = Type.class.getDeclaredConstructor(int.class, char[].class, int.class, int.class);
            con.setAccessible(true);
            type = con.newInstance(12, "AnyExceptionType".toCharArray(), 0, 16);
        }
        catch (Throwable e)
        {
            throw new ASMSupportException(e);
        }
    }
    
    /*public static void main(String[] args)
    {
        Type t = ANY.getType();
        System.out.print(t.getSort() + ":" + t.getSize());
    }*/
    
    /*@Override
    public GlobalVariable getGlobalVariable(String name)
    {
        throw new UnsupportedOperationException();
    }*/

    @Override
    public boolean isArray()
    {
        return false;
    }

    @Override
    public int getDimension()
    {
        return 0;
    }

    @Override
    public String getDescription()
    {
        return type.getDescriptor();
    }

    @Override
    public List<GlobalVariableMeta> getGlobalVariableMeta(String name)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPrimitive()
    {
        return false;
    }

    @Override
    public int getCastOrder()
    {
        return 12;
    }

    @Override
    public boolean existStaticInitBlock()
    {
        return false;
    }

    @Override
    public String getPackage()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName()
    {
        return "Any Exception";
    }

    @Override
    public int getModifiers()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getVersion()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?> getSuperClass()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?>[] getInterfaces()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj == ANY;
    }

    @Override
    public boolean isChildOrEqual(AClass cls)
    {
        return cls == ANY;
    }

    @Override
    public Type getType()
    {
        return type;
    }

    @Override
    public AMethodMeta getSuperMethod(String methodName, AClass[] parameterTypes)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public AMethodMeta getSuperConstructor(AClass[] parameterTypes)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
        return "Any Exception";
    }
    
}
