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
package cn.wensiqun.asmsupport.core.build.impl;

import cn.wensiqun.asmsupport.core.block.method.AbstractKernelMethodBody;
import cn.wensiqun.asmsupport.core.build.BytecodeResolver;
import cn.wensiqun.asmsupport.core.build.MethodBuilder;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.MutableClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.utils.Modifiers;
import cn.wensiqun.asmsupport.utils.ASConstants;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class DefaultMethodBuilder implements MethodBuilder {

	private String name;
	private IClass[] arguments;
	private String[] argNames;
	private IClass returnType;
	private IClass[] exceptions;
	private int access;
	private AbstractKernelMethodBody methodBody;
	private AMethodMeta meta;
	private AMethod method;
	private int buildMode;

    /**
     * Set create mode
     * @param mode
     * @see #MODE_ADD
     * @see #MODE_MODIFY
     * @see #MODE_DELEGATE
     */
	public void setMethodBuildMode(int mode){
		this.buildMode = mode;
	}
	
	public static DefaultMethodBuilder buildForModify(String name, IClass[] arguments, String[] argNames,
                                                      IClass returnType, IClass[] exceptions, int access, AbstractKernelMethodBody mb){
		DefaultMethodBuilder mc = new DefaultMethodBuilder(name, arguments, argNames, returnType, exceptions, access, mb);
		mc.setMethodBuildMode(MODE_MODIFY);
		return mc;
	}
	
	public static DefaultMethodBuilder buildForNew(String name, IClass[] arguments, String[] argNames,
                                                   IClass returnType, IClass[] exceptions, int access, AbstractKernelMethodBody mb){
		DefaultMethodBuilder mc = new DefaultMethodBuilder(name, arguments, argNames, returnType, exceptions, access, mb);
		mc.setMethodBuildMode(MODE_ADD);
		return mc;
	}

    public static DefaultMethodBuilder buildForDelegate(DefaultMethodBuilder delegate, AbstractKernelMethodBody mb){
        DefaultMethodBuilder mc = new DefaultMethodBuilder(delegate.getName(), delegate.getArguments(), null,
                delegate.getReturnType(), null, 0, mb);
        mc.setMethodBuildMode(MODE_DELEGATE);
        mb.setExecutor(delegate.methodBody);
        return mc;
    }
	
	private DefaultMethodBuilder(String name, IClass[] arguments, String[] argNames,
                                 IClass returnType, IClass[] exceptions, int access, AbstractKernelMethodBody mb) {
		this.name = name;
		this.arguments = arguments;
		this.argNames = argNames;
		this.returnType = returnType;
		this.exceptions = exceptions;
		this.access = access;
		this.methodBody = mb;
	}

	@Override
	public void prepare() {
		if(!Modifiers.isAbstract(access)){
			methodBody.prepare();
		}
	}

	@Override
	public void execute() {
        if(buildMode != MODE_DELEGATE) {
            method.startup();
        }
	}

	@Override
	public void create(BytecodeResolver resolver){
        if(buildMode == MODE_DELEGATE) {
            return;
        }
		MutableClass owner = resolver.getCurrentClass();
		meta = new AMethodMeta(resolver.getClassLoader(), name, owner, owner, arguments, argNames, returnType, exceptions, access);
		method = new AMethod(meta, resolver.getClassVisitor(), resolver.getClassLoader(), methodBody, buildMode);
		if(method.getMeta().getName().equals(ASConstants.INIT)){
			owner.addConstructor(meta);
		}else if(Modifiers.isBridge(method.getMeta().getModifiers())){
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
	public int getModifiers() {
		return access;
	}

	@Override
	public IClass[] getArguments() {
		return arguments;
	}

    @Override
    public IClass getReturnType() {
        return returnType;
    }

}
