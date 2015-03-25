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
	private String jvmProxyClassName;
	
	public ClassNameRefactorAdapter(ClassVisitor cv) {
		super(cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		jvmProxyClassName = name + ASConstant.CLASS_PROXY_SUFFIX;
		originalName = name;
		super.visit(version, access, jvmProxyClassName, signature, superName, interfaces);
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
				owner = jvmProxyClassName;
			}
			super.visitFieldInsn(opcode, owner, name, desc);
		}

		@Override
		public void visitMethodInsn(int opcode, String owner, String name,
				String desc) {
			if(originalName.equals(owner)){
				owner = jvmProxyClassName;
			}
			super.visitMethodInsn(opcode, owner, name, desc);
		}

		@Override
		public void visitLocalVariable(String name, String desc,
				String signature, Label start, Label end, int index) {
			if(desc.equals("L" + originalName + ";")){
				desc = "L" + jvmProxyClassName + ";";
			}
			super.visitLocalVariable(name, desc, signature, start, end, index);
		}
		
	}
	
	public String getJVMProxyClassName() {
		return jvmProxyClassName;
	}
	
}
