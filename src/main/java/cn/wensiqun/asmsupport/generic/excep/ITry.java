package cn.wensiqun.asmsupport.generic.excep;

import cn.wensiqun.asmsupport.generic.body.CommonBody;
import cn.wensiqun.asmsupport.generic.body.IBody;

public interface ITry<_Catch extends IBody, _Finally extends IBody> extends CommonBody {

	public _Catch _catch(_Catch catchBlock);
    
    public _Finally _finally(_Finally finallyClient);
	
}
