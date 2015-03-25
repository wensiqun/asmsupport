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
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class StringAppender extends AbstractOperator implements Parameterized{
    
    private Parameterized[] paras;

    private boolean byOtherUsed;
    private MethodInvoker invoker;
    
    protected StringAppender(ProgramBlockInternal block, Parameterized par1, Parameterized... pars) {
        super(block);
        this.paras = new Parameterized[pars.length + 1];
        this.paras[0] = par1;
        System.arraycopy(pars, 0, this.paras, 1, pars.length);
        
        AClass strBlderCls = AClassFactory.defType(StringBuilder.class);

        MethodInvoker mi = block.call(block.new_(strBlderCls), "append", par1);
        for(Parameterized par : pars){
            mi = block.call(mi, "append", par);
        }
        invoker = block.call(mi, "toString");
    }

    @Override
    protected void verifyArgument() {
        // Do nothing
    }

    @Override
    protected void checkCrement() {
        // Do nothing
    }

    @Override
    protected void checkAsArgument() {
        // Do nothing
    }
    
    @Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ASMSupportException("the string append operator has not been used by other operator.");
        }
    }

    @Override
    protected void doExecute() {
        if(paras.length == 1){
            paras[0].loadToStack(block);
        }else{
            invoker.loadToStack(block);
        }
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        this.execute();
    }

    @Override
    public AClass getParamterizedType() {
        return AClass.STRING_ACLASS;
    }

    @Override
    public void asArgument() {
        byOtherUsed = true;
        block.removeExe(this);
        if(paras.length == 1){
            paras[0].asArgument();
        }else{
            invoker.asArgument();
        }
    }

}
