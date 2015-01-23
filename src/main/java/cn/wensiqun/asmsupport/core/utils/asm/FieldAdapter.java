package cn.wensiqun.asmsupport.core.utils.asm;

import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.FieldVisitor;

public class FieldAdapter extends FieldVisitor {

	public FieldAdapter() {
		super(ASConstant.ASM_VERSION);
	}

	public FieldAdapter(FieldVisitor fv) {
		super(ASConstant.ASM_VERSION, fv);
	}

}
