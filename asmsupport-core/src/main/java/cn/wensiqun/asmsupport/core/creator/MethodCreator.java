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
package cn.wensiqun.asmsupport.core.creator;

import cn.wensiqun.asmsupport.core.block.method.AbstractKernelMethodBody;
import cn.wensiqun.asmsupport.core.clazz.MutableClass;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class MethodCreator implements IMethodCreator {

	private String name;
	private AClass[] arguments;
	private String[] argNames;
	private AClass returnClass;
	private AClass[] exceptions;
	private int access;
	private AbstractKernelMethodBody methodBody;
	private AMethodMeta me;
	private AMethod method;
	private int mtdCrtMode;
	
	void setMethodCreateMode(int mode){
		this.mtdCrtMode = mode;
	}
	
	public static MethodCreator methodCreatorForModify(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, AClass[] exceptions, int access, AbstractKernelMethodBody mb){
		MethodCreator mc = new MethodCreator(name, arguments, argNames, returnClass, exceptions, access, mb);
		mc.setMethodCreateMode(ASConstant.METHOD_CREATE_MODE_MODIFY);
		return mc;
	}
	
	public static MethodCreator methodCreatorForAdd(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, AClass[] exceptions, int access, AbstractKernelMethodBody mb){
		MethodCreator mc = new MethodCreator(name, arguments, argNames, returnClass, exceptions, access, mb);
		mc.setMethodCreateMode(ASConstant.METHOD_CREATE_MODE_ADD);
		return mc;
	}
	
	private MethodCreator(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, AClass[] exceptions, int access, AbstractKernelMethodBody mb) {
		this.name = name;
		this.arguments = arguments;
		this.argNames = argNames;
		this.returnClass = returnClass;
		this.exceptions = exceptions;
		this.access = access;
		this.methodBody = mb;
	}

	@Override
	public void prepare() {
		if(!ModifierUtils.isAbstract(access)){
			method.getMethodBody().prepare();
		}
	}

	@Override
	public void execute() {
		method.startup();
	}

	@Override
	public void create(IClassContext context){
		MutableClass owner = context.getCurrentClass();
		me = new AMethodMeta(name, owner, owner, arguments, argNames, returnClass, exceptions, access);
		method = new AMethod(me, context, methodBody, mtdCrtMode);
		if(method.getMeta().getName().equals(ASConstant.INIT)){
			owner.addConstructor(method);
		}else if(ModifierUtils.isBridge(method.getMeta().getModifier())){
			owner.getBridgeMethod().add(method);
		}else{
			owner.addMethod(method);
		}
	}

	public String getName() {
		return name;
	}

	public AClass[] getArguments() {
		return arguments;
	}

	public String[] getArgNames() {
		return argNames;
	}

	public AClass getReturnClass() {
		return returnClass;
	}

	public AClass[] getExceptions() {
		return exceptions;
	}

	public int getAccess() {
		return access;
	}
	
	

	@Override
	public String getMethodString() {
		return me.getMethodString();
	}

}
