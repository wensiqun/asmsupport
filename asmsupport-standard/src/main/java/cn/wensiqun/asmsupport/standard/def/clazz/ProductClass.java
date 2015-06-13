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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;

import cn.wensiqun.asmsupport.org.objectweb.asm.ClassReader;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.utils.ByteCodeConstant;
import cn.wensiqun.asmsupport.utils.asm.ClassAdapter;
import cn.wensiqun.asmsupport.utils.lang.InterfaceLooper;


/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class ProductClass extends MutableClass {

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
    public Field getField(final String name) {
        
        final LinkedList<Field> found = new LinkedList<Field>();
        
        for(Field gv : getFields()){
            if(gv.getName().equals(name)){
                found.add(gv);
            }
        }
        
        if(found.isEmpty()) {
            Class<?> fieldOwner = reallyClass;
            for(;!fieldOwner.equals(Object.class); fieldOwner = fieldOwner.getSuperclass()){
                try {
                    java.lang.reflect.Field f = fieldOwner.getDeclaredField(name);
                    found.add(new Field(this,
                            AClassFactory.getType(fieldOwner),
                            AClassFactory.getType(f.getType()), f.getModifiers(), name));
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
                    found.add(new Field(ProductClass.this,
                            AClassFactory.getType(inter),
                            AClassFactory.getType(f.getType()), f.getModifiers(), name));
                    return true;
                } catch (NoSuchFieldException e) {
                    return false;
                }
            }
        }.loop(reallyClass.getInterfaces());
        
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
				if(name.equals(ByteCodeConstant.CLINIT)){
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
            throw new ASMSupportException(e);
		}
		
		return exist[0];
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
