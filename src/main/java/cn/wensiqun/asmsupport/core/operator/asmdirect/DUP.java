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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;


/**
 * integrated DUP DUP2 instruction according to type of the top element.
 *
 */
public class DUP extends ASMDirect implements Parameterized {

	private static final Log LOG = LogFactory.getLog(DUP.class);
	
	private AClass type;
	
	protected DUP(ProgramBlockInternal block, AClass stackTopType) {
		super(block);
		type = stackTopType;
	}

	@Override
	public void loadToStack(ProgramBlockInternal block) {
		this.execute();
	}

	@Override
	public AClass getParamterizedType() {
		return type;
	}

	@Override
	public void asArgument() {
        block.removeExe(this);
	}

	@Override
	protected void doExecute() {
		if(LOG.isDebugEnabled()){
			LOG.debug("duplicate the top of stack and push it to stack");
		}
        block.getInsnHelper().dup(type.getType());
	}

}
