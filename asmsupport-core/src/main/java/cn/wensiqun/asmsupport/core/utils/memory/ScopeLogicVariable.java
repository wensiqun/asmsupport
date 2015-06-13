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


import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;


/**
 * Represent a logic variable, it's different to {@link LocalVariable}, this class
 * will use internal in order to compute size of {@link LocalVariables} and determine
 * the scope of variable.
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class ScopeLogicVariable extends ScopeComponent {

    private Type type;
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
    
    public ScopeLogicVariable(String name, Scope parent, Type type,
            Type actuallyClass) {
        super(parent.locals);
        setParent(parent);
        parent.addComponent(this);

        this.name = name;
        if(type.equals(Type.VOID_TYPE) || actuallyClass.equals(Type.VOID_TYPE) ){
        	throw new ASMSupportException("cannot declare a void type");
        }
        this.type = type;
        this.specifiedStartLabel = parent.getStart();
        store();
    }
    
    /**
     * Check the variable is anonymous
     */
    public boolean isAnonymous() {
        return anonymous;
    }

    private void store() {
        int localSize = locals.getSize();
        int needSize = type.getSize();
        for (int i = 0; i < localSize; i++) {
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
                        throw new ASMSupportException("Duplicate local variable \"" + name + "\"");
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
     * Check the space in local variable of current variable is sharable for other variable.
     * 
     * @return boolean {@code true} allow share
     */
    private boolean isShareable(ScopeLogicVariable var) {
        if (compareComponentOrder(this.componentOrder, var.componentOrder) == -1 || this.equals(var)) {
            return false;
        }
        
        int generationGap = this.generation - var.generation;

        //如果两个变量的代数不同，代数多的一直向上获取parent直到获取到和另一个变量相同的代数
        if (generationGap >= 0) {// 如果当前变量的代数多，既辈份低
            // 当前变量向上获取parent直到获取到和另一个变量相同的辈份
            ScopeComponent currCom = this;
            while (generationGap > 0) {
                currCom = currCom.getParent();
                generationGap--;
            }
            // 如果他们的父类相同说明不能share
            if (currCom.getParent().equals(var.getParent())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check current variable is bellow in a specially {@code Scope}
     */
    public boolean isSubOf(Scope scope) {
        ScopeComponent com = this;
        while (com != null) {
            if (com.equals(scope)) {
                return true;
            }
            com = com.getParent();
        }
        return false;
    }

    /**
     * Check current variable is available for a specially {@link ScopeComponent}
     */
    public boolean availableFor(ScopeComponent scopeComponent) {
        if (this.equals(scopeComponent)) {
            return true;
        }

        if (compareComponentOrder(this.componentOrder, scopeComponent.componentOrder) == 1) {
        	return false;
        }

        // 当前变量与com相差的代数
        int currDiff = scopeComponent.generation - this.generation;

        // 如果两个变量的代数不同，代数多的一直向上获取parent直到获取到和另一个变量相同的代数

        if (currDiff >= 0) {// 如果当前变量的代数少，既辈份高
            // Com向上获取parent直到获取到和另一个变量相同的辈份
            // Component currCom = com;
            while (currDiff > 0) {
                scopeComponent = scopeComponent.getParent();
                currDiff--;
            }
            // 如果他们的父类相同说明可是使用
            if (this.getParent().equals(scopeComponent.getParent())) {
                return true;
            }
        }
        return false;
    }

    public int getCompileOrder() {
        return compileOrder;
    }

    /**
     * Set compile order. Represent the order in class instruction.
     * 
     * @param compileOrder
     */
    public void setCompileOrder(Integer compileOrder) {
        this.compileOrder = compileOrder;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int[] getPositions() {
        return positions;
    }

    public int getInitStartPos() {
        return initStartPos;
    }

    public Label getSpecifiedStartLabel() {
        return specifiedStartLabel;
    }

    @Override
    public String toString() {
        return getType() + " " + getName();
    }
}
