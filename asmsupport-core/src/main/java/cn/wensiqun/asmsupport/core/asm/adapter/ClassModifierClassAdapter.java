/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.core.asm.adapter;

import cn.wensiqun.asmsupport.core.builder.IMethodBuilder;
import cn.wensiqun.asmsupport.core.builder.impl.ClassModifier;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.ClassVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.utils.ASMSupportConstant;
import cn.wensiqun.asmsupport.utils.asm.ClassAdapter;
import cn.wensiqun.asmsupport.utils.asm.MethodAdapter;
import cn.wensiqun.asmsupport.utils.collections.CollectionUtils;

import java.util.*;


/**
 * this adapter will be change the method name from format xxx to xxx@original
 * 
 * @author
 * 
 */
public class ClassModifierClassAdapter extends ClassAdapter {

	private List<IMethodBuilder> needModify;
	private String classInternalName;

    private Map<String, List<VisitXInsnAdapter>> superConstructorMap;
	
	public ClassModifierClassAdapter(ClassVisitor cv, ClassModifier classModifier) {
		super(cv);
		this.needModify = new LinkedList<IMethodBuilder>();
		if (classModifier.getMethodModifiers() != null) {
			CollectionUtils.addAll(this.needModify, classModifier.getMethodModifiers().iterator());
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
     * rename the invocation. like:
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
				final String owner, final String name, final String desc, boolean itf){
			if(owner.equals(classInternalName) && isModified(name, desc)){
				super.visitMethodInsn(opcode, owner, name + ASMSupportConstant.METHOD_PROXY_SUFFIX, desc, itf);
			}else{
				super.visitMethodInsn(opcode, owner, name, desc, itf);
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
			
			if (name.equals(ASMSupportConstant.INIT)) {
				name = ASMSupportConstant.INIT_PROXY;
				methodVisitor = new ConstructorVisitor(super.visitMethod(access, name + ASMSupportConstant.METHOD_PROXY_SUFFIX, desc, signature, exceptions), desc);
			}else{
				if (name.equals(ASMSupportConstant.CLINIT)) {
					name = ASMSupportConstant.CLINIT_PROXY;
				}
				methodVisitor =super.visitMethod(access, name + ASMSupportConstant.METHOD_PROXY_SUFFIX, desc, signature, exceptions);
			}
		}
		
		return new InvocationRenameMethodAdapter(methodVisitor);
	}

	private boolean isModified(String name, String desc) {
		IMethodBuilder imm = null;
		for (IMethodBuilder m : needModify) {
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

	private boolean methodEqual(IMethodBuilder mm, String name, String desc) {
		if (!mm.getName().equals(name)) {
			return false;
		}

		IClass[] mmArgs = mm.getArguments();
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

	public Map<String, List<VisitXInsnAdapter>> getSuperConstructorMap() {
		return superConstructorMap;
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
                String desc, boolean itf) {
            if (invokedSuper){
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }else{
                addSuperConstructorMap(constructorDesc, new VisitMethodInsnAdapter(opcode, owner, name, desc, itf));
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
