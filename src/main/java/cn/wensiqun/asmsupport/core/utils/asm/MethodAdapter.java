package cn.wensiqun.asmsupport.core.utils.asm;

import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;

public class MethodAdapter extends MethodVisitor {

	public MethodAdapter(MethodVisitor mv) {
		super(ASConstant.ASM_VERSION, mv);
	}

	public MethodAdapter() {
		super(ASConstant.ASM_VERSION);
	}

	
	
}
