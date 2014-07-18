package cn.wensiqun.asmsupport.asm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.exception.InstructionException;
import cn.wensiqun.asmsupport.utils.asm.MethodAdapter;
import cn.wensiqun.asmsupport.utils.memory.Stack;
import cn.wensiqun.asmsupport.utils.memory.Stackable;

public class StackLocalMethodVisitor extends MethodAdapter implements Opcodes{

    private static Log log = LogFactory.getLog(StackLocalMethodVisitor.class);    
    private static final Type OBJECT_TYPE = Type.getType(Object.class);
    
	private Stack stack;
	private Type[] nextPushTypes;
	
	public Stack getStack(){
		return stack;
	}
	
	private Type[] triggerPushStack(){
		if(nextPushTypes == null){
			return null;
		}
		stack.push(nextPushTypes);
		Type[] pushed = nextPushTypes;
		nextPushTypes = null;
		return pushed;
	}
	
	void setNextPushTypes(Type... types){
		this.nextPushTypes = types;
	}
	
	public StackLocalMethodVisitor(MethodVisitor mv, Stack stack) {
		super(mv);
		this.stack = stack;
	}
	
	private void stackLocalOperator(int opcode){
		stackLocalOperator(opcode, -1);
	}
	
	/**
	 * Stack : ACONST_NULL, ALOAD,
	 * @param opcode
	 */
	private void stackLocalOperator(int opcode, int popNum){
		Stackable top;
		switch(opcode){
		    case NOP :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : NOP");
		    	break;
		    case ACONST_NULL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ACONST_NULL");
		    	if(nextPushTypes == null){
		    		nextPushTypes = new Type[]{OBJECT_TYPE};
		    		//throw new InternalError("please reference a type");
		    	}
		    	break;
		    case ICONST_M1 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ICONST_M1");
		    	nextPushTypes = new Type[]{Type.INT_TYPE};
		    	break;
		    case ICONST_0 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ICONST_0");
		    	nextPushTypes = new Type[]{Type.INT_TYPE};
		    	break;
		    case ICONST_1 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ICONST_1");
		    	nextPushTypes = new Type[]{Type.INT_TYPE};
		    	break;
		    case ICONST_2 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ICONST_2");
		    	nextPushTypes = new Type[]{Type.INT_TYPE};
		    	break;
		    case ICONST_3 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ICONST_3");
		    	nextPushTypes = new Type[]{Type.INT_TYPE};
		    	break;
		    case ICONST_4 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ICONST_4");
		    	nextPushTypes = new Type[]{Type.INT_TYPE};
		    	break;
		    case ICONST_5 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ICONST_5");
		    	nextPushTypes = new Type[]{Type.INT_TYPE};
		    	break;
		    case LCONST_0 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LCONST_0");
		    	nextPushTypes = new Type[]{Type.LONG_TYPE};
		    	break;
		    case LCONST_1 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LCONST_1");
		    	nextPushTypes = new Type[]{Type.LONG_TYPE};
		    	break;
		    case FCONST_0 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FCONST_0");
		    	nextPushTypes = new Type[]{Type.FLOAT_TYPE};
		    	break;
		    case FCONST_1 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FCONST_1");
		    	nextPushTypes = new Type[]{Type.FLOAT_TYPE};
		    	break;
		    case FCONST_2 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FCONST_2");
		    	nextPushTypes = new Type[]{Type.FLOAT_TYPE};
		    	break;
		    case DCONST_0 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DCONST_0");
		    	nextPushTypes = new Type[]{Type.DOUBLE_TYPE};
		    	break;
		    case DCONST_1 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DCONST_1");
		    	nextPushTypes = new Type[]{Type.DOUBLE_TYPE};
		    	break;
		    case BIPUSH :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : BIPUSH");
		    	nextPushTypes = new Type[]{Type.INT_TYPE};
		    	break;
		    case SIPUSH :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : SIPUSH");
		    	nextPushTypes = new Type[]{Type.INT_TYPE};
		    	break;
		    case LDC :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LDC");
		    	if(nextPushTypes == null){
		    		throw new InternalError("please reference a type");
		    	}
		    	break;
		    case ILOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ILOAD");
		    	nextPushTypes = new Type[]{Type.INT_TYPE};
		    	break;
		    case LLOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LLOAD");
		    	nextPushTypes = new Type[]{Type.LONG_TYPE};
		    	break;
		    case FLOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FLOAD");
		    	nextPushTypes = new Type[]{Type.FLOAT_TYPE};
		    	break;
		    case DLOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DLOAD");
		    	nextPushTypes = new Type[]{Type.DOUBLE_TYPE};
		    	break;
		    case ALOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ALOAD");
		    	if(nextPushTypes == null){
		    		nextPushTypes = new Type[]{OBJECT_TYPE};
		    		//throw new InternalError("please reference a type");
		    	}
		    	break;
		    case IALOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IALOAD");
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	nextPushTypes = new Type[]{Type.INT_TYPE};
		    	break;
		    case LALOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LALOAD");
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	nextPushTypes = new Type[]{Type.LONG_TYPE};
		    	break;
		    case FALOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FALOAD");
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	nextPushTypes = new Type[]{Type.FLOAT_TYPE};
		    	break;
		    case DALOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DALOAD");
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	nextPushTypes = new Type[]{Type.DOUBLE_TYPE};
		    	break;
		    case AALOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : AALOAD");
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	top = stack.pop();
		    	Type nextEleType = Type.getType(top.getType().getDescriptor().substring(1));
		    	this.setNextPushTypes(nextEleType);
		    	break;
		    case BALOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : BALOAD");
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	nextPushTypes = new Type[]{Type.BYTE_TYPE};
		    	break;
		    case CALOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : CALOAD");
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	nextPushTypes = new Type[]{Type.CHAR_TYPE};
		    	break;
		    case SALOAD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : SALOAD");
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	nextPushTypes = new Type[]{Type.SHORT_TYPE};
		    	break;
		    case ISTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ISTORE");
		    	stack.pop();
		    	break;
		    case LSTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LSTORE");
		    	stack.pop();
		    	break;
		    case FSTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FSTORE");
		    	stack.pop();
		    	break;
		    case DSTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DSTORE");
		    	stack.pop();
		    	break;
		    case ASTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ASTORE");
		    	stack.pop();
		    	break;
		    case IASTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IASTORE");
		    	//pop value
		    	stack.pop();
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	break;
		    case LASTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LASTORE");
		    	//pop value
		    	stack.pop();
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	break;
		    case FASTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FASTORE");
		    	//pop value
		    	stack.pop();
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	break;
		    case DASTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DASTORE");
		    	//pop value
		    	stack.pop();
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	break;
		    case AASTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : AASTORE");
		    	//pop value
		    	stack.pop();
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	break;
		    case BASTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : BASTORE");
		    	//pop value
		    	stack.pop();
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	break;
		    case CASTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : CASTORE");
		    	//pop value
		    	stack.pop();
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	break;
		    case SASTORE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : SASTORE");
		    	//pop value
		    	stack.pop();
		    	//pop index
		    	stack.pop();
		    	//pop array reference
		    	stack.pop();
		    	break;	
		    case POP :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : POP");
		    	stack.pop();
		    	break;
		    case POP2 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : POP2");
		    	stack.pop(2);
		    	break;
		    case DUP :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DUP");
		    	top = stack.peek();
		    	if(top.getSize() == 1){
			    	setNextPushTypes(top.getType());
		    	}else{
		    		throw new InstructionException("two word value off the operand stack", DUP, (Stack)stack.clone());
		    	}
		    	break;
		    case DUP_X1 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DUP_X1");
		    	top = stack.peek();
		    	if(top.getSize() == 1){
			    	stack.insert(2, top);
		    	}else{
		    		throw new InstructionException("two word value off the operand stack", DUP_X1, (Stack)stack.clone());
		    	}
		    	break;
		    case DUP_X2 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DUP_X2");
		    	top = stack.peek();
		    	if(top.getSize() == 1){
			    	stack.insert(3, top);
		    	}else{
		    		throw new InstructionException("two word value off the operand stack", DUP_X2, (Stack)stack.clone());
		    	}
		    	break;
		    case DUP2 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DUP2");
		    	if(stack.peek().getSize() == 2){
		    		setNextPushTypes(stack.peek().getType());
		    	}else if(stack.peek().getSize() == 1
		    		  && stack.peek(1).getSize() == 1){
		    		setNextPushTypes(stack.peek(1).getType(), stack.peek().getType());
		    	}else{
		    		throw new InstructionException("cannot dup top two from stack", DUP2, (Stack)stack.clone());
		    	}
		    	break;
		    case DUP2_X1 :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DUP2_X1");
		    	if(stack.peek().getSize() == 2){
			    	stack.insert(2, stack.peek());
		    	}else if(stack.peek().getSize() == 1
		    		  && stack.peek(1).getSize() == 1){
		    		stack.insert(2, stack.peek(1));
		    		stack.insert(2, stack.peek());
		    	}else{
		    		throw new InstructionException("cannot dup top two from stack", DUP2_X1, (Stack)stack.clone());
		    	}
		    	break;
		    case DUP2_X2 :
		    	if(log.isDebugEnabled()){
					log.debug("Instruction : DUP2_X2");
		        }
		    	if(stack.peek().getSize() == 2){
			    	stack.insert(3, stack.peek());
		    	}else if(stack.peek().getSize() == 1
		    		  && stack.peek(1).getSize() == 1){
		    		stack.insert(3, stack.peek(1));
		    		stack.insert(3, stack.peek());
		    	}else{
		    		throw new InstructionException("cannot dup top two from stack", DUP2_X2, (Stack)stack.clone());
		    	}
		    	break;
		    case SWAP :
		    	if(log.isDebugEnabled()){
					log.debug("Instruction : SWAP");
		    	}
		    	top = stack.peek();
		    	stack.pop();
		    	stack.insert(1, top);
		    	break;
		    case IADD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IADD");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break;
		    case LADD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LADD");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case FADD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FADD");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.FLOAT_TYPE);
		    	break;
		    case DADD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DADD");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.DOUBLE_TYPE);
		    	break;
		    case ISUB :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ISUB");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break; 
		    case LSUB :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LSUB");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case FSUB :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FSUB");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.FLOAT_TYPE);
		    	break;
		    case DSUB :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DSUB");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.DOUBLE_TYPE);
		    	break;
		    case IMUL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IMUL");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break;
		    case LMUL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LMUL");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case FMUL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FMUL");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.FLOAT_TYPE);
		    	break;
		    case DMUL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DMUL");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.DOUBLE_TYPE);
		    	break;
		    case IDIV :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IDIV");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break;
		    case LDIV :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LDIV");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case FDIV :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FDIV");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.FLOAT_TYPE);
		    	break;
		    case DDIV :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DDIV");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.DOUBLE_TYPE);
		    	break;
		    case IREM :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IREM");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break;
		    case LREM :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LREM");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case FREM :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FREM");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.FLOAT_TYPE);
		    	break;
		    case DREM :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DREM");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.DOUBLE_TYPE);
		    	break;
		    case INEG :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : INEG");
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break;
		    case LNEG :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LNEG");
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case FNEG :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FNEG");
		    	stack.pop();
		    	setNextPushTypes(Type.FLOAT_TYPE);
		    	break;
		    case DNEG :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DNEG");
		    	stack.pop();
		    	setNextPushTypes(Type.DOUBLE_TYPE);
		    	break;
		    case ISHL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ISHL");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break;
		    case LSHL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LSHL");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case ISHR :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ISHR");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break;
		    case LSHR :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LSHR");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case IUSHR :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IUSHR");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break;
		    case LUSHR :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LUSHR");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case IAND :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IAND");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break;
		    case LAND :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LAND");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case IOR :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IOR");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break;
		    case LOR :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LOR");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case IXOR :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IXOR");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.INT_TYPE);
		    	break;
		    case LXOR :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LXOR");
		    	stack.pop();
		    	stack.pop();
		    	setNextPushTypes(Type.LONG_TYPE);
		    	break;
		    case IINC :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IINC");
		    	break;
		    case I2L :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : I2L");
		    	stack.pop();
		    	stack.push(Type.LONG_TYPE);
		    	break;
		    case I2F :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : I2F");
		    	stack.pop();
		    	stack.push(Type.FLOAT_TYPE);
		    	break;
		    case I2D :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : I2D");
		    	stack.pop();
		    	stack.push(Type.DOUBLE_TYPE);
		    	break;
		    case L2I :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : L2I");
		    	stack.pop();
		    	stack.push(Type.INT_TYPE);
		    	break;
		    case L2F :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : L2F");
		    	stack.pop();
		    	stack.push(Type.FLOAT_TYPE);
		    	break;
		    case L2D :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : L2D");
		    	stack.pop();
		    	stack.push(Type.DOUBLE_TYPE);
		    	break;
		    case F2I :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : F2I");
		    	stack.pop();
		    	stack.push(Type.INT_TYPE);
		    	break;
		    case F2L :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : F2L");
		    	stack.pop();
		    	stack.push(Type.INT_TYPE);
		    	break;
		    case F2D :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : F2D");
		    	stack.pop();
		    	stack.push(Type.DOUBLE_TYPE);
		    	break;
		    case D2I :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : D2I");
		    	stack.pop();
		    	stack.push(Type.INT_TYPE);
		    	break;
		    case D2L :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : D2L");
		    	stack.pop();
		    	stack.push(Type.LONG_TYPE);
		    	break;
		    case D2F :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : D2F");
		    	stack.pop();
		    	stack.push(Type.FLOAT_TYPE);
		    	break;
		    case I2B :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : I2B");
		    	stack.pop();
		    	stack.push(Type.BYTE_TYPE);
		    	break;
		    case I2C :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : I2C");
		    	stack.pop();
		    	stack.push(Type.CHAR_TYPE);
		    	break;
		    case I2S :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : I2S");
		    	stack.pop();
		    	stack.push(Type.SHORT_TYPE);
		    	break;
		    case LCMP :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LCMP");
		    	stack.pop();
		    	stack.pop();
		    	stack.push(Type.INT_TYPE);
		    	break;
		    case FCMPL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FCMPL");
		    	stack.pop();
		    	stack.pop();
		    	stack.push(Type.INT_TYPE);
		    	break;
		    case FCMPG :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FCMPG");
		    	stack.pop();
		    	stack.pop();
		    	stack.push(Type.INT_TYPE);
		    	break;
		    case DCMPL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DCMPL");
		    	stack.pop();
		    	stack.pop();
		    	stack.push(Type.INT_TYPE);
		    	break;
		    case DCMPG :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DCMPG");
		    	stack.pop();
		    	stack.pop();
		    	stack.push(Type.INT_TYPE);
		    	break;
		    case IFEQ :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IFEQ");
		    	stack.pop();
		    	break;
		    case IFNE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IFNE");
		    	stack.pop();
		    	break;
		    case IFLT :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IFLT");
		    	stack.pop();
		    	break;
		    case IFGE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IFGE");
		    	stack.pop();
		    	break;
		    case IFGT :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IFGT");
		    	stack.pop();
		    	break;
		    case IFLE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IFLE");
		    	stack.pop();
		    	break;
		    case IF_ICMPEQ :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IF_ICMPEQ");
		    	stack.pop();
		    	stack.pop();
		    	break;
		    case IF_ICMPNE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IF_ICMPNE");
		    	stack.pop();
		    	stack.pop();
		    	break;
		    case IF_ICMPLT :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IF_ICMPLT");
		    	stack.pop();
		    	stack.pop();
		    	break;
		    case IF_ICMPGE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IF_ICMPGE");
		    	stack.pop();
		    	stack.pop();
		    	break;
		    case IF_ICMPGT :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IF_ICMPGT");
		    	stack.pop();
		    	stack.pop();
		    	break;
		    case IF_ICMPLE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IF_ICMPLE");
		    	stack.pop();
		    	stack.pop();
		    	break;
		    case IF_ACMPEQ :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IF_ACMPEQ");
		    	stack.pop();
		    	stack.pop();
		    	break;
		    case IF_ACMPNE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IF_ACMPNE");
		    	stack.pop();
		    	stack.pop();
		    	break;
		    case GOTO :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : GOTO");
		    	break;
		    case JSR :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : JSR");
		    	break;
		    case RET :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : RET");
		    	break;
		    case TABLESWITCH :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : TABLESWITCH");
		    	break;
		    case LOOKUPSWITCH :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LOOKUPSWITCH");
		    	break;
		    case IRETURN :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IRETURN");
		    	stack.pop();
		    	break;
		    case LRETURN :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : LRETURN");
		    	stack.pop();
		    	break;
		    case FRETURN :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : FRETURN");
		    	stack.pop();
		    	break;
		    case DRETURN :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : DRETURN");
		    	stack.pop();
		    	break;
		    case ARETURN :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ARETURN");
		    	stack.pop();
		    	break;
		    case RETURN :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : RETURN");
		    	break;
		    case GETSTATIC :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : GETSTATIC");
		    	if(nextPushTypes == null){
		    		throw new InternalError("please reference a type");
		    	}
		    	break;
		    case PUTSTATIC :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : PUTSTATIC");
		    	stack.pop();
		    	break;
		    case GETFIELD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : GETFIELD");
		    	if(nextPushTypes == null){
		    		throw new InternalError("please reference a type");
		    	}
		    	break;
		    case PUTFIELD :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : PUTFIELD");
		    	stack.pop();
		    	stack.pop();
		    	break;
		    case INVOKEVIRTUAL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : INVOKEVIRTUAL");
		    	//pop arguments
		    	stack.pop(popNum);
		    	//pop object reference
		    	stack.pop();
		    	break;
		    case INVOKESPECIAL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : INVOKESPECIAL");
		    	//pop arguments
		    	stack.pop(popNum);
		    	//pop object reference
		    	stack.pop();
		    	break;
		    case INVOKESTATIC :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : INVOKESTATIC");
		    	//pop arguments
		    	stack.pop(popNum);
		    	break;
		    case INVOKEINTERFACE :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : INVOKEINTERFACE");
		    	//pop arguments
		    	stack.pop(popNum);
		    	//pop object reference
		    	stack.pop();
		    	break;
		    case INVOKEDYNAMIC :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : INVOKEDYNAMIC");
		    	//pop arguments
		    	stack.pop(popNum);
		    	//pop object reference
		    	stack.pop();
		    	break;
		    case NEW :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : NEW");
		    	break;
		    case NEWARRAY :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : NEWARRAY");
		    	//pop allocated dim number
		    	stack.pop();
		    	break;
		    case ANEWARRAY :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ANEWARRAY");
		    	//pop the size of array
		    	stack.pop();
		    	break;
		    case ARRAYLENGTH :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ARRAYLENGTH");
		    	setNextPushTypes(Type.INT_TYPE);
		    	//pop the reference of array
		    	stack.pop();
		    	break;
		    case ATHROW :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : ATHROW");
		    	//pop the exception reference
		    	stack.pop();
		    	break;
		    case CHECKCAST :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : CHECKCAST");
		    	//pop the raw object reference
		    	stack.pop();
		    	break;
		    case INSTANCEOF :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : INSTANCEOF");
		    	//pop the object reference
		    	stack.pop();
		    	break;
		    case MONITORENTER :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : MONITORENTER");
		    	stack.pop();
		    	break;
		    case MONITOREXIT :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : MONITOREXIT");
		    	stack.pop();
		    	break;
		    case MULTIANEWARRAY :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : MULTIANEWARRAY");
		    	stack.pop(popNum);
		    	break;
		    case IFNULL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IFNULL");
		    	stack.pop();
		    	break;
		    case IFNONNULL :
		    	if(log.isDebugEnabled())
					log.debug("Instruction : IFNONNULL");
		    	stack.pop();
		    	break;
		    default :
				throw new InternalError("cannot found instruction " + opcode);
	    }
		triggerPushStack();
		if(opcode != NOP){
	        stack.printState();
		}
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
        return mv.visitAnnotationDefault();
    }

	@Override
    public AnnotationVisitor visitAnnotation(
        final String desc,
        final boolean visible)
    {
        return mv.visitAnnotation(desc, visible);
    }

	@Override
    public AnnotationVisitor visitParameterAnnotation(
        final int parameter,
        final String desc,
        final boolean visible)
    {
        return mv.visitParameterAnnotation(parameter, desc, visible);
    }

	@Override
    public void visitAttribute(final Attribute attr) {
        mv.visitAttribute(attr);
    }

	@Override
    public void visitCode() {
        mv.visitCode();
    }

	@Override
    public void visitFrame(
        final int type,
        final int nLocal,
        final Object[] local,
        final int nStack,
        final Object[] stack)
    {
        mv.visitFrame(type, nLocal, local, nStack, stack);
    }

	
	@Override
    public void visitInsn(final int opcode) {
		stackLocalOperator(opcode);
        mv.visitInsn(opcode);
    }

	@Override
    public void visitIntInsn(final int opcode, final int operand) {
		if(opcode == NEWARRAY){
			Type arrayType = Type.getType("[" + getTypeByOperand(operand).getDescriptor());
			setNextPushTypes(arrayType);
		}
		stackLocalOperator(opcode);
        mv.visitIntInsn(opcode, operand);
    }

	@Override
    public void visitVarInsn(final int opcode, final int var) {
		stackLocalOperator(opcode);
        mv.visitVarInsn(opcode, var);
    }

	@Override
    public void visitTypeInsn(final int opcode, final String type) {
		if(opcode == INSTANCEOF){
			setNextPushTypes(Type.INT_TYPE);
		}else{
		    setNextPushTypes(Type.getObjectType(type));
		}
		stackLocalOperator(opcode);
        mv.visitTypeInsn(opcode, type);
    }

	@Override
    public void visitFieldInsn(
        final int opcode,
        final String owner,
        final String name,
        final String desc)
    {
        if(opcode == GETSTATIC || opcode == GETFIELD){
        	Type type = Type.getType(desc); 
		    this.setNextPushTypes(type);
		}
		stackLocalOperator(opcode);
        mv.visitFieldInsn(opcode, owner, name, desc);
    }

	@Override
    public void visitMethodInsn(
        final int opcode,
        final String owner,
        final String name,
        final String desc)
    {
		Type returnType = Type.getReturnType(desc);
		if(!Type.VOID_TYPE.equals(returnType)){
			setNextPushTypes(returnType);
		}
		//pop argument
		Type[] argTypes = Type.getArgumentTypes(desc);
		stackLocalOperator(opcode, argTypes.length);
        mv.visitMethodInsn(opcode, owner, name, desc);
    }

	@Override
    public void visitJumpInsn(final int opcode, final Label label) {
		stackLocalOperator(opcode);
        mv.visitJumpInsn(opcode, label);
    }

	@Override
    public void visitLabel(final Label label) {
        mv.visitLabel(label);
    }

	@Override
    public void visitLdcInsn(final Object cst) {
		Class<?> ctsCls = cst.getClass();
		if(ctsCls.equals(Type.class)){
			setNextPushTypes(AClass.CLASS_ACLASS.getType());
		}else if(ctsCls.equals(Integer.class)){
			setNextPushTypes(Type.INT_TYPE);
		}else if(ctsCls.equals(Float.class)){
			setNextPushTypes(Type.FLOAT_TYPE);
		}else if(ctsCls.equals(Long.class)){
			setNextPushTypes(Type.LONG_TYPE);
		}else if(ctsCls.equals(Double.class)){
			setNextPushTypes(Type.DOUBLE_TYPE);
		}else if(ctsCls.equals(String.class)){
			setNextPushTypes(AClass.STRING_ACLASS.getType());
		}
		stackLocalOperator(LDC);
        mv.visitLdcInsn(cst);
    }

	@Override
    public void visitIincInsn(final int var, final int increment) {
		stackLocalOperator(IINC);
        mv.visitIincInsn(var, increment);
    }

	@Override
    public void visitTableSwitchInsn(
        final int min,
        final int max,
        final Label dflt,
        final Label... labels)
    {
		stackLocalOperator(TABLESWITCH);
        mv.visitTableSwitchInsn(min, max, dflt, labels);
    }

	@Override
    public void visitLookupSwitchInsn(
        final Label dflt,
        final int[] keys,
        final Label[] labels)
    {
		stackLocalOperator(LOOKUPSWITCH);
        mv.visitLookupSwitchInsn(dflt, keys, labels);
    }

	@Override
    public void visitMultiANewArrayInsn(final String desc, final int dims) {
		setNextPushTypes(Type.getType(desc));
		stackLocalOperator(MULTIANEWARRAY, dims);
        mv.visitMultiANewArrayInsn(desc, dims);
    }

	@Override
    public void visitTryCatchBlock(
        final Label start,
        final Label end,
        final Label handler,
        final String type)
    {
        mv.visitTryCatchBlock(start, end, handler, type);
    }

	@Override
    public void visitLocalVariable(
        final String name,
        final String desc,
        final String signature,
        final Label start,
        final Label end,
        final int index)
    {
        mv.visitLocalVariable(name, desc, signature, start, end, index);
    }

	@Override
    public void visitLineNumber(final int line, final Label start) {
        mv.visitLineNumber(line, start);
    }

	@Override
    public void visitMaxs(final int maxStack, final int maxLocals) {
        mv.visitMaxs(maxStack, maxLocals);
    }

	@Override
    public void visitEnd() {
        mv.visitEnd();
    }
	
	private Type getTypeByOperand(int order){
		switch(order){
		    case T_BOOLEAN :
		    	return Type.BOOLEAN_TYPE;
		    case T_CHAR :
		    	return Type.CHAR_TYPE;
		    case T_BYTE :
		    	return Type.BYTE_TYPE;
		    case T_SHORT :
		    	return Type.SHORT_TYPE;
		    case T_INT :
		    	return Type.INT_TYPE;
		    case T_FLOAT :
		    	return Type.FLOAT_TYPE;
		    case T_LONG :
		    	return Type.LONG_TYPE;
		    case T_DOUBLE :
		    	return Type.DOUBLE_TYPE;
		    default :
		    	throw new InternalError();
		}
	}
	
}
