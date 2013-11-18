package cn.wensiqun.asmsupport.utils.asm;

import org.objectweb.asm.AnnotationVisitor;

import cn.wensiqun.asmsupport.utils.ASConstant;

public class AnnotationAdapter extends AnnotationVisitor {

	public AnnotationAdapter(AnnotationVisitor av) {
		super(ASConstant.ASM_VERSION, av);
	}

	public AnnotationAdapter() {
		super(ASConstant.ASM_VERSION);
	}

	
	
}
