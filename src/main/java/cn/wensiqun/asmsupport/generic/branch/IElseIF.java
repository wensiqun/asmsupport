package cn.wensiqun.asmsupport.generic.branch;

import cn.wensiqun.asmsupport.generic.body.CommonBody;
import cn.wensiqun.asmsupport.generic.body.IBody;

public interface IElseIF<_ElseIF extends IBody, _Else extends IBody> extends CommonBody {
	
	_ElseIF _elseif(_ElseIF elseif);

	_Else _else(_Else els);
	
}
