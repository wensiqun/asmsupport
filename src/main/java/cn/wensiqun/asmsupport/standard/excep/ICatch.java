package cn.wensiqun.asmsupport.standard.excep;

import cn.wensiqun.asmsupport.standard.body.IBody;
import cn.wensiqun.asmsupport.standard.body.LocalVariableBody;


public interface ICatch<_Catch extends IBody, _Finally extends IBody> extends LocalVariableBody {

	public _Catch catch_(_Catch catchBlock);
    
    public _Finally finally_(_Finally finallyClient);
	
}
