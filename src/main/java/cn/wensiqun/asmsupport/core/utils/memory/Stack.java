package cn.wensiqun.asmsupport.core.utils.memory;

import java.util.EmptyStackException;





import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.org.apache.commons.collections.ArrayStack;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class Stack implements Printable, Cloneable {

    private static Log log = LogFactory.getLog(Stack.class);
    private ArrayStack stack;
    /**栈的大小 */
    private int size;
    /**栈的最大空间 */
    private int maxSize;
    /**栈中有多少个值 */
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

	public Stack() {
        stack = new ArrayStack();
        ph = new PrintHelper();
    }

    /**
     * Returns the top item off of this stack without removing it.
     * 
     * @return the top item on the stack
     * @throws EmptyStackException
     *             if the stack is empty
     */
    public Stackable peek() throws EmptyStackException {
        return (Stackable) stack.peek();
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
    public Stackable peek(int n) throws EmptyStackException {
        return (Stackable) stack.peek(n);
    }

    public Stackable pop() throws EmptyStackException {
    	Stackable s = peek();
        pop(1);
        return s;
    }

    public void pop(int times) throws EmptyStackException {
        while (times > 0) {
            Stackable t = (Stackable) stack.pop();
            size -= t.getSize();
            times--;
            valueNumber--;
        }
    }

    public void push(Stackable item) {
        valueNumber++;
        stack.push(item);
        size += item.getSize();
        if (maxSize < size) {
            maxSize = size;
        }
    }

    public void push(Type item) {
        Stackable stype = new StackableType(item);
        push(stype);
    }

    public void push(Type... items) {
    	if(items != null){
    		for(Type item : items){
    			push(item);
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
    public void insert(int pos, Stackable item){
        valueNumber++;
        stack.add(getReallyPosition(pos), item);
        size += item.getSize();
        if (maxSize < size) {
            maxSize = size;
        }
    }
    
    public void insert(int i, Type item){
        insert(stack.size() - i, new StackableType(item));
    }
    
/*    private void swap(){
        Stackable top1 = this.peek();
        pop();

        Stackable top2 = this.peek();
        pop();
        
        push(top1);
        push(top2);
    }*/
    
    private int getReallyPosition(int stackPosition){
        //++后 表示占了多少个栈空间
        stackPosition++;
        
        //从开始到当前遍历到的位置总共占了多少空间
        int totalNum = 0;
        int index = 0;
         for(; index<stack.size(); index++){
            totalNum += peek(index).getSize();
            if(stackPosition == totalNum){
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
        log.debug(ph.getGridString(generateGridArray(), true, "Stack states"));
    }

    @Override
    public String[][] generateGridArray() {
        int rowSize = size + 1;
        if (rowSize < 6) {
            rowSize = 6;
        }
        String[][] grid = new String[rowSize][2];
        grid[0][1] = "Type";
        
        /*第几个值*/
        int valueIndex = valueNumber;
        int rowIndex = 1;
        for(; rowIndex<size + 1; ){
            Type t = ((Stackable) stack.get(valueIndex - 1)).getType();
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
        
        for( ; rowIndex < rowSize; rowIndex++ ) {
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
