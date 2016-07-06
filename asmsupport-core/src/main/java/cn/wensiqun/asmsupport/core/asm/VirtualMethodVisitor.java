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
package cn.wensiqun.asmsupport.core.asm;

import cn.wensiqun.asmsupport.core.exception.InstructionException;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.core.utils.memory.OperandStack;
import cn.wensiqun.asmsupport.core.utils.memory.OperandType;
import cn.wensiqun.asmsupport.org.objectweb.asm.*;
import cn.wensiqun.asmsupport.utils.ASConstants;

public class VirtualMethodVisitor extends MethodVisitor implements Opcodes {

    private static final Log LOG = LogFactory.getLog(VirtualMethodVisitor.class);
    private static final Type OBJECT_TYPE = Type.getType(Object.class);

    private OperandStack stack;
    private Type[] nextPushTypes;

    /**
     * Get the operand stack
     *
     * @return
     */
    OperandStack getOperandStack() {
        return stack;
    }

    private Type[] triggerPushStack() {
        if (nextPushTypes == null) {
            return null;
        }
        stack.push(nextPushTypes);
        Type[] pushed = nextPushTypes;
        nextPushTypes = null;
        return pushed;
    }

    void setNextPushTypes(Type... types) {
        this.nextPushTypes = types;
    }

    VirtualMethodVisitor(MethodVisitor mv) {
        super(ASConstants.ASM_VERSION, mv);
        this.stack = new OperandStack();
    }

    private void stackLocalOperator(int opcode) {
        stackLocalOperator(opcode, -1);
    }

    /**
     * Stack : ACONST_NULL, ALOAD,
     * 
     * @param opcode
     */
    private void stackLocalOperator(int opcode, int popNum) {
        OperandType top;
        switch (opcode) {
        case NOP:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : NOP");
            }
            break;
        case ACONST_NULL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ACONST_NULL");
            }
            if (nextPushTypes == null) {
                nextPushTypes = new Type[] { OBJECT_TYPE };
            }
            break;
        case ICONST_M1:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ICONST_M1");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_0:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ICONST_0");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_1:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ICONST_1");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_2:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ICONST_2");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_3:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ICONST_3");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_4:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ICONST_4");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_5:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ICONST_5");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case LCONST_0:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LCONST_0");
            }
            nextPushTypes = new Type[] { Type.LONG_TYPE };
            break;
        case LCONST_1:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LCONST_1");
            }
            nextPushTypes = new Type[] { Type.LONG_TYPE };
            break;
        case FCONST_0:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FCONST_0");
            }
            nextPushTypes = new Type[] { Type.FLOAT_TYPE };
            break;
        case FCONST_1:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FCONST_1");
            }
            nextPushTypes = new Type[] { Type.FLOAT_TYPE };
            break;
        case FCONST_2:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FCONST_2");
            }
            nextPushTypes = new Type[] { Type.FLOAT_TYPE };
            break;
        case DCONST_0:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DCONST_0");
            }
            nextPushTypes = new Type[] { Type.DOUBLE_TYPE };
            break;
        case DCONST_1:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DCONST_1");
            }
            nextPushTypes = new Type[] { Type.DOUBLE_TYPE };
            break;
        case BIPUSH:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : BIPUSH");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case SIPUSH:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : SIPUSH");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case LDC:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LDC");
            }
            if (nextPushTypes == null) {
                throw new InternalError("please reference a type");
            }
            break;
        case ILOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ILOAD");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case LLOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LLOAD");
            }
            nextPushTypes = new Type[] { Type.LONG_TYPE };
            break;
        case FLOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FLOAD");
            }
            nextPushTypes = new Type[] { Type.FLOAT_TYPE };
            break;
        case DLOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DLOAD");
            }
            nextPushTypes = new Type[] { Type.DOUBLE_TYPE };
            break;
        case ALOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ALOAD");
            }
            if (nextPushTypes == null) {
                nextPushTypes = new Type[] { OBJECT_TYPE };
                // throw new InternalError("please reference a type");
            }
            break;
        case IALOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case LALOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.LONG_TYPE };
            break;
        case FALOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.FLOAT_TYPE };
            break;
        case DALOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.DOUBLE_TYPE };
            break;
        case AALOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : AALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            top = stack.pop();
            Type nextEleType = Type.getType(top.getType().getDescriptor().substring(1));
            this.setNextPushTypes(nextEleType);
            break;
        case BALOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : BALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.BYTE_TYPE };
            break;
        case CALOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : CALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.CHAR_TYPE };
            break;
        case SALOAD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : SALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.SHORT_TYPE };
            break;
        case ISTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ISTORE");
            }
            stack.pop();
            break;
        case LSTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LSTORE");
            }
            stack.pop();
            break;
        case FSTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FSTORE");
            }
            stack.pop();
            break;
        case DSTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DSTORE");
            }
            stack.pop();
            break;
        case ASTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ASTORE");
            }
            stack.pop();
            break;
        case IASTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case LASTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case FASTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case DASTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case AASTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : AASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case BASTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : BASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case CASTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : CASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case SASTORE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : SASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case POP:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : POP");
            }
            stack.pop();
            break;
        case POP2:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : POP2");
            }
            stack.pop(2);
            break;
        case DUP:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DUP");
            }
            top = stack.peek();
            if (top.getSize() == 1) {
                setNextPushTypes(top.getType());
            } else {
                throw new InstructionException("Two word value off the operand stack", DUP, (OperandStack) stack.clone());
            }
            break;
        case DUP_X1:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DUP_X1");
            }
            top = stack.peek();
            if (top.getSize() == 1) {
                stack.insert(2, top);
            } else {
                throw new InstructionException("two word value off the operand stack", DUP_X1, (OperandStack) stack.clone());
            }
            break;
        case DUP_X2:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DUP_X2");
            }
            top = stack.peek();
            if (top.getSize() == 1) {
                stack.insert(3, top);
            } else {
                throw new InstructionException("two word value off the operand stack", DUP_X2, (OperandStack) stack.clone());
            }
            break;
        case DUP2:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DUP2");
            }
            if (stack.peek().getSize() == 2) {
                setNextPushTypes(stack.peek().getType());
            } else if (stack.peek().getSize() == 1 && stack.peek(1).getSize() == 1) {
                setNextPushTypes(stack.peek(1).getType(), stack.peek().getType());
            } else {
                throw new InstructionException("cannot dup top two from stack", DUP2, (OperandStack) stack.clone());
            }
            break;
        case DUP2_X1:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DUP2_X1");
            }
            if (stack.peek().getSize() == 2) {
                stack.insert(2, stack.peek());
            } else if (stack.peek().getSize() == 1 && stack.peek(1).getSize() == 1) {
                stack.insert(2, stack.peek(1));
                stack.insert(2, stack.peek());
            } else {
                throw new InstructionException("cannot dup top two from stack", DUP2_X1, (OperandStack) stack.clone());
            }
            break;
        case DUP2_X2:
            if (LOG.isPrintEnabled()) {
                {
                    LOG.print("Instruction : DUP2_X2");
                }
            }
            if (stack.peek().getSize() == 2) {
                stack.insert(3, stack.peek());
            } else if (stack.peek().getSize() == 1 && stack.peek(1).getSize() == 1) {
                stack.insert(3, stack.peek(1));
                stack.insert(3, stack.peek());
            } else {
                throw new InstructionException("cannot dup top two from stack", DUP2_X2, (OperandStack) stack.clone());
            }
            break;
        case SWAP:
            if (LOG.isPrintEnabled()) {
                {
                    LOG.print("Instruction : SWAP");
                }
            }
            top = stack.peek();
            stack.pop();
            stack.insert(1, top);
            break;
        case IADD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IADD");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LADD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LADD");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FADD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FADD");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DADD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DADD");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case ISUB:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ISUB");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LSUB:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LSUB");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FSUB:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FSUB");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DSUB:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DSUB");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case IMUL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IMUL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LMUL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LMUL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FMUL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FMUL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DMUL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DMUL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case IDIV:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IDIV");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LDIV:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LDIV");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FDIV:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FDIV");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DDIV:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DDIV");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case IREM:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IREM");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LREM:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LREM");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FREM:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FREM");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DREM:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DREM");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case INEG:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : INEG");
            }
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LNEG:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LNEG");
            }
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FNEG:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FNEG");
            }
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DNEG:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DNEG");
            }
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case ISHL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ISHL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LSHL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LSHL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case ISHR:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ISHR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LSHR:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LSHR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case IUSHR:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IUSHR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LUSHR:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LUSHR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case IAND:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IAND");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LAND:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LAND");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case IOR:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IOR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LOR:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LOR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case IXOR:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IXOR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LXOR:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LXOR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case IINC:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IINC");
            }
            break;
        case I2L:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : I2L");
            }
            stack.pop();
            stack.push(Type.LONG_TYPE);
            break;
        case I2F:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : I2F");
            }
            stack.pop();
            stack.push(Type.FLOAT_TYPE);
            break;
        case I2D:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : I2D");
            }
            stack.pop();
            stack.push(Type.DOUBLE_TYPE);
            break;
        case L2I:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : L2I");
            }
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case L2F:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : L2F");
            }
            stack.pop();
            stack.push(Type.FLOAT_TYPE);
            break;
        case L2D:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : L2D");
            }
            stack.pop();
            stack.push(Type.DOUBLE_TYPE);
            break;
        case F2I:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : F2I");
            }
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case F2L:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : F2L");
            }
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case F2D:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : F2D");
            }
            stack.pop();
            stack.push(Type.DOUBLE_TYPE);
            break;
        case D2I:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : D2I");
            }
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case D2L:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : D2L");
            }
            stack.pop();
            stack.push(Type.LONG_TYPE);
            break;
        case D2F:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : D2F");
            }
            stack.pop();
            stack.push(Type.FLOAT_TYPE);
            break;
        case I2B:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : I2B");
            }
            stack.pop();
            stack.push(Type.BYTE_TYPE);
            break;
        case I2C:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : I2C");
            }
            stack.pop();
            stack.push(Type.CHAR_TYPE);
            break;
        case I2S:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : I2S");
            }
            stack.pop();
            stack.push(Type.SHORT_TYPE);
            break;
        case LCMP:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LCMP");
            }
            stack.pop();
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case FCMPL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FCMPL");
            }
            stack.pop();
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case FCMPG:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FCMPG");
            }
            stack.pop();
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case DCMPL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DCMPL");
            }
            stack.pop();
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case DCMPG:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DCMPG");
            }
            stack.pop();
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case IFEQ:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IFEQ");
            }
            stack.pop();
            break;
        case IFNE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IFNE");
            }
            stack.pop();
            break;
        case IFLT:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IFLT");
            }
            stack.pop();
            break;
        case IFGE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IFGE");
            }
            stack.pop();
            break;
        case IFGT:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IFGT");
            }
            stack.pop();
            break;
        case IFLE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IFLE");
            }
            stack.pop();
            break;
        case IF_ICMPEQ:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IF_ICMPEQ");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ICMPNE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IF_ICMPNE");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ICMPLT:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IF_ICMPLT");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ICMPGE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IF_ICMPGE");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ICMPGT:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IF_ICMPGT");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ICMPLE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IF_ICMPLE");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ACMPEQ:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IF_ACMPEQ");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ACMPNE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IF_ACMPNE");
            }
            stack.pop();
            stack.pop();
            break;
        case GOTO:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : GOTO");
            }
            break;
        case JSR:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : JSR");
            }
            break;
        case RET:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : RET");
            }
            break;
        case TABLESWITCH:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : TABLESWITCH");
            }
            break;
        case LOOKUPSWITCH:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LOOKUPSWITCH");
            }
            break;
        case IRETURN:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IRETURN");
            }
            stack.pop();
            break;
        case LRETURN:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : LRETURN");
            }
            stack.pop();
            break;
        case FRETURN:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : FRETURN");
            }
            stack.pop();
            break;
        case DRETURN:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : DRETURN");
            }
            stack.pop();
            break;
        case ARETURN:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ARETURN");
            }
            stack.pop();
            break;
        case RETURN:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : RETURN");
            }
            break;
        case GETSTATIC:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : GETSTATIC");
            }
            if (nextPushTypes == null) {
                throw new InternalError("please reference a type");
            }
            break;
        case PUTSTATIC:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : PUTSTATIC");
            }
            stack.pop();
            break;
        case GETFIELD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : GETFIELD");
            }
            if (nextPushTypes == null) {
                throw new InternalError("please reference a type");
            }
            break;
        case PUTFIELD:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : PUTFIELD");
            }
            stack.pop();
            stack.pop();
            break;
        case INVOKEVIRTUAL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : INVOKEVIRTUAL");
            }
            // pop arguments
            stack.pop(popNum);
            // pop object reference
            stack.pop();
            break;
        case INVOKESPECIAL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : INVOKESPECIAL");
            }
            // pop arguments
            stack.pop(popNum);
            // pop object reference
            stack.pop();
            break;
        case INVOKESTATIC:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : INVOKESTATIC");
            }
            // pop arguments
            stack.pop(popNum);
            break;
        case INVOKEINTERFACE:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : INVOKEINTERFACE");
            }
            // pop arguments
            stack.pop(popNum);
            // pop object reference
            stack.pop();
            break;
        case INVOKEDYNAMIC:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : INVOKEDYNAMIC");
            }
            // pop arguments
            stack.pop(popNum);
            // pop object reference
            stack.pop();
            break;
        case NEW:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : NEW");
            }
            break;
        case NEWARRAY:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : NEWARRAY");
            }
            // pop allocated dim number
            stack.pop();
            break;
        case ANEWARRAY:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ANEWARRAY");
            }
            // pop the size of array
            stack.pop();
            break;
        case ARRAYLENGTH:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ARRAYLENGTH");
            }
            setNextPushTypes(Type.INT_TYPE);
            // pop the reference of array
            stack.pop();
            break;
        case ATHROW:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : ATHROW");
            }
            // pop the exception reference
            stack.pop();
            break;
        case CHECKCAST:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : CHECKCAST");
            }
            // pop the raw object reference
            stack.pop();
            break;
        case INSTANCEOF:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : INSTANCEOF");
            }
            // pop the object reference
            stack.pop();
            break;
        case MONITORENTER:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : MONITORENTER");
            }
            stack.pop();
            break;
        case MONITOREXIT:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : MONITOREXIT");
            }
            stack.pop();
            break;
        case MULTIANEWARRAY:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : MULTIANEWARRAY");
            }
            stack.pop(popNum);
            break;
        case IFNULL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IFNULL");
            }
            stack.pop();
            break;
        case IFNONNULL:
            if (LOG.isPrintEnabled()) {
                LOG.print("Instruction : IFNONNULL");
            }
            stack.pop();
            break;
        default:
            throw new InternalError("cannot found instruction " + opcode);
        }
        triggerPushStack();
        if (opcode != NOP) {
            stack.printState();
        }
    }

    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        return mv.visitAnnotationDefault();
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        return mv.visitAnnotation(desc, visible);
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(final int parameter, final String desc, final boolean visible) {
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
    public void visitFrame(final int type, final int nLocal, final Object[] local, final int nStack,
            final Object[] stack) {
        mv.visitFrame(type, nLocal, local, nStack, stack);
    }

    @Override
    public void visitInsn(final int opcode) {
        stackLocalOperator(opcode);
        mv.visitInsn(opcode);
    }

    @Override
    public void visitIntInsn(final int opcode, final int operand) {
        if (opcode == NEWARRAY) {
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
        if (opcode == INSTANCEOF) {
            setNextPushTypes(Type.INT_TYPE);
        } else {
            setNextPushTypes(Type.getObjectType(type));
        }
        stackLocalOperator(opcode);
        mv.visitTypeInsn(opcode, type);
    }

    @Override
    public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
        if (opcode == GETSTATIC || opcode == GETFIELD) {
            Type type = Type.getType(desc);
            this.setNextPushTypes(type);
        }
        stackLocalOperator(opcode);
        mv.visitFieldInsn(opcode, owner, name, desc);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        Type returnType = Type.getReturnType(desc);
        if (!Type.VOID_TYPE.equals(returnType)) {
            setNextPushTypes(returnType);
        }
        // pop argument
        Type[] argTypes = Type.getArgumentTypes(desc);
        stackLocalOperator(opcode, argTypes.length);
        mv.visitMethodInsn(opcode, owner, name, desc, itf);
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
        if (ctsCls.equals(Type.class)) {
            setNextPushTypes(Type.getType(Class.class));
        } else if (ctsCls.equals(Integer.class)) {
            setNextPushTypes(Type.INT_TYPE);
        } else if (ctsCls.equals(Float.class)) {
            setNextPushTypes(Type.FLOAT_TYPE);
        } else if (ctsCls.equals(Long.class)) {
            setNextPushTypes(Type.LONG_TYPE);
        } else if (ctsCls.equals(Double.class)) {
            setNextPushTypes(Type.DOUBLE_TYPE);
        } else if (ctsCls.equals(String.class)) {
            setNextPushTypes(Type.getType(String.class));
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
    public void visitTableSwitchInsn(final int min, final int max, final Label dflt, final Label... labels) {
        stackLocalOperator(TABLESWITCH);
        mv.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(final Label dflt, final int[] keys, final Label[] labels) {
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
    public void visitTryCatchBlock(final Label start, final Label end, final Label handler, final String type) {
        mv.visitTryCatchBlock(start, end, handler, type);
    }

    @Override
    public void visitLocalVariable(final String name, final String desc, final String signature, final Label start,
            final Label end, final int index) {
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

}
