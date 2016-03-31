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
/**
 *
 */
package cn.wensiqun.asmsupport.core.block.method.init;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.method.AbstractKernelMethodBody;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.UnreachableCodeCheckSkipable;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.operator.method.SuperConstructorInvoker;
import cn.wensiqun.asmsupport.core.operator.numerical.OperatorFactory;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.standard.block.method.IConstructorBody;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.MutableClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 *
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class KernelConstructorBody extends AbstractKernelMethodBody implements IConstructorBody<KernelParam, LocalVariable> {

    private boolean calledSuper = false;

    @Override
    public final void generateBody() {
        body(argments);
        if(!calledSuper) {
            calledSuper = true;
            MethodInvoker superCall = supercall(UnreachableCodeCheckSkipableSuperConstructorInvoker.class);
            getExecutor().getQueue().remove(superCall);
            getExecutor().getQueue().setHead(superCall);
        }
    }

    public MethodInvoker supercall(KernelParam... arguments) {
        return supercall(SuperConstructorInvoker.class, arguments);
    }

    private MethodInvoker supercall(Class<? extends MethodInvoker> superCallClass, KernelParam... arguments) {
        MutableClass owner = getMethodDeclaringClass();
        if(ModifierUtils.isEnum(getMethodDeclaringClass().getModifiers())){
            throw new ASMSupportException("Cannot invoke super constructor from enum type " + owner);
        }
        invokeVerify(owner);
        calledSuper = true;
        return OperatorFactory.newOperator(superCallClass,
                new Class<?>[]{KernelProgramBlock.class, IClass.class, KernelParam[].class},
                getExecutor(), owner, arguments);
    }

    private static class UnreachableCodeCheckSkipableSuperConstructorInvoker extends SuperConstructorInvoker implements UnreachableCodeCheckSkipable {

        protected UnreachableCodeCheckSkipableSuperConstructorInvoker(
                KernelProgramBlock block, IClass aclass, KernelParam[] arguments) {
            super(block, aclass, arguments);
        }

    }

}
