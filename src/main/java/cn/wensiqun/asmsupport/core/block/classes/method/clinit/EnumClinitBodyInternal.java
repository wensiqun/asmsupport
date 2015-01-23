package cn.wensiqun.asmsupport.core.block.classes.method.clinit;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.method.AbstractMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.array.ArrayValue;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.generic.method.IEnumStaticBlockBody;

/**
 * 
 *
 */
public abstract class EnumClinitBodyInternal extends AbstractMethodBody implements IEnumStaticBlockBody {

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
		Parameterized[] argus;
		  
		private EnumConstructorInfo(String name, Parameterized[] argus) {
			super();
			this.name = name;
			this.argus = argus;
		}
	}

	@Override
	public void newEnum(String name, Parameterized... argus) {
        if(!ModifierUtils.isEnum(getMethodOwner().getModifiers())){
        	throw new IllegalArgumentException("cannot create an enum constant cause by current class is not enum type");
        }
        GlobalVariable constant = getMethodOwner().getGlobalVariable(name);
        if(!ModifierUtils.isEnum(constant.getGlobalVariableMeta().getModifiers())){
        	throw new IllegalArgumentException("cannot new an enum instant assign to non-enum type variable");
        }
        enumArgumentsList.add(new EnumConstructorInfo(name, argus));
    }
	
	/**
	 * 将构造枚举类型的操作添加到执行队列
	 */
	private void constructEachEnumConstant(){

		constructEnumField();
		
		if(getMethodOwner().getEnumNum() != enumArgumentsList.size()){
			throw new ASMSupportException("exist unassign enum constant!");
		}
		
		Parameterized[] values = new Parameterized[getMethodOwner().getEnumNum()];
		GlobalVariable enumConstant;
		int i = 0;
		for(EnumConstructorInfo enumArgu : enumArgumentsList){
			enumConstant = getMethodOwner().getGlobalVariable(enumArgu.name);
			
			values[i] = enumConstant;
			String enumName = enumArgu.name;
			Parameterized[] otherArgus = enumArgu.argus;
	        Parameterized[] enumArgus = new Parameterized[otherArgus.length + 2];
	        enumArgus[0] = Value.value(enumName);
	        enumArgus[1] = Value.value(i);
	        System.arraycopy(otherArgus, 0, enumArgus, 2, otherArgus.length);
	        
	        MethodInvoker mi = _new(getMethodOwner(), enumArgus);
	        _assign(enumConstant, mi);
	        i++;
		}
		
		GlobalVariable gv = getMethodOwner().getGlobalVariable("ENUM$VALUES");
		
		ArrayValue av = _newArrayWithValue(AClassFactory.getArrayClass(getMethodOwner(), 1), values);
		_assign(gv, av);
	}
	
	@Override
	public void generateBody() {
		
		constructEachEnumConstant();
		
		body();
	}
	
	/**
	 * call newEnum method at this method.
	 * get some information about current enum type constructor
	 * 
	 * 在此方法中调用newEnum方法， 获取构造枚举列表中每个枚举类型需要的参数信息，枚举类型名等信息。
	 * 
	 */
	public abstract void constructEnumField();
	
}
