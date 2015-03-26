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

import java.util.LinkedList;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.creator.clazz.InterfaceCreator;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.CommonUtils;
import cn.wensiqun.asmsupport.core.utils.lang.StringUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class DummyInterface {

    /** Version of Class File Format */
    private int javaVersion = CommonUtils.getSystemJDKVersion();

    /** Package name of class */
    private String packageName = StringUtils.EMPTY;

    /** Package name of class */
    private String name;

    /** Any interfaces in the class */
    private Class<?>[] interfaces;
    
    /** Specify the classloader */
    private ClassLoader classLoader;

    /** What's the class generate path of the class, use this for debug normally */
    private String classOutPutPath;
    
    /** All fields */
    private LinkedList<DummyField> fieldDummies = new LinkedList<DummyField>();
    

    /** All methods */
    private LinkedList<DummyInterfaceMethod> methodDummies = new LinkedList<DummyInterfaceMethod>();
    
    /** class static block */
    private StaticBlockBody staticBlock;
    
    /**
     * The modifiers
     */
    protected int modifiers = Opcodes.ACC_ABSTRACT;
    
    public DummyInterface() {
    }
    
    /**
     * The interface qualified name.
     * 
     * @param qualifiedName
     */
    public DummyInterface(String qualifiedName) {
        if(StringUtils.isNotBlank(qualifiedName)) {
            int lastDot = qualifiedName.lastIndexOf('.');
            if(lastDot > 0) {
                packageName = qualifiedName.substring(0, lastDot);
                name = qualifiedName.substring(lastDot + 1);
            }
        }
    }
    
    /**
     * Constructor
     * 
     * @param pkgName package name.
     * @param name interface name
     */
    public DummyInterface(String pkgName, String name) {
    	this.packageName = pkgName;
    	this.name = name;
    }
    
    
    /**
     * Set the access to private.
     * 
     * @return
     */
    public DummyInterface public_() {
        modifiers = (modifiers & ~Opcodes.ACC_PUBLIC) + Opcodes.ACC_PUBLIC;
        return this;
    }

    /**
     * Check the access whether or not public.
     * 
     * @return
     */
    public boolean isPublic() {
        return (modifiers & Opcodes.ACC_PUBLIC) != 0;
    }
    

    /**
     * Set the access to default.
     * 
     * @return
     */
    public DummyInterface default_() {
        modifiers = modifiers & ~ Opcodes.ACC_PUBLIC;
        return this;
    }
    
    /**
     * Check the access whether or not default.
     * 
     * @return
     */
    public boolean isDefault() {
        return !isPublic();
    }
    
    /**
     * Set the package name of class. 
     * 
     * @param packageName
     * @return
     */
    public DummyInterface package_(String packageName) {
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
    public DummyInterface name(String name) {
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
     * Set the jdk version of Class File Format
     * 
     * @param version
     * @return
     */
    public DummyInterface setJavaVersion(int version) {
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
     * Set the interfaces
     * 
     * @param interfaces
     * @return
     */
    public DummyInterface extends_(Class<?>... interfaces) {
        this.interfaces = interfaces;
        return this;
    }
    
    /**
     * Get the interfaces.
     * 
     * @return
     */
    public Class<?>[] getExtends() {
        if(interfaces == null) {
            return new Class[0];
        }
        Class<?>[] copy = new Class[interfaces.length];
        System.arraycopy(interfaces, 0, copy, 0, copy.length);
        return copy;
    }
    
    /**
     * Set the classloader
     * 
     * @param cl
     * @return
     */
    public DummyInterface setClassLoader(ClassLoader cl) {
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
    public DummyInterface setClassOutPutPath(String path) {
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
     * Create a field
     * 
     * @return
     */
    public DummyField newField(AClass type, String name) {
        DummyField field = new DummyField(); 
        fieldDummies.add(field);
        field.type(type).name(name);
        return field;
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
        return field;
    }
    

    /**
     * Create a method.
     * 
     * @return
     */
    public DummyInterfaceMethod newMethod(String name) {
        DummyInterfaceMethod method = new DummyInterfaceMethod();
        method.name(name);
        methodDummies.add(method);
        return method;
    }    


    /**
     * Create a static block.
     * 
     * @return
     */
    public DummyInterface newStaticBlock(StaticBlockBody staticBlock) {
        if(this.staticBlock != null) {
            throw new ASMSupportException("Static Block is already existes.");
        }
        this.staticBlock = staticBlock;
        return this;
    }
    
    /**
     * Build class. 
     * 
     * @return
     */
    public Class<?> build() {
        InterfaceCreator ici = new InterfaceCreator(javaVersion, packageName + "." + name, interfaces);
        
        for(DummyField dummy : fieldDummies) {
            if(dummy.getType() == null) {
                throw new ASMSupportException("Not specify field type for field '" +  dummy.getName() + "'");
            }
            if(dummy.getName() == null) {
                throw new ASMSupportException("Not specify field name.");
            }
            ici.createField(dummy.getName(), dummy.getType(), dummy.getValue());
        }
        
        for(DummyInterfaceMethod dummy : methodDummies) {
            ici.createMethod(dummy.getName(), dummy.getArgTypes(), dummy.getReturnType(), dummy.getThrows());
        }
        
        if(staticBlock != null) {
            ici.createStaticBlock(staticBlock.target);
        }
        
        ici.setParentClassLoader(classLoader);
        ici.setClassOutPutPath(classOutPutPath);
        return ici.startup();
    }
}
