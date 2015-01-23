package cn.wensiqun.asmsupport.core.utils.asm;

import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;

public class ClassAdapter extends ClassVisitor {

	public ClassAdapter(ClassVisitor cv) {
		super(ASConstant.ASM_VERSION, cv);
	}

	public ClassAdapter() {
		super(ASConstant.ASM_VERSION);
	}

}
