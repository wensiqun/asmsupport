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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;

/**
 * 用于执行Return
 * @author 温斯群(Joe Wen)
 *
 */
public class Return extends BreakStack {

    private static Log log = LogFactory.getLog(Return.class);
    
    private Parameterized returner;
    
    private Type returnType;
    
    protected Return(ProgramBlockInternal block, Parameterized returner) {
        super(block, false);
        this.returner = returner;
        if(returner != null){
            returner.asArgument();
        }
    }
    
    protected Return(ProgramBlockInternal block, Parameterized returner, boolean autoCreate) {
        super(block, autoCreate);
        this.returner = returner;
        if(returner != null){
            returner.asArgument();
        }
    }
    
    

    @Override
    protected void verifyArgument() {
        
    }

    @Override
    protected void checkAsArgument() {
        
    }
    
    @Override
    public void breakStackExecuting() {
    	
        if(returner == null){
            log.debug("direct return from method");
            insnHelper.returnInsn();
        }else{
            returner.loadToStack(block);
            returnType = returner.getParamterizedType().getType();
            if(returnType == null){
                throw new NullPointerException("return type must be non-null!");
            }
            insnHelper.returnInsn(returnType);
        }
    }

}
