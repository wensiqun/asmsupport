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
import java.util.Collection;
import java.util.LinkedList;

import cn.wensiqun.asmsupport.org.objectweb.asm.ClassReader;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.AsmsupportClassLoader;
import cn.wensiqun.asmsupport.standard.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.utils.AsmsupportConstant;
import cn.wensiqun.asmsupport.utils.asm.ClassAdapter;
import cn.wensiqun.asmsupport.utils.lang.InterfaceLooper;


/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class ProductClass extends MutableClass {
	
    private Class<?> reallyClass;
    
    private volatile boolean searchedInClass = false;
    
    protected ProductClass(AsmsupportClassLoader classLoader){
    	super(classLoader);
    }
    
    public ProductClass(Class<?> cls, AsmsupportClassLoader classLoader) {
    	super(classLoader);
        this.name = cls.getName();
        this.mod = cls.getModifiers();
        if(cls.getSuperclass() != null) {
            this.superClass = classLoader.getType(cls.getSuperclass());
        }
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
                    		classLoader.getType(fieldOwner),
                    		classLoader.getType(f.getType()), f.getModifiers(), name));
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
                    		classLoader.getType(inter),
                    		classLoader.getType(f.getType()), f.getModifiers(), name));
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
            throw new ASMSupportException("The class " + getName() + " is not array");
        }
        return type.getDimensions();
    }

	@Override
	public AMethodMeta getDeclaredConstructor(IClass... parameterTypes) {
		AMethodMeta result = super.getDeclaredConstructor(parameterTypes);
		if(result == null) {
			parseRealClass();
			result = super.getDeclaredConstructor(parameterTypes);
		}
		return result;
	}

	@Override
	public Collection<AMethodMeta> getDeclaredConstructors() {
		parseRealClass();
		return super.getDeclaredConstructors();
	}

	@Override
	public AMethodMeta getDeclaredMethod(String name, IClass... parameterTypes) {
		AMethodMeta result = super.getDeclaredMethod(name, parameterTypes);
		if(result == null) {
			parseRealClass();
			result = super.getDeclaredMethod(name, parameterTypes);
		}
		return result;
	}

	@Override
	public Collection<AMethodMeta> getDeclaredMethods() {
		parseRealClass();
		return super.getDeclaredMethods();
	}
    
	private void parseRealClass() {
		if(!searchedInClass) {
			synchronized (this) {
				if(!searchedInClass) {
					ClassVisitor cv = new ClassAdapter(){
						@Override
						public MethodVisitor visitMethod(int access, String name,
								String desc, String signature, String[] exceptions) {
							Type methodType = Type.getMethodType(desc);
							Type[] argumentTypes = methodType.getArgumentTypes();
							IClass[] parameterTypes = new BaseClass[argumentTypes.length];
							for(int i = 0; i<argumentTypes.length; i++) {
								parameterTypes[i] = classLoader.getType(argumentTypes[i].getDescriptor());
							}
							
							IClass returnClass = classLoader.getType(methodType.getReturnType().getDescriptor());
							
							IClass[] exceptionTypes = new IClass[exceptions == null ? 0 : exceptions.length];
							for(int i = 0; i<exceptionTypes.length; i++) {
								exceptionTypes[i] = classLoader.getType(exceptions[i]);
							}

							AMethodMeta meta = new AMethodMeta(classLoader, name, ProductClass.this, ProductClass.this,
									parameterTypes, null, returnClass, exceptionTypes, access);
							
							if(name.equals(AsmsupportConstant.CLINIT)){
								ProductClass.this.addClinitMethod(meta);
							} else if (name.equals(AsmsupportConstant.INIT)) {
								ProductClass.this.addConstructor(meta);
							} else if (ModifierUtils.isBridge(access)){
								ProductClass.this.getBridgeMethod().add(meta);
							} else {
								ProductClass.this.addDeclaredMethod(meta);
							}
							return super.visitMethod(access, name, desc, signature, exceptions);
						}
					};
					try {
			    		AsmsupportClassLoader classLoader = ProductClass.this.getClassLoader();
			    		InputStream in = classLoader.getResourceAsStream(reallyClass.getName().replace('.', '/') + ".class");
			    		if (in != null) {
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
					searchedInClass = true;
				}
			}
		}
	}
	
}
