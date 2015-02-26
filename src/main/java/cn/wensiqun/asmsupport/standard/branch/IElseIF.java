package cn.wensiqun.asmsupport.standard.branch;

import cn.wensiqun.asmsupport.standard.body.CommonBody;
import cn.wensiqun.asmsupport.standard.body.IBody;

public interface IElseIF<_ElseIF extends IBody, _Else extends IBody> extends CommonBody {
	
	_ElseIF _elseif(_ElseIF elseif);

	_Else _else(_Else els);
	
}
