package cn.wensiqun.asmsupport.clazz;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.utils.ASConstant;
import cn.wensiqun.asmsupport.utils.asm.ClassAdapter;
import cn.wensiqun.asmsupport.utils.reflet.ModifierUtils;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ProductClass extends NewMemberClass {

    private Class<?> reallyClass;
    
    protected ProductClass(){
    	
    }
    
    ProductClass(Class<?> cls) {
        this.name = cls.getName();
        this.mod = cls.getModifiers();
        this.superClass = cls.getSuperclass();
        this.interfaces = cls.getInterfaces();
        reallyClass = cls;
        type = Type.getType(cls);
    }
    
    @Override
    public String getDescription() {
        return Type.getDescriptor(reallyClass);
    }

    public Class<?> getReallyClass() {
        return reallyClass;
    }

    @Override
    public GlobalVariableMeta getGlobalVariableMeta(String name) {
        Class<?> fieldOwner = reallyClass;
        GlobalVariableMeta entiey = null;
        for(;!fieldOwner.equals(Object.class)  ;fieldOwner = fieldOwner.getSuperclass()){
            try {
                Field f = fieldOwner.getDeclaredField(name);
                entiey = new GlobalVariableMeta(this, 
                		AClassFactory.getProductClass(fieldOwner),
                		AClassFactory.getProductClass(f.getType()), f.getModifiers(), name);
                break;
            } catch (NoSuchFieldException e) {
                //throw new IllegalArgumentException("no such method exception : " + methodName);
            }
        }
        return entiey;
    }

    

    @Override
    public GlobalVariable getGlobalVariable(String name) {
    	GlobalVariable gv = super.getGlobalVariable(name);
    	if(gv != null){
    		return gv;
    	}
    	
        Class<?> fieldOwner = reallyClass;
        Field f = null;
        for(;fieldOwner!=null ;fieldOwner = fieldOwner.getSuperclass()){
            try {
                f = fieldOwner.getDeclaredField(name);
                break;
            } catch (NoSuchFieldException e) {
            }
        }
        
        if(f == null){
            throw new ASMSupportException("no such field exception : " + name);
        }
        
        if(!ModifierUtils.isStatic(f.getModifiers())){
            throw new ASMSupportException("the field \"" + f.getName() + "\" is non-static, cannot use current method!");
        }
        
        return new GlobalVariable(this, AClassFactory.getProductClass(fieldOwner),
        		AClassFactory.getProductClass(f.getType()), f.getModifiers(), name);
    }
    
    @Override
    public int getCastOrder(){
        int order = 0;
        if(type == Type.BOOLEAN_TYPE || name.equals(Boolean.class.getName())){
            order = 1;
        } else if(type == Type.BYTE_TYPE || name.equals(Byte.class.getName())){
            order = 2;
        } else if(type == Type.SHORT_TYPE || name.equals(Short.class.getName())){
            order = 3;
        } else if (type == Type.CHAR_TYPE || name.equals(Character.class.getName())) {
            order = 3;
        } else if (type == Type.INT_TYPE || name.equals(Integer.class.getName())) {
            order = 4;
        } else if (type == Type.LONG_TYPE || name.equals(Long.class.getName())) {
            order = 5;
        } else if (type == Type.FLOAT_TYPE || name.equals(Float.class.getName())) {
            order = 6;
        } else if (type == Type.DOUBLE_TYPE || name.equals(Double.class.getName())) {
            order = 7;
        } else {
            order = 8;
        }
        return order;
    }
    
    

    @Override
	public boolean isChildOrEqual(AClass cls) {
    	if(cls instanceof ProductClass){
    		return ((ProductClass)cls).getReallyClass().isAssignableFrom(reallyClass);
    	}
		return super.isChildOrEqual(cls);
	}

	@Override
    public boolean isPrimitive() {
        return reallyClass.isPrimitive();
    }

    @Override
    public boolean isArray() {
        return reallyClass.isArray();
    }
    
    @Override
    public int getDimension() {
        if(!reallyClass.isArray()){
            throw new ASMSupportException("this class is not array");
        }
        return type.getDimensions();
    }

	@Override
	public boolean existStaticInitBlock() {
		if(super.existStaticInitBlock()){
			return true;
		}
		
		final boolean[] exist = new boolean[]{false};
		
		ClassVisitor cv = new ClassAdapter(){
			@Override
			public MethodVisitor visitMethod(int access, String name,
					String desc, String signature, String[] exceptions) {
				if(name.equals(ASConstant.CLINIT)){
					exist[0] = true;
				}
				return super.visitMethod(access, name, desc, signature, exceptions);
			}
		};
		
    	try {
    		ClassLoader loader = Thread.currentThread().getContextClassLoader();
    		
    		URL resource = loader.getResource(reallyClass.getName().replace('.', '/') + ".class");
    		if (resource != null) {
                InputStream in = resource.openStream();
                try {
                    ClassReader classReader = new ClassReader(in);
                    classReader.accept(cv, ClassReader.SKIP_DEBUG);
                } finally {
                    in.close();
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return exist[0];
	}
    
}
