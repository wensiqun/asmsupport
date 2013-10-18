package cn.wensiqun.asmsupport.block.method.cinit;

import cn.wensiqun.asmsupport.block.method.SuperMethodBody;

public abstract class CInitBody extends SuperMethodBody implements ICInitBody {

	@Override
	protected void init() {
		return;
	}
	
	/*public void addTryCatchInfo(Label start, Label end, Label hander, AClass exception){
        throw new ASMSupportException("cannot throw excetpion in static block, plase catch " + exception + " in program");
    }*/
}
