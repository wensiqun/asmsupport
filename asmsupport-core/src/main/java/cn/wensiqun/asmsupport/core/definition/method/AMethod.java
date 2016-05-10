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
package cn.wensiqun.asmsupport.core.definition.method;

import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.asm.CommonInstructionHelper;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.asm.StackLocalMethodVisitor;
import cn.wensiqun.asmsupport.core.block.AbstractKernelBlock;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.method.AbstractKernelMethodBody;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.core.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.core.utils.common.ThrowExceptionContainer;
import cn.wensiqun.asmsupport.core.utils.memory.LocalVariables;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.core.utils.memory.Stack;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.MutableClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.AsmsupportConstant;
import cn.wensiqun.asmsupport.utils.collections.CollectionUtils;

/**
 * The method
 * 
 * @author wensiqun at 163.com(Joe Wen)
 */
public class AMethod {

    /** Method Meta */
    private AMethodMeta meta;

    /** A stakc of current method */
    private Stack stack;

    /** 0 : indicate add, 1 : indicate modif*/
    private int mode = AsmsupportConstant.METHOD_CREATE_MODE_ADD;

    /** The local vairable container of current method*/
    private LocalVariables locals;

    private InstructionHelper insnHelper;

    /** The method body of current method */
    private AbstractKernelMethodBody methodBody;

    /** A counter indicate the jvm instruction count */
    private int instructionCounter = 0;

    /** Indicate the method that's need to throw in this method  */
    private ThrowExceptionContainer exceptionContainer;

    /** The method of current method */
    private LocalVariable[] arguments;

    /** super key word  */
    private SuperVariable superVariable;

    /** this key word */
    private ThisVariable thisVariable;

    private ClassVisitor classVisitor;
    
    private ASMSupportClassLoader classLoader;
    
    public AMethod(AMethodMeta meta, ClassVisitor classVisitor, ASMSupportClassLoader classLoader, AbstractKernelMethodBody methodBody, int mode) {
        this.classVisitor = classVisitor;
        this.classLoader = classLoader;
        this.meta = meta;
        this.mode = mode;
        this.exceptionContainer = new ThrowExceptionContainer();
        this.stack = new Stack();
        this.locals = new LocalVariables();
        CollectionUtils.addAll(exceptionContainer, meta.getExceptions());
        this.insnHelper = new CommonInstructionHelper(this);

        if (!ModifierUtils.isAbstract(meta.getModifiers())) {
            if (methodBody != null) {
                this.methodBody = methodBody;
                this.methodBody.setScope(new Scope(this.locals, null));
                this.methodBody.setInsnHelper(insnHelper);
            } else {
                throw new ASMSupportException("Error while create method '" + meta.getName()
                        + "', cause by not found method body and it not abstract method.");
            }
        }
    }

    /**
     * Get all exception that's need to throws.
     */
    private void getThrowExceptionsInProgramBlock(AbstractKernelBlock block) {
        if (block instanceof KernelProgramBlock) {
            ThrowExceptionContainer blockExceptions = ((KernelProgramBlock) block).getThrowExceptions();
            if (blockExceptions != null) {
                for (IClass exp : blockExceptions) {
                    exceptionContainer.add(exp);
                }
            }
        }

        for (Executable exe : block.getQueue()) {
            if (exe instanceof AbstractKernelBlock) {
                getThrowExceptionsInProgramBlock((AbstractKernelBlock) exe);
            }
        }
    }

    /**
     * Create {@link MethodVisitor} for current method
     */
    private void createMethodVisitor() {

        if (!ModifierUtils.isAbstract(meta.getModifiers())) {
            for (Executable exe : getMethodBody().getQueue()) {
                if (exe instanceof AbstractKernelBlock) {
                    getThrowExceptionsInProgramBlock((AbstractKernelBlock) exe);
                }
            }
        }

        String[] exceptions = new String[this.exceptionContainer.size()];
        int i = 0;
        for (IClass te : this.exceptionContainer) {
            exceptions[i++] = te.getType().getInternalName();
        }

        MethodVisitor mv = classVisitor.visitMethod(meta.getModifiers(), meta.getName(), meta.getDescription(), null,
                exceptions);

        insnHelper.setMv(new StackLocalMethodVisitor(mv, stack));

    }

    /**
     * Start create/modify method
     */
    public void startup() {
        createMethodVisitor();
        if (!ModifierUtils.isAbstract(meta.getModifiers())) {
            this.methodBody.execute();
            this.methodBody.endMethodBody();
        }
        insnHelper.endMethod();
    }

    /**
     * Get the operand stack of current method
     */
    public Stack getStack() {
        return stack;
    }

    /**
     * Get the local variable container of current method
     */
    public LocalVariables getLocals() {
        return locals;
    }

    /**
     * Get the order of next instruction.
     */
    public int getNextInstructionNumber() {
        return ++instructionCounter;
    }

    
    public AbstractKernelMethodBody getMethodBody() {
        return methodBody;
    }

    /**
     * Get helper
     */
    public InstructionHelper getInsnHelper() {
        return insnHelper;
    }

    /**
     * Get the method meta.
     */
    public AMethodMeta getMeta() {
        return meta;
    }

    /**
     * Remove a exception type from container
     */
    public void removeThrowException(IClass exception) {
        exceptionContainer.remove(exception);
    }

    @Override
    public String toString() {
        return meta.getMethodString();
    }

    /**
     * Returns the {@code MutableClass} object representing the class or interface
     * that declares the method represented by this {@code AMethod} object.
     */
    public MutableClass getDeclaringClass() {
        return (MutableClass) meta.getActuallyOwner();
    }

    /**
     * Return the parameters, the parameter represent as a {@link LocalVariable}
     */
    public LocalVariable[] getParameters() {
        return arguments;
    }

    /**
     * Set the parameters to this method
     */
    public void setParameters(LocalVariable[] arguments) {
        this.arguments = arguments;
    }

    public int getMode() {
        return mode;
    }
    
    public SuperVariable getSuperVariable() {
    	if(superVariable == null){
            superVariable = new SuperVariable(meta.getActuallyOwner());
    	}
        return superVariable;
    }

    public ThisVariable getThisVariable() {
    	if(thisVariable == null){
            thisVariable = new ThisVariable(meta.getActuallyOwner());
    	}
        return thisVariable;
    }

	public ASMSupportClassLoader getClassLoader() {
		return classLoader;
	}

}
