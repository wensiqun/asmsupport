package cn.wensiqun.asmsupport.client.gram.util;

import cn.wensiqun.asmsupport.client.block.ConstructorBody;
import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.standard.def.IParam;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.utils.lang.StringUtils;

public class PrimaryData {

	private Param letter;
	
	private Param this_;
	
	private String superMember;
	
	private Param expression;
	
	private Param allocation;
	
	private IClass resultType;
	
	private String name;
	
	private Param value;

	public Param getLetter() {
		return letter;
	}

	public void setLetter(Param letter) {
		this.letter = letter;
	}

	public Param getThis() {
		return this_;
	}

	public void setThis(Param this_) {
		this.this_ = this_;
	}

	public String getSuperMember() {
		return superMember;
	}

	public void setSuperMember(String superMember) {
		this.superMember = superMember;
	}

	public Param getExpression() {
		return expression;
	}

	public void setExpression(Param expression) {
		this.expression = expression;
	}

	public Param getAllocation() {
		return allocation;
	}

	public void setAllocation(Param allocation) {
		this.allocation = allocation;
	}

	public IClass getResultType() {
		return resultType;
	}

	public void setResultType(IClass resultType) {
		this.resultType = resultType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Param getValue() {
		return value;
	}
	
	public PrimaryData arrayLoad(ProgramBlock<?> block, Param dim) {
		IParam owner = null;
		
		if(expression != null) {
			owner = expression;
		} else if (StringUtils.isNotBlank(superMember)) {
			owner = block.super_().field(superMember);
		} else if (allocation != null) {
			owner = allocation;
		} else if (StringUtils.isNotBlank(name)) {
			owner = name2Param(block, name);
		} else if (value != null) {
			owner = value;
		}
		
		if(owner != null) {
			PrimaryData data = new PrimaryData();
			data.value = block.arrayLength((Param)owner, dim);
			return data;
		}
		
		return null;
	}

    public PrimaryData field(ProgramBlock<?> block, String fieldName) {
		IParam owner = null;
    	if(letter != null) {
    		owner = letter;
    	} else if (this_ != null) {
    		owner = this_;
    	} else if (StringUtils.isNotBlank(superMember)) {
			owner = block.super_().field(superMember);
		} else if(expression != null) {
			owner = expression;
		} else if (allocation != null) {
			owner = allocation;
		} else if (resultType != null) {
			owner = block.val(Class.class);
		} else if (StringUtils.isNotBlank(name)) {
			owner = name2Param(block, name);
		}  else if (value != null) {
			owner = value;
		}
		if(owner != null) {
			PrimaryData data = new PrimaryData();
			data.value = (Param) owner.field(fieldName);
			return data;
		}
    	return null;
    }
    
    public PrimaryData call(ProgramBlock<?> block, Param[] args) {
    	String methodName = null;
    	IParam owner = null;
    	IClass type = null;
    	
    	if(StringUtils.isNotBlank(superMember)) {
    		methodName = superMember;
    		owner = block.super_();
    	} else if (StringUtils.isNotBlank(name)) {
    		int dotIdx = name.lastIndexOf('.');
    		if(dotIdx > -1) {
    			methodName = name.substring(dotIdx + 1);
    			name = name.substring(0, dotIdx);
    		} else {
    			methodName = name;
    			name = null;
    		}
    		
    		if(StringUtils.isNotBlank(name)) {
    			try {
    				type = block.getType(name);
    			} catch (Exception e) {
    				owner = name2Param(block, name);
    			} 
    		}
    	}
    	
    	if("super".equals(methodName)) {
    		PrimaryData data = new PrimaryData();
			data.value = ((ConstructorBody)block).supercall(args);
			return data;
    	} else if("this".equals(methodName)) {
    		throw new UnsupportedOperationException();
    	} else if (owner != null) {
    		PrimaryData data = new PrimaryData();
			data.value = block.call((Param)owner, methodName, args);
			return data;
    	} else if (type != null) {
    		PrimaryData data = new PrimaryData();
			data.value = block.call(type, methodName, args);
			return data;
    	}
    	
    	return null;
    }
    
    private IParam name2Param(ProgramBlock<?> block, String name) {
    	IParam owner = null;
    	String[] segments = name.split("\\.");
		for(String seg : segments) {
			if(owner == null) {
				if("this".equals(seg)) {
					owner = block.this_();
				} else if ("super".equals(seg)) {
					owner = block.super_();
				} else {
					owner = block.field(seg);
				}
			} else {
				owner = owner.field(seg);
			}
		}
		return owner;
    }
}
