package jw.asmsupport.creator;

import jw.asmsupport.block.method.SuperMethodBody;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.AClassFactory;
import jw.asmsupport.clazz.NewMemberClass;
import jw.asmsupport.clazz.ProductClass;
import jw.asmsupport.clazz.SemiClass;
import jw.asmsupport.definition.method.Method;
import jw.asmsupport.entity.MethodEntity;
import jw.asmsupport.utils.ASConstant;
import jw.asmsupport.utils.ModifierUtils;

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
	private Class<?>[] exceptions;
	private int access;
	private SuperMethodBody methodBody;
	private MethodEntity me;
	private Method method;
	private int mtdCrtMode;
	
	void setMethodCreateMode(int mode){
		this.mtdCrtMode = mode;
	}
	
	public static MethodCreator methodCreatorForModify(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, Class<?>[] exceptions, int access, SuperMethodBody mb){
		MethodCreator mc = new MethodCreator(name, arguments, argNames, returnClass, exceptions, access, mb);
		mc.setMethodCreateMode(ASConstant.METHOD_CREATE_MODE_MODIFY);
		return mc;
	}
	
	public static MethodCreator methodCreatorForAdd(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, Class<?>[] exceptions, int access, SuperMethodBody mb){
		MethodCreator mc = new MethodCreator(name, arguments, argNames, returnClass, exceptions, access, mb);
		mc.setMethodCreateMode(ASConstant.METHOD_CREATE_MODE_ADD);
		return mc;
	}
	
	private MethodCreator(String name, AClass[] arguments, String[] argNames,
			AClass returnClass, Class<?>[] exceptions, int access, SuperMethodBody mb) {
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
	public void create(IClassContext context, SemiClass owner) {
		create(context, (NewMemberClass)owner);
	};

	@Override
	public void create(IClassContext context, ProductClass owner) {
		create(context, (NewMemberClass)owner);
	}
	
	private void create(IClassContext context, NewMemberClass owner){
		AClass[] exces;
		if(exceptions == null){
			exces = new AClass[0];
		}else{
			exces = new AClass[exceptions.length];

			for (int i = 0; i < exceptions.length; i++) {
				exces[i] = AClassFactory.getProductClass(exceptions[i]);
			}
		}
		
		me = new MethodEntity(name, owner, owner, arguments, argNames, returnClass, exces, access);

		method = new Method(me, context, methodBody, mtdCrtMode);
		if(method.getMethodEntity().getName().equals(ASConstant.INIT)){
			owner.addConstructor(method);
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

	public Class<?>[] getExceptions() {
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
