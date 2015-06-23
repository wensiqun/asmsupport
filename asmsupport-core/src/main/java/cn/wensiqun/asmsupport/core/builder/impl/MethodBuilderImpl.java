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
package cn.wensiqun.asmsupport.core.builder.impl;

import cn.wensiqun.asmsupport.core.block.method.AbstractKernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.IClassBuilder;
import cn.wensiqun.asmsupport.core.builder.IMethodBuilder;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.MutableClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.utils.AsmsupportConstant;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class MethodBuilderImpl implements IMethodBuilder {

	private String name;
	private IClass[] arguments;
	private String[] argNames;
	private IClass returnClass;
	private IClass[] exceptions;
	private int access;
	private AbstractKernelMethodBody methodBody;
	private AMethodMeta meta;
	private AMethod method;
	private int mtdCrtMode;
	
	void setMethodCreateMode(int mode){
		this.mtdCrtMode = mode;
	}
	
	public static MethodBuilderImpl methodCreatorForModify(String name, IClass[] arguments, String[] argNames,
			IClass returnClass, IClass[] exceptions, int access, AbstractKernelMethodBody mb){
		MethodBuilderImpl mc = new MethodBuilderImpl(name, arguments, argNames, returnClass, exceptions, access, mb);
		mc.setMethodCreateMode(AsmsupportConstant.METHOD_CREATE_MODE_MODIFY);
		return mc;
	}
	
	public static MethodBuilderImpl methodCreatorForAdd(String name, IClass[] arguments, String[] argNames,
			IClass returnClass, IClass[] exceptions, int access, AbstractKernelMethodBody mb){
		MethodBuilderImpl mc = new MethodBuilderImpl(name, arguments, argNames, returnClass, exceptions, access, mb);
		mc.setMethodCreateMode(AsmsupportConstant.METHOD_CREATE_MODE_ADD);
		return mc;
	}
	
	private MethodBuilderImpl(String name, IClass[] arguments, String[] argNames,
			IClass returnClass, IClass[] exceptions, int access, AbstractKernelMethodBody mb) {
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
	public void create(IClassBuilder context){
		MutableClass owner = context.getCurrentClass();
		meta = new AMethodMeta(context.getClassLoader(), name, owner, owner, arguments, argNames, returnClass, exceptions, access);
		method = new AMethod(meta, context.getClassVisitor(), context.getClassLoader(), methodBody, mtdCrtMode);
		if(method.getMeta().getName().equals(AsmsupportConstant.INIT)){
			owner.addConstructor(meta);
		}else if(ModifierUtils.isBridge(method.getMeta().getModifier())){
			owner.getBridgeMethod().add(meta);
		}else{
			owner.addDeclaredMethod(meta);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IClass[] getArguments() {
		return arguments;
	}

}
