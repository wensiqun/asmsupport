package cn.wensiqun.asmsupport.core.utils.asm;

import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.AnnotationVisitor;

public class AnnotationAdapter extends AnnotationVisitor {

	public AnnotationAdapter(AnnotationVisitor av) {
		super(ASConstant.ASM_VERSION, av);
	}

	public AnnotationAdapter() {
		super(ASConstant.ASM_VERSION);
	}

	
	
}
