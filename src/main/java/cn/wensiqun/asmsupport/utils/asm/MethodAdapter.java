package cn.wensiqun.asmsupport.utils.asm;

import org.objectweb.asm.MethodVisitor;

import cn.wensiqun.asmsupport.utils.ASConstant;

public class MethodAdapter extends MethodVisitor {

	public MethodAdapter(MethodVisitor mv) {
		super(ASConstant.ASM_VERSION, mv);
	}

	public MethodAdapter() {
		super(ASConstant.ASM_VERSION);
	}

	
	
}
