package cn.wensiqun.asmsupport.core.operator.method;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.exception.NoSuchMethod;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.operator.array.ArrayValue;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.ArrayUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class MethodInvoker extends AbstractOperator implements
    Parameterized {

    protected static String METHOD_NAME_INIT = "<init>";
    
    private static Log log = LogFactory.getLog(MethodInvoker.class);

    //protected AClass[] argumentClasses;
    protected String name;
    protected Parameterized[] arguments;
    protected AClass methodOwner;
    
    /** 调用完方法是否需要保存返回值栈 如果是构造方法则是否需要保存新构造的方法的一个引用 */
    private boolean saveReturn;
    
    /** found method entity will be called*/
    protected AMethodMeta mtdEntity;
    
    /**
     * 
     * @param owner
     * @param name
     * @param arguments
     * @param block
     */
    protected MethodInvoker(ProgramBlockInternal block, AClass owner, 
            String name, 
            Parameterized[] arguments) {
        super(block);
        this.methodOwner = owner;
        this.name = name;
        this.arguments = arguments;
    }

    /**
     * 将方法的参数入栈
     */
    protected void argumentsToStack() {
    	for(int i=0; i<arguments.length; i++){
            Parameterized argu = arguments[i];
            log.debug("push argument to stack");
            if(argu instanceof IVariable){
                ((IVariable) argu).availableFor(this);
            }
            argu.loadToStack(block);
            cast(argu.getParamterizedType(), mtdEntity.getArgClasses()[i]);
        }
    }
    
    private void cast(AClass from, AClass to){
        if(from.isPrimitive() && to.isPrimitive()){
            insnHelper.cast(from.getType(), to.getType());
        }else if(from.isPrimitive()){
            insnHelper.box(from.getType());
        }else if(AClassUtils.isPrimitiveWrapAClass(from) && to.isPrimitive()){
            Type primType = InstructionHelper.getUnBoxedType(from.getType());
            insnHelper.unbox(from.getType());
            insnHelper.cast(primType, to.getType());
        }
    }

    @Override
    protected void initAdditionalProperties() {
    	AClass[] argumentClasses = new AClass[arguments.length];
        List<AClass> argumentClassList = new ArrayList<AClass>();
        for (int i = 0; i < arguments.length; i++) {
            argumentClassList.add(arguments[i].getParamterizedType());
        }
        argumentClassList.toArray(argumentClasses);
        
    	AMethod currentMethod = block.getMethod();
        if(currentMethod.getMode() == ASConstant.METHOD_CREATE_MODE_MODIFY && name.endsWith(ASConstant.METHOD_PROXY_SUFFIX)){
        	mtdEntity = (AMethodMeta) currentMethod.getMethodMeta().clone();
            mtdEntity.setName(name);
        }else{
            // 如果是构造方法则返回类型为自己本身
            if (name.equals(METHOD_NAME_INIT)) {
                mtdEntity = methodOwner.availableConstructor(block.getMethodOwner(), argumentClasses);
                if(mtdEntity == null){
                    throw new NoSuchMethod(methodOwner, name, argumentClasses);
                }
            }else{
                mtdEntity = methodOwner.availableMethod(block.getMethodOwner(), name, argumentClasses);
                if(mtdEntity == null){
                    throw new NoSuchMethod(methodOwner, name, argumentClasses);
                }
            }
        }
        
        if(ModifierUtils.isVarargs(mtdEntity.getModifier())){
            
        	AClass[] foundMethodArgTypes = mtdEntity.getArgClasses();
        	
        	if(ArrayUtils.getLength(foundMethodArgTypes) != ArrayUtils.getLength(arguments) ||
        	   !arguments[ArrayUtils.getLength(arguments) - 1].getParamterizedType().isArray()){
        		
        		int fixedArgsLen = mtdEntity.getArgClasses().length - 1;//argumentClasses.length - 1;
                Parameterized[] fixedArgs = new Parameterized[fixedArgsLen];
                System.arraycopy(arguments, 0, fixedArgs, 0, fixedArgsLen);

                ArrayValue variableVarifyArauments;
                ArrayClass arrayClass = (ArrayClass)mtdEntity.getArgClasses()[mtdEntity.getArgClasses().length - 1];
                variableVarifyArauments = block._newArrayWithValue(arrayClass, 
                       (Parameterized[]) ArrayUtils.subarray(arguments, fixedArgsLen , arguments.length));
                variableVarifyArauments.asArgument();
                
                arguments = (Parameterized[]) ArrayUtils.add(fixedArgs, variableVarifyArauments);
        	}
        }

        for(AClass exception : mtdEntity.getExceptions()){
    	    block.addException(exception);
    	}
        
    }

    @Override
    protected void checkAsArgument() {
        for (Parameterized argu : arguments) {
            // 将argu从Block的执行队列中删除
            // 因为此时由方法调用负责调用value
            argu.asArgument();
        }
        /*if(variableVarifyArauments != null){
            variableVarifyArauments.asArgument();
        }*/
    }

    public boolean isSaveReference() {
        return saveReturn;
    }
    
    @Override
    public void asArgument() {
        //如果方法调用是作为参数传入给其他参数。那么将此方法调用操作从执行队列中移除
        block.removeExe(this);
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        if(getReturnType().equals(Type.VOID_TYPE)){
            throw new ASMSupportException("cannot push the void return type to stack!");
        }
        
        boolean saveRef = isSaveReference();
        setSaveReference(true);
        this.execute();
        setSaveReference(saveRef);
    }
    
    /*public MethodInvoker invoke(String methodName, Parameterized... arguments) {
        return new MethodInvokeInvoker(block, this, methodName, arguments);
    }*/

    public void setSaveReference(boolean saveReturn) {
        this.saveReturn = saveReturn;
    }
    
    public Type getReturnType() {
        return getReturnClass() != null ? getReturnClass().getType() : Type.VOID_TYPE;
    }

    public final AClass getReturnClass() {
        if(name.equals(METHOD_NAME_INIT)){
            return methodOwner;
        }else if(mtdEntity != null){
            return mtdEntity.getReturnClass();
        }else{
            return AClass.VOID_ACLASS;
        }
    }
    
    protected AClass getActuallyOwner(){
        return mtdEntity.getActuallyOwner();
    }
    
    /**
     * 
     * @return
     */
    public int getModifiers() {
        return this.mtdEntity.getModifier();
    }

    @Override
    public AClass getParamterizedType() {
        return getReturnClass();
    }
    
}
