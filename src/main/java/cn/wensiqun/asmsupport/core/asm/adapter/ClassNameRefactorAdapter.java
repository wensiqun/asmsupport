package cn.wensiqun.asmsupport.core.asm.adapter;


import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.asm.ClassAdapter;
import cn.wensiqun.asmsupport.core.utils.asm.MethodAdapter;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;


/**
 * refactor original class name
 *
 */
public class ClassNameRefactorAdapter extends ClassAdapter {
	
	private String originalName;
	private String JVMProxyClassName;
	
	public ClassNameRefactorAdapter(ClassVisitor cv) {
		super(cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		JVMProxyClassName = name + ASConstant.CLASS_PROXY_SUFFIX;
		originalName = name;
		super.visit(version, access, JVMProxyClassName, signature, superName, interfaces);
	}

	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		return new ClassNameRefactorVisitor(super.visitMethod(access, name, desc, signature, exceptions));
	}
	
	class ClassNameRefactorVisitor extends MethodAdapter {

		public ClassNameRefactorVisitor(MethodVisitor mv) {
			super(mv);
		}

		@Override
		public void visitFieldInsn(int opcode, String owner, String name,
				String desc) {
			if(originalName.equals(owner)){
				owner = JVMProxyClassName;
			}
			super.visitFieldInsn(opcode, owner, name, desc);
		}

		@Override
		public void visitMethodInsn(int opcode, String owner, String name,
				String desc) {
			if(originalName.equals(owner)){
				owner = JVMProxyClassName;
			}
			super.visitMethodInsn(opcode, owner, name, desc);
		}

		@Override
		public void visitLocalVariable(String name, String desc,
				String signature, Label start, Label end, int index) {
			if(desc.equals("L" + originalName + ";")){
				desc = "L" + JVMProxyClassName + ";";
			}
			super.visitLocalVariable(name, desc, signature, start, end, index);
		}
		
	}
	
	public String getJVMProxyClassName() {
		return JVMProxyClassName;
	}
	
}
