package cn.wensiqun.asmsupport.utils.asm;

import org.objectweb.asm.ClassVisitor;

import cn.wensiqun.asmsupport.utils.ASConstant;

public class ClassAdapter extends ClassVisitor {

	public ClassAdapter(ClassVisitor cv) {
		super(ASConstant.ASM_VERSION, cv);
	}

	public ClassAdapter() {
		super(ASConstant.ASM_VERSION);
	}

}
