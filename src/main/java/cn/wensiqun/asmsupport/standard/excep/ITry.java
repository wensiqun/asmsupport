package cn.wensiqun.asmsupport.standard.excep;

import cn.wensiqun.asmsupport.standard.body.CommonBody;
import cn.wensiqun.asmsupport.standard.body.IBody;

public interface ITry<_Catch extends IBody, _Finally extends IBody> extends CommonBody {

	public _Catch _catch(_Catch catchBlock);
    
    public _Finally _finally(_Finally finallyClient);
	
}
