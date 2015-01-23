/**
 * 
 */
package cn.wensiqun.asmsupport.core.asm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.memory.LocalVariables;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 字节码操作的帮助类。 这里只需要操作栈。而本地变量的操作已经由LcoalVariables和ScopeLogicVariable封装完成了。
 * 
 * @author 温斯群(Joe Wen)
 */
public abstract class InstructionHelper {

    private static Log log = LogFactory.getLog(InstructionHelper.class);    
    
    private static final String CLDESC = "Ljava/lang/Class;";
    
    /**是否自动装箱和拆箱以及转换类型 */
    private boolean castAndbox = true;
    
    private StackLocalMethodVisitor mv;
    private AMethod method;
    /*protected Stack stack;*/
    protected LocalVariables locals;

    public InstructionHelper(MethodVisitor mv, AMethod method) {
        super();
        this.mv = new StackLocalMethodVisitor(mv, method.getStack());
        this.method = method;
        this.locals = this.method.getLocals();
    }
    
    /**
     * Test use
     */
    InstructionHelper(){}

    public AMethod getMethod() {
        return method;
    }

    public StackLocalMethodVisitor getMv() {
        return mv;
    }
    
    public void setMv(StackLocalMethodVisitor mv){
        this.mv = mv;
    }

    /*public Stack getStack() {
        return null;
    }*/

    // ------------------------------------------------------------------------
    // Instructions to push constants on the stack
    // ------------------------------------------------------------------------

    private void pushInt(final int value) {
        if (value >= -1 && value <= 5) {
            getMv().visitInsn(Opcodes.ICONST_0 + value);
        } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            getMv().visitIntInsn(Opcodes.BIPUSH, value);
        } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            getMv().visitIntInsn(Opcodes.SIPUSH, value);
        } else {
            getMv().visitLdcInsn(new Integer(value));
        }
    }

    /**
     * Generates the instruction to push the given value on the stack.
     * 
     * @param value
     *            the value to be pushed on the stack.
     */
    public void push(final boolean value) {
        pushInt(value ? 1 : 0);
    }

    /**
     * Generates the instruction to push the given value on the stack.
     * 
     * @param value
     *            the value to be pushed on the stack.
     */
    public void push(final int value) {
        pushInt(value);
    }

    /**
     * Generates the instruction to push the given value on the stack.
     * 
     * @param b
     */
    public void push(final byte value) {
        pushInt(value);
    }

    /**
     * Generates the instruction to push the given value on the stack.
     * 
     * @param b
     */
    public void push(final short value) {
        pushInt(value);
    }

    /**
     * Generates the instruction to push the given value on the stack.
     * 
     * @param b
     */
    public void push(final char value) {
        pushInt(value);
    }

    /**
     * Generates the instruction to push the given value on the stack.
     * 
     * @param value
     *            the value to be pushed on the stack.
     */
    public void push(final long value) {
        if (value == 0L || value == 1L) {
            getMv().visitInsn(Opcodes.LCONST_0 + (int) value);
        } else {
            getMv().visitLdcInsn(new Long(value));
        }
    }

    /**
     * Generates the instruction to push the given value on the stack.
     * 
     * @param value
     *            the value to be pushed on the stack.
     */
    public void push(final float value) {
        int bits = Float.floatToIntBits(value);
        if (bits == 0L || bits == 0x3f800000 || bits == 0x40000000) { // 0..2
            getMv().visitInsn(Opcodes.FCONST_0 + (int) value);
        } else {
            getMv().visitLdcInsn(new Float(value));
        }
    }

    /**
     * Generates the instruction to push the given value on the stack.
     * 
     * @param value
     *            the value to be pushed on the stack.
     */
    public void push(final double value) {
        long bits = Double.doubleToLongBits(value);
        if (bits == 0L || bits == 0x3ff0000000000000L) { // +0.0d and 1.0d
            getMv().visitInsn(Opcodes.DCONST_0 + (int) value);
        } else {
            getMv().visitLdcInsn(new Double(value));
        }
    }

    /**
     * Generates the instruction to push the given value on the stack.
     * 
     * @param value
     *            the value to be pushed on the stack. May be <tt>null</tt>.
     */
    public void push(final String value) {
        if (value == null) {
        	push(AClass.STRING_ACLASS.getType());
        } else {
            getMv().visitLdcInsn(value);
        }
    }

    /**
     * push null
     */
    public void push(Type type) {
    	getMv().setNextPushTypes(type);
        getMv().visitInsn(Opcodes.ACONST_NULL);
    }    
    


    /**
     * Generates the instruction to push the given value on the stack.
     * 
     * @param value the value to be pushed on the stack.
     */
    public void pushClass(final Type value) {
        if (value == null) {
            mv.visitInsn(Opcodes.ACONST_NULL);
        } else {
            switch (value.getSort()) {
                case Type.BOOLEAN:
                    mv.visitFieldInsn(Opcodes.GETSTATIC,
                            "java/lang/Boolean",
                            "TYPE",
                            CLDESC);
                    break;
                case Type.CHAR:
                    mv.visitFieldInsn(Opcodes.GETSTATIC,
                            "java/lang/Character",
                            "TYPE",
                            CLDESC);
                    break;
                case Type.BYTE:
                    mv.visitFieldInsn(Opcodes.GETSTATIC,
                            "java/lang/Byte",
                            "TYPE",
                            CLDESC);
                    break;
                case Type.SHORT:
                    mv.visitFieldInsn(Opcodes.GETSTATIC,
                            "java/lang/Short",
                            "TYPE",
                            CLDESC);
                    break;
                case Type.INT:
                    mv.visitFieldInsn(Opcodes.GETSTATIC,
                            "java/lang/Integer",
                            "TYPE",
                            CLDESC);
                    break;
                case Type.FLOAT:
                    mv.visitFieldInsn(Opcodes.GETSTATIC,
                            "java/lang/Float",
                            "TYPE",
                            CLDESC);
                    break;
                case Type.LONG:
                    mv.visitFieldInsn(Opcodes.GETSTATIC,
                            "java/lang/Long",
                            "TYPE",
                            CLDESC);
                    break;
                case Type.DOUBLE:
                    mv.visitFieldInsn(Opcodes.GETSTATIC,
                            "java/lang/Double",
                            "TYPE",
                            CLDESC);
                    break;
                default:
                    mv.visitLdcInsn(value);
            }
        }
    }
    
    /**
     * Generates the instructions to cast a numerical value from one type to
     * another.
     * 
     * @param from the type of the top stack value
     * @param to the type into which this value must be cast.
     */
    public void cast(final Type from, final Type to) {
        if(!castAndbox){
            return;
        }
        
        if (from != to) {
            if (from == Type.BOOLEAN_TYPE || to == Type.BOOLEAN_TYPE) {
                throw new IllegalArgumentException("canot cast from boolean or to boolean type!");
            }
            
            if (from == Type.DOUBLE_TYPE) {
                if (to == Type.FLOAT_TYPE) {
                    mv.visitInsn(Opcodes.D2F);
                } else if (to == Type.LONG_TYPE) {
                    mv.visitInsn(Opcodes.D2L);
                } else {
                    mv.visitInsn(Opcodes.D2I);
                    cast(Type.INT_TYPE, to);
                }
            } else if (from == Type.FLOAT_TYPE) {
                if (to == Type.DOUBLE_TYPE) {
                    mv.visitInsn(Opcodes.F2D);
                } else if (to == Type.LONG_TYPE) {
                    mv.visitInsn(Opcodes.F2L);
                } else {
                    mv.visitInsn(Opcodes.F2I);
                    cast(Type.INT_TYPE, to);
                }
            } else if (from == Type.LONG_TYPE) {
                if (to == Type.DOUBLE_TYPE) {
                    mv.visitInsn(Opcodes.L2D);
                } else if (to == Type.FLOAT_TYPE) {
                    mv.visitInsn(Opcodes.L2F);
                } else {
                    mv.visitInsn(Opcodes.L2I);
                    cast(Type.INT_TYPE, to);
                }
            } else { //if ( from == Type.BYTE_TYPE || from == Type.SHORT_TYPE  || from == Type.CHAR_TYPE || from == Type.INT_TYPE ) {
                if (to == Type.BYTE_TYPE) {
                    mv.visitInsn(Opcodes.I2B);
                } else if (to == Type.CHAR_TYPE) {
                    mv.visitInsn(Opcodes.I2C);
                } else if (to == Type.DOUBLE_TYPE) {
                    mv.visitInsn(Opcodes.I2D);
                } else if (to == Type.FLOAT_TYPE) {
                    mv.visitInsn(Opcodes.I2F);
                } else if (to == Type.LONG_TYPE) {
                    mv.visitInsn(Opcodes.I2L);
                } else if (to == Type.SHORT_TYPE) {
                    mv.visitInsn(Opcodes.I2S);
                }
            } 
        }
    }    

    /**
     * Generates the instruction to check that the top stack value is of the
     * given type.
     * 
     * @param type a class or interface type.
     */
    public void checkCast(final Type type) {
        if (!type.equals(AClass.OBJECT_ACLASS.getType())) {
            typeInsn(Opcodes.CHECKCAST, type);
        }
    }
    
    //*******************************************************************************************//
    //                                Box and unbox                                              //
    //*******************************************************************************************//    
    
    public static Type getBoxedType(final Type type) {
        switch (type.getSort()) {
            case Type.BYTE:
                return AClass.BYTE_WRAP_ACLASS.getType();
            case Type.BOOLEAN:
                return AClass.BOOLEAN_WRAP_ACLASS.getType();
            case Type.SHORT:
                return AClass.SHORT_WRAP_ACLASS.getType();
            case Type.CHAR:
                return AClass.CHARACTER_WRAP_ACLASS.getType();
            case Type.INT:
                return AClass.INTEGER_WRAP_ACLASS.getType();
            case Type.FLOAT:
                return AClass.FLOAT_WRAP_ACLASS.getType();
            case Type.LONG:
                return AClass.LONG_WRAP_ACLASS.getType();
            case Type.DOUBLE:
                return AClass.DOUBLE_WRAP_ACLASS.getType();
        }
        return type;
    }
    
    public static Type getUnBoxedType(final Type type){
        if(type.equals(AClass.BYTE_WRAP_ACLASS.getType())){
            return Type.BYTE_TYPE;
        }else if(type.equals(AClass.BOOLEAN_WRAP_ACLASS.getType())){
            return Type.BOOLEAN_TYPE;
        }else if(type.equals(AClass.SHORT_WRAP_ACLASS.getType())){
            return Type.SHORT_TYPE;
        }else if(type.equals(AClass.CHARACTER_WRAP_ACLASS.getType())){
            return Type.CHAR_TYPE;
        }else if(type.equals(AClass.INTEGER_WRAP_ACLASS.getType())){
            return Type.INT_TYPE;
        }else if(type.equals(AClass.FLOAT_WRAP_ACLASS.getType())){
            return Type.FLOAT_TYPE;
        }else if(type.equals(AClass.LONG_WRAP_ACLASS.getType())){
            return Type.LONG_TYPE;
        }else if(type.equals(AClass.DOUBLE_WRAP_ACLASS.getType())){
            return Type.DOUBLE_TYPE;
        }
        return type;
    }
    
    /**
     * Generates the instructions to box the top stack value. This value is
     * replaced by its boxed equivalent on top of the stack.
     * 
     * @param type the type of the top stack value.
     */
    public void box(final Type type) {
        if(!castAndbox){
            return;
        }
        
        if (type.getSort() == Type.OBJECT || type.getSort() == Type.ARRAY || type.getSort() == Type.VOID) {
            return;
        }
        if (type == Type.VOID_TYPE) {
            push((String) null);
        } else {
            valueOf(type);
            /*Type boxed = getBoxedType(type);
            newInstance(boxed);
            if (type.getSize() == 2) {
                // Pp -> Ppo -> oPpo -> ooPpo -> ooPp -> o
                dupX2();
                dupX2();
                pop();
            } else {
                // p -> po -> opo -> oop -> o
                dupX1();
                swap();
            }
            invokeConstructor(boxed,
                    new Type[] { type });*/
        }
    }

    /**
     * Generates the instructions to box the top stack value using Java 5's
     * valueOf() method. This value is replaced by its boxed equivalent on top
     * of the stack.
     * 
     * @param type the type of the top stack value.
     * @author Prashant Deva
     */
    public void valueOf(final Type type) {
        if (type.getSort() == Type.OBJECT || type.getSort() == Type.ARRAY) {
            return;
        }
        if (type == Type.VOID_TYPE) {
            push((String) null);
        } else {
            Type boxed = getBoxedType(type);
            invokeStatic(boxed, "valueOf", boxed, new Type[] { type });
        }
    }
    
    /**
     * Generates the instructions to unbox the top stack value. This value is
     * replaced by its unboxed equivalent on top of the stack.
     * 
     * @param type the type of the top stack value.
     */
    public void unbox(final Type type) {
        if(!castAndbox){
            return;
        }
        
        if(type.getSort() < Type.OBJECT){
            return;
        }
        Type primitiveType = getUnBoxedType(type);
        String methodName = null;
        switch (primitiveType.getSort()) {
            case Type.VOID:
                return;
            case Type.CHAR:
                methodName = "cahrValue";
                break;
            case Type.BOOLEAN:
                methodName = "booleanValue";
                break;
            case Type.DOUBLE:
                methodName = "doubleValue";
                break;
            case Type.FLOAT:
                methodName = "floatValue";
                break;
            case Type.LONG:
                methodName = "longValue";
                break;
            case Type.INT:
                methodName = "intValue";
                break;
            case Type.SHORT:
                methodName = "shortValue";
                break;
            case Type.BYTE:
                methodName = "byteValue";
                break;
        }
        if(methodName != null){
            invokeVirtual(type, methodName, primitiveType, new Type[0]);
        }
    }    
    
    /**
     * Marks the current code position with the given label.
     * 
     * @param label
     *            a label.
     */
    public void mark(final Label label) {
        getMv().visitLabel(label);
    }

    /**
     * don't do any thing
     */
    public void nop() {
        getMv().visitInsn(Opcodes.NOP);
    }

    /**
     * Generates the instruction to push a local variable on the stack.
     * 
     * @param type
     *            the type of the local variable to be loaded.
     * @param index
     *            an index in the frame's local variables array.
     */
    public void loadInsn(final Type type, final int index) {
    	int opcode = type.getOpcode(Opcodes.ILOAD);
    	if(opcode == Opcodes.ALOAD){
        	getMv().setNextPushTypes(type);
    	}
        getMv().visitVarInsn(opcode, index);
    }

    /**
     * Generates the instruction to load 'this' on the stack.
     */
    public void loadThis() {
    	getMv().setNextPushTypes(method.getMethodMeta().getOwner().getType());
        getMv().visitVarInsn(Opcodes.ALOAD, 0);
    }

    /**
     * Generates the instruction to store the top stack value in a local
     * variable.
     * 
     * @param type
     *            the type of the local variable to be stored.
     * @param index
     *            an index in the frame's local variables array.
     */
    public void storeInsn(final LocalVariable var) {
        locals.setCursor(var.getScopeLogicVar());
        locals.printState();
        getMv().visitVarInsn(var.getVariableMeta().getDeclareClass().getType().getOpcode(Opcodes.ISTORE), 
                var.getScopeLogicVar().getInitStartPos());
    }
    
    /**
     * Generates a SWAP instruction.
     */
    public void swap() {
        mv.visitInsn(Opcodes.SWAP);
    }    

    /**
     * Generates a DUP instruction.
     */
    public void dup() {
        getMv().visitInsn(Opcodes.DUP);
    }
    
    /**
     * Generates a DUP_X1 instruction.
     */
    public void dupX1() {
        mv.visitInsn(Opcodes.DUP_X1);
    }

    /**
     * Generates a DUP_X2 instruction.
     */
    public void dupX2() {
        mv.visitInsn(Opcodes.DUP_X2);
    }  

    /**
     * Generates a DUP2 instruction.
     */
    public void dup2() {
        getMv().visitInsn(Opcodes.DUP2);
    }
    
    /**
     * 
     * @param type
     */
    public void dup(Type type)
    {
        int sort = type.getSort();
        if(sort > 6 && sort < 10)
        {
            dup2();
        }
        else
        {
            dup();
        }
    }
    
    // ------------------------------------------------------------------------
    // Instructions to create objects and arrays
    // ------------------------------------------------------------------------

    /**
     * Generates a type dependent instruction.
     * 
     * @param opcode
     *            the instruction's opcode.
     * @param type
     *            the instruction's operand.
     */
    private void typeInsn(final int opcode, final Type type) {
        getMv().visitTypeInsn(opcode, type.getInternalName());
    }

    /**
     * Generates the instruction to create a new object.
     * 
     * @param type
     *            the class of the object to be created.
     */
    public void newInstance(final Type type) {
        typeInsn(Opcodes.NEW, type);
    }

    // ------------------------------------------------------------------------
    // Instructions to invoke methods
    // ------------------------------------------------------------------------

    /**
     * /** Generates an invoke method instruction.
     * 
     * @param opcode
     * @param type
     * @param name
     * @param returnType
     * @param arguTypes
     */
    private void invokeInsn(final int opcode, final Type type,
            final String name, final Type returnType, final Type[] arguTypes) {
        String owner = type.getSort() == Type.ARRAY ? type.getDescriptor() : type.getInternalName();
        getMv().visitMethodInsn(opcode, owner, name,
                Type.getMethodDescriptor(returnType, arguTypes));
    }

    /**
     * Generates the instruction to invoke a normal method.
     * 
     * @param owner
     * @param name
     * @param returnType
     * @param arguTypes
     */
    public void invokeVirtual(final Type owner, final String name,
            final Type returnType, final Type[] arguTypes) {
        invokeInsn(Opcodes.INVOKEVIRTUAL, owner, name, returnType, arguTypes);
    }

    /**
     * Generates the instruction to invoke a constructor.
     * 
     * @param ownerType
     *            the class in which the constructor is defined.
     * @param method
     *            the constructor to be invoked.
     */
    public void invokeConstructor(final Type ownerType, final Type[] argTypes) {
        invokeInsn(Opcodes.INVOKESPECIAL, ownerType, "<init>", Type.VOID_TYPE,
                argTypes);
    }
    
    /**
     * 调用父类方法
     * @param ownerType
     * @param name
     * @param returnType
     * @param argTypes
     */
    public void invokeSuperMethod(final Type ownerType, final String name,final Type returnType, final Type[] argTypes){
        invokeInsn(Opcodes.INVOKESPECIAL, ownerType, name, returnType, argTypes);
    }

    /**
     * Generates the instruction to invoke a static method.
     * 
     * @param owner
     * @param name
     * @param returnType
     * @param arguTypes
     */
    public void invokeStatic(final Type owner, final String name,
            final Type returnType, final Type[] arguTypes) {
        invokeInsn(Opcodes.INVOKESTATIC, owner, name, returnType, arguTypes);
    }

    /**
     * Generates the instruction to invoke an interface method.
     * 
     * @param owner
     * @param name
     * @param returnType
     * @param arguTypes
     */
    public void invokeInterface(final Type owner, final String name,
            final Type returnType, final Type[] arguTypes) {
        invokeInsn(Opcodes.INVOKEINTERFACE, owner, name, returnType, arguTypes);
    }

    /**
     * Generates a get field or set field instruction.
     * 
     * @param opcode the instruction's opcode.
     * @param ownerType the class in which the field is defined.
     * @param name the name of the field.
     * @param fieldType the type of the field.
     */
    private void fieldInsn(
        final int opcode,
        final Type ownerType,
        final String name,
        final Type fieldType)
    {
        getMv().visitFieldInsn(opcode, ownerType.getInternalName(), name,  fieldType.getDescriptor());
    }
    
    /**
     * Generates the instruction to push the value of a static field on the
     * stack.
     * 
     * @param owner the class in which the field is defined.
     * @param name the name of the field.
     * @param type the type of the field.
     */
    public void getStatic(final Type owner, final String name, final Type type)
    {
    	fieldInsn(Opcodes.GETSTATIC, owner, name, type);
    }

    /**
     * Generates the instruction to store the top stack value in a static field.
     * 
     * @param owner the class in which the field is defined.
     * @param name the name of the field.
     * @param type the type of the field.
     */
    public void putStatic(final Type owner, final String name, final Type type)
    {
        fieldInsn(Opcodes.PUTSTATIC, owner, name, type);
    }

    /**
     * Generates the instruction to push the value of a non static field on the
     * stack.
     * 
     * @param owner the class in which the field is defined.
     * @param name the name of the field.
     * @param type the type of the field.
     */
    public void getField(final Type owner, final String name, final Type type) {
        fieldInsn(Opcodes.GETFIELD, owner, name, type);
    }

    /**
     * Generates the instruction to store the top stack value in a non static
     * field.
     * 
     * @param owner the class in which the field is defined.
     * @param name the name of the field.
     * @param type the type of the field.
     */
    public void putField(final Type owner, final String name, final Type type) {
        fieldInsn(Opcodes.PUTFIELD, owner, name, type);
    }
    
    /**
     * 
     * @param gv
     */
    public void commonPutField(ExplicitVariable var) {
    	if(var instanceof LocalVariable)
    	{
    		storeInsn((LocalVariable)var);
    	}
    	else if(var instanceof GlobalVariable)
    	{
    		GlobalVariable gv = (GlobalVariable) var;
    		if(gv.getStaticOwner() != null){
                putStatic(gv.getStaticOwner().getType(), 
                    gv.getVariableMeta().getName(),
                    gv.getVariableMeta().getDeclareClass().getType());
            }else if(gv.getVariableOwner() != null){
                putField(gv.getVariableOwner().getVariableMeta().getDeclareClass().getType(), 
                    gv.getVariableMeta().getName(),
                    gv.getVariableMeta().getDeclareClass().getType());
            }
    	}
    	
    	
    }
    
    //*******************************************************************************************//
    //                                Arithmetic Operator                                        //
    //*******************************************************************************************//
    
    private void arithmetic(int opcode){
        mv.visitInsn(opcode);
    }      
    
    /**
     * generated the instruction to add top two stack value
     * 
     * @param type which add type
     */
    public void add(Type type){
        arithmetic(type.getOpcode(Opcodes.IADD));
    }
    
    /**
     * generated the instruction to sub top two stack value
     * 
     * @param type which add type
     */
    public void sub(Type type){
        arithmetic(type.getOpcode(Opcodes.ISUB));
    }
    
    /**
     * generated the instruction to mul top two stack value
     * 
     * @param type which add type
     */
    public void mul(Type type){
        arithmetic(type.getOpcode(Opcodes.IMUL));
    }

    
    /**
     * generated the instruction to mul top two stack value
     * 
     * @param type which add type
     */
    public void div(Type type){
        arithmetic(type.getOpcode(Opcodes.IDIV));
    }
    
    /**
     * generated the instruction to rem top two stack value
     * @param type
     */
    public void rem(Type type){
        arithmetic(type.getOpcode(Opcodes.IREM));
    }
    
    /**
     * 
     * @param type
     */
    public void neg(Type type){
        mv.visitInsn(type.getOpcode(Opcodes.INEG));
    }
    
    /**
     * Generates the instruction to increment the given local variable.
     * 
     * @param local the local variable to be incremented.
     * @param amount the amount by which the local variable must be incremented.
     */
    public void iinc(final int local, final int amount) {
        mv.visitIincInsn(local, amount);
    }
    
    //*******************************************************************************************//
    //                                       Bit Operator                                        //
    //*******************************************************************************************//
    
    /**
     * 
     * @param type
     */
    public void inverts(final Type type){
        if(type.equals(AClass.LONG_ACLASS)){
            push(-1L);
        }else{
            push(-1);
        }
        mv.visitInsn(type.getOpcode(Opcodes.IXOR));
    }
    
    public void bitAnd(final Type type){
        mv.visitInsn(type.getOpcode(Opcodes.IAND));
    }
    
    public void bitOr(final Type type){
        mv.visitInsn(type.getOpcode(Opcodes.IOR));
    }
    
    public void bitXor(final Type type){
        mv.visitInsn(type.getOpcode(Opcodes.IXOR));
    }

    public void leftShift(final Type type){
        mv.visitInsn(type.getOpcode(Opcodes.ISHL));
    }

    public void rightShift(final Type type){
        mv.visitInsn(type.getOpcode(Opcodes.ISHR));
    }

    public void unsignedRightShift(final Type type){
        mv.visitInsn(type.getOpcode(Opcodes.IUSHR));
    }
    
    //*******************************************************************************************//
    //                                Array Operator                                             //
    //*******************************************************************************************//
    
    
    /**
     * Generates the instruction to create a new array.
     * 
     * @param type the type of the array elements.
     */
    public void newArray(final Type type) {
        int typ;
        switch (type.getSort()) {
            case Type.BOOLEAN:
                typ = Opcodes.T_BOOLEAN;
                break;
            case Type.CHAR:
                typ = Opcodes.T_CHAR;
                break;
            case Type.BYTE:
                typ = Opcodes.T_BYTE;
                break;
            case Type.SHORT:
                typ = Opcodes.T_SHORT;
                break;
            case Type.INT:
                typ = Opcodes.T_INT;
                break;
            case Type.FLOAT:
                typ = Opcodes.T_FLOAT;
                break;
            case Type.LONG:
                typ = Opcodes.T_LONG;
                break;
            case Type.DOUBLE:
                typ = Opcodes.T_DOUBLE;
                break;
            default:
                typeInsn(Opcodes.ANEWARRAY, type);
                return;
        }
        mv.visitIntInsn(Opcodes.NEWARRAY, typ);
    }
    
    /**
     * 
     * @param type
     * @param dims
     */
    public void multiANewArrayInsn(Type type, int dims){
        mv.visitMultiANewArrayInsn(type.getDescriptor(), dims);
    }
    
    /**
     * Generates the instruction to load an element from an array.
     * 
     * @param type the type of the array element to be loaded.
     */
    public void arrayLoad(final Type type) {
        mv.visitInsn(type.getOpcode(Opcodes.IALOAD));
    }

    /**
     * Generates the instruction to store an element in an array.
     * 
     * @param type the type of the array element to be stored.
     */
    public void arrayStore(final Type type) {
        mv.visitInsn(type.getOpcode(Opcodes.IASTORE));
    }
    
    /**
     * Generates the instruction to compute the length of an array.
     */
    public void arrayLength() {
        mv.visitInsn(Opcodes.ARRAYLENGTH);
    }

    /**
     * Constant for the {@link #ifCmp ifCmp} method.
     */
    public static final int EQ = Opcodes.IFEQ;

    /**
     * Constant for the {@link #ifCmp ifCmp} method.
     */
    public static final int NE = Opcodes.IFNE;

    /**
     * Constant for the {@link #ifCmp ifCmp} method.
     */
    public static final int LT = Opcodes.IFLT;

    /**
     * Constant for the {@link #ifCmp ifCmp} method.
     */
    public static final int GE = Opcodes.IFGE;

    /**
     * Constant for the {@link #ifCmp ifCmp} method.
     */
    public static final int GT = Opcodes.IFGT;

    /**
     * Constant for the {@link #ifCmp ifCmp} method.
     */
    public static final int LE = Opcodes.IFLE;
    
    public int getReverseCmp(int opcode)
    {
        if(opcode == Opcodes.IFNONNULL)
            return Opcodes.IFNULL;
        
        if(opcode == Opcodes.IFNULL)
            return Opcodes.IFNONNULL;
        
        if(opcode % 2 == 0)
            return opcode - 1;
        else
            return opcode + 1;
    }
    
    /**
     * Generates the instructions to jump to a label based on the comparison of
     * the top two stack values.
     * 
     * @param type the type of the top two stack values.
     * @param mode how these values must be compared. One of EQ, NE, LT, GE, GT,
     *        LE.
     * @param label where to jump if the comparison result is <tt>true</tt>.
     */
    public void ifCmp(final Type type, final int mode, final Label label) {
        switch (type.getSort()) {
            case Type.LONG:
                mv.visitInsn(Opcodes.LCMP);
                break;
            case Type.DOUBLE:
                mv.visitInsn(mode == GE || mode == GT ? Opcodes.DCMPG : Opcodes.DCMPL);
                break;
            case Type.FLOAT:
                mv.visitInsn(mode == GE || mode == GT ? Opcodes.FCMPG : Opcodes.FCMPL);
                break;
            case Type.ARRAY:
            case Type.OBJECT:
                switch (mode) {
                    case EQ:
                        mv.visitJumpInsn(Opcodes.IF_ACMPEQ, label);
                        return;
                    case NE:
                        mv.visitJumpInsn(Opcodes.IF_ACMPNE, label);
                        return;
                }
                throw new IllegalArgumentException("Bad comparison for type "
                        + type);
            default:
                int intOp = -1;
                switch (mode) {
                    case EQ:
                        intOp = Opcodes.IF_ICMPEQ;
                        break;
                    case NE:
                        intOp = Opcodes.IF_ICMPNE;
                        break;
                    case GE:
                        intOp = Opcodes.IF_ICMPGE;
                        break;
                    case LT:
                        intOp = Opcodes.IF_ICMPLT;
                        break;
                    case LE:
                        intOp = Opcodes.IF_ICMPLE;
                        break;
                    case GT:
                        intOp = Opcodes.IF_ICMPGT;
                        break;
                }
                mv.visitJumpInsn(intOp, label);
                return;
        }
        mv.visitJumpInsn(mode, label);
    }

    /**
     * Generates the instructions to jump to a label based on the comparison of
     * the top integer stack value with zero.
     * 
     * @param mode how these values must be compared. One of EQ, NE, LT, GE, GT,
     *        LE.
     * @param label where to jump if the comparison result is <tt>true</tt>.
     */
    public void ifZCmp(final int mode, final Label label) {
        mv.visitJumpInsn(mode, label);
    }

    /**
     * Generates the instruction to jump to the given label if the top stack
     * value is null.
     * 
     * @param label where to jump if the condition is <tt>true</tt>.
     */
    public void ifNull(final Label label) {
        mv.visitJumpInsn(Opcodes.IFNULL, label);
    }

    /**
     * Generates the instruction to jump to the given label if the top stack
     * value is not null.
     * 
     * @param label where to jump if the condition is <tt>true</tt>.
     */
    public void ifNonNull(final Label label) {
        mv.visitJumpInsn(Opcodes.IFNONNULL, label);
    }

    /**
     * Generates the instruction to jump to the given label.
     * 
     * @param label where to jump if the condition is <tt>true</tt>.
     */
    public void goTo(final Label label) {
        mv.visitJumpInsn(Opcodes.GOTO, label);
    }
    
    /**
     * Generates the instruction to test if the top stack value is of the given
     * type.
     * 
     * @param type a class or interface type.
     */
    public void instanceOf(final Type type) {
        typeInsn(Opcodes.INSTANCEOF, type);
    }
    
    public void tryCatchBlock(Label tryStart, Label tryEnd, Label catchStart , Type exceptionType){
        mv.visitTryCatchBlock(tryStart, tryEnd, catchStart, exceptionType == null ? null : exceptionType.getInternalName());
    }
    
    /**
     * Generates the instruction to throw an exception.
     */
    public void throwException() {
        mv.visitInsn(Opcodes.ATHROW);
    }
    
    
    /**
     * Generates the instruction to get the monitor of the top stack value.
     */
    public void monitorEnter() {
    	if(log.isDebugEnabled()){
    		log.debug("execute monitorenter");
    	}
        mv.visitInsn(Opcodes.MONITORENTER);
    }
    
    /**
     * Generates the instruction to release the monitor of the top stack value.
     */
    public void monitorExit() {
    	if(log.isDebugEnabled()){
    		log.debug("execute monitorexit");
    	}
        mv.visitInsn(Opcodes.MONITOREXIT);
    }
    
    public void pop() {
        pop(1);
    }

    public void pop(int time) {
        while (time > 0) {
            getMv().visitInsn(Opcodes.POP);
            time--;
        }
    }

    public void returnInsn() {
        getMv().visitInsn(Opcodes.RETURN);
    }

    public void returnInsn(Type type) {
        getMv().visitInsn(type.getOpcode(Opcodes.IRETURN));
    }

    public void declarationVariable(final String name, final String desc,
            final String signature, final Label start, final Label end,
            final int index) {
        getMv().visitLocalVariable(name, desc, null, start, end, index);
    }

    public void maxs(int stack, int locals) {
        log.debug("Method : " + method.getMethodMeta().getMethodString() + " Maxs(" + "stack:"
                + stack + " locals:" + locals + ")");
        getMv().visitMaxs(stack, locals);
    }

    public void endMethod() {
        getMv().visitEnd();
    }

}
