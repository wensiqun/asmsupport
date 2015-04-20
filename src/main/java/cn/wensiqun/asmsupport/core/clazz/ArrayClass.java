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
package cn.wensiqun.asmsupport.core.clazz;

import java.io.Serializable;

import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;

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
    	version = cls.getVersion();
        mod = cls.getModifiers();
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
    public boolean isArray() {
        return true;
    }

    @Override
    public int getDimension() {
        return dim;
    }

    @Override
    public Field getField(String name) {
        throw new UnsupportedOperationException();
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
    public final GlobalVariable field(String name) {
        throw new UnsupportedOperationException();
    }

    public final Value getDefaultValue(){
        return Value.defaultValue(this);
    }
    
}
