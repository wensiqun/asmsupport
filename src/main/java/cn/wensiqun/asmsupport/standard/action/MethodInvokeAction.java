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
package cn.wensiqun.asmsupport.standard.action;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;


/**
 * 
 * 生成方法调用操作
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface MethodInvokeAction {
    
    /**
     * <p>
     * 生成方法调用操作
     * <ul>
     * <li>objRef : 方法所属的对象</li>
     * <li>name : 方法名</li>
     * <li>arguments : 方法参数列表</li>
     * </ul>
     * </p>
     * <p>对应于红色部分代码:</p>
     * 
     * 
     * <p style="border:1px solid;width:550px;padding:10px;">
     * <b style="color:#FF3300">String.class.toString();</b><br/>
     * <b style="color:#FF3300">object.test("hello");</b><br/>
     * <b style="color:#FF3300">object.getOther().test("hello");</b><br/>
     * </p>
     * 
     * 上面红色部分对应的asmsupport代码
     * <pre style="border:1px solid;width:550px;padding:10px;">
     * invoke(AClass.STRING_ACLASS, "toString");
     * invoke(objectParameterized, "test" Value.value("hello"));
     * //objectParameterized是上面object在ASMSupport中的表示
     * invoke(invoke(objectParameterized, "getOther"), "test" Value.value("hello"));
     * </pre>
     * 
     * <p>
     * 这个方法同样也可以调用静态方法，如果传入的方法名在对象中是一个静态方法，那么ASMSuppport底层就自动生成调用今天方法的指令。当然我们也可以直接通过
     * 调用{@link #call(AClass, String, Parameterized...)}方法生成静态方法调用指令。这一点其实和我们编写java代码是
     * 一样的，当我们调用某一个变量的方法时候，如果这个方法是静态方法，我们可以采用"变量名.方法名()"和"类名.方法名()"这两种方式。
     * </p>
     * 
     * @param objRef
     * @param methodName
     * @param arguments
     * @return {@link MethodInvoker}
     */
    public MethodInvoker call(Parameterized objRef, String methodName, Parameterized... arguments);

    /**
     * Generate method invoke, this method equivalence to 
     * <pre>
     * _invoke(_this(), methodName, arguments);
     * </pre>
     * 
     * @param methodName
     * @param args
     * @return
     */
    public MethodInvoker call(String methodName, Parameterized... args);
    
    /**
     * 
     * <p>
     * 调用静态方法， 对应下面红色部分的代码
     * </p>
     * 
     * <p style="border:1px solid;width:550px;padding:10px;">
     * <b style="color:#FF3300">Thread.getAllStackTraces();</b><br/>
     * <b style="color:#FF3300">Thread.sleep(1000)</b><br/>
     * </p>
     * 
     * 上面红色部分对应的asmsupport代码
     * <pre style="border:1px solid;width:550px;padding:10px;">
     * invokeStatic(ThreadAClass, "getAllStackTraces");
     * invokeStatic(ThreadAClass, "sleep", Value.value(1000));
     * </pre>
     * 
     * 
     * @param owner
     * @param methodName
     * @param arguments
     * @return {@link MethodInvoker}
     */
    public MethodInvoker call(AClass owner, String methodName, Parameterized... arguments);
    
    
    /**
     * 
     * <p>
     * 调用构造方法， 对应下面红色部分的代码
     * </p>
     * 
     * 
     * <p style="border:1px solid;width:550px;padding:10px;">
     * String str1 = <b style="color:#FF3300">new String()</b>;<br/>
     * String str2 = <b style="color:#FF3300">new String("hello world")</b>;<br/>
     * </p>
     * 
     * 上面红色部分对应的asmsupport代码
     * <pre style="border:1px solid;width:550px;padding:10px;">
     * invokeConstructor(AClass.STRING_ACLASS);
     * invokeConstructor(AClass.STRING_ACLASS, Value.value("hello world"));
     * </pre>
     * 
     * 
     * @param owner
     * @param arguments
     * @return {@link MethodInvoker}
     */
    public MethodInvoker new_(AClass owner, Parameterized... arguments);
    
    /**
     * @param owner
     * @param arguments
     * @return
     * @see #new_(AClass, Parameterized...)
     */
    public MethodInvoker new_(Class<?> owner, Parameterized... arguments);
    
    
    /**
     * 这个是用在修改class的时候，当我们修改某个方法的时候，ASMSupport会自动为这个方法生成一个代理方法，
     * 我们只需要调用这个各方，就可以在代理方法中调用被修改的方法。比如存在方法：
     * 
     * <pre>
     * public String test(){
     *     return "hello world".
     * }
     * <pre>
     * 
     * 那么我们希望在返回"hello world"之后打印出所花费的时间,经过asmsupport修改之后就会出现下面方法内容
     * <pre>
     * 
     * public String test@original(){
     *     return "hello world".
     * }
     * 
     * public String test(){
     *     long start = System.currentTimeMillis();
     *     String value = test@original();
     *     System.out.println(System.currentTimeMillis() - start);
     *     return value;
     * }
     * </pre>
     * 
     * 在上面的代码中"test@original()"这个方法调用指令就是asmsupport调用了invokeOriginalMethod方法生成的。
     * @return {@link MethodInvoker}
     */
    public MethodInvoker callOrig();
    
}
