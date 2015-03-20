package cn.wensiqun.asmsupport.standard.branch;

import cn.wensiqun.asmsupport.standard.body.CommonBody;
import cn.wensiqun.asmsupport.standard.body.IBody;

public interface IIF<_ElseIF extends IBody, _Else extends IBody> extends CommonBody {

	_ElseIF elseif(_ElseIF elseif);
	
	_Else else_(_Else els);
	
}
