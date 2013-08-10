package jw.asmsupport.utils.finder.method;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import jw.asmsupport.clazz.AClass;
import jw.asmsupport.utils.finder.method.MethodInfoCollecter.MethodVisitorInfo;
import jw.asmsupport.utils.finder.method.MethodInfoCollecter.VisitLocalVariableInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.googlecode.jwcommon.ModifierUtils;

public class StackLocalMethodVisitor extends LabelNumberMethodAdapter implements Opcodes {
	
    private static Log LOG = LogFactory.getLog(StackLocalMethodVisitor.class);
    //private final Type THROWABLE = Type.getType(Throwable.class);
    
	private      Type[]                   argumentTypes;
	protected    Stack<Type>              stack;
	protected    List<Type>               localVariables;
	private      List<Label>              exceptionHandlerPointers;
	private      List<String>             exceptions;
	private      MethodVisitorInfo        methodVisitorInfo;

	public StackLocalMethodVisitor(String methodDesc, int modifiers, Type owner, MethodVisitorInfo  methodVisitorInfo) {
		super();
		this.methodVisitorInfo = methodVisitorInfo;
		argumentTypes = Type.getArgumentTypes(methodDesc);
		//returnType = Type.getReturnType(methodDesc);
		localVariables = new ArrayList<Type>();
		stack = new Stack<Type>();
		
		if(!ModifierUtils.isStatic(modifiers)){
			localVariables.add(owner);
		}
        for(Type t : argumentTypes){
        	int size = t.getSize();
        	while(size-- > 0){
            	localVariables.add(t);
        	}
        }
	}
	
	@Override
	public void visitFrame(final int type, final int nLocal,
			final Object[] local, final int nStack, final Object[] stack) {
		if(stack != null){
	        for(Object obj : stack){
	        	if(obj == null){
	        	    this.stack.push(AClass.THROWABLE_ACLASS.getType());
	        	}else if(obj.equals(Opcodes.INTEGER)){
	        		this.stack.push(Type.INT_TYPE);
	        	}else if(obj.equals(Opcodes.LONG)){
	        		this.stack.push(Type.LONG_TYPE);
	        	}else if(obj.equals(Opcodes.FLOAT)){
	        		this.stack.push(Type.FLOAT_TYPE);
	        	}else if(obj.equals(Opcodes.DOUBLE)){
	        		this.stack.push(Type.DOUBLE_TYPE);
	        	}else if(obj.equals(Opcodes.NULL)){
	        		this.stack.push(AClass.OBJECT_ACLASS.getType());
	        	}else if(obj.equals(Opcodes.UNINITIALIZED_THIS)){
	        		this.stack.push(this.stack.firstElement());
	        	}else if(obj.equals(Opcodes.TOP)){
	        	    
	        	}else{
	        		this.stack.push(Type.getObjectType(obj.toString()));
	        	}
	        }
		}
		super.visitFrame(type, nLocal, local, nStack, stack);
	}

	@Override
	public void visitInsn(final int opcode) {
		Type word1;
		Type word2;
		Type word3;
		Type word4;
		switch (opcode) {
		case NOP:
			break;
		case ACONST_NULL:
			stack.push(AClass.OBJECT_ACLASS.getType());
			break;
		case ICONST_M1:
			stack.push(Type.INT_TYPE);
			break;
		case ICONST_0:
			stack.push(Type.INT_TYPE);
			break;
		case ICONST_1:
			stack.push(Type.INT_TYPE);
			break;
		case ICONST_2:
			stack.push(Type.INT_TYPE);
			break;
		case ICONST_3:
			stack.push(Type.INT_TYPE);
			break;
		case ICONST_4:
			stack.push(Type.INT_TYPE);
			break;
		case ICONST_5:
			stack.push(Type.INT_TYPE);
			break;
		case LCONST_0:
			stack.push(Type.LONG_TYPE);
			break;
		case LCONST_1:
			stack.push(Type.LONG_TYPE);
			break;
		case FCONST_0:
			stack.push(Type.FLOAT_TYPE);
			break;
		case FCONST_1:
			stack.push(Type.FLOAT_TYPE);
			break;
		case FCONST_2:
			stack.push(Type.FLOAT_TYPE);
			break;
		case DCONST_0:
			stack.push(Type.DOUBLE_TYPE);
			break;
		case DCONST_1:
			stack.push(Type.DOUBLE_TYPE);
			break;
		case IALOAD:
			stack.pop();//pop index
			stack.pop();//pop array ref
			stack.push(Type.INT_TYPE);
			break;
		case LALOAD:
			stack.pop();//pop index
			stack.pop();//pop array ref
			stack.push(Type.LONG_TYPE);
			break;
		case FALOAD:
			stack.pop();//pop index
			stack.pop();//pop array ref
			stack.push(Type.FLOAT_TYPE);
			break;
		case DALOAD:
			stack.pop();//pop index
			stack.pop();//pop array ref
			stack.push(Type.DOUBLE_TYPE);
			break;
		case AALOAD:
			stack.pop();//pop index
			stack.push(getSubType(stack.pop()));
			break;
		case BALOAD:
			stack.pop();//pop index
			stack.pop();//pop array ref
			stack.push(Type.BYTE_TYPE);
			break;
		case CALOAD:
			stack.pop();//pop index
			stack.pop();//pop array ref
			stack.push(Type.CHAR_TYPE);
			break;
		case SALOAD:
			stack.pop();//pop index
			stack.pop();//pop array ref
			stack.push(Type.SHORT_TYPE);
			break;
		case IASTORE:
		case LASTORE:
		case FASTORE:
		case DASTORE:
		case AASTORE:
		case BASTORE:
		case CASTORE:
		case SASTORE:
			stack.pop();//pop value
			stack.pop();//pop index
			stack.pop();//pop array ref
			break;
		case POP:
			stack.pop();//pop value
			break;
		case POP2:
			word1 = stack.pop();
			if(word1.getSize() != 2){
				stack.pop();//pop value
			}
			
			break;
		case DUP:
			stack.push(stack.peek());
			break;
		case DUP_X1:
			word1 = stack.pop();
			word2 = stack.pop();
			stack.push(word1);
			stack.push(word2);
			stack.push(word1);
			break;
		case DUP_X2:
			word1 = stack.pop();
			word2 = stack.pop();
			word3 = stack.pop();
			stack.push(word1);
			stack.push(word3);
			stack.push(word2);
			stack.push(word1);
			break;
		case DUP2:
			word1 = stack.pop();
			if(word1.getSize() == 2){
				stack.push(word1);
				stack.push(word1);
			}else{
				word2 = stack.pop();
				stack.push(word2);
				stack.push(word1);
				stack.push(word2);
				stack.push(word1);
			}
			break;
		case DUP2_X1:
			word1 = stack.pop();
		    word2 = stack.pop();
			if(word1.getSize() == 2){
			    stack.push(word1);
			    stack.push(word2);
			    stack.push(word1);
			}else{
			    word3 = stack.pop();
			    stack.push(word2);
			    stack.push(word1);
			    stack.push(word3);
			    stack.push(word2);
			    stack.push(word1);
			}
			break;
		case DUP2_X2:
			word1 = stack.pop();
			word2 = stack.pop();
			if(word1.getSize() == 2){
				if(word2.getSize() == 2){
				    stack.push(word1);
				    stack.push(word2);
				    stack.push(word1);
				}else{
					word3 = stack.pop();
				    stack.push(word1);
				    stack.push(word3);
				    stack.push(word2);
				    stack.push(word1);
				}
			}else{
				word3 = stack.pop();
				if(word3.getSize() == 2){
					stack.push(word2);
					stack.push(word1);
					stack.push(word3);
					stack.push(word2);
					stack.push(word1);
				}else{
					word4 = stack.pop();
					stack.push(word2);
					stack.push(word1);
					stack.push(word4);
					stack.push(word3);
					stack.push(word2);
					stack.push(word1);
				}
			}
			break;
		case SWAP:
			word1 = stack.pop();
			word2 = stack.pop();
			stack.push(word1);
			stack.push(word2);
			break;
		case IADD:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LADD:
			stack.pop();
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case FADD:
			stack.pop();
			stack.pop();
			stack.push(Type.FLOAT_TYPE);
			break;
		case DADD:
			stack.pop();
			stack.pop();
			stack.push(Type.DOUBLE_TYPE);
			break;
		case ISUB:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LSUB:
			stack.pop();
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case FSUB:
			stack.pop();
			stack.pop();
			stack.push(Type.FLOAT_TYPE);
			break;
		case DSUB:
			stack.pop();
			stack.pop();
			stack.push(Type.DOUBLE_TYPE);
			break;
		case IMUL:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LMUL:
			stack.pop();
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case FMUL:
			stack.pop();
			stack.pop();
			stack.push(Type.FLOAT_TYPE);
			break;
		case DMUL:
			stack.pop();
			stack.pop();
			stack.push(Type.DOUBLE_TYPE);
			break;
		case IDIV:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LDIV:
			stack.pop();
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case FDIV:
			stack.pop();
			stack.pop();
			stack.push(Type.FLOAT_TYPE);
			break;
		case DDIV:
			stack.pop();
			stack.pop();
			stack.push(Type.DOUBLE_TYPE);
			break;
		case IREM:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LREM:
			stack.pop();
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case FREM:
			stack.pop();
			stack.pop();
			stack.push(Type.FLOAT_TYPE);
			break;
		case DREM:
			stack.pop();
			stack.pop();
			stack.push(Type.DOUBLE_TYPE);
			break;
		case INEG:
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LNEG:
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case FNEG:
			stack.pop();
			stack.push(Type.FLOAT_TYPE);
			break;
		case DNEG:
			stack.pop();
			stack.push(Type.DOUBLE_TYPE);
			break;
		case ISHL:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LSHL:
			stack.pop();
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case ISHR:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LSHR:
			stack.pop();
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case IUSHR:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LUSHR:
			stack.pop();
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case IAND:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LAND:
			stack.pop();
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case IOR:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LOR:
			stack.pop();
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case IXOR:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case LXOR:
			stack.pop();
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case I2L:
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case I2F:
			stack.pop();
			stack.push(Type.FLOAT_TYPE);
			break;
		case I2D:
			stack.pop();
			stack.push(Type.DOUBLE_TYPE);
			break;
		case L2I:
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case L2F:
			stack.pop();
			stack.push(Type.FLOAT_TYPE);
			break;
		case L2D:
			stack.pop();
			stack.push(Type.DOUBLE_TYPE);
			break;
		case F2I:
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case F2L:
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case F2D:
			stack.pop();
			stack.push(Type.DOUBLE_TYPE);
			break;
		case D2I:
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case D2L:
			stack.pop();
			stack.push(Type.LONG_TYPE);
			break;
		case D2F:
			stack.pop();
			stack.push(Type.FLOAT_TYPE);
			break;
		case I2B:
			stack.pop();
			stack.push(Type.BYTE_TYPE);
			break;
		case I2C:
			stack.pop();
			stack.push(Type.CHAR_TYPE);
			break;
		case I2S:
			stack.pop();
			stack.push(Type.SHORT_TYPE);
			break;
		case LCMP:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case FCMPL:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case FCMPG:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case DCMPL:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case DCMPG:
			stack.pop();
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case IRETURN:
			stack.pop();
			break;
		case LRETURN:
			stack.pop();
			break;
		case FRETURN:
			stack.pop();
			break;
		case DRETURN:
			stack.pop();
			break;
		case ARETURN:
			stack.pop();
			break;
		case RETURN:
			break;
		case ARRAYLENGTH:
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		case ATHROW:
			stack.pop();
			break;
		case MONITORENTER:
			stack.pop();
			break;
		case MONITOREXIT:
			stack.pop();
			break;
		}
	}

	@Override
	public void visitIntInsn(final int opcode, final int operand) {
		switch (opcode) {
		case BIPUSH:
			stack.push(Type.INT_TYPE);
			break;
		case SIPUSH:
			stack.push(Type.INT_TYPE);
			break;
		case NEWARRAY:
			stack.push(Type.getType("[" + getTypeByOperand(operand).getDescriptor()));
			break;
		}
		super.visitIntInsn(opcode, operand);
	}

	private void storeLocalVariable(Type type, int pos){
		int size = pos + type.getSize();
		if(size > localVariables.size()){
			for(int i=0, ln=size - localVariables.size(); i<ln ; i++){
				localVariables.add(null);
			}
		}
		for(int i=pos; i<localVariables.size(); i++){
			localVariables.set(pos, type);
		}
	}
	
	private VisitLocalVariableInfo getVisitLocalVariableInfo(int lblNum, int index){
		List<VisitLocalVariableInfo> varInfors = methodVisitorInfo.getVisitLocalVariableInfors();
		VisitLocalVariableInfo mostMatch = null;

		if(varInfors != null){
			int mostMatchSubVal = -1;
			for(VisitLocalVariableInfo v : varInfors){
				if(v.getIndex() == index){
					int subVal = lblNum - v.getStart();
					if(subVal == 0){
						return v;
					}else{
						if(mostMatchSubVal == -1 || subVal < mostMatchSubVal){
							mostMatchSubVal = subVal;
							mostMatch = v;
						}
					}
				}
			}
		}
		return mostMatch;
	}
	
	private void loadToStack(final int opcode, final int var){
		Type t;
		if(var >= localVariables.size() || (t = localVariables.get(var)) == null){
		    Type added;
			switch(opcode){
			case LLOAD:
				added = Type.LONG_TYPE;
				break;
			case FLOAD:
				added = Type.FLOAT_TYPE;
				break;
			case DLOAD:
				added = Type.DOUBLE_TYPE;
				break;
			default :
				VisitLocalVariableInfo varInfo = getVisitLocalVariableInfo(currentLabelNumber, var);
				if(varInfo == null){
					added = AClass.OBJECT_ACLASS.getType();
				}else{
					added = Type.getType(varInfo.getDesc());
				}
				break;
		    }
			storeLocalVariable(added, var);
		}
		t = localVariables.get(var);
		int size = t.getSize();
		while(size-- > 0){
			stack.push(t);
		}
	}
	
	@Override
	public void visitVarInsn(final int opcode, final int var) {
		
		switch (opcode) {
		case ILOAD:
		case LLOAD:
		case FLOAD:
		case DLOAD:
		case ALOAD:
			loadToStack(opcode, var);
			break;
		case ISTORE:
		case LSTORE:
		case FSTORE:
		case DSTORE:
		case ASTORE:
			Type top = stack.pop();
			storeLocalVariable(top, var);
			break;
		case RET:
			break;
		}
		super.visitVarInsn(opcode, var);
	}

	@Override
	public void visitTypeInsn(final int opcode, final String type) {
		
		switch (opcode) {
		case NEW:
			stack.push(Type.getObjectType(type));
			break;
		case ANEWARRAY:
			stack.pop();
			String arrayType;
			if(type.startsWith("[")){
				arrayType = "[" + type;
			}else{
				arrayType = "[L" + type + ";" ;
			}
			stack.push(Type.getType(arrayType));
			break;
		case CHECKCAST:
			stack.pop();
			stack.push(Type.getObjectType(type));
			break;
		case INSTANCEOF:
			stack.pop();
			stack.push(Type.INT_TYPE);
			break;
		}
		super.visitTypeInsn(opcode, type);
	}

	@Override
	public void visitFieldInsn(final int opcode, final String owner,
			final String name, final String desc) {
		
		switch (opcode) {
		case GETSTATIC:
			stack.push(Type.getType(desc));
			break;
		case PUTSTATIC:
			stack.pop();
			break;
		case GETFIELD:
			stack.pop();
			stack.push(Type.getType(desc));
			break;
		case PUTFIELD:
			stack.pop();
			stack.pop();
			break;
		}
		super.visitFieldInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitMethodInsn(final int opcode, final String owner,
			final String name, final String desc) {
		
		Type[] args = Type.getArgumentTypes(desc);
		Type retType = Type.getReturnType(desc);
		int argLn = args.length;
		while(argLn-- > 0){
			stack.pop();
		}
		switch (opcode) {
		case INVOKEVIRTUAL:
		case INVOKESPECIAL:
			stack.pop();
			break;
		case INVOKESTATIC:
			break;
		case INVOKEINTERFACE:
			stack.pop();
			break;
		case INVOKEDYNAMIC:
			break;
		}
		if(retType.getSize() > 0){
			stack.push(retType);
		}
		super.visitMethodInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitJumpInsn(final int opcode, final Label label) {
		
		switch (opcode) {
		case IFEQ:
		case IFNE:
		case IFLT:
		case IFGE:
		case IFGT:
		case IFLE:
			stack.pop();
			break;
		case IF_ICMPEQ:
		case IF_ICMPNE:
		case IF_ICMPLT:
		case IF_ICMPGE:
		case IF_ICMPGT:
		case IF_ICMPLE:
		case IF_ACMPEQ:
		case IF_ACMPNE:
			stack.pop();
			stack.pop();
			break;
		case GOTO:
			break;
		case JSR:
			stack.push(Type.INT_TYPE);
			break;
		case IFNULL:
		case IFNONNULL:
			stack.pop();
			break;
		}
		super.visitJumpInsn(opcode, label);
	}

	@Override
	public void visitLabel(final Label label) {
		if(exceptionHandlerPointers != null){
			for(int i=0; i<exceptionHandlerPointers.size(); i++){
				if(label.equals(exceptionHandlerPointers.get(i))){
					String type = exceptions.get(i);
					if(type == null){
						this.stack.add(AClass.THROWABLE_ACLASS.getType());
					}else{
						this.stack.add(Type.getObjectType(type));	
					}
					break;
				}
			}
		}
		super.visitLabel(label);
	}
	
	@Override
	public void visitLineNumber(int line, Label start) {
		super.visitLineNumber(line, start);
	}

	@Override
	public void visitLdcInsn(final Object cst) {
		Class<?> ctsCls = cst.getClass();
		if(ctsCls.equals(Type.class)){
			stack.push(AClass.CLASS_ACLASS.getType());
		}else if(ctsCls.equals(Integer.class)){
			stack.push(Type.INT_TYPE);
		}else if(ctsCls.equals(Float.class)){
			stack.push(Type.FLOAT_TYPE);
		}else if(ctsCls.equals(Long.class)){
			stack.push(Type.LONG_TYPE);
		}else if(ctsCls.equals(Double.class)){
			stack.push(Type.DOUBLE_TYPE);
		}else if(ctsCls.equals(String.class)){
			stack.push(AClass.STRING_ACLASS.getType());
		}
		super.visitLdcInsn(cst);
	}

	@Override
	public void visitIincInsn(final int var, final int increment) {
		LOG.debug("iinc");
		super.visitIincInsn(var, increment);
	}

	@Override
	public void visitTableSwitchInsn(final int min, final int max,
			final Label dflt, final Label[] labels) {
		LOG.debug("TABLESWITCH");
        stack.pop();
		super.visitTableSwitchInsn(min, max, dflt, labels);
	}

	@Override
	public void visitLookupSwitchInsn(final Label dflt, final int[] keys,
			final Label[] labels) {
		LOG.debug("LookupSwitch");
        stack.pop();
		super.visitLookupSwitchInsn(dflt, keys, labels);
	}

	@Override
	public void visitMultiANewArrayInsn(final String desc, final int dims) {
		LOG.debug("MultiANewArray");
		int i = dims;
		while(i-- > 0){
	        stack.pop();
		};
		stack.push(Type.getType(desc));
		super.visitMultiANewArrayInsn(desc, dims);
	}

	@Override
	public void visitTryCatchBlock(final Label start, final Label end,
			final Label handler, final String type) {
		if(exceptionHandlerPointers == null){
			exceptionHandlerPointers = new ArrayList<Label>();
		}
		if(exceptions == null){
			exceptions = new ArrayList<String>();
		}
		exceptionHandlerPointers.add(handler);
		exceptions.add(type);
		super.visitTryCatchBlock(start, end, handler, type);
	}

	@Override
	public void visitLocalVariable(final String name, final String desc,
			final String signature, final Label start, final Label end,
			final int index) {
		super.visitLocalVariable(name, desc, signature, start, end, index);
	}

	private Type getTypeByOperand(int order) {
		switch (order) {
		case T_BOOLEAN:
			return Type.BOOLEAN_TYPE;
		case T_CHAR:
			return Type.CHAR_TYPE;
		case T_BYTE:
			return Type.BYTE_TYPE;
		case T_SHORT:
			return Type.SHORT_TYPE;
		case T_INT:
			return Type.INT_TYPE;
		case T_FLOAT:
			return Type.FLOAT_TYPE;
		case T_LONG:
			return Type.LONG_TYPE;
		case T_DOUBLE:
			return Type.DOUBLE_TYPE;
		default:
			throw new InternalError();
		}
	}

	private Type getSubType(Type arrayType){
		String desc = arrayType.getDescriptor();
		Type sub = Type.getType(desc.substring(0));
		return sub;
	}
}
