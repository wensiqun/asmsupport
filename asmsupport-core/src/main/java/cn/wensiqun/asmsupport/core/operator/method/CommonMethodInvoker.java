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

import java.lang.reflect.Modifier;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.var.meta.VarMeta;

/**
 * Represent a method call.
 *
 */
public class CommonMethodInvoker extends MethodInvoker {
	
	private static final Log LOG = LogFactory.getLog(CommonMethodInvoker.class);
    
	private KernelParam callObjReference;
	
	protected CommonMethodInvoker(KernelProgramBlock block, KernelParam objRef, String name, KernelParam[] arguments) {
		super(block, objRef.getResultType(), name, arguments);
		this.callObjReference = objRef;
		if(callObjReference.getResultType().isPrimitive()){
			throw new IllegalArgumentException("Cannot invoke method at primitive type \"" + callObjReference.getResultType() +  "\" : must be a non-primitive variable");
		}
        if(callObjReference instanceof MethodInvoker){
        	//set the method caller to save reference;
            ((MethodInvoker)callObjReference).setSaveReference(true);    
        } else {
            //default to don't save return result reference of this method.
            setSaveReference(false);
        }
	}

    @Override
    protected void checkAsArgument() {
    	callObjReference.asArgument();
        super.checkAsArgument();
    }

	@Override
    public void endingPrepare() {
		//if current method call is a static method call, than 
		//change CommonMethodInvoker to StaticMethodInvoker
        if(Modifier.isStatic(getModifiers())){
        	//constructor a StaticMethodInvoker
            MethodInvoker mi = new StaticMethodInvoker(block, getActuallyOwner(), name, arguments);
            //Remove the StaticMethodInvoker from execute queue cause 
            //by add to queue when construct StaticMethodInvoker default.
            block.removeExe(mi);
            //replace CommonMethodInvoker to StaticMethodInvoker
            block.getQueue().replace(this, mi);
        }
    }

	@Override
	protected void doExecute() {
        
        /* if method is non satic*/
        if(!Modifier.isStatic(getModifiers())){
            LOG.print("put reference to stack");
            callObjReference.loadToStack(block);
            argumentsToStack();
            if(callObjReference.getResultType().isInterface()){
            	LOG.print("invoke interface method : " + name);
                insnHelper.invokeInterface(callObjReference.getResultType().getType(), this.name, getReturnType(), mtdEntity.getArgTypes(), true);
            }else{
                LOG.print("invoke class method : " + name);
                if(callObjReference instanceof IVariable){
                	 VarMeta ve = ((IVariable)callObjReference).getMeta();
                	 if(ve.getName().equals(ASConstant.SUPER)){
                         insnHelper.invokeSuperMethod(callObjReference.getResultType().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
                     }else {
                         insnHelper.invokeVirtual(callObjReference.getResultType().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
                     }
                }else{
                	insnHelper.invokeVirtual(callObjReference.getResultType().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
                }
            }
            if(!isSaveReference()){
                if(!getReturnType().equals(Type.VOID_TYPE)){
                    insnHelper.pop();
                }
            }
        }
	}
	
    @Override
    public String toString() {
        return callObjReference + "." + mtdEntity;
    }
}
