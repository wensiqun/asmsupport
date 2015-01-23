package cn.wensiqun.asmsupport.core.block.interfaces;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.operator.Return;
import cn.wensiqun.asmsupport.core.operator.checkcast.CheckCast;
import cn.wensiqun.asmsupport.core.operator.numerical.posinegative.Negative;
import cn.wensiqun.asmsupport.core.operator.numerical.ternary.TernaryOperator;


/**
 * 
 * 所有操作的汇总
 * 
 * @author wensiqun(at)gamil
 *
 */
public interface IBlock extends 
KeywordVariableable, 
VariableOperator, 
MethodInvokeOperator, 
ArrayOperator, 
ArithmeticOperator, 
Bitwise, 
CrementOperator,
RelationalOperator,
LogicalOperator,
CreateBlockOperator 
{
    
    /**
     * 转换类，将对象cc转换成目标类型to,对应下面的红色java代码
     * <p style="border:1px solid;width:300px;padding:10px;">
     * String cc1 = <b style="color:#FF3300">(String)cc;</b>
     * </p>
     * 
     * 红色部分对应的asmsupport代码：
     * <p style="border:1px solid;width:300px;padding:10px;">
     * checkCast(cc_para, AClass.STRING_ACLASS);
     * </p>
     * 
     * 
     * @param cc 需要被强制转换的对象
     * @param to 需要被转换成的类型
     * @return {@link CheckCast}
     */
    public CheckCast _checkcast(Parameterized cc, AClass to);
    
    /**
     * 取负操作,对应下面的红色java代码
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int negValue = <b style="color:#FF3300">-factor;</b>
     * </p>
     * 
     * 红色部分对应的asmsupport代码：
     * <p style="border:1px solid;width:300px;padding:10px;">
     * neg(factor);
     * </p>
     * 
     * 
     * @param factor 这个参数必须是一个数字类型的参数
     * @return {@link Negative}
     */
    public Negative _neg(Parameterized factor);
    
    
    /**
     * java中的三元操作，传入三个参数从左只至右依次代码三元操作的三个元，比如下面红色部分的java代码。
     * <p style="border:1px solid;width:500px;padding:10px;">
     * String result = <b style="color:#FF3300">booleanValue ? "YES" : "NO" ;</b>
     * </p>
     * 
     * 
     * 红色部分对应的asmsupport代码：
     * <p style="border:1px solid;width:500px;padding:10px;">
     * ternary(booleanValueParameterized, Value.value("YES"), Value.value("NO"));
     * </p>
     * 
     * 
     * @param exp1 这个参数必须是一个boolean或者Boolean类型的
     * @param exp2
     * @param exp3
     * @return {@link TernaryOperator}
     */
    public TernaryOperator _ternary(Parameterized exp1, Parameterized exp2, Parameterized exp3);

    /**
     * 字符串拼接操作，我们知道我们在java中可以随意是用"+"符合对字符串进行拼接，那么在ASMSupport同样也支持这样的操作。比如下面红色代码
     * 
     * <p style="border:1px solid;width:500px;padding:10px;">
     * String sayHello = <b style="color:#FF3300">"hello " + "world";</b>
     * </p>
     * 
     * 红色部分对应的asmsupport代码：
     * <p style="border:1px solid;width:500px;padding:10px;">
     * append(Value.value("hello "), Value.value("world"));
     * </p>
     * 
     * 这里的两个参数可以是任何非null值的参数，如果发现传入的参数并非String类型，都会自动调用参数的toString方法，
     * 使用这个方法的返回值完成字符串拼接操作。
     * 
     * @param par1
     * @param pars
     * @return 
     */
    public Parameterized _append(Parameterized par1, Parameterized... pars);
    
    /**
     * java代码中instanceof关键字操作。比如下面红色代码
     * 
     * <p style="border:1px solid;width:500px;padding:10px;">
     * boolean isString = <b style="color:#FF3300">object instanceof String;</b>
     * </p>
     * 
     * 
     * 红色部分对应的asmsupport代码：
     * <p style="border:1px solid;width:500px;padding:10px;">
     * instanceOf(object, AClass.STRING_AClass);
     * </p>
     * 
     * 
     * @param obj
     * @param type
     * @return {@link Parameterized}一个boolean类型的Parameterized
     */
    public Parameterized _instanceof(Parameterized obj, AClass type);

    
    /**
     * java中的break关键字的指令，如果当前正处在循环体内，那么调用这个方法，则是跳出循环，
     * 
     * 
     */
    public void _break();
    
    /**
     * java中的continue关键字的指令，如果当前正处在循环体内，那么调用这个方法，则是当前循环进入下一次循环，
     */
    public void _continue();
    
    /**
     * 抛出异常，传入的参数为异常对象的Parameterized, 比如下面红色代码
     * 
     * <p style="border:1px solid;width:500px;padding:10px;">
     * <b style="color:#FF3300">throw new RuntimeException()</b>
     * </p>
     * 
     * 红色部分对应的asmsupport代码：
     * <p style="border:1px solid;width:500px;padding:10px;">
     * throwException(invokeConstructor(RuntimeException_ACLASS));
     * </p>
     * 
     * 
     * @param exception
     */
    public void _throw(Parameterized exception);

    
    /**
     * 如果当前方法没有返回值，则调用这个方法生成无返回值return关键字指令
     * 
     * @return {{@link Return}
     */
    public Return _return();
    
    /**
     * 如果当前方法有返回值，则调用这个方法生成有返回值return关键字指令
     * 
     * 
     * 抛出异常，传入的参数为异常对象的Parameterized, 比如下面红色代码
     * 
     * <p style="border:1px solid;width:500px;padding:10px;">
     * <b style="color:#FF3300">return "hello world";</b>
     * </p>
     * 
     * 红色部分对应的asmsupport代码：
     * <p style="border:1px solid;width:500px;padding:10px;">
     * runReturn(Value.value("hello world"));
     * </p>
     * 
     * @param parame 返回值
     * @return {{@link Return}
     * 
     */
    public Return _return(Parameterized parame);
}
