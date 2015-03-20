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
package cn.wensiqun.asmsupport.client;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.creator.clazz.EnumCreator;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.CommonUtils;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.StringUtils;

public class DummyEnum {

    /** Version of Class File Format */
    private int javaVersion = CommonUtils.getSystemJDKVersion();

    /** Package name of class */
    private String packageName = StringUtils.EMPTY;

    /** Package name of class */
    private String name;

    /** Any interfaces in the class */
    private Class<?>[] interfaces;
 
    /** All enum constants. */
    private Set<String> enums = new HashSet<String>();

    /** All constructors */
    private LinkedList<DummyEnumConstructor> constructorDummies = new LinkedList<DummyEnumConstructor>();

    /** class static block */
    private EnumStaticBlockBody staticBlock;
    
    /** All fields */
    private LinkedList<DummyField> fieldDummies = new LinkedList<DummyField>();
    
    /** All methods */
    private LinkedList<DummyMethod> methodDummies = new LinkedList<DummyMethod>();

    /** Specify the classloader */
    private ClassLoader classLoader;

    /** What's the class generate path of the class, use this for debug normally */
    private String classOutPutPath;

    public DummyEnum() {
    }
    
    public DummyEnum(String qualifiedName) {
        if(StringUtils.isNotBlank(qualifiedName)) {
            int lastDot = qualifiedName.lastIndexOf('.');
            if(lastDot > 0) {
                packageName = qualifiedName.substring(0, lastDot);
                name = qualifiedName.substring(lastDot + 1);
            }
        }
    }
    
    /**
     * Set the jdk version of Class File Format
     * 
     * @param version
     * @return
     */
    public DummyEnum setJavaVersion(int version) {
        javaVersion = version;
        return this;
    }

    /**
     * 
     * Get the jdk version of Class File Format
     * 
     * @return
     */
    public int getJavaVersion() {
        return javaVersion;
    }
    

    
    /**
     * Set the package name of class. 
     * 
     * @param packageName
     * @return
     */
    public DummyEnum package_(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * Get the class package name.
     * 
     * @return
     */
    public String getPackage() {
        return packageName;
    }

    /**
     * Set the class name.
     * 
     * @param name
     * @return
     */
    public DummyEnum name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the class name
     * 
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the interfaces
     * 
     * @param interfaces
     * @return
     */
    public DummyEnum implements_(Class<?>... interfaces) {
        this.interfaces = interfaces;
        return this;
    }


    /**
     * Set the classloader
     * 
     * @param cl
     * @return
     */
    public DummyEnum setClassLoader(ClassLoader cl) {
        this.classLoader = cl;
        return this;
    }
    
    /**
     * Get the class loader
     * 
     * @return
     */
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * Set the class out put path.
     * 
     * @param path
     * @return
     */
    public DummyEnum setClassOutPutPath(String path) {
        this.classOutPutPath = path;
        return this;
    }
    
    /**
     * Get the class out put path.
     * 
     * @return
     */
    public String getClassOutPutPath() {
        return classOutPutPath;
    }
    
    /**
     * Get the interfaces.
     * 
     * @return
     */
    public Class<?>[] getImplements() {
        if(interfaces == null) {
            return new Class[0];
        }
        Class<?>[] copy = new Class[interfaces.length];
        System.arraycopy(interfaces, 0, copy, 0, copy.length);
        return copy;
    }
    
    /**
     * create a new enum constant.
     * 
     * @param enumName
     * @return
     */
    public DummyEnum newEnum(String enumName) {
        if(enums.contains(enumName)) {
            throw new ASMSupportException("Then enum \"" + enumName + "\" type are duplicated declare.");
        }
        enums.add(enumName);
        return this;
    }
    
    /**
     * Create Constructor.
     * 
     * @return
     */
    public DummyEnumConstructor newConstructor() {
        constructorDummies.add(new DummyEnumConstructor());
        return constructorDummies.getLast();
    }
    

    /**
     * Create a static block.
     * 
     * @return
     */
    public DummyEnum newStaticBlock(EnumStaticBlockBody staticBlock) {
        if(this.staticBlock != null) {
            throw new ASMSupportException("Static Block is already existes.");
        }
        this.staticBlock = staticBlock;
        return this;
    }
    
    /**
     * Create a field
     * 
     * @return
     */
    public DummyField newField(AClass type, String name) {
        DummyField field = new DummyField(); 
        fieldDummies.add(field);
        field.type(type).name(name);
        return fieldDummies.getLast();
    }
    
    /**
     * Create a field
     * 
     * @return
     */
    public DummyField newField(Class<?> type, String name) {
        DummyField field = new DummyField(); 
        fieldDummies.add(field);
        field.type(type).name(name);
        return fieldDummies.getLast();
    }
    
    

    /**
     * Create a method.
     * 
     * @return
     */
    public DummyMethod newMethod(String name) {
        DummyMethod method = new DummyMethod();
        methodDummies.add(method);
        method.name(name);
        return method;
    }
    


    /**
     * Build class. 
     * 
     * @return
     */
    public Class<?> build() {
        EnumCreator eci = new EnumCreator(javaVersion, packageName + "." + name, interfaces);
        for(DummyEnumConstructor dummy : constructorDummies) {
            if(dummy.getConstructorBody() != null) {
                eci.createConstructor(dummy.getArgumentTypes(), dummy.getArgumentNames(), dummy.getConstructorBody().target);    
            } else {
                throw new ASMSupportException("Not found body...");
            }
        }
        
        for(String enumConstant : enums) {
            eci.createEnumConstant(enumConstant);
        }
        
        for(DummyField dummy : fieldDummies) {
            if(dummy.getType() == null) {
                throw new ASMSupportException("Not specify field type for field '" +  dummy.getName() + "'");
            }
            if(dummy.getName() == null) {
                throw new ASMSupportException("Not specify field name.");
            }
            eci.createField(dummy.getName(), dummy.getModifiers(), dummy.getType(), dummy.getValue());
        }
        
        for(DummyMethod dummy : methodDummies) {
            eci.createMethodForDummy(dummy.getName(), dummy.getArgumentTypes(),
                    dummy.getArgumentNames(), 
                    dummy.getReturnType(), 
                    dummy.getThrows(), 
                    dummy.getModifiers(), 
                    dummy.getMethodBody().target);
        }
        
        if(staticBlock != null) {
            eci.createStaticBlock(staticBlock.target);
        }
        
        eci.setParentClassLoader(classLoader);
        eci.setClassOutPutPath(classOutPutPath);
        return eci.startup();
    }
}
