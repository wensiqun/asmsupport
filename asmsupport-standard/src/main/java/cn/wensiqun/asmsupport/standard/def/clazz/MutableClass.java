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
package cn.wensiqun.asmsupport.standard.def.clazz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.utils.jls.TypeUtils;
import cn.wensiqun.asmsupport.utils.ByteCodeConstant;


public abstract class MutableClass extends AClass {
    
    private List<AMethodMeta> methods;

    /**
     * store bridge method.
     * 1. overried method that return type is child of super method return type.
     * 2. generice type method(implement future)
     */
    private List<AMethodMeta> bridgeMethod;
    
    private List<AMethodMeta> constructors;

    private Set<Field> fields;

    //available only create enum class
    private int enumNum;

	public MutableClass() {
	}

	public int getEnumNum() {
		return enumNum;
	}

	public void setEnumNum(int enumNum) {
		this.enumNum = enumNum;
	}

    public List<AMethodMeta> getMethods() {
    	if(methods == null){
   	        methods = new ArrayList<AMethodMeta>();
    	}
        return methods;
    }
    
    
    public List<AMethodMeta> getBridgeMethod() {
    	if(bridgeMethod == null){
    		bridgeMethod = new ArrayList<AMethodMeta>();
    	}
		return bridgeMethod;
	}
    
    /**
     * add method
     * 
     * @param method
     */
    public void addMethod(AMethodMeta method) {
    	getMethods().add(method);
    }
    
    /**
     * 
     * @param constructor
     */
    public void addConstructor(AMethodMeta constructor) {
    	getConstructors().add(constructor);
    }

	public List<AMethodMeta> getConstructors() {
		if(constructors == null){
	        constructors = new ArrayList<AMethodMeta>();
		}
		return constructors;
	}
    
    protected Set<Field> getFields() {
    	if(fields == null){
    	    fields = new HashSet<Field>();
    	}
		return fields;
	}
    
	/**
     * 
     * add
     * @param field
     */
    public void addField(Field e) {
        getFields().add(e);
    }
    
    @Override
	public boolean existStaticInitBlock() {
		for(AMethodMeta m : this.getMethods()){
			if(m.getName().equals(ByteCodeConstant.CLINIT)){
				return true;
			}
		}
    	return false;
	}

    @Override
    public boolean isChildOrEqual(AClass otherType) {
        return TypeUtils.isSubtyping(this, otherType);
    }
    
}
