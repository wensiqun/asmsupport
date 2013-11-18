package cn.wensiqun.asmsupport.utils.asm;

import org.objectweb.asm.FieldVisitor;

import cn.wensiqun.asmsupport.utils.ASConstant;

public class FieldAdapter extends FieldVisitor {

	public FieldAdapter() {
		super(ASConstant.ASM_VERSION);
	}

	public FieldAdapter(FieldVisitor fv) {
		super(ASConstant.ASM_VERSION, fv);
	}

}
