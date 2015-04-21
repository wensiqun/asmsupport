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

import cn.wensiqun.asmsupport.core.creator.clazz.ClassModifier;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.lang.StringUtils;
import cn.wensiqun.asmsupport.standard.exception.ASMSupportException;

public class DummyModifiedClass {

    private Class<?> original;

    /** Specify the classloader */
    private ClassLoader classLoader;

    /** What's the class generate path of the class, use this for debug normally */
    private String classOutPutPath;
    
    /** Whether or not print log.*/
    private boolean printLog;
    
    /** The log file path.*/
    private String logFilePath;

    /** All constructors */
    private LinkedList<DummyConstructor> constructorDummies = new LinkedList<DummyConstructor>();
    
    /** All fields */
    private LinkedList<DummyField> fieldDummies = new LinkedList<DummyField>();
    
    /** All methods */
    private LinkedList<DummyMethod> methodDummies = new LinkedList<DummyMethod>();
    
    /** class static block */
    private StaticBlockBody staticBlock;
    
    /** All modify methods */
    private LinkedList<DummyModifiedMethod> modifyDummies = new LinkedList<DummyModifiedMethod>();
    

    public DummyModifiedClass(Class<?> original) {
        this.original = original;
    }
    
    /**
     * Set the classloader
     * 
     * @param cl
     * @return
     */
    public DummyModifiedClass setClassLoader(ClassLoader cl) {
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
    public DummyModifiedClass setClassOutPutPath(String path) {
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
     * Whether or not print log information. if set {@code true} than print it to console.
     * Also you can print to file by method {@link DummyClass#setLogFilePath(String)}
     * 
     * @param print
     * @return
     */
    public DummyModifiedClass setPrintLog(boolean print) {
        printLog = print;
        return this;
    }

    /**
     * The log information out put path. call the method will set {@code printLog} to {@code true.}
     * 
     * @param logFile
     * @return
     */
    public DummyModifiedClass setLogFilePath(String logFile){
        logFilePath = logFile;
        printLog = true;
        return this;
    }

    /**
     * Create a field
     * 
     * @return
     */
    public DummyField newField() {
        fieldDummies.add(new DummyField());
        return fieldDummies.getLast();
    }

    /**
     * Create Constructor.
     * 
     * @return
     */
    public DummyConstructor newConstructor() {
        constructorDummies.add(new DummyConstructor());
        return constructorDummies.getLast();
    }

    /**
     * Create a method.
     * 
     * @return
     */
    public DummyMethod newMethod(String name) {
        DummyMethod method = new DummyMethod();
        method.name(name);
        methodDummies.add(method);
        return method;
    }

    /**
     * Create a static block.
     * 
     * @return
     */
    public DummyModifiedClass newStaticBlock(StaticBlockBody staticBlock) {
        if(staticBlock != null) {
            throw new ASMSupportException("Static Block is already existes.");
        }
        this.staticBlock = staticBlock;
        return this;
    }
    
    /**
     * Modify common method.
     * 
     * @param method
     * @param argTypes
     * @param body
     * @return
     */
    public DummyModifiedClass modify(String method, Class<?>[] argTypes, ModifiedMethodBody body){
        DummyModifiedMethod dmm = new DummyModifiedMethod(method, argTypes, body);
        this.modifyDummies.add(dmm);
        return this;
    }
    
    /**
     * Modify constructor
     * 
     * @param argTypes
     * @param body
     * @return
     */
    public DummyModifiedClass modifyConstructor(Class<?>[] argTypes, ModifiedMethodBody body) {
        return this.modify(ASConstant.INIT, argTypes, body);
    }
    
    /**
     * Modify static block.
     * 
     * @param body
     * @return
     */
    public DummyModifiedClass modifyStaticBlock(ModifiedMethodBody body) {
        return this.modify(ASConstant.CLINIT, null, body);
    }

    /**
     * Build class. 
     * 
     * @return
     */
    public Class<?> build() {
        ASConstant.LOG_FACTORY_LOCAL.remove();
        if(StringUtils.isNotBlank(logFilePath)) {
            ASConstant.LOG_FACTORY_LOCAL.set(new LogFactory(logFilePath)); 
        } else if(printLog) {
            ASConstant.LOG_FACTORY_LOCAL.set(new LogFactory()); 
        }
        ClassModifier cmi = new ClassModifier(original);
        for(DummyConstructor dummy : constructorDummies) {
            if(dummy.getConstructorBody() != null) {
                cmi.createConstructor(dummy.getModifiers(), dummy.getArgumentTypes(), dummy.getArgumentNames(), dummy.getThrows(), dummy.getConstructorBody().target);    
            } else {
                throw new ASMSupportException("Not found body...");
            }
        }
        
        for(DummyField dummy : fieldDummies) {
            if(dummy.getType() == null) {
                throw new ASMSupportException("Not specify field type for field '" +  dummy.getName() + "'");
            }
            if(dummy.getName() == null) {
                throw new ASMSupportException("Not specify field name.");
            }
            cmi.createField(dummy.getName(), dummy.getModifiers(), dummy.getType(), dummy.getValue());
        }
        
        for(DummyMethod dummy : methodDummies) {
            cmi.createMethodForDummy(dummy.getModifiers(), dummy.getName(), dummy.getArgTypes(),
                    dummy.getArgNames(), dummy.getReturnType(), dummy.getThrows(), dummy.getMethodBody().target);
        }
        
        if(staticBlock != null) {
            cmi.createStaticBlock(staticBlock.target);
        }
        
        for(DummyModifiedMethod dummy : modifyDummies) {
            cmi.modifyMethod(dummy.getName(), dummy.getArgumentTypes(), dummy.getMethodBody().target);
        }
        
        cmi.setParentClassLoader(classLoader);
        cmi.setClassOutPutPath(classOutPutPath);
        return cmi.startup();
    }
}
