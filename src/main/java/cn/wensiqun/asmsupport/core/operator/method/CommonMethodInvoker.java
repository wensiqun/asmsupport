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

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.definition.variable.meta.VariableMeta;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

public class CommonMethodInvoker extends MethodInvoker {
	
	private static final Log LOG = LogFactory.getLog(CommonMethodInvoker.class);
    
	private Parameterized callObjReference;
	
	protected CommonMethodInvoker(ProgramBlockInternal block, Parameterized objRef, String name, Parameterized[] arguments) {
		super(block, objRef.getParamterizedType(), name, arguments);
		this.callObjReference = objRef;
		if(callObjReference.getParamterizedType().isPrimitive()){
			throw new IllegalArgumentException("cannot invoke method at primitive type \"" + callObjReference.getParamterizedType() +  "\" : must be a non-primitive variable");
		}
        //默认不保存引用
        setSaveReference(false);
        if(callObjReference instanceof MethodInvoker){
        	//set the method caller to save reference;
            ((MethodInvoker)callObjReference).setSaveReference(true);    
        }
	}

    @Override
    protected void checkAsArgument() {
    	callObjReference.asArgument();
        super.checkAsArgument();
    }

	@Override
    public void endingPrepare() {
        //如果是静态方法那么则创建一个静态方法调用者到执行队列
        if(Modifier.isStatic(getModifiers())){
            //移除当前的方法调用
            MethodInvoker mi = new StaticMethodInvoker(block, getActuallyOwner(), name, arguments);
            block.removeExe(mi);
            block.getQueue().replace(this, mi);
        }
    }

	@Override
	protected void doExecute() {
        
        /* if method is non satic*/
        if(!Modifier.isStatic(getModifiers())){
            LOG.print("put reference to stack");
            //变量入栈
            callObjReference.loadToStack(block);
            argumentsToStack();
            if(callObjReference.getParamterizedType().isInterface()){
            	LOG.print("invoke interface method : " + name);
                //如果是接口
                insnHelper.invokeInterface(callObjReference.getParamterizedType().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
            }else{
                LOG.print("invoke class method : " + name);
                if(callObjReference instanceof IVariable){
                	 VariableMeta ve = ((IVariable)callObjReference).getVariableMeta();
                	 if(ve.getName().equals(ASConstant.SUPER)){
                         insnHelper.invokeSuperMethod(callObjReference.getParamterizedType().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
                     }else {
                         insnHelper.invokeVirtual(callObjReference.getParamterizedType().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
                     }
                }else{
                	insnHelper.invokeVirtual(callObjReference.getParamterizedType().getType(), this.name, getReturnType(), mtdEntity.getArgTypes());
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
