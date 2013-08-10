package jw.asmsupport.utils.finder.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.EmptyVisitor;




public class MethodInfoCollecter extends EmptyVisitor {

	private Map<String, MethodVisitorInfo> methodInfors;

	public static String getKey(String name, String desc) {
		return name + " " + desc;
	}

	public MethodInfoCollecter() {
		methodInfors = new HashMap<String, MethodVisitorInfo>();
	}

	public Map<String, MethodVisitorInfo> getMethodInfors() {
		return methodInfors;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitorInfo mvInfo = new MethodVisitorInfo();
		methodInfors.put(getKey(name, desc), mvInfo);
		return new MethodInfoVisitor(mvInfo);
	}

	private static class MethodInfoVisitor extends LabelNumberMethodAdapter {

		private MethodVisitorInfo methodVisitorInfo;
		
		protected Map<Label, Integer> labelNumMapper;
		
		public MethodInfoVisitor(MethodVisitorInfo methodVisitorInfo) {
			super();
			this.methodVisitorInfo = methodVisitorInfo;
			this.labelNumMapper = new HashMap<Label, Integer>();
		}

		@Override
		public void visitLocalVariable(String name, String desc,
				String signature, Label start, Label end, int index) {
			VisitLocalVariableInfo vlvi = new VisitLocalVariableInfo();
			vlvi.setName(name);
			vlvi.setDesc(desc);
			vlvi.setSignature(signature);
			vlvi.setStart(labelNumMapper.get(start));
			vlvi.setEnd(labelNumMapper.get(end));
			vlvi.setIndex(index);
			if(methodVisitorInfo.getVisitLocalVariableInfors() == null){
				methodVisitorInfo.setVisitLocalVariableInfors(new ArrayList<VisitLocalVariableInfo>());
			}
			methodVisitorInfo.getVisitLocalVariableInfors().add(vlvi);
		}

		@Override
		public void visitLabel(Label label) {
            labelNumMapper.put(label, currentLabelNumber);
			super.visitLabel(label);
		}

	}

	static class MethodVisitorInfo {
		private List<VisitLocalVariableInfo> visitLocalVariableInfors;

		public List<VisitLocalVariableInfo> getVisitLocalVariableInfors() {
			return visitLocalVariableInfors;
		}

		public void setVisitLocalVariableInfors(
				List<VisitLocalVariableInfo> visitLocalVariableInfors) {
			this.visitLocalVariableInfors = visitLocalVariableInfors;
		}

	}

	static class VisitLocalVariableInfo {
		private String name;
		private String desc;
		private String signature;
		private int start;
		private int end;
		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getSignature() {
			return signature;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		public int getStart() {
			return start;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}
}
