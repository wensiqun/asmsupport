package cn.wensiqun.asmsupport.clazz;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.definition.method.AMethod;
import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.utils.ASConstant;
import cn.wensiqun.asmsupport.utils.reflet.ModifierUtils;


public abstract class NewMemberClass extends AClass {

    /** super key word  */
    private SuperVariable superVariable;

    /** this key word */
    private ThisVariable thisVariable;
    
    /** */
    private List<AMethod> methods;

    /**
     * store bridge method.
     * 1. overried method that return type is child of super method return type.
     * 2. generice type method(implement future)
     */
    private List<AMethod> bridgeMethod;
    
    private List<AMethod> constructors;
    
    private AMethod staticBlock;

    private List<GlobalVariable> globalVariables;

    //available only create enum class
    private int enumNum;

	public NewMemberClass() {
	}

	public int getEnumNum() {
		return enumNum;
	}

	public void setEnumNum(int enumNum) {
		this.enumNum = enumNum;
	}

    public List<AMethod> getMethods() {
    	if(methods == null){
   	        methods = new ArrayList<AMethod>();
    	}
        return methods;
    }
    
    
    public List<AMethod> getBridgeMethod() {
    	if(bridgeMethod == null){
    		bridgeMethod = new ArrayList<AMethod>();
    	}
		return bridgeMethod;
	}

	
    public AMethod getStaticBlock() {
        return staticBlock;
    }

    public void setStaticBlock(AMethod staticBlock) {
        this.staticBlock = staticBlock;
    }
    
    /**
     * add method
     * 
     * @param method
     */
    public void addMethod(AMethod method) {
    	getMethods().add(method);
    }
    

    public SuperVariable getSuperVariable() {
    	if(superVariable == null){
            superVariable = new SuperVariable(this);
    	}
        return superVariable;
    }

    public ThisVariable getThisVariable() {
    	if(thisVariable == null){
            thisVariable = new ThisVariable(this);
    	}
        return thisVariable;
    }
    
    /**
     * 
     * @param constructor
     */
    public void addConstructor(AMethod constructor) {
    	getConstructors().add(constructor);
    }

	public List<AMethod> getConstructors() {
		if(constructors == null){
	        constructors = new ArrayList<AMethod>(3);
		}
		return constructors;
	}
    
    protected List<GlobalVariable> getGlobalVariables() {
    	if(globalVariables == null){
            globalVariables = new ArrayList<GlobalVariable>();
    	}
		return globalVariables;
	}
    
	/**
     * 
     * add
     * @param field
     */
    public void addGlobalVariableEntity(GlobalVariableMeta e) {
        if (ModifierUtils.isStatic(e.getModifiers())) {
        	getGlobalVariables().add(new GlobalVariable(e.getOwner(), e));
        } else {
        	getGlobalVariables().add(new GlobalVariable(getThisVariable(), e));
        } 
    }
    
    @Override
	public boolean existStaticInitBlock() {
		for(AMethod m : this.getMethods()){
			if(m.getMethodMeta().getName().equals(ASConstant.CLINIT)){
				return true;
			}
		}
    	return false;
	}

	
    @Override
    public GlobalVariableMeta getGlobalVariableMeta(String name) {
        for (GlobalVariable f : getGlobalVariables()) {
            if (f.getGlobalVariableMeta().getName().equals(name)) {
                return f.getGlobalVariableMeta();
            }
        }
        return null;
    }

    @Override
    public GlobalVariable getGlobalVariable(String name) {
        for(GlobalVariable gv : getGlobalVariables()){
            if(gv.getVariableMeta().getName().equals(name)){
                return gv;
            }
        }
        return null;
    }

}
