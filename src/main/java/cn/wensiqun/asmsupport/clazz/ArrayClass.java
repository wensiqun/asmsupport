package cn.wensiqun.asmsupport.clazz;

import java.io.Serializable;

import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.meta.GlobalVariableMeta;

/**
 * 数组类型
 * @author 温斯群(Joe Wen)
 *
 */
public class ArrayClass extends AClass {

    private AClass aclass;

    private String desc; 
    
    /** indicate the dimension of this class if this class is a array type, otherwise the this dim is -1 */
    protected int dim;
    
    /**
     * 
     * @param cls
     * @param dim
     */
    ArrayClass(AClass cls, int dim) {
    	version = cls.version;
        mod = cls.mod;
        superClass = Object.class;
        interfaces = new Class[]{Cloneable.class, Serializable.class};
        
        this.aclass = cls;
        this.dim = dim;
        StringBuilder descsb = new StringBuilder();
        int tmpDim = dim;
        while(tmpDim>0){
            descsb.append("[");
            tmpDim--;
        }
        descsb.append(cls.getDescription());
        desc = descsb.toString();
        name = desc;
    }
    
    @Override
	public boolean existStaticInitBlock() {
    	return false;
    }
    
    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public GlobalVariable getGlobalVariable(String name) {
        return aclass.getGlobalVariable(name);
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public int getDimension() {
        return dim;
    }

    @Override
    public GlobalVariableMeta getGlobalVariableMeta(String name) {
        return aclass.getGlobalVariableMeta(name);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public int getCastOrder() {
        return 9;
    }
    
    /**
     * 获取下一维的类型
     * @return
     */
    public AClass getNextDimType(){
        if(dim > 1){
            return new ArrayClass(aclass, dim - 1);
        }else{
            return aclass;
        }
    }
    
    /**
     * 获取数组的最基本类型
     * @return
     */
    public AClass getRootComponentClass(){
        return aclass;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(aclass.getName());
        for(int i=0; i<dim; i++){
            sb.append("[]");
        }
        return sb.toString();
    }

	@Override
	public boolean isChildOrEqual(AClass cls) {
		if(cls instanceof ArrayClass){
			String clsName = cls.getName();
	        if (getName().equals(clsName)) {
	            return true;
	        }
		}
		
		if(cls instanceof ProductClass){
			if(((ProductClass)cls).getReallyClass().equals(Object.class)){
				return true;
			}
		}
		return false;
	}
    
}
