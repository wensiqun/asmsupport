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
package cn.wensiqun.asmsupport.core.operator.method;

import cn.wensiqun.asmsupport.core.context.MethodContext;
import cn.wensiqun.asmsupport.core.asm.Instructions;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.build.MethodBuilder;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.operator.AbstractParamOperator;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.array.KernelArrayValue;
import cn.wensiqun.asmsupport.core.utils.jls15_12_2.MethodChooser;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.ArrayClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;
import cn.wensiqun.asmsupport.utils.Modifiers;
import cn.wensiqun.asmsupport.utils.ASConstants;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class MethodInvoker extends AbstractParamOperator {

    private static final Log LOG = LogFactory.getLog(MethodInvoker.class);

    protected String name;
    protected KernelParam[] arguments;
    protected IClass methodOwner;
    
    /** whether or not save the return value to stack, must be save a 
     * result to stack when call constructor*/
    private boolean saveReturn;
    
    /** found method entity will be called*/
    protected AMethodMeta methodMeta;
    
    /**
     * 
     * @param owner
     * @param name
     * @param arguments
     * @param block
     */
    protected MethodInvoker(KernelProgramBlock block, IClass owner, String name, KernelParam[] arguments) {
        super(block, Operator.COMMON);
        this.methodOwner = owner;
        this.name = name;
        this.arguments = arguments;
    }

    protected void argumentsToStack(MethodContext context) {
    	for(int i=0; i<arguments.length; i++){
            KernelParam arg = arguments[i];
            if(LOG.isPrintEnabled()) {
                LOG.print("push argument to stack");
            }
            if(arg instanceof IVariable){
                ((IVariable) arg).availableFor(this);
            }
            arg.loadToStack(context);
            cast(context, arg.getResultType(), methodMeta.getParameterTypes()[i]);
        }
    }
    
    private void cast(MethodContext context, IClass from, IClass to){
        Instructions instructions = context.getInstructions();
        if(from.isPrimitive() && to.isPrimitive()){
            instructions.cast(from.getType(), to.getType());
        }else if(from.isPrimitive()){
            instructions.box(from.getType());
        }else if(IClassUtils.isPrimitiveWrapAClass(from) && to.isPrimitive()){
            Type primType = Instructions.getUnBoxedType(from.getType());
            instructions.unbox(from.getType());
            instructions.cast(primType, to.getType());
        }
    }

    @Override
    protected void initAdditionalProperties() {
    	IClass[] argumentClasses = new IClass[arguments.length];
        List<IClass> argumentClassList = new ArrayList<>();
        for (int i = 0; i < arguments.length; i++) {
            argumentClassList.add(arguments[i].getResultType());
        }
        argumentClassList.toArray(argumentClasses);
        KernelProgramBlock block = getParent();
    	AMethod currentMethod = block.getMethod();
        if(currentMethod.getMode() == MethodBuilder.MODE_MODIFY && name.endsWith(ASConstants.METHOD_PROXY_SUFFIX)){
        	methodMeta = (AMethodMeta) currentMethod.getMeta().clone();
            methodMeta.setName(name);
        }else{
            methodMeta = new MethodChooser(block.getMethod().getClassLoader(), block.getMethodDeclaringClass(), methodOwner, name, argumentClasses).chooseMethod();
            if(methodMeta == null){
                throw new ASMSupportException("No such method " + AMethodMeta.getMethodString(name, argumentClasses) + " in " + methodOwner);
            }
        }
        
        if(Modifiers.isVarargs(methodMeta.getModifiers())){
            
        	IClass[] foundMethodArgTypes = methodMeta.getParameterTypes();
        	
        	if(ArrayUtils.getLength(foundMethodArgTypes) != ArrayUtils.getLength(arguments) ||
        	   !arguments[ArrayUtils.getLength(arguments) - 1].getResultType().isArray()){
        		
        		int fixedArgsLen = methodMeta.getParameterTypes().length - 1;
                KernelParam[] fixedArgs = new KernelParam[fixedArgsLen];
                System.arraycopy(arguments, 0, fixedArgs, 0, fixedArgsLen);

                KernelArrayValue variableVerifyArgs;
                ArrayClass arrayClass = (ArrayClass) methodMeta.getParameterTypes()[methodMeta.getParameterTypes().length - 1];
                variableVerifyArgs = block.newarray(arrayClass,
                       ArrayUtils.subarray(arguments, fixedArgsLen , arguments.length));
                variableVerifyArgs.asArgument();
                
                arguments = ArrayUtils.add(fixedArgs, variableVerifyArgs);
        	}
        }

        for(IClass exception : methodMeta.getExceptions()){
    	    block.throwException(exception);
    	}
        
    }

    @Override
    protected void checkAsArgument() {
        for (KernelParam argu : arguments) {
            argu.asArgument();
        }
    }

    public boolean isSaveReference() {
        return saveReturn;
    }
    
    @Override
    public void asArgument() {
        getParent().removeChild(this);
    }

    @Override
    public void loadToStack(MethodContext context) {
        if(getReturnType().equals(Type.VOID_TYPE)){
            throw new ASMSupportException("cannot push the void return type to stack!");
        }
        
        boolean saveRef = isSaveReference();
        setSaveReference(true);
        this.execute(context);
        setSaveReference(saveRef);
    }

    protected void setSaveReference(boolean saveReturn) {
        this.saveReturn = saveReturn;
    }
    
    public Type getReturnType() {
        return getReturnClass() != null ? getReturnClass().getType() : Type.VOID_TYPE;
    }

    public final IClass getReturnClass() {
        if(name.equals(ASConstants.INIT)){
            return methodOwner;
        }else if(methodMeta != null){
            return methodMeta.getReturnClass();
        }else{
            return getType(void.class);
        }
    }
    
    protected IClass getActuallyOwner(){
        return methodMeta.getActuallyOwner();
    }
    
    /**
     * 
     * @return
     */
    public int getModifiers() {
        return this.methodMeta.getModifiers();
    }

    @Override
    public IClass getResultType() {
        return getReturnClass();
    }
    
}
