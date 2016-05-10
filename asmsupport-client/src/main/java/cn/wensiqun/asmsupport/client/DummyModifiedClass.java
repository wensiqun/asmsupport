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

import cn.wensiqun.asmsupport.client.block.BlockPostern;
import cn.wensiqun.asmsupport.client.block.ModifiedMethodBody;
import cn.wensiqun.asmsupport.client.block.StaticBlockBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassModifier;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.AsmsupportConstant;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;

import java.util.LinkedList;

public class DummyModifiedClass extends AbstractDummy {

    private Class<?> original;

    /** What's the class generate path of the class, use this for debug normally */
    private String classOutPutPath;
    
    /** Whether or not print log.*/
    private boolean printLog;
    
    /** The log file path.*/
    private String logFilePath;

    /** All constructors */
    private LinkedList<DummyConstructor> constructorDummies = new LinkedList<>();
    
    /** All fields */
    private LinkedList<DummyField> fieldDummies = new LinkedList<>();
    
    /** All methods */
    private LinkedList<DummyMethod> methodDummies = new LinkedList<>();
    
    /** class static block */
    private StaticBlockBody staticBlock;
    
    /** All modify methods */
    private LinkedList<DummyModifiedMethod> modifyDummies = new LinkedList<>();
    
    public DummyModifiedClass(Class<?> original) {
    	this(original, CachedThreadLocalClassLoader.getInstance());
    }
    
    public DummyModifiedClass(Class<?> original, ASMSupportClassLoader classLoader) {
    	super(classLoader);
        this.original = original;
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
        fieldDummies.add(new DummyField(getClassLoader()));
        return fieldDummies.getLast();
    }

    /**
     * Create Constructor.
     * 
     * @return
     */
    public DummyConstructor newConstructor() {
        constructorDummies.add(new DummyConstructor(getClassLoader()));
        return constructorDummies.getLast();
    }

    /**
     * Create a method.
     * 
     * @return
     */
    public DummyMethod newMethod(String name) {
        DummyMethod method = new DummyMethod(getClassLoader());
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
        return this.modify(AsmsupportConstant.INIT, argTypes, body);
    }
    
    /**
     * Modify static block.
     * 
     * @param body
     * @return
     */
    public DummyModifiedClass modifyStaticBlock(ModifiedMethodBody body) {
        return this.modify(AsmsupportConstant.CLINIT, null, body);
    }

    /**
     * Build class. 
     * 
     * @return
     */
    public Class<?> build() {
    	LogFactory.LOG_FACTORY_LOCAL.remove();
        if(StringUtils.isNotBlank(logFilePath)) {
        	LogFactory.LOG_FACTORY_LOCAL.set(new LogFactory(logFilePath)); 
        } else if(printLog) {
        	LogFactory.LOG_FACTORY_LOCAL.set(new LogFactory()); 
        }
        ClassModifier cmi = new ClassModifier(original, getClassLoader()) ;
        for(DummyConstructor dummy : constructorDummies) {
            if(dummy.getConstructorBody() != null) {
                cmi.createConstructor(dummy.getModifiers(), dummy.getArgumentTypes(), dummy.getArgumentNames(), dummy.getThrows(), 
                        BlockPostern.getTarget(dummy.getConstructorBody()));    
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
                    dummy.getArgNames(), dummy.getReturnType(), dummy.getThrows(), BlockPostern.getTarget(dummy.getMethodBody()));
        }
        
        if(staticBlock != null) {
            cmi.createStaticBlock(BlockPostern.getTarget(staticBlock));
        }
        
        for(DummyModifiedMethod dummy : modifyDummies) {
            cmi.modifyMethod(dummy.getName(), dummy.getArgumentTypes(), BlockPostern.getTarget(dummy.getMethodBody()));
        }
        
        cmi.setClassOutPutPath(classOutPutPath);
        return cmi.startup();
    }
}
