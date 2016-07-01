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
package cn.wensiqun.asmsupport.core.operator.asmdirect;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;


/**
 * integrated DUP DUP2 instruction according to type of the top element.
 *
 */
public class DUP extends ASMDirect implements KernelParam {

	private static final Log LOG = LogFactory.getLog(DUP.class);
	
	private IClass type;
	
	protected DUP(KernelProgramBlock block, IClass stackTopType) {
		super(block);
		type = stackTopType;
	}

	@Override
	public void loadToStack(KernelProgramBlock block) {
		this.execute();
	}

	@Override
	public IClass getResultType() {
		return type;
	}

	@Override
	public void asArgument() {
        block.removeExe(this);
	}

	@Override
	protected void doExecute() {
		if(LOG.isPrintEnabled()){
			LOG.print("duplicate the top of stack and push it to stack");
		}
        getInstructions().dup(type.getType());
	}

    @Override
    public GlobalVariable field(String name) {
        throw new UnsupportedOperationException("Un imple");
    }

}
