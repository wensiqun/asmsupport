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

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;

/**
 * Indicate Array Class
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class ArrayClass extends BaseClass {
	
    private IClass elementType;

    private String desc; 
    
    /** indicate the dimension of this class if this class is a array type, otherwise the this dim is -1 */
    protected int dim;
    
    /**
     * 
     * @param elementType
     * @param dim
     */
    public ArrayClass(IClass elementType, int dim, ASMSupportClassLoader classLoader) {
    	super(classLoader);
    	version = elementType.getVersion();
        mod = elementType.getModifiers();
        superClass = classLoader.getType(Object.class);
        interfaces = new IClass[]{classLoader.getType(Cloneable.class), classLoader.getType(Serializable.class)};
        
        this.elementType = elementType;
        this.dim = dim;
        StringBuilder descsb = new StringBuilder();
        int tmpDim = dim;
        while(tmpDim>0){
            descsb.append("[");
            tmpDim--;
        }
        descsb.append(elementType.getDescription());
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
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public IClass getNextDimType(){
        if(dim > 1){
            return new ArrayClass(elementType, dim - 1, classLoader);
        }else{
            return elementType;
        }
    }
    
    @Override
    public IClass getRootComponentClass(){
        return elementType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(elementType.getName());
        for(int i=0; i<dim; i++){
            sb.append("[]");
        }
        return sb.toString();
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public Collection<AMethodMeta> getDeclaredMethods() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public AMethodMeta getDeclaredMethod(String name, IClass... parameterTypes) {
        return null;
	}
    
}
