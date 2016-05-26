package cn.wensiqun.asmsupport.standard.action;

import cn.wensiqun.asmsupport.standard.def.IParam;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

/**
 * Created by sqwen on 2016/5/26.
 */
public interface GenericAction<_P extends IParam> {

    /**
     * check cast object type, such as following code:
     * <p style="border:1px solid;width:300px;padding:10px;">
     * String cc1 = <b style="color:#FF3300">(String)cc;</b>
     * </p>
     *
     * Following code is the asmsupport code.
     * <p style="border:1px solid;width:300px;padding:10px;">
     * checkcast(cc_para, AClassFactory.defType(String.class));
     * </p>
     *
     *
     * @param obj
     *            original object
     * @param to
     *            the type need to check cast.
     * @return {@link _P}
     */
    _P checkcast(_P obj, IClass to);

    /**
     * check cast object type, such as following code:
     * <p style="border:1px solid;width:300px;padding:10px;">
     * String cc1 = <b style="color:#FF3300">(String)cc;</b>
     * </p>
     *
     * Following code is the asmsupport code.
     * <p style="border:1px solid;width:300px;padding:10px;">
     * checkcast(cc_para, AClassFactory.defType(String.class));
     * </p>
     *
     *
     * @param cc
     *            original object
     * @param to
     *            the type need to check cast.
     * @return
     */
    _P checkcast(_P cc, Class<?> to);

    /**
     * the negative operator. following is example.
     * <p style="border:1px solid;width:300px;padding:10px;">
     * int negValue = <b style="color:#FF3300">-factor;</b>
     * </p>
     *
     * Following code is the asmsupport code.
     * <p style="border:1px solid;width:300px;padding:10px;">
     * neg(factor);
     * </p>
     *
     *
     * @param factor
     *            must a number.
     * @return {@link _P}
     */
    _P neg(_P factor);

    /**
     * The ternary operator in java.
     * <p style="border:1px solid;width:500px;padding:10px;">
     * String result = <b style="color:#FF3300">booleanValue ? "YES" : "NO"
     * ;</b>
     * </p>
     *
     *
     * Following code is the asmsupport code.
     * <p style="border:1px solid;width:500px;padding:10px;">
     * ternary(booleanValueParameterized, Value.value("YES"),
     * Value.value("NO"));
     * </p>
     *
     *
     * @param exp1
     *            the expression result must be a boolean.
     * @param exp2
     * @param exp3
     * @return {@link _P}
     */
    _P ternary(_P exp1, _P exp2, _P exp3);

    /**
     *
     * <p>
     * The string append operator. such as "A" + "B" + "C". if we want append
     * some string, can't use
     * {@link ArithmeticAction#add(_P, _P)} method, that
     * is different to write java code directly.
     * </p>
     *
     * Following is the java sample.
     * <p style="border:1px solid;width:500px;padding:10px;">
     * String sayHello = <b style="color:#FF3300">"hello " + "world";</b>
     * </p>
     *
     *
     * Following code is the asmsupport code.
     * <p style="border:1px solid;width:500px;padding:10px;">
     * stradd(Value.value("hello "), Value.value("world"));
     * </p>
     *
     * @param par1
     * @param pars
     * @return
     */
    _P stradd(_P par1, _P... pars);

    /**
     * Generate the instanceof instruction.
     *
     * <p style="border:1px solid;width:500px;padding:10px;">
     * boolean isString = <b style="color:#FF3300">object instanceof String;</b>
     * </p>
     *
     * Following code is the asmsupport code.
     * <p style="border:1px solid;width:500px;padding:10px;">
     * instanceOf(object, AClassFactory.defType(String.class));
     * </p>
     *
     *
     * @param obj
     * @param type
     * @return {@link _P}a boolean type Parameterized
     */
    _P instanceof_(_P obj, IClass type);

    /**
     * Generate the instanceof instruction, the method is same as
     * {@link #instanceof_(_P, IClass)}
     *
     * @see #instanceof_(_P, IClass)
     * @param obj
     * @param type
     * @return
     */
    _P instanceof_(_P obj, Class<?> type);

    /**
     * Corresponding to break statement in loop.
     *
     */
    public void break_();

    /**
     * Corresponding to continue statement in loop.
     *
     */
    void continue_();

    /**
     * Throw an exception.
     *
     * <p style="border:1px solid;width:500px;padding:10px;">
     * <b style="color:#FF3300">throw new RuntimeException()</b>
     * </p>
     *
     * Following code is the asmsupport code.
     * <p style="border:1px solid;width:500px;padding:10px;">
     * throwException(invokeConstructor(RuntimeException_ACLASS));
     * </p>
     *
     *
     * @param exception
     */
    void throw_(_P exception);

    /**
     * Corresponding to return statement with no return value.
     *
     */
    void return_();

    /**
     * Corresponding to return statement with return value.
     *
     * @param param
     *            return value.
     *
     */
    void return_(_P param);

}
