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
import cn.wensiqun.asmsupport.core.block.AbstractBlockInternal;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.block.control.exception.TryInternal;
import cn.wensiqun.asmsupport.core.block.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.core.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.core.creator.IClassContext;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.collections.CollectionUtils;
import cn.wensiqun.asmsupport.core.utils.common.ThrowExceptionContainer;
import cn.wensiqun.asmsupport.core.utils.memory.LocalVariables;
import cn.wensiqun.asmsupport.core.utils.memory.Scope;
import cn.wensiqun.asmsupport.core.utils.memory.Stack;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.clazz.AClass;

/**
 * 方法的抽象。
 * 
 * @author 温斯群(Joe Wen)
 * 
 */
public class AMethod {

    /** 方法实体 */
    private AMethodMeta me;

    /** 该方法对应的栈 */
    private Stack stack;

    /** 表示当前的Method是添加到Class中还是修改Method */
    private int mode = ASConstant.METHOD_CREATE_MODE_ADD;

    /** 该方法对应的本地变量 */
    private LocalVariables locals;

    /** 调用ASM框架的帮助类 */
    private InstructionHelper insnHelper;

    /** 当前Method的methodBody类 */
    private AbstractMethodBody methodBody;

    /** 当前Method所包含的所有字节码操作 */
    private int totalIns = 0;

    /** ClassCreator 或者 ClassModifier的引用 */
    private IClassContext context;

    /** 当前方法的描述 */
    private String methodDesc;

    /** 当前方法需要抛出的异常 */
    private ThrowExceptionContainer throwExceptions;

    /** 当前方法的参数 */
    private LocalVariable[] arguments;

    /**
     * 
     */
    private boolean creatingImplicitFinally = false;

    /**
     * 当在Method中发现需要创建try catch finally程序块的时候将 try语句块的引用保存在此变量中
     * 然后延迟try程序块内的一些操作的创建。 当和当前Try相关的所有catch和finally程序块都创建 完成 再一一调用期prepare方法
     * 具体可见ProgramBlock的tiggerTryCatchPrepare方法
     * 
     * 这样做的主要目的是为了能自动将finally语句块的内容插入到try或catch中所有return指令之前
     **/
    private TryInternal nearlyTryBlock;

    /**
     * 构造方法
     * 
     * @param me
     * @param context
     * @param methodBody
     * @param mode
     */
    public AMethod(AMethodMeta me, IClassContext context, AbstractMethodBody methodBody, int mode) {
        super();
        this.me = me;
        this.context = context;
        this.throwExceptions = new ThrowExceptionContainer();
        this.stack = new Stack();
        this.locals = new LocalVariables();
        this.mode = mode;

        CollectionUtils.addAll(throwExceptions, me.getExceptions());

        Type[] argTypes = new Type[me.getArgClasses().length];

        for (int i = 0; i < argTypes.length; i++) {
            argTypes[i] = me.getArgClasses()[i].getType();
        }

        methodDesc = Type.getMethodDescriptor(this.me.getReturnType(), argTypes);

        this.insnHelper = new CommonInstructionHelper(this);

        if (!ModifierUtils.isAbstract(me.getModifier())) {
            if (methodBody != null) {
                // 设置method属性
                this.methodBody = methodBody;
                this.methodBody.setScope(new Scope(this.locals, null));
                this.methodBody.setInsnHelper(insnHelper);
            } else {
                throw new ASMSupportException("Error while create method '" + me.getName()
                        + "', cause by not found method body and it not abstract method.");
            }
        }
    }

    /**
     * 获取所有需要抛出的异常
     * 
     * @param block
     */
    private void getThrowExceptionsInProgramBlock(AbstractBlockInternal block) {
        if (block instanceof ProgramBlockInternal) {
            ThrowExceptionContainer blockExceptions = ((ProgramBlockInternal) block).getThrowExceptions();
            if (blockExceptions != null) {
                for (AClass exp : blockExceptions) {
                    throwExceptions.add(exp);
                }
            }
        }

        for (Executable exe : block.getQueue()) {
            if (exe instanceof AbstractBlockInternal) {
                getThrowExceptionsInProgramBlock((AbstractBlockInternal) exe);
            }
        }
    }

    /**
     * 创建ASM的MethodVisitor
     */
    private void createMethodVisitor() {

        if (!ModifierUtils.isAbstract(me.getModifier())) {
            for (Executable exe : getMethodBody().getQueue()) {
                if (exe instanceof AbstractBlockInternal) {
                    getThrowExceptionsInProgramBlock((AbstractBlockInternal) exe);
                }
            }
        }

        String[] exceptions = new String[this.throwExceptions.size()];
        int i = 0;
        for (AClass te : this.throwExceptions) {
            exceptions[i++] = te.getType().getInternalName();
        }

        MethodVisitor mv = context.getClassVisitor().visitMethod(me.getModifier(), me.getName(), methodDesc, null,
                exceptions);

        insnHelper.setMv(new StackLocalMethodVisitor(mv, stack));

    }

    /**
     * 当前Method是否是static的
     * 
     * @return
     */
    public boolean isStatic() {
        return (me.getModifier() & Opcodes.ACC_STATIC) != 0;
    }

    /**
     * 启动创建或修改程序
     */
    public void startup() {
        createMethodVisitor();
        if (!ModifierUtils.isAbstract(me.getModifier())) {
            this.methodBody.execute();
            this.methodBody.endMethodBody();
        }
        insnHelper.endMethod();
    }

    public Stack getStack() {
        return stack;
    }

    public LocalVariables getLocals() {
        return locals;
    }

    /**
     * 下一条指令的序号
     * 
     * @return
     */
    public int nextInsNumber() {
        totalIns++;
        return totalIns;
    }

    public AbstractMethodBody getMethodBody() {
        return methodBody;
    }

    public InstructionHelper getInsnHelper() {
        return insnHelper;
    }

    public AMethodMeta getMethodMeta() {
        return me;
    }

    public void removeThrowException(AClass exception) {
        throwExceptions.remove(exception);
    }

    public String getDesc() {
        return methodDesc;
    }

    @Override
    public String toString() {
        return me.getMethodString();
    }

    public NewMemberClass getMethodOwner() {
        return context.getCurrentClass();
    }

    public LocalVariable[] getArguments() {
        return arguments;
    }

    public void setArguments(LocalVariable[] arguments) {
        this.arguments = arguments;
    }

    public int getMode() {
        return mode;
    }

    public TryInternal getNearlyTryBlock() {
        return nearlyTryBlock;
    }

    public void setNearlyTryBlock(TryInternal nearlyTryBlock) {
        this.nearlyTryBlock = nearlyTryBlock;
    }

    public boolean isCreatingImplicitFinally() {
        return creatingImplicitFinally;
    }

    public void setCreatingImplicitFinally(boolean creatingImplicitFinally) {
        this.creatingImplicitFinally = creatingImplicitFinally;
    }

}
