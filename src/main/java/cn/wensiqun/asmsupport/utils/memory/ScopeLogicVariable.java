package cn.wensiqun.asmsupport.utils.memory;


import org.apache.commons.lang3.ArrayUtils;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;

import cn.wensiqun.asmsupport.exception.ASMSupportException;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ScopeLogicVariable extends Component implements Localable {

    private Type actuallyType;
    private Type declareType;
    private String name;
    private int[] positions;
    private int initStartPos;
    private int compileOrder;
    private boolean anonymous;
    private Label specifiedStartLabel;

    public ScopeLogicVariable(Scope parent, Type declareClass,
            Type actuallyClass, boolean anonymous){
        this("anonymous", parent, actuallyClass, actuallyClass);
        this.anonymous = anonymous;
    }
    
    public ScopeLogicVariable(String name, Scope parent, Type declareClass,
            Type actuallyClass) {
        super(parent.locals);
        // 添加父子关系
        this.setParent(parent);
        parent.addComponent(this);

        this.name = name;
        if(declareClass.equals(Type.VOID_TYPE) || actuallyClass.equals(Type.VOID_TYPE) ){
        	throw new ASMSupportException("cannot declare a void type");
        }
        this.actuallyType = actuallyClass;
        this.declareType = declareClass;
        this.specifiedStartLabel = parent.getStart();
        store();
    }
    
    public boolean isAnonymous() {
        return anonymous;
    }

    private void store() {
        int localSize = locals.getSize();
        int needSize = actuallyType.getSize();
        int i = 0;
        if (localSize > 0 && locals.getLastVariable(0) instanceof ScopeThis) {
            i = 1;
        }

        for (; i < localSize; i++) {
            ScopeLogicVariable survivor = (ScopeLogicVariable) locals.getLastVariable(i);
            if (isShareable(survivor)) {
                this.positions = ArrayUtils.add(this.positions, i);
                this.locals.store(i, this);
                survivor.positions = ArrayUtils.subarray(survivor.positions, 1,
                        survivor.positions.length);
                needSize--;
                if (needSize == 0) {
                    break;
                }
            } else {
                if(!this.isAnonymous() && !survivor.isAnonymous()){
                    if (survivor.getName().equals(this.name)) {
                        throw new DuplicateLocalVariableNameException();
                    }
                }
            }
        }
        while (needSize > 0) {
            this.positions = ArrayUtils
                    .add(this.positions, this.locals.getSize());
            this.locals.store(this);
            needSize--;
        }
        this.initStartPos = this.positions[0];
    }
    
    /**
     * var相对于当前变量是可共享空间的。
     * 
     * @return
     */
    private boolean isShareable(ScopeLogicVariable var) {
        if (compareComponentOrder(this.componentOrder, var.componentOrder) == -1 || this.equals(var)) {
            return false;
        }
        
        // 当前变量与var相差的代数
        int currDiff = this.generation - var.generation;

        // 如果两个变量的代数不同，代数多的一直向上获取parent直到获取到和另一个变量相同的代数

        if (currDiff >= 0) {// 如果当前变量的代数多，既辈份低
            // 当前变量向上获取parent直到获取到和另一个变量相同的辈份
            Component currCom = this;
            while (currDiff > 0) {
                currCom = currCom.getParent();
                currDiff--;
            }
            // 如果他们的父类相同说明不能share
            if (currCom.getParent().equals(var.getParent())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 变量是Scope说的子代
     * 
     * @param scope
     * @return
     */
    public boolean isSubOf(Scope scope) {
        Component com = this;
        while (com != null) {
            if (com.equals(scope)) {
                return true;
            }
            com = com.getParent();
        }
        return false;
    }

    /**
     * 指定的Component可否使用该变量
     * 
     * @param scope
     * @return
     */
    public boolean availableFor(Component com) {
        if (this.equals(com)) {
            return true;
        }

        if (compareComponentOrder(this.componentOrder, com.componentOrder) == 1) {
        	return false;
        }

        // 当前变量与com相差的代数
        int currDiff = com.generation - this.generation;

        // 如果两个变量的代数不同，代数多的一直向上获取parent直到获取到和另一个变量相同的代数

        if (currDiff >= 0) {// 如果当前变量的代数少，既辈份高
            // Com向上获取parent直到获取到和另一个变量相同的辈份
            // Component currCom = com;
            while (currDiff > 0) {
                com = com.getParent();
                currDiff--;
            }
            // 如果他们的父类相同说明可是使用
            if (this.getParent().equals(com.getParent())) {
                return true;
            }
        }
        return false;
    }

    public int getCompileOrder() {
        return compileOrder;
    }

    /**
     * 设置编译顺序
     * @param compileOrder
     */
    public void setCompileOrder(Integer compileOrder) {
        this.compileOrder = compileOrder;
    }

    private class DuplicateLocalVariableNameException extends RuntimeException {

        private static final long serialVersionUID = -78653305684942300L;

        private DuplicateLocalVariableNameException() {
            super("Duplicate local variable \"" + name + "\"");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public Type getActuallyType() {
        return actuallyType;
    }

    public Type getDeclareType() {
        return declareType;
    }

    @Override
    public int[] getPositions() {
        return positions;
    }

    public int getInitStartPos() {
        return initStartPos;
    }

    public Label getSpecifiedStartLabel() {
        return specifiedStartLabel;
    }

    public void setSpecifiedStartLabel(Label specifiedStartLabel) {
        this.specifiedStartLabel = specifiedStartLabel;
    }

    @Override
    public String toString() {
        return getDeclareType() + " " + getName();
    }
}
