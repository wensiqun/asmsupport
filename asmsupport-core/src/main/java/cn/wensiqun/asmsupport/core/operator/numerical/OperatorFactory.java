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
package cn.wensiqun.asmsupport.core.operator.numerical;

import cn.wensiqun.asmsupport.core.ByteCodeExecutor;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.block.control.exception.ExceptionSerialBlock;
import cn.wensiqun.asmsupport.core.exception.UnreachableCodeException;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.operator.UnreachableCodeCheckSkipable;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class OperatorFactory {

    /**
     * Create {@link AbstractOperator} by reflect
     * 
     * @param clazz operator type
     * @param parameterTypes the argument type list
     * @param arguments the argument value
     * @return {@link T} the operator the type is the first argument
     * @see {@link #newOperator(Class, boolean, Class[], Object...)}
     */
    public static <T extends AbstractOperator> T newOperator(Class<T> clazz, Class<?>[] parameterTypes,
            Object... arguments) {
        return newOperator(clazz, true, parameterTypes, arguments);
    }

    /**
     * Create {@link AbstractOperator} by reflect
     * 
     * @param clazz operator type
     * @param checkSerial whether or not to call method {@link ExceptionSerialBlock#prepare()}
     * @param parameterTypes the argument type list
     * @param arguments the argument value
     * @return {@link T} the operator the type is the first argument
     */
    public static <T extends AbstractOperator> T newOperator(Class<T> clazz, boolean checkSerial,
            Class<?>[] parameterTypes, Object... arguments) {
        if (parameterTypes != null && arguments != null) {
            if (!ArrayUtils.isSameLength(parameterTypes, arguments)) {
                throw new ASMSupportException();
            }
        } else if (ArrayUtils.isEmpty(parameterTypes) || ArrayUtils.isEmpty(arguments)) {
            throw new NullPointerException();
        } else if (!KernelProgramBlock.class.equals(parameterTypes[0])) {
            throw new ASMSupportException("first argument type must be ProgramBlock");
        }

        KernelProgramBlock block = (KernelProgramBlock) arguments[0];
        ByteCodeExecutor last = block.getQueue().getLast();
        if (checkSerial && last != null && last instanceof ExceptionSerialBlock) {
            last.prepare();
        }

        try {

            Constructor<T> constructor = parameterTypes == null ? clazz.getDeclaredConstructor() : clazz
                    .getDeclaredConstructor(parameterTypes);
            boolean accessible = constructor.isAccessible();
            constructor.setAccessible(true);
            T instance = parameterTypes == null ? constructor.newInstance() : constructor.newInstance(arguments);
            constructor.setAccessible(accessible);

            if (!(instance instanceof UnreachableCodeCheckSkipable)) {
                if (block.isFinish()) {
                    throw new UnreachableCodeException("Unreachable code to " + instance, block, instance);
                }
            }

            Method checkAsArgument = AbstractOperator.class.getDeclaredMethod("checkAsArgument");
            accessible = checkAsArgument.isAccessible();
            checkAsArgument.setAccessible(true);
            checkAsArgument.invoke(instance);
            checkAsArgument.setAccessible(accessible);

            instance.prepare();

            return instance;
        } catch (RuntimeException e) {
            throw e;
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof ASMSupportException) {
                throw (ASMSupportException) e.getTargetException();
            } else if (e.getTargetException() instanceof RuntimeException) {
                throw (RuntimeException) e.getTargetException();
            } else {
                throw new ASMSupportException(e);
            }
        } catch (Exception e) {
            throw new ASMSupportException(e);
        }
    }

}
