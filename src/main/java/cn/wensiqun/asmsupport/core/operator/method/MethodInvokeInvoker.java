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

import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.log.Log;
import cn.wensiqun.asmsupport.core.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 * @see CommonMethodInvoker
 */
@Deprecated
public class MethodInvokeInvoker extends MethodInvoker {

    private static final Log LOG = LogFactory.getLog(MethodInvokeInvoker.class);
    
    private MethodInvoker caller;
    
    @Deprecated
    protected MethodInvokeInvoker(ProgramBlockInternal block, MethodInvoker caller, String name, InternalParameterized[] arguments) {
        super(block, caller.getReturnClass(), name, arguments);
        this.caller = caller;
        //this.methodType = MethodType.METHOD;
        //默认不保存引用
        setSaveReference(false);
        //set the method caller to save reference;
        caller.setSaveReference(true);    
    }
    
    
    
    @Override
    protected void initAdditionalProperties() {
        methodOwner = caller.getReturnClass();
        super.initAdditionalProperties();
    }

    @Override
    protected void verifyArgument() {
        if(caller.getReturnClass().isPrimitive()){
            throw new IllegalArgumentException("Variable must be a non-primitive variable");
        }
        super.verifyArgument();
    }

    @Override
    protected void checkAsArgument() {
        caller.asArgument();
        super.checkAsArgument();
    }

    @Override
    public void endingPrepare() {
        if(Modifier.isStatic(getModifiers())){
            block.removeExe(caller);
            MethodInvoker mi = new StaticMethodInvoker(block, getActuallyOwner(), name, arguments);
            block.removeExe(mi);
            block.getQueue().replace(this, mi);
        }
    }

    @Override
    public void doExecute() {
        caller.loadToStack(block);
        if(!Modifier.isStatic(getModifiers())){
            LOG.print("call method by method return value");
            argumentsToStack();
            if(caller.getReturnClass().isInterface()){
                LOG.print("invoke interface method : " + name);
                //如果是接口
                insnHelper.invokeInterface(this.caller.getReturnType(), this.name, getReturnType(), mtdEntity.getArgTypes());
            }else{
                LOG.print("invoke class method : " + name);
                insnHelper.invokeVirtual(this.caller.getReturnType(), this.name, getReturnType(), mtdEntity.getArgTypes());
            }
        }
        
        if(!isSaveReference()){
            if(!getReturnType().equals(Type.VOID_TYPE)){
                insnHelper.pop();
            }
        }
    }

    MethodInvoker getCaller() {
        return caller;
    }

    @Override
    public String toString() {
        return caller + "." + mtdEntity;
    }

}
