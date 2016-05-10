package cn.wensiqun.asmsupport.client.def.behavior;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;


/**
 * 
 * Define a behavior, the behavior is similar to XXXAction interface in standard modules.
 * There have a different between behavior and action, The Behavior support chain call,
 * and action support nested call. for example :
 * 
 * <p>
 * nested call :
 * <pre>
 * call(call(stringBuilder, "append", val("Hello")), "append", val("ASMSupport"));
 * </pre>
 * chain call :
 * <pre>
 * stringBuilder.call("append", val("Hello")).call("append", val("ASMSupport"));
 * </pre>
 * </p>
 * 
 * @author WSQ
 *
 */
public interface CommonBehavior {

	/**
	 * Generate expression : ==
	 * 
	 * @return {@link BoolBehavior}
	 */
    BoolBehavior eq(Param para);

    /**
	 * Generate expression : !=
	 * 
	 * @return {@link BoolBehavior}
	 */
    BoolBehavior ne(Param para);
    
    /**
     * Generate string add operator : +
     * 
	 * @return {@link ObjectBehavior}
     */
    ObjectBehavior stradd(Param param);
    
    /**
     * Assign the result of current behavior to specify variable
     * 
     * @param var the variable
     * @return {@link UncertainBehavior} 
     */
    CommonBehavior assignTo(Var var);
    
    /**
     * Use current behavior as an anonymous variable, and
     * use the result of current behavior as the variable
     * type. the method is equivalent of 
     * '<code>asVar(currentBehaviorResultType)</code>'
     * 
     * @return {@link LocVar}
     */
    LocVar asVar();
    
    /**
     * 
     * Use current behavior as an anonymous variable, and specify 
     * a type that's a sub type of result of current behavior
     * 
     * @param type
     * @return {@link LocVar}
     */
    LocVar asVar(IClass type);
    
    /**
     * 
     * Use current behavior as an anonymous variable, and specify 
     * a type that's a sub type of result of current behavior
     * 
     * @param type
     * @return {@link LocVar}
     */
    LocVar asVar(Class<?> type);

    /**
     * 
     * Use current behavior as an variable, and specify a variable
     * name, and use the result tyoe of current behavior as variable
     * type. the method is equivalent of '<code>asVar(varName, currentBehaviorResultType)</code>'
     * 
     * @param varName
     * @return {@link LocVar}
     */
    LocVar asVar(String varName);
    
    /**
     * Use current behavior as an variable, and specify a variable
     * name, and the type is a sub type of result of current 
     * behavior
     * 
     * @param varName variable name
     * @param type variable type
     * @return {@link LocVar}
     */
    LocVar asVar(String varName, IClass type);
    
    /**
     * Use current behavior as an variable, and specify a variable
     * name, and the type is a sub type of result of current 
     * behavior
     * 
     * @param varName variable name
     * @param type variable type
     * @return {@link LocVar}
     */
    LocVar asVar(String varName, Class<?> type);
    
}
