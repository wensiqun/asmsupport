package cn.wensiqun.asmsupport.standard.method;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.standard.body.LocalVariablesBody;

public interface IEnumStaticBlockBody extends LocalVariablesBody {

    /**
     * 
     * @param name
     * @param argus
     */
	void constructEnumConst(String name, Parameterized... argus);
	
	/**
	 * <p>
	 * call {@link #constructEnumConst} method at this method.
	 * get some information about current enum type constructor.
	 * </p>
	 * <p>
	 * 在此方法中调用{@link #constructEnumConst}方法， 获取构造枚举列表中每个枚举类型需要的参数信息，枚举类型名等信息。
	 * </p>
	 */
	void constructEnumConsts();
	
}
