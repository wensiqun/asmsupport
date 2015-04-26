package cn.wensiqun.asmsupport.client.def.param;

import cn.wensiqun.asmsupport.client.block.KernelProgramBlockCursor;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.operations.action.EqualAction;
import cn.wensiqun.asmsupport.client.operations.action.NotEqualAction;
import cn.wensiqun.asmsupport.core.definition.KernelParame;

public class NullParam extends DummyParam {

	public NullParam(KernelProgramBlockCursor cursor, KernelParame target) {
		super(cursor, target);
	}

    public ObjectParam stradd(Param param) {
        return new ObjectParam(cursor, cursor.getPointer().stradd(target, ParamPostern.getTarget(param)));
    }

    public BoolParam eq(Param para) {
        return new BoolParam(cursor, new EqualAction(cursor), this);
    }

    public BoolParam ne(Param para) {
        return new BoolParam(cursor, new NotEqualAction(cursor), this);
    }
}
