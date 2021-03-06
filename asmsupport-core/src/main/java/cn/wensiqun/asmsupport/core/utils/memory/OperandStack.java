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
package cn.wensiqun.asmsupport.core.utils.memory;

import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 
 * Represent a stack of stack frame in jvm.
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class OperandStack implements Printable, Cloneable {

    private static final Log LOG = LogFactory.getLog(OperandStack.class);
    
    private Stack<OperandType> stack;
    
    /** Stack current size */
    private int size;
    
    /** Stack max size */
    private int maxSize;
    
    /** How many value in the stack */
    private int valueNumber;
    
    private PrintHelper ph;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    public OperandStack() {
        stack = new java.util.Stack<>();
        ph = new PrintHelper();
    }

    /**
     * Returns the top item off of this stack without removing it.
     * 
     * @return the top item on the stack
     * @throws EmptyStackException
     *             if the stack is empty
     */
    public OperandType peek() throws EmptyStackException {
        return stack.peek();
    }

    /**
     * Returns the n'th item down (zero-relative) from the top of this stack
     * without removing it.
     * 
     * @param n
     *            the number of items down to go
     * @return the n'th item on the stack, zero relative
     * @throws EmptyStackException
     *             if there are not enough items on the stack to satisfy this
     *             request
     */
    public OperandType peek(int n) throws EmptyStackException {
        synchronized (stack) {
            int m = (stack.size() - n) - 1;
            if (m < 0) {
                throw new EmptyStackException();
            } else {
                return stack.get(m);
            }
        }
    }

    public OperandType pop() throws EmptyStackException {
        OperandType s = peek();
        pop(1);
        return s;
    }

    public void pop(int times) throws EmptyStackException {
        while (times > 0) {
            OperandType t = stack.pop();
            size -= t.getSize();
            times--;
            valueNumber--;
        }
    }

    public void push(OperandType item) {
        valueNumber++;
        stack.push(item);
        size += item.getSize();
        if (maxSize < size) {
            maxSize = size;
        }
    }

    public void push(Type item) {
        OperandType stype = new OperandType(item);
        push(stype);
    }

    public void push(Type... items) {
        if (items != null) {
            for (Type item : items) {
                push(item);
            }
        }
    }

    public void insert(int pos, OperandType item) {
        valueNumber++;
        stack.add(getReallyPosition(pos), item);
        size += item.getSize();
        if (maxSize < size) {
            maxSize = size;
        }
    }

    private int getReallyPosition(int stackPosition) {
        // ++后 表示占了多少个栈空间
        stackPosition++;

        // 从开始到当前遍历到的位置总共占了多少空间
        int totalNum = 0;
        int index = 0;
        for (; index < stack.size(); index++) {
            totalNum += peek(index).getSize();
            if (stackPosition == totalNum) {
                index++;
                break;
            }
        }

        return stack.size() - index;
    }

    public int getSize() {
        return size;
    }

    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public void printState() {
        LOG.print(ph.getGridString(generateGridArray(), true, "\nStack States"));
    }

    @Override
    public String[][] generateGridArray() {
        int rowSize = size + 1;
        if (rowSize < 6) {
            rowSize = 6;
        }
        String[][] grid = new String[rowSize][2];
        grid[0][1] = "Type";

        /* 第几个值 */
        int valueIndex = valueNumber;
        int rowIndex = 1;
        for (; rowIndex < size + 1;) {
            Type t = (stack.get(valueIndex - 1)).getType();
            int valueSize = t.getSize();

            while (valueSize > 0) {
                grid[rowIndex][0] = stackGraph(rowIndex - 1);
                if (valueSize == 1) {
                    grid[rowIndex][1] = t.getDescriptor();
                }

                valueSize--;
                rowIndex++;
            }
            valueIndex--;
        }

        for (; rowIndex < rowSize; rowIndex++) {
            grid[rowIndex][0] = stackGraph(rowIndex - 1);
        }

        return grid;
    }

    private String stackGraph(int i) {
        switch (i) {
        case 0:
            return " ____";
        case 1:
            return "/ ___|   ";
        case 2:
            return "\\___ \\   ";
        case 3:
            return " ___) |  ";
        case 4:
            return "|____/   ";
        }
        return null;
    }

}
