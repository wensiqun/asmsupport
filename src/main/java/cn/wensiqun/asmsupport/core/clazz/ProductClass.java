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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.LinkedList;

import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.asm.ClassAdapter;
import cn.wensiqun.asmsupport.core.utils.lang.InterfaceLooper;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassReader;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


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
    public LinkedList<GlobalVariableMeta> getGlobalVariableMeta(final String name) {
        
        final LinkedList<GlobalVariableMeta> found = new LinkedList<GlobalVariableMeta>();
        
        for(GlobalVariableMeta gv : getGlobalVariableMetas()){
            if(gv.getName().equals(name)){
                found.add(gv);
            }
        }
        
        if(found.isEmpty()) {
            Class<?> fieldOwner = reallyClass;
            for(;!fieldOwner.equals(Object.class); fieldOwner = fieldOwner.getSuperclass()){
                try {
                    Field f = fieldOwner.getDeclaredField(name);
                    found.add(new GlobalVariableMeta(this,
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
                    Field f = inter.getDeclaredField(name);
                    found.add(new GlobalVariableMeta(ProductClass.this,
                            AClassFactory.getType(inter),
                            AClassFactory.getType(f.getType()), f.getModifiers(), name));
                    return true;
                } catch (NoSuchFieldException e) {
                    return false;
                }
            }
        }.loop(reallyClass.getInterfaces());
        return found;
    }
    
    @Override
    public int getCastOrder(){
        int order = 0;
        if(type == Type.BOOLEAN_TYPE || name.equals(Boolean.class.getName())){
            order = 1;
        } else if(type == Type.CHAR_TYPE || name.equals(Character.class.getName())){
            order = 2;
        } else if(type == Type.BYTE_TYPE || name.equals(Byte.class.getName())){
            order = 3;
        } else if (type == Type.SHORT_TYPE || name.equals(Short.class.getName())) {
            order = 4;
        } else if (type == Type.INT_TYPE || name.equals(Integer.class.getName())) {
            order = 5;
        } else if (type == Type.LONG_TYPE || name.equals(Long.class.getName())) {
            order = 6;
        } else if (type == Type.FLOAT_TYPE || name.equals(Float.class.getName())) {
            order = 7;
        } else if (type == Type.DOUBLE_TYPE || name.equals(Double.class.getName())) {
            order = 8;
        } else {
            order = 9;
        }
        
        return order;
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
            throw new ASMSupportException(e);
		}
		
		return exist[0];
	}

}
