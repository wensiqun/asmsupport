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

import cn.wensiqun.asmsupport.core.block.method.AbstractKernelMethodBody;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.array.KernelArrayValue;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.standard.block.method.IEnumStaticBlockBody;
import cn.wensiqun.asmsupport.standard.def.var.IFieldVar;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 */
public abstract class KernelEnumStaticBlockBody extends AbstractKernelMethodBody implements IEnumStaticBlockBody<KernelParam, LocalVariable> {

	private List<EnumConstructorInfo>  enumArgumentsList;
	
	protected List<String> enumNameList;
	
	public void setEnumNameList(List<String> enumNameList) {
		this.enumNameList = enumNameList;
	}

	@Override
	protected void init() {
		enumArgumentsList = new ArrayList<>(3);
		return;
	}
	
	private class EnumConstructorInfo{
		String name;
		KernelParam[] argus;
		  
		private EnumConstructorInfo(String name, KernelParam[] argus) {
			super();
			this.name = name;
			this.argus = argus;
		}
	}

	@Override
	public void constructEnumConst(String name, KernelParam... argus) {
        if(!ModifierUtils.isEnum(getMethodDeclaringClass().getModifiers())){
        	throw new IllegalArgumentException("cannot create an enum constant cause by current class is not enum type");
        }
        IFieldVar constant = val(getMethodDeclaringClass()).field(name);
        if(!ModifierUtils.isEnum(constant.getModifiers())){
        	throw new IllegalArgumentException("cannot new an enum instant assign to non-enum type variable");
        }
        enumArgumentsList.add(new EnumConstructorInfo(name, argus));
    }
	
	
	private void constructEachEnumConstant(){

		constructEnumConsts();
		
		if(getMethodDeclaringClass().getEnumNum() != enumArgumentsList.size()){
			throw new ASMSupportException("exist unassigned enum constant!");
		}
		
		KernelParam[] values = new KernelParam[getMethodDeclaringClass().getEnumNum()];
		GlobalVariable enumConstant;
		int i = 0;
		for(EnumConstructorInfo enumArg : enumArgumentsList){
			enumConstant = val(getMethodDeclaringClass()).field(enumArg.name);
			values[i] = enumConstant;
			String enumName = enumArg.name;
			KernelParam[] otherArgus = enumArg.argus;
	        KernelParam[] enumArgus = new KernelParam[otherArgus.length + 2];
	        enumArgus[0] = Value.value(getClassHolder(), enumName);
	        enumArgus[1] = Value.value(getClassHolder(), i);
	        System.arraycopy(otherArgus, 0, enumArgus, 2, otherArgus.length);
	        MethodInvoker mi = new_(getMethodDeclaringClass(), enumArgus);
	        assign(enumConstant, mi);
	        i++;
		}
		GlobalVariable gv = val(getMethodDeclaringClass()).field("ENUM$VALUES");
		KernelArrayValue av = newarray(getClassHolder().getArrayType(getMethodDeclaringClass(), 1), values);
		assign(gv, av);
	}
	
	@Override
	public void generateBody() {
		
		constructEachEnumConstant();
		
		body();
	}
	
	
}
