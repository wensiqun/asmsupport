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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.exception.InstructionException;
import cn.wensiqun.asmsupport.core.utils.asm.MethodAdapter;
import cn.wensiqun.asmsupport.core.utils.memory.Stack;
import cn.wensiqun.asmsupport.core.utils.memory.Stackable;
import cn.wensiqun.asmsupport.org.objectweb.asm.AnnotationVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Attribute;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.MethodVisitor;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

public class StackLocalMethodVisitor extends MethodAdapter implements Opcodes {

    private static final Log LOG = LogFactory.getLog(StackLocalMethodVisitor.class);
    private static final Type OBJECT_TYPE = Type.getType(Object.class);

    private Stack stack;
    private Type[] nextPushTypes;

    public Stack getStack() {
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

    public StackLocalMethodVisitor(MethodVisitor mv, Stack stack) {
        super(mv);
        this.stack = stack;
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
        Stackable top;
        switch (opcode) {
        case NOP:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : NOP");
            }
            break;
        case ACONST_NULL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ACONST_NULL");
            }
            if (nextPushTypes == null) {
                nextPushTypes = new Type[] { OBJECT_TYPE };
            }
            break;
        case ICONST_M1:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ICONST_M1");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_0:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ICONST_0");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_1:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ICONST_1");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_2:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ICONST_2");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_3:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ICONST_3");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_4:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ICONST_4");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case ICONST_5:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ICONST_5");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case LCONST_0:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LCONST_0");
            }
            nextPushTypes = new Type[] { Type.LONG_TYPE };
            break;
        case LCONST_1:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LCONST_1");
            }
            nextPushTypes = new Type[] { Type.LONG_TYPE };
            break;
        case FCONST_0:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FCONST_0");
            }
            nextPushTypes = new Type[] { Type.FLOAT_TYPE };
            break;
        case FCONST_1:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FCONST_1");
            }
            nextPushTypes = new Type[] { Type.FLOAT_TYPE };
            break;
        case FCONST_2:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FCONST_2");
            }
            nextPushTypes = new Type[] { Type.FLOAT_TYPE };
            break;
        case DCONST_0:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DCONST_0");
            }
            nextPushTypes = new Type[] { Type.DOUBLE_TYPE };
            break;
        case DCONST_1:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DCONST_1");
            }
            nextPushTypes = new Type[] { Type.DOUBLE_TYPE };
            break;
        case BIPUSH:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : BIPUSH");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case SIPUSH:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : SIPUSH");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case LDC:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LDC");
            }
            if (nextPushTypes == null) {
                throw new InternalError("please reference a type");
            }
            break;
        case ILOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ILOAD");
            }
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case LLOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LLOAD");
            }
            nextPushTypes = new Type[] { Type.LONG_TYPE };
            break;
        case FLOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FLOAD");
            }
            nextPushTypes = new Type[] { Type.FLOAT_TYPE };
            break;
        case DLOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DLOAD");
            }
            nextPushTypes = new Type[] { Type.DOUBLE_TYPE };
            break;
        case ALOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ALOAD");
            }
            if (nextPushTypes == null) {
                nextPushTypes = new Type[] { OBJECT_TYPE };
                // throw new InternalError("please reference a type");
            }
            break;
        case IALOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.INT_TYPE };
            break;
        case LALOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.LONG_TYPE };
            break;
        case FALOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.FLOAT_TYPE };
            break;
        case DALOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.DOUBLE_TYPE };
            break;
        case AALOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : AALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            top = stack.pop();
            Type nextEleType = Type.getType(top.getType().getDescriptor().substring(1));
            this.setNextPushTypes(nextEleType);
            break;
        case BALOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : BALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.BYTE_TYPE };
            break;
        case CALOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : CALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.CHAR_TYPE };
            break;
        case SALOAD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : SALOAD");
            }
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            nextPushTypes = new Type[] { Type.SHORT_TYPE };
            break;
        case ISTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ISTORE");
            }
            stack.pop();
            break;
        case LSTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LSTORE");
            }
            stack.pop();
            break;
        case FSTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FSTORE");
            }
            stack.pop();
            break;
        case DSTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DSTORE");
            }
            stack.pop();
            break;
        case ASTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ASTORE");
            }
            stack.pop();
            break;
        case IASTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case LASTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case FASTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case DASTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case AASTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : AASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case BASTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : BASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case CASTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : CASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case SASTORE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : SASTORE");
            }
            // pop value
            stack.pop();
            // pop index
            stack.pop();
            // pop array reference
            stack.pop();
            break;
        case POP:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : POP");
            }
            stack.pop();
            break;
        case POP2:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : POP2");
            }
            stack.pop(2);
            break;
        case DUP:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DUP");
            }
            top = stack.peek();
            if (top.getSize() == 1) {
                setNextPushTypes(top.getType());
            } else {
                throw new InstructionException("Two word value off the operand stack", DUP, (Stack) stack.clone());
            }
            break;
        case DUP_X1:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DUP_X1");
            }
            top = stack.peek();
            if (top.getSize() == 1) {
                stack.insert(2, top);
            } else {
                throw new InstructionException("two word value off the operand stack", DUP_X1, (Stack) stack.clone());
            }
            break;
        case DUP_X2:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DUP_X2");
            }
            top = stack.peek();
            if (top.getSize() == 1) {
                stack.insert(3, top);
            } else {
                throw new InstructionException("two word value off the operand stack", DUP_X2, (Stack) stack.clone());
            }
            break;
        case DUP2:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DUP2");
            }
            if (stack.peek().getSize() == 2) {
                setNextPushTypes(stack.peek().getType());
            } else if (stack.peek().getSize() == 1 && stack.peek(1).getSize() == 1) {
                setNextPushTypes(stack.peek(1).getType(), stack.peek().getType());
            } else {
                throw new InstructionException("cannot dup top two from stack", DUP2, (Stack) stack.clone());
            }
            break;
        case DUP2_X1:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DUP2_X1");
            }
            if (stack.peek().getSize() == 2) {
                stack.insert(2, stack.peek());
            } else if (stack.peek().getSize() == 1 && stack.peek(1).getSize() == 1) {
                stack.insert(2, stack.peek(1));
                stack.insert(2, stack.peek());
            } else {
                throw new InstructionException("cannot dup top two from stack", DUP2_X1, (Stack) stack.clone());
            }
            break;
        case DUP2_X2:
            if (LOG.isDebugEnabled()) {
                {
                    LOG.debug("Instruction : DUP2_X2");
                }
            }
            if (stack.peek().getSize() == 2) {
                stack.insert(3, stack.peek());
            } else if (stack.peek().getSize() == 1 && stack.peek(1).getSize() == 1) {
                stack.insert(3, stack.peek(1));
                stack.insert(3, stack.peek());
            } else {
                throw new InstructionException("cannot dup top two from stack", DUP2_X2, (Stack) stack.clone());
            }
            break;
        case SWAP:
            if (LOG.isDebugEnabled()) {
                {
                    LOG.debug("Instruction : SWAP");
                }
            }
            top = stack.peek();
            stack.pop();
            stack.insert(1, top);
            break;
        case IADD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IADD");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LADD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LADD");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FADD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FADD");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DADD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DADD");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case ISUB:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ISUB");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LSUB:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LSUB");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FSUB:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FSUB");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DSUB:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DSUB");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case IMUL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IMUL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LMUL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LMUL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FMUL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FMUL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DMUL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DMUL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case IDIV:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IDIV");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LDIV:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LDIV");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FDIV:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FDIV");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DDIV:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DDIV");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case IREM:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IREM");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LREM:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LREM");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FREM:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FREM");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DREM:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DREM");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case INEG:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : INEG");
            }
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LNEG:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LNEG");
            }
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case FNEG:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FNEG");
            }
            stack.pop();
            setNextPushTypes(Type.FLOAT_TYPE);
            break;
        case DNEG:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DNEG");
            }
            stack.pop();
            setNextPushTypes(Type.DOUBLE_TYPE);
            break;
        case ISHL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ISHL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LSHL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LSHL");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case ISHR:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ISHR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LSHR:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LSHR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case IUSHR:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IUSHR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LUSHR:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LUSHR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case IAND:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IAND");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LAND:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LAND");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case IOR:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IOR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LOR:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LOR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case IXOR:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IXOR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.INT_TYPE);
            break;
        case LXOR:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LXOR");
            }
            stack.pop();
            stack.pop();
            setNextPushTypes(Type.LONG_TYPE);
            break;
        case IINC:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IINC");
            }
            break;
        case I2L:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : I2L");
            }
            stack.pop();
            stack.push(Type.LONG_TYPE);
            break;
        case I2F:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : I2F");
            }
            stack.pop();
            stack.push(Type.FLOAT_TYPE);
            break;
        case I2D:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : I2D");
            }
            stack.pop();
            stack.push(Type.DOUBLE_TYPE);
            break;
        case L2I:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : L2I");
            }
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case L2F:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : L2F");
            }
            stack.pop();
            stack.push(Type.FLOAT_TYPE);
            break;
        case L2D:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : L2D");
            }
            stack.pop();
            stack.push(Type.DOUBLE_TYPE);
            break;
        case F2I:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : F2I");
            }
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case F2L:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : F2L");
            }
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case F2D:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : F2D");
            }
            stack.pop();
            stack.push(Type.DOUBLE_TYPE);
            break;
        case D2I:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : D2I");
            }
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case D2L:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : D2L");
            }
            stack.pop();
            stack.push(Type.LONG_TYPE);
            break;
        case D2F:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : D2F");
            }
            stack.pop();
            stack.push(Type.FLOAT_TYPE);
            break;
        case I2B:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : I2B");
            }
            stack.pop();
            stack.push(Type.BYTE_TYPE);
            break;
        case I2C:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : I2C");
            }
            stack.pop();
            stack.push(Type.CHAR_TYPE);
            break;
        case I2S:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : I2S");
            }
            stack.pop();
            stack.push(Type.SHORT_TYPE);
            break;
        case LCMP:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LCMP");
            }
            stack.pop();
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case FCMPL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FCMPL");
            }
            stack.pop();
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case FCMPG:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FCMPG");
            }
            stack.pop();
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case DCMPL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DCMPL");
            }
            stack.pop();
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case DCMPG:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DCMPG");
            }
            stack.pop();
            stack.pop();
            stack.push(Type.INT_TYPE);
            break;
        case IFEQ:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IFEQ");
            }
            stack.pop();
            break;
        case IFNE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IFNE");
            }
            stack.pop();
            break;
        case IFLT:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IFLT");
            }
            stack.pop();
            break;
        case IFGE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IFGE");
            }
            stack.pop();
            break;
        case IFGT:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IFGT");
            }
            stack.pop();
            break;
        case IFLE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IFLE");
            }
            stack.pop();
            break;
        case IF_ICMPEQ:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IF_ICMPEQ");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ICMPNE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IF_ICMPNE");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ICMPLT:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IF_ICMPLT");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ICMPGE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IF_ICMPGE");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ICMPGT:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IF_ICMPGT");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ICMPLE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IF_ICMPLE");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ACMPEQ:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IF_ACMPEQ");
            }
            stack.pop();
            stack.pop();
            break;
        case IF_ACMPNE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IF_ACMPNE");
            }
            stack.pop();
            stack.pop();
            break;
        case GOTO:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : GOTO");
            }
            break;
        case JSR:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : JSR");
            }
            break;
        case RET:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : RET");
            }
            break;
        case TABLESWITCH:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : TABLESWITCH");
            }
            break;
        case LOOKUPSWITCH:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LOOKUPSWITCH");
            }
            break;
        case IRETURN:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IRETURN");
            }
            stack.pop();
            break;
        case LRETURN:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : LRETURN");
            }
            stack.pop();
            break;
        case FRETURN:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : FRETURN");
            }
            stack.pop();
            break;
        case DRETURN:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : DRETURN");
            }
            stack.pop();
            break;
        case ARETURN:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ARETURN");
            }
            stack.pop();
            break;
        case RETURN:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : RETURN");
            }
            break;
        case GETSTATIC:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : GETSTATIC");
            }
            if (nextPushTypes == null) {
                throw new InternalError("please reference a type");
            }
            break;
        case PUTSTATIC:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : PUTSTATIC");
            }
            stack.pop();
            break;
        case GETFIELD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : GETFIELD");
            }
            if (nextPushTypes == null) {
                throw new InternalError("please reference a type");
            }
            break;
        case PUTFIELD:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : PUTFIELD");
            }
            stack.pop();
            stack.pop();
            break;
        case INVOKEVIRTUAL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : INVOKEVIRTUAL");
            }
            // pop arguments
            stack.pop(popNum);
            // pop object reference
            stack.pop();
            break;
        case INVOKESPECIAL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : INVOKESPECIAL");
            }
            // pop arguments
            stack.pop(popNum);
            // pop object reference
            stack.pop();
            break;
        case INVOKESTATIC:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : INVOKESTATIC");
            }
            // pop arguments
            stack.pop(popNum);
            break;
        case INVOKEINTERFACE:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : INVOKEINTERFACE");
            }
            // pop arguments
            stack.pop(popNum);
            // pop object reference
            stack.pop();
            break;
        case INVOKEDYNAMIC:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : INVOKEDYNAMIC");
            }
            // pop arguments
            stack.pop(popNum);
            // pop object reference
            stack.pop();
            break;
        case NEW:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : NEW");
            }
            break;
        case NEWARRAY:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : NEWARRAY");
            }
            // pop allocated dim number
            stack.pop();
            break;
        case ANEWARRAY:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ANEWARRAY");
            }
            // pop the size of array
            stack.pop();
            break;
        case ARRAYLENGTH:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ARRAYLENGTH");
            }
            setNextPushTypes(Type.INT_TYPE);
            // pop the reference of array
            stack.pop();
            break;
        case ATHROW:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : ATHROW");
            }
            // pop the exception reference
            stack.pop();
            break;
        case CHECKCAST:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : CHECKCAST");
            }
            // pop the raw object reference
            stack.pop();
            break;
        case INSTANCEOF:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : INSTANCEOF");
            }
            // pop the object reference
            stack.pop();
            break;
        case MONITORENTER:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : MONITORENTER");
            }
            stack.pop();
            break;
        case MONITOREXIT:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : MONITOREXIT");
            }
            stack.pop();
            break;
        case MULTIANEWARRAY:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : MULTIANEWARRAY");
            }
            stack.pop(popNum);
            break;
        case IFNULL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IFNULL");
            }
            stack.pop();
            break;
        case IFNONNULL:
            if (LOG.isDebugEnabled()) {
                LOG.debug("Instruction : IFNONNULL");
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
    public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc) {
        Type returnType = Type.getReturnType(desc);
        if (!Type.VOID_TYPE.equals(returnType)) {
            setNextPushTypes(returnType);
        }
        // pop argument
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
        if (ctsCls.equals(Type.class)) {
            setNextPushTypes(AClass.CLASS_ACLASS.getType());
        } else if (ctsCls.equals(Integer.class)) {
            setNextPushTypes(Type.INT_TYPE);
        } else if (ctsCls.equals(Float.class)) {
            setNextPushTypes(Type.FLOAT_TYPE);
        } else if (ctsCls.equals(Long.class)) {
            setNextPushTypes(Type.LONG_TYPE);
        } else if (ctsCls.equals(Double.class)) {
            setNextPushTypes(Type.DOUBLE_TYPE);
        } else if (ctsCls.equals(String.class)) {
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
