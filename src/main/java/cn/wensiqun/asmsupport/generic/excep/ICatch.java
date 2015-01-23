package cn.wensiqun.asmsupport.generic.excep;

import cn.wensiqun.asmsupport.generic.body.IBody;
import cn.wensiqun.asmsupport.generic.body.LocalVariableBody;


public interface ICatch<_Catch extends IBody, _Finally extends IBody> extends LocalVariableBody {

	public _Catch _catch(_Catch catchBlock);
    
    public _Finally _finally(_Finally finallyClient);
	
}
