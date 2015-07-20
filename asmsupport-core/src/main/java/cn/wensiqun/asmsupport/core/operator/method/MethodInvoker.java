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

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.asm.InstructionHelper;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.operator.AbstractParamOperator;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.array.KernelArrayValue;
import cn.wensiqun.asmsupport.core.utils.jls15_12_2.MethodChooser;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.ArrayClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;
import cn.wensiqun.asmsupport.utils.AsmsupportConstant;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;

public abstract class MethodInvoker extends AbstractParamOperator {

    private static final Log LOG = LogFactory.getLog(MethodInvoker.class);

    protected String name;
    protected KernelParam[] arguments;
    protected IClass methodOwner;
    
    /** whether or not save the return value to stack, must be save a 
     * result to stack when call constructor*/
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
    protected MethodInvoker(KernelProgramBlock block, IClass owner, String name, KernelParam[] arguments) {
        super(block, Operator.COMMON);
        this.methodOwner = owner;
        this.name = name;
        this.arguments = arguments;
    }

    protected void argumentsToStack() {
    	for(int i=0; i<arguments.length; i++){
            KernelParam argu = arguments[i];
            if(LOG.isPrintEnabled()) {
                LOG.print("push argument to stack");
            }
            if(argu instanceof IVariable){
                ((IVariable) argu).availableFor(this);
            }
            argu.loadToStack(block);
            cast(argu.getResultType(), mtdEntity.getParameterTypes()[i]);
        }
    }
    
    private void cast(IClass from, IClass to){
        if(from.isPrimitive() && to.isPrimitive()){
            insnHelper.cast(from.getType(), to.getType());
        }else if(from.isPrimitive()){
            insnHelper.box(from.getType());
        }else if(IClassUtils.isPrimitiveWrapAClass(from) && to.isPrimitive()){
            Type primType = InstructionHelper.getUnBoxedType(from.getType());
            insnHelper.unbox(from.getType());
            insnHelper.cast(primType, to.getType());
        }
    }

    @Override
    protected void initAdditionalProperties() {
    	IClass[] argumentClasses = new IClass[arguments.length];
        List<IClass> argumentClassList = new ArrayList<IClass>();
        for (int i = 0; i < arguments.length; i++) {
            argumentClassList.add(arguments[i].getResultType());
        }
        argumentClassList.toArray(argumentClasses);
        
    	AMethod currentMethod = block.getMethod();
        if(currentMethod.getMode() == AsmsupportConstant.METHOD_CREATE_MODE_MODIFY && name.endsWith(AsmsupportConstant.METHOD_PROXY_SUFFIX)){
        	mtdEntity = (AMethodMeta) currentMethod.getMeta().clone();
            mtdEntity.setName(name);
        }else{
            mtdEntity = new MethodChooser(block.getMethod().getClassLoader(), block.getMethodDeclaringClass(), methodOwner, name, argumentClasses).chooseMethod();
            if(mtdEntity == null){
                throw new ASMSupportException("No such method " + AMethodMeta.getMethodString(name, argumentClasses) + " in " + methodOwner);
            }
        }
        
        if(ModifierUtils.isVarargs(mtdEntity.getModifiers())){
            
        	IClass[] foundMethodArgTypes = mtdEntity.getParameterTypes();
        	
        	if(ArrayUtils.getLength(foundMethodArgTypes) != ArrayUtils.getLength(arguments) ||
        	   !arguments[ArrayUtils.getLength(arguments) - 1].getResultType().isArray()){
        		
        		int fixedArgsLen = mtdEntity.getParameterTypes().length - 1;
                KernelParam[] fixedArgs = new KernelParam[fixedArgsLen];
                System.arraycopy(arguments, 0, fixedArgs, 0, fixedArgsLen);

                KernelArrayValue variableVarifyArauments;
                ArrayClass arrayClass = (ArrayClass)mtdEntity.getParameterTypes()[mtdEntity.getParameterTypes().length - 1];
                variableVarifyArauments = block.newarray(arrayClass, 
                       (KernelParam[]) ArrayUtils.subarray(arguments, fixedArgsLen , arguments.length));
                variableVarifyArauments.asArgument();
                
                arguments = (KernelParam[]) ArrayUtils.add(fixedArgs, variableVarifyArauments);
        	}
        }

        for(IClass exception : mtdEntity.getExceptions()){
    	    block.addException(exception);
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
        block.removeExe(this);
    }

    @Override
    public void loadToStack(KernelProgramBlock block) {
        if(getReturnType().equals(Type.VOID_TYPE)){
            throw new ASMSupportException("cannot push the void return type to stack!");
        }
        
        boolean saveRef = isSaveReference();
        setSaveReference(true);
        this.execute();
        setSaveReference(saveRef);
    }

    protected void setSaveReference(boolean saveReturn) {
        this.saveReturn = saveReturn;
    }
    
    public Type getReturnType() {
        return getReturnClass() != null ? getReturnClass().getType() : Type.VOID_TYPE;
    }

    public final IClass getReturnClass() {
        if(name.equals(AsmsupportConstant.INIT)){
            return methodOwner;
        }else if(mtdEntity != null){
            return mtdEntity.getReturnClass();
        }else{
            return block.getClassHolder().getType(void.class);
        }
    }
    
    protected IClass getActuallyOwner(){
        return mtdEntity.getActuallyOwner();
    }
    
    /**
     * 
     * @return
     */
    public int getModifiers() {
        return this.mtdEntity.getModifiers();
    }

    @Override
    public IClass getResultType() {
        return getReturnClass();
    }
    
}
