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

import cn.wensiqun.asmsupport.core.exception.ClassException;
import cn.wensiqun.asmsupport.core.utils.lang.ClassUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;


/**
 * AClass Factory.
 *
 */
public abstract class AClassFactory {
	
    /**
     * Get a asmsupport class type. if parameter is non-array type than 
     * get a {@link cn.wensiqun.asmsupport.core.clazz.ProductClass.ProductClass}, 
     * if parameter is array type than get a {@link cn.wensiqun.asmsupport.core.clazz.ProductClass.ArrayClass}
     * 
     * @param type
     * @return
     */
    public static AClass getType(Class<?> type){
    	AClass aclass;
		if(type.isArray()){
			aclass = getArrayType(ClassUtils.getRootComponentType(type), Type.getType(type).getDimensions());
		}else{
			aclass = new ProductClass(type);
		}
		return aclass;
    }
    
    /**
     * Get array type.
     * 
     * @param rootEleType
     * @param dim
     * @return
     */
    public static ArrayClass getArrayType(Class<?> rootEleType, int dim){
        if(rootEleType.isArray()){
            throw new ClassException("the class " + rootEleType + " has already an array class");
        }
        return new ArrayClass(getType(rootEleType), dim);
    }
    
    /**
     * Get array type.
     * 
     * @param cls
     * @param dim
     * @return
     */
    public static ArrayClass getArrayType(AClass rootEleType, int dim){
    	if(rootEleType.isArray()){
            throw new ClassException("the class " + rootEleType + " has already an array class");
        }
		
        StringBuilder arrayClassDesc = new StringBuilder();
        int tmpDim = dim;
        while(tmpDim-- > 0){
        	arrayClassDesc.append("[");
        }
        arrayClassDesc.append(rootEleType.getDescription());
        return new ArrayClass(rootEleType, dim);
    }
    
    /**
     * Use it internal, this method will get a class it's we want to create.
     * 
     * @param version
     * @param access
     * @param name
     * @param superCls
     * @param interfaces
     * @return
     */
    protected static SemiClass newSemiClass(int version, int access, String name, Class<?> superCls, Class<?>[] interfaces){
        return new SemiClass(version, access, name, superCls, interfaces);
    }
    

}
