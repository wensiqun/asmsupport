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
/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class InstanceofOperator extends AbstractOperator implements Parameterized{

    private AClass type; 
    private Parameterized obj;
    private boolean byOtherUsed;
    
    protected InstanceofOperator(ProgramBlockInternal block, Parameterized obj, AClass type) {
        super(block);
        this.obj = obj;
        this.type = type;
    }

    @Override
    protected void verifyArgument() {
        if(obj.getParamterizedType().isPrimitive()){
            throw new ASMSupportException("Incompatible conditional operand types " + obj.getParamterizedType() + " int and " + type);
        }
    }

    @Override
    protected void checkCrement() {
        //Do nothing here
    }

    @Override
    protected void checkAsArgument() {
        obj.asArgument();
    }

    @Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ASMSupportException("the instanceof operator has not been used by other operator.");
        }
    }

    @Override
    protected void doExecute() {
        obj.loadToStack(block);
        insnHelper.instanceOf(type.getType());
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        this.execute();
    }

    @Override
    public AClass getParamterizedType() {
        return AClassFactory.getType(boolean.class);
    }

    @Override
    public void asArgument() {
        byOtherUsed = true;
        block.removeExe(this);
    }

}
