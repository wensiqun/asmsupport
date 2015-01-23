package cn.wensiqun.asmsupport.core.asm.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.creator.IMethodCreator;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassModifierInternal;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.asm.ClassAdapter;
import cn.wensiqun.asmsupport.core.utils.asm.MethodAdapter;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.apache.commons.collections.CollectionUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;


/**
 * this adapter will be change the method name from format xxx to xxx@original
 * 
 * @author
 * 
 */
public class ClassModifierClassAdapter extends ClassAdapter {

	private List<IMethodCreator> needModify;
	private ClassModifierInternal classModifer;
	private String classInternalName;

    private Map<String, List<VisitXInsnAdapter>> superConstructorMap;
	
	public ClassModifierClassAdapter(ClassVisitor cv, ClassModifierInternal classModifer) {
		super(cv);
		this.classModifer = classModifer;
		this.needModify = new LinkedList<IMethodCreator>();
		if (classModifer.getMethodModifiers() != null) {
			CollectionUtils.addAll(this.needModify, classModifer.getMethodModifiers().iterator());
		}
	}
	
	public List<VisitXInsnAdapter> getSuperConstructorOperators(String desc){
		return superConstructorMap.get(desc);
	}
	

    @Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    	this.classInternalName = name;
		super.visit(version, access, name, signature, superName, interfaces);
	}

    /**
     * rename the invokecation. like:
     * we will modify method "a". and other method "b" will call method "a",
     * so we change method b to follow:
     * b(){
     *     this.a@original();
     * }
     *
     */
	private class InvocationRenameMethodAdapter extends MethodAdapter{

    	
		public InvocationRenameMethodAdapter(MethodVisitor mv) {
			super(mv);
		}
		
		@Override
		public void visitMethodInsn(final int opcode, 
				final String owner, final String name, final String desc){
			if(owner.equals(classInternalName) && isModified(name, desc)){
				super.visitMethodInsn(opcode, owner, name + ASConstant.METHOD_PROXY_SUFFIX, desc);
			}else{
				super.visitMethodInsn(opcode, owner, name, desc);
			}
		}
    	
    }
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor methodVisitor;
		if (!isModified(name, desc)) {
			methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
		} else {
			if(ModifierUtils.isFinal(access)){
				throw new InternalError("method [" + name + "] is final cannot modifier.");
			}
			
			// set method to private
			if (!ModifierUtils.isPrivate(access)) {
				if (ModifierUtils.isProtected(access)) {
					access -= Opcodes.ACC_PROTECTED;
				} else if (ModifierUtils.isPublic(access)) {
					access -= Opcodes.ACC_PUBLIC;
				}
				access += Opcodes.ACC_PRIVATE;
			}
			
			if (name.equals(ASConstant.INIT)) {
				name = ASConstant.INIT_PROXY;
				methodVisitor = new ConstructorVisitor(super.visitMethod(access, name + ASConstant.METHOD_PROXY_SUFFIX, desc, signature, exceptions), desc);
			}else{
				if (name.equals(ASConstant.CLINIT)) {
					name = ASConstant.CLINIT_PROXY;
				}
				methodVisitor =super.visitMethod(access, name + ASConstant.METHOD_PROXY_SUFFIX, desc, signature, exceptions);
			}
		}
		
		return new InvocationRenameMethodAdapter(methodVisitor);
	}

	private boolean isModified(String name, String desc) {
		IMethodCreator imm = null;
		for (IMethodCreator m : needModify) {
			if (methodEqual(m, name, desc)) {
				imm = m;
				break;
			}
		}
		if (imm != null) {
			return true;
		}
		return false;
	}

	private boolean methodEqual(IMethodCreator mm, String name, String desc) {
		if (!mm.getName().equals(name)) {
			return false;
		}

		AClass[] mmArgs = mm.getArguments();
		Type[] types = Type.getArgumentTypes(desc);
		if (mmArgs.length != types.length) {
			return false;
		}

		for (int i = 0; i < mmArgs.length; i++) {
			if (!types[i].equals(mmArgs[i].getType())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void visitEnd() {
		classModifer.modify(superConstructorMap);
		super.visitEnd();
	}
	
	private void addSuperConstructorMap(String constructorDesc, VisitXInsnAdapter operator){
		if(superConstructorMap == null){
			superConstructorMap = new HashMap<String, List<VisitXInsnAdapter>>();
		}
		
		if(superConstructorMap.containsKey(constructorDesc)){
			List<VisitXInsnAdapter> operators = superConstructorMap.get(constructorDesc);
			operators.add(operator);
		}else{
			List<VisitXInsnAdapter> operators = new ArrayList<VisitXInsnAdapter>();
			operators.add(operator);
			superConstructorMap.put(constructorDesc, operators);
		}
	}

	
	class ConstructorVisitor extends MethodAdapter {
        //是否调用了super
		private boolean invokedSuper = false;
		private String constructorDesc;
		
		public ConstructorVisitor(MethodVisitor mv, String desc) {
			super(mv);
			this.constructorDesc = desc;
		}

		@Override
		public void visitInsn(int opcode) {
			if (invokedSuper) {
				super.visitInsn(opcode);
			}else{
				addSuperConstructorMap(constructorDesc, new VisitInsnAdapter(opcode));	
			}
		}

		@Override
		public void visitIntInsn(int opcode, int operand) {
			if (invokedSuper){
				super.visitIntInsn(opcode, operand);
			}else{
				addSuperConstructorMap(constructorDesc, new VisitIntInsnAdapter(operand, operand));
			}
		}

		@Override
		public void visitVarInsn(int opcode, int var) {
			if (invokedSuper){
				super.visitVarInsn(opcode, var);
			}else{
				addSuperConstructorMap(constructorDesc, new VisitVarInsnAdapter(opcode, var));
			}
		}

		@Override
		public void visitTypeInsn(int opcode, String type) {
			if (invokedSuper){
				super.visitTypeInsn(opcode, type);
			}else{
				addSuperConstructorMap(constructorDesc, new VisitTypeInsnAdapter(opcode, type));
			}
		}

		@Override
		public void visitFieldInsn(int opcode, String owner, String name,
				String desc) {
			if (invokedSuper){
				super.visitFieldInsn(opcode, owner, name, desc);
			}else{
				addSuperConstructorMap(constructorDesc, new VisitFieldInsnAdapter(opcode, owner, name, desc));
			}
		}

		@Override
		public void visitMethodInsn(int opcode, String owner, String name,
				String desc) {
			if (invokedSuper){
				super.visitMethodInsn(opcode, owner, name, desc);
			}else{
				addSuperConstructorMap(constructorDesc, new VisitMethodInsnAdapter(opcode, owner, name, desc));
			}

			if (opcode == Opcodes.INVOKESPECIAL) {
				invokedSuper = true;
			}
		}

		@Override
		public void visitLdcInsn(Object cst) {
			if (invokedSuper){
				super.visitLdcInsn(cst);
			}else{
				addSuperConstructorMap(constructorDesc, new VisitLdcInsnAdapter(cst));
			}
		}

		@Override
		public void visitIincInsn(int var, int increment) {
			if (invokedSuper){
				super.visitIincInsn(var, increment);
			}else{
				addSuperConstructorMap(constructorDesc, new VisitIincInsnAdapter(var, increment));
			}
		}

		@Override
		public void visitMultiANewArrayInsn(String desc, int dims) {
			if (invokedSuper){
				super.visitMultiANewArrayInsn(desc, dims);
			}else{
				addSuperConstructorMap(constructorDesc, new VisitMultiANewArrayInsnAdapter(desc, dims));
			}
		}

	}
}
