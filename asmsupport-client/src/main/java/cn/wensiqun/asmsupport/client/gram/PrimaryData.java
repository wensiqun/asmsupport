package cn.wensiqun.asmsupport.client.gram;

import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.standard.def.IParam;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;

public class PrimaryData {

	private Param owner;
	
	private String fieldOrMethod;

	private String superFieldOrMethod;

	public Param getOwner() {
		return owner;
	}

	public void setOwner(Param owner) {
		this.owner = owner;
	}

	public String getFieldOrMethod() {
		return fieldOrMethod;
	}

	public void setFieldOrMethod(String fieldOrMethod) {
		this.fieldOrMethod = fieldOrMethod;
	}

	public String getSuperFieldOrMethod() {
		return superFieldOrMethod;
	}

	public void setSuperFieldOrMethod(String superFieldOrMethod) {
		this.superFieldOrMethod = superFieldOrMethod;
	}
	
	public PrimaryData loadArrayElement(ProgramBlock<?> block, Param dim) {
		PrimaryData data = new PrimaryData();
		if(owner != null) {
			data.setOwner(block.arrayLoad(owner, dim));
		} else if (StringUtils.isNotBlank(fieldOrMethod)) {
			
		}
		
		
		return data;
	}
	
	
}
