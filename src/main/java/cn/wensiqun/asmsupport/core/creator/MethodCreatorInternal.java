package cn.wensiqun.asmsupport.core.creator;

import cn.wensiqun.asmsupport.core.block.classes.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.generic.creator.IClassContext;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class MethodCreatorInternal implements IMethodCreator {

	private String name;
	private AClass[] arguments;
	private String[] argNames;
	private AClass returnClass;
	private AClass[] exceptions;
	private int access;
	private AbstractMethodBody methodBody;
	private AMethodMeta me;
	private AMethod method;
	private int mtdCrtMode;
	
	void setMethodCreateMode(int mode){
		this.mtdCrtMode = mode;
	}
	
	public static MethodCreatorInternal methodCreatorForModify(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, AClass[] exceptions, int access, AbstractMethodBody mb){
		MethodCreatorInternal mc = new MethodCreatorInternal(name, arguments, argNames, returnClass, exceptions, access, mb);
		mc.setMethodCreateMode(ASConstant.METHOD_CREATE_MODE_MODIFY);
		return mc;
	}
	
	public static MethodCreatorInternal methodCreatorForAdd(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, AClass[] exceptions, int access, AbstractMethodBody mb){
		MethodCreatorInternal mc = new MethodCreatorInternal(name, arguments, argNames, returnClass, exceptions, access, mb);
		mc.setMethodCreateMode(ASConstant.METHOD_CREATE_MODE_ADD);
		return mc;
	}
	
	private MethodCreatorInternal(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, AClass[] exceptions, int access, AbstractMethodBody mb) {
		super();
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
		NewMemberClass owner = context.getCurrentClass();
		me = new AMethodMeta(name, owner, owner, arguments, argNames, returnClass, exceptions, access);
		method = new AMethod(me, context, methodBody, mtdCrtMode);
		if(method.getMethodMeta().getName().equals(ASConstant.INIT)){
			owner.addConstructor(method);
		}else if(ModifierUtils.isBridge(method.getMethodMeta().getModifier())){
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
