package cn.wensiqun.asmsupport.client.def.behavior;

import cn.wensiqun.asmsupport.client.def.Param;


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
    
}
