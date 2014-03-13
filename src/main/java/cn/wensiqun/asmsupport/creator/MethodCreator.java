package cn.wensiqun.asmsupport.creator;

import cn.wensiqun.asmsupport.block.method.GenericMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.definition.method.AMethod;
import cn.wensiqun.asmsupport.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.utils.ASConstant;
import cn.wensiqun.asmsupport.utils.reflet.ModifierUtils;

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
	private GenericMethodBody methodBody;
	private AMethodMeta me;
	private AMethod method;
	private int mtdCrtMode;
	
	void setMethodCreateMode(int mode){
		this.mtdCrtMode = mode;
	}
	
	public static MethodCreator methodCreatorForModify(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, AClass[] exceptions, int access, GenericMethodBody mb){
		MethodCreator mc = new MethodCreator(name, arguments, argNames, returnClass, exceptions, access, mb);
		mc.setMethodCreateMode(ASConstant.METHOD_CREATE_MODE_MODIFY);
		return mc;
	}
	
	public static MethodCreator methodCreatorForAdd(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, AClass[] exceptions, int access, GenericMethodBody mb){
		MethodCreator mc = new MethodCreator(name, arguments, argNames, returnClass, exceptions, access, mb);
		mc.setMethodCreateMode(ASConstant.METHOD_CREATE_MODE_ADD);
		return mc;
	}
	
	private MethodCreator(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, AClass[] exceptions, int access, GenericMethodBody mb) {
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
