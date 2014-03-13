package cn.wensiqun.asmsupport.definition.method;


import org.apache.commons.collections.CollectionUtils;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.Executable;
import cn.wensiqun.asmsupport.asm.CommonInstructionHelper;
import cn.wensiqun.asmsupport.asm.InstructionHelper;
import cn.wensiqun.asmsupport.asm.StackLocalMethodVisitor;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.block.control.Try;
import cn.wensiqun.asmsupport.block.method.GenericMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.creator.IClassContext;
import cn.wensiqun.asmsupport.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.operators.util.ThrowExceptionContainer;
import cn.wensiqun.asmsupport.utils.ASConstant;
import cn.wensiqun.asmsupport.utils.memory.LocalVariables;
import cn.wensiqun.asmsupport.utils.memory.Scope;
import cn.wensiqun.asmsupport.utils.memory.Stack;
import cn.wensiqun.asmsupport.utils.reflet.ModifierUtils;


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
    
    //0 : indicate add, 1 : indicate modify
    /** 表示当前的Method是添加到Class中还是修改Method */
    private int mode = ASConstant.METHOD_CREATE_MODE_ADD;

    /** 该方法对应的本地变量 */
    private LocalVariables locals;

    /** 调用ASM框架的帮助类 */
    private InstructionHelper insnHelper;

    /** 当前Method的methodBody类 */
    private GenericMethodBody methodBody;

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
     * 当在Method中发现需要创建try catch finally程序块的时候将 try语句块的引用保存在此变量中
     * 然后延迟try程序块内的一些操作的创建。 当和当前Try相关的所有catch和finally程序块都创建
     * 完成 再一一调用期prepare方法 具体可见ProgramBlock的tiggerTryCatchPrepare方法
     * 
     * 这样做的主要目的是为了能自动将finally语句块的内容插入到try或catch中所有return指令之前
     **/
    private Try nearlyTryBlock;

    /**
     * 构造方法
     * @param me 
     * @param context
     * @param methodBody
     * @param mode
     */
    public AMethod(AMethodMeta me, IClassContext context, GenericMethodBody methodBody, int mode) {
        super();
        this.me = me;
        this.context = context;
        this.throwExceptions = new ThrowExceptionContainer();
        this.stack = new Stack();
        this.locals = new LocalVariables();
        this.mode = mode;

        CollectionUtils.addAll(throwExceptions, me.getExceptions());
        
        Type[] argTypes = new Type[me.getArgClasses().length];
        
        for(int i=0; i<argTypes.length; i++){
            argTypes[i] = me.getArgClasses()[i].getType();
        }
        
        methodDesc = Type.getMethodDescriptor(this.me.getReturnType(),
                argTypes);
        
        this.insnHelper = new CommonInstructionHelper(this);
        
        if(!ModifierUtils.isAbstract(me.getModifier())){
            // 设置method属性
            this.methodBody = methodBody;
            this.methodBody.setScope(new Scope(this.locals, null));
            this.methodBody.setOwnerBlock(null);
            this.methodBody.setInsnHelper(insnHelper);
        }
    }
    
    /**
     * 获取所有需要抛出的异常
     * @param block
     */
    private void getThrowExceptionsInProgramBlock(ProgramBlock block){
    	ThrowExceptionContainer blockExceptions = block.getThrowExceptions();
    	if(blockExceptions != null){
    		for(AClass exp : blockExceptions){
    			throwExceptions.add(exp);
    		}
    	}
    	
    	for(Executable exe : block.getExecuteQueue()){
    		if(exe instanceof ProgramBlock){
    			getThrowExceptionsInProgramBlock((ProgramBlock)exe);
    		}
    	}
    }
    
    /**
     * 创建ASM的MethodVisitor
     */
    private void createMethodVisitor(){
    	
    	if(!ModifierUtils.isAbstract(me.getModifier())){
            for(Executable exe : getMethodBody().getExecuteQueue()){
    		    if(exe instanceof ProgramBlock){
    			    getThrowExceptionsInProgramBlock((ProgramBlock)exe);
    		    }
    	    }
    	}
    	
        String[] exceptions = new String[this.throwExceptions.size()];
        int i = 0;
        for(AClass te : this.throwExceptions){
            exceptions[i++] = te.getType().getInternalName();
        }
        
        MethodVisitor mv = context.getClassVisitor().visitMethod(
                me.getModifier(), me.getName(), methodDesc, null, exceptions);
        
        insnHelper.setMv(new StackLocalMethodVisitor(mv, stack));
        
    }

    /**
     * 当前Method是否是static的
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
        if(!ModifierUtils.isAbstract(me.getModifier())){
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
     * @return
     */
    public int nextInsNumber() {
        totalIns++;
        return totalIns;
    }

    public GenericMethodBody getMethodBody() {
        return methodBody;
    }

    public InstructionHelper getInsnHelper() {
        return insnHelper;
    }

    public AMethodMeta getMethodMeta() {
        return me;
    }
    
    public void removeThrowException(AClass exception){
    	throwExceptions.remove(exception);
    }
    
    public String getDesc(){
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

	public Try getNearlyTryBlock() {
		return nearlyTryBlock;
	}

	public void setNearlyTryBlock(Try nearlyTryBlock) {
		this.nearlyTryBlock = nearlyTryBlock;
	}
	
	
}
