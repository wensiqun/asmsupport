package example.operators;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.operators.method.MethodInvoker;
import example.AbstractExample;

/**
 * 这个例子我们将实现方法调用的操作，方法调用主要包括了
 * 1.调用非静态方法
 * 2.调用静态方法
 * 3.调用父类方法
 * 2.调用构造方法
 *
 * 执行这个类的main方法将会生成如下类，并且调用其main方法。
 * 
 * public class MyObject {
 *
 *     @Override
 *     public String toString() {
 *         return "description is \"" + super.toString() + "\"";
 *     }
 *
 *     public String description(){
 *         return toString();
 *     }
 *     
 *     public static String getDescription(MyObject obj){
 *         return obj.description();
 *     }
 *     
 *     public static void main(String[] args){
 *         MyObject obj = new MyObject();
 *         System.out.println("Call static method : " + MyObject.getDescription(obj));
 *     }
 * }
 */
public class MethodInvokeOperatorGenerate extends AbstractExample {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.MethodInvokeOperatorGenerateExample", null, null);
        
        /**
         * 实现如下方法
         * @Override
         * public String toString() {
         *     return "description is \"" + super.toString() + "\"";
         * }
         * 这里我们将看到如何生产调用父类方法
         */
        creator.createMethod("toString", null, null, AClass.STRING_ACLASS, null, Opcodes.ACC_PUBLIC, new CommonMethodBody(){

            @Override
            public void body(LocalVariable... argus) {
                //通常我们将super看作是一个变量所以我们用invoke(Parameterized caller, String methodName, Parameterized... arguments)
                //方法实现super.xxxx。通过getSuper方法获取super变量
                MethodInvoker superToString = invoke(getSuper(), "toString");
                runReturn(append(Value.value("description is \""), superToString, Value.value("\"")));
            }
            
        });
        
        /**
         * 实现如下方法
         * public String description(){
         *    return toString();
         * }
         * 
         */
        creator.createMethod("description", null, null, AClass.STRING_ACLASS, null, Opcodes.ACC_PUBLIC, new CommonMethodBody(){

            @Override
            public void body(LocalVariable... argus) {
                /**
                 * 这里和调用super.xxx是一样的。调用当前类中的非静态方法都是通过
                 * invoke(Parameterized caller, String methodName, Parameterized... arguments)方法调用。
                 * 通过getThis方法获取this变量
                 */
                runReturn(invoke(getThis(), "toString"));                
            }
            
        });
        
        /**
         * 生成如下方法的字节码：
         * public static String getDescription(MyObject obj){
         *     return obj.description();
         * }
         */
        creator.createStaticMethod("getDescription", new AClass[]{creator.getCurrentClass()}, new String[]{"obj"}, AClass.STRING_ACLASS, null,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

            @Override
            public void body(LocalVariable... argus) {
                /**
                 * 和上面。
                 * 这里argus[0]就是我们定义的参数obj。如果需要传递参数可以直接在
                 * 方法调用的时候添加需要传递的参数，因为这是个变元方法
                 */
                runReturn(invoke(argus[0], "description"));
            }
        });
        
        /**
         * public static void main(String[] args){
         *     MyObject obj = new MyObject();
         *     System.out.println("Call static method : " + MyObject.getDescription(obj));
         * }
         */
        creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

            @Override
            public void body(LocalVariable... argus) {
                /**
                 * 首先调用构造方法生成MyObject obj = new MyObject();
                 * 通过invokeConstructor方法实现调用构造方法
                 * 这个方法后两个参数。
                 * 1.表示需要构造的类，这里我们通过调用getMethodOwner获取当前操作类
                 * 2.表示构造方法的参数。这个参数是个变元参数
                 */
            	LocalVariable obj = createVariable("obj", getMethodOwner(), false, invokeConstructor(getMethodOwner()));
                
            	/**
            	 * 实现System.out.println("Call static method : " + MyObject.getDescription(obj));
            	 * 这里将学习到如何生成调用静态方法
            	 * 
            	 * 调用静态方法是通过invokeStatic(AClass owner, String methodName, Parameterized... arguments)
            	 * 实现的。
            	 * 这个方法有三个参数
            	 * 1.通过那个Class调用静态方法。
            	 * 2.静态方法的名称
            	 * 3.参数
            	 */
            	MethodInvoker getDescriptionInvoker = invokeStatic(getMethodOwner(), "getDescription", obj);
            	
            	invoke(systemOut, "println", append(Value.value("Call static method : "), getDescriptionInvoker));
                
            	runReturn();
            }
        });
        generate(creator);
    }

}
