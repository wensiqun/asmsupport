package jw.asmsupport.utils.finder.method;


import org.objectweb.asm.Label;
import org.objectweb.asm.commons.EmptyVisitor;



public class LabelNumberMethodAdapter extends EmptyVisitor {

	protected int currentLabelNumber;

	@Override
	public void visitLabel(Label label) {
		currentLabelNumber++;
		super.visitLabel(label);
	}
	
	

}
