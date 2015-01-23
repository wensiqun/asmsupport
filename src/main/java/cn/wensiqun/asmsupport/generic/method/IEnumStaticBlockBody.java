package cn.wensiqun.asmsupport.generic.method;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.generic.body.LocalVariablesBody;

public interface IEnumStaticBlockBody extends LocalVariablesBody {

	void newEnum(String name, Parameterized... argus);
	
	/**
	 * call newEnum method at this method.
	 * get some information about current enum type constructor
	 * 
	 * 在此方法中调用newEnum方法， 获取构造枚举列表中每个枚举类型需要的参数信息，枚举类型名等信息。
	 * 
	 */
	void constructEnumField();
	
}
