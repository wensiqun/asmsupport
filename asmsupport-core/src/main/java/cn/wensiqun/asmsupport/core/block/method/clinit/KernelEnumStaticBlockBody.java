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
package cn.wensiqun.asmsupport.core.block.method.clinit;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.block.method.AbstractKernelMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.KernelParame;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.array.KernelArrayValue;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.standard.block.method.IEnumStaticBlockBody;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 * 
 *
 */
public abstract class KernelEnumStaticBlockBody extends AbstractKernelMethodBody implements IEnumStaticBlockBody<KernelParame, LocalVariable> {

	private List<EnumConstructorInfo>  enumArgumentsList;
	
	protected List<String> enumNameList;
	
	public void setEnumNameList(List<String> enumNameList) {
		this.enumNameList = enumNameList;
	}

	@Override
	protected void init() {
		enumArgumentsList = new ArrayList<EnumConstructorInfo>(3);
		return;
	}
	
	private class EnumConstructorInfo{
		String name;
		KernelParame[] argus;
		  
		private EnumConstructorInfo(String name, KernelParame[] argus) {
			super();
			this.name = name;
			this.argus = argus;
		}
	}

	@Override
	public void constructEnumConst(String name, KernelParame... argus) {
        if(!ModifierUtils.isEnum(getMethodOwner().getModifiers())){
        	throw new IllegalArgumentException("cannot create an enum constant cause by current class is not enum type");
        }
        IFieldVar constant = val(getMethodOwner()).field(name);
        if(!ModifierUtils.isEnum(constant.getModifiers())){
        	throw new IllegalArgumentException("cannot new an enum instant assign to non-enum type variable");
        }
        enumArgumentsList.add(new EnumConstructorInfo(name, argus));
    }
	
	/**
	 * 将构造枚举类型的操作添加到执行队列
	 */
	private void constructEachEnumConstant(){

		constructEnumConsts();
		
		if(getMethodOwner().getEnumNum() != enumArgumentsList.size()){
			throw new ASMSupportException("exist unassign enum constant!");
		}
		
		KernelParame[] values = new KernelParame[getMethodOwner().getEnumNum()];
		GlobalVariable enumConstant;
		int i = 0;
		for(EnumConstructorInfo enumArgu : enumArgumentsList){
			enumConstant = val(getMethodOwner()).field(enumArgu.name);
			
			values[i] = enumConstant;
			String enumName = enumArgu.name;
			KernelParame[] otherArgus = enumArgu.argus;
	        KernelParame[] enumArgus = new KernelParame[otherArgus.length + 2];
	        enumArgus[0] = Value.value(enumName);
	        enumArgus[1] = Value.value(i);
	        System.arraycopy(otherArgus, 0, enumArgus, 2, otherArgus.length);
	        
	        MethodInvoker mi = new_(getMethodOwner(), enumArgus);
	        assign(enumConstant, mi);
	        i++;
		}
		
		GlobalVariable gv = val(getMethodOwner()).field("ENUM$VALUES");
		
		KernelArrayValue av = newarray(AClassFactory.getArrayType(getMethodOwner(), 1), values);
		assign(gv, av);
	}
	
	@Override
	public void generateBody() {
		
		constructEachEnumConstant();
		
		body();
	}
	
	
}
