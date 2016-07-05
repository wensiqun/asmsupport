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

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.Executable;
import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.core.utils.InstructionBlockNode;
import cn.wensiqun.asmsupport.core.utils.common.ThrowExceptionContainer;
import cn.wensiqun.asmsupport.core.utils.memory.LocalVariables;
import cn.wensiqun.asmsupport.core.utils.memory.OperandStack;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.MutableClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.Modifiers;
import cn.wensiqun.asmsupport.utils.collections.CollectionUtils;

/**
 * The method
 * 
 * @author wensiqun at 163.com(Joe Wen)
 */
public class AMethod {

    /** Method Meta */
    private AMethodMeta meta;

    /** A stack of current method */
    private OperandStack stack;

    private int mode;

    /** The local vairables container of current method*/
    private LocalVariables locals;

    /** Operate asm utils. */
    private Instructions instructions;

    /** The method body of current method */
    private AbstractMethodBody body;

    /** A counter indicate the jvm instruction count */
    private int instructionCounter = 0;

    /** Indicate the method that's need to throw in this method  */
    private ThrowExceptionContainer exceptions;

    /** ASM ClassVisitor. */
    private ClassVisitor classVisitor;

    /** ASMSupport Class Loader. */
    private ASMSupportClassLoader classLoader;
    
    public AMethod(AMethodMeta meta, ClassVisitor classVisitor, ASMSupportClassLoader classLoader, AbstractMethodBody body, int mode) {
        this.classVisitor = classVisitor;
        this.classLoader = classLoader;
        this.meta = meta;
        this.mode = mode;
        this.exceptions = new ThrowExceptionContainer();
        this.stack = new OperandStack();
        this.locals = new LocalVariables();
        CollectionUtils.addAll(exceptions, meta.getExceptions());
        this.instructions = new Instructions(locals, stack);

        if (!Modifiers.isAbstract(meta.getModifiers())) {
            if (body != null) {
                this.body = body;
                this.body.setScope(new Scope(this.locals, null));
                this.body.setMethod(this);
            } else {
                throw new ASMSupportException("Error while create method '" + meta.getName()
                        + "', cause by not found method body and it not abstract method.");
            }
        }
    }

    /**
     * Get all exception that's need to throws.
     */
    private void recheckThrows(InstructionBlockNode block) {
        if (block instanceof KernelProgramBlock) {
            ThrowExceptionContainer blockExceptions = ((KernelProgramBlock) block).getThrowExceptions();
            if (blockExceptions != null) {
                for (IClass exp : blockExceptions) {
                    exceptions.add(exp);
                }
            }
        }

        for (Executable exe : block.getChildren()) {
            if (exe instanceof InstructionBlockNode) {
                recheckThrows((InstructionBlockNode) exe);
            }
        }
    }

    /**
     * Create {@link MethodVisitor} for current method
     */
    private void createMethodVisitor() {

        if (!Modifiers.isAbstract(meta.getModifiers())) {
            for (Executable exe : getBody().getChildren()) {
                if (exe instanceof InstructionBlockNode) {
                    recheckThrows((InstructionBlockNode) exe);
                }
            }
        }

        String[] exceptions = new String[this.exceptions.size()];
        int i = 0;
        for (IClass te : this.exceptions) {
            exceptions[i++] = te.getType().getInternalName();
        }
        instructions.setMv(classVisitor.visitMethod(meta.getModifiers(), meta.getName(), meta.getDescription(), null,
                exceptions));
    }

    /**
     * Start create/modify method
     */
    public void startup() {
        createMethodVisitor();
        if (!Modifiers.isAbstract(meta.getModifiers())) {
            MethodContext context = new MethodContext();
            context.setMethod(this);
            this.body.execute(context);
            this.body.endMethodBody();
        }
        instructions.endMethod();
    }

    /**
     * Get the operand stack of current method
     */
    public OperandStack getStack() {
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

    /**
     * Get the body of the method
     * @return
     */
    public AbstractMethodBody getBody() {
        return body;
    }

    /**
     * Get helper
     */
    public Instructions getInstructions() {
        return instructions;
    }

    /**
     * Get the method meta.
     */
    public AMethodMeta getMeta() {
        return meta;
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

    public int getMode() {
        return mode;
    }

	public ASMSupportClassLoader getClassLoader() {
		return classLoader;
	}

}
