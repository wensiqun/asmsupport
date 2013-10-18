package cn.wensiqun.asmsupport.clazz;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.definition.method.Method;
import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.entity.GlobalVariableEntity;
import cn.wensiqun.asmsupport.utils.ASConstant;
import cn.wensiqun.asmsupport.utils.ModifierUtils;


public abstract class NewMemberClass extends AClass {

    /** super key word  */
    private SuperVariable superVariable;

    /** this key word */
    private ThisVariable thisVariable;
    
    /** */
    private List<Method> methods;

    private List<Method> constructors;
    
    private Method staticBlock;

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

    public List<Method> getMethods() {
    	if(methods == null){
   	        methods = new ArrayList<Method>();
    	}
        return methods;
    }

    public Method getStaticBlock() {
        return staticBlock;
    }

    public void setStaticBlock(Method staticBlock) {
        this.staticBlock = staticBlock;
    }
    
    /**
     * add method
     * 
     * @param method
     */
    public void addMethod(Method method) {
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
    public void addConstructor(Method constructor) {
    	getConstructors().add(constructor);
    }

	public List<Method> getConstructors() {
		if(constructors == null){
	        constructors = new ArrayList<Method>(3);
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
    public void addGlobalVariableEntity(GlobalVariableEntity e) {
        if (ModifierUtils.isStatic(e.getModifiers())) {
        	getGlobalVariables().add(new GlobalVariable(e.getOwner(), e));
        } else {
        	getGlobalVariables().add(new GlobalVariable(getThisVariable(), e));
        } 
    }
    
    @Override
	public boolean existStaticInitBlock() {
		for(Method m : this.getMethods()){
			if(m.getMethodEntity().getName().equals(ASConstant.CLINIT)){
				return true;
			}
		}
    	return false;
	}

	/*@Override
    public MethodEntity availableConstructor(AClass aclass,
            AClass[] parameterTypes) {
        MethodEntity me = null;
        for (Method m : getConstructors()) {
            AClass[] actual = m.getMethodEntity().getArgClasses();
            if (m.getMethodEntity().getName().equals("<init>")
                    && actual.length == parameterTypes.length) {
                me = m.getMethodEntity();
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (!parameterTypes[i].getName()
                            .equals(actual[i].getName())) {
                        me = null;
                        break;
                    }
                }
                if (me != null) {
                    return me;
                }
            }
        }
        return null;
    }*/

	
    @Override
    public GlobalVariableEntity getGlobalVariableEntity(String name) {
        for (GlobalVariable f : getGlobalVariables()) {
            if (f.getGlobalVariableEntity().getName().equals(name)) {
                return f.getGlobalVariableEntity();
            }
        }
        return null;
    }

    @Override
    public GlobalVariable getGlobalVariable(String name) {
        for(GlobalVariable gv : getGlobalVariables()){
            if(gv.getVariableEntity().getName().equals(name)){
                return gv;
            }
        }
        return null;
    }

}
