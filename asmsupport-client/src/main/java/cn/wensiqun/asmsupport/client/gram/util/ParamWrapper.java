package cn.wensiqun.asmsupport.client.gram.util;

import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

public class ParamWrapper {

	private Param single;

	/**
	 * The Param array
	 */
	private Object array;
	
	private Param[] dimensions = null;
	
	public void setSingle(Param single) {
		if (array == null && dimensions == null) {
			this.single = single;
		} else {
			if(dimensions != null) {
				throw new ASMSupportException(
						"The field 'dimensions' has already initialized.");
			} else {
				throw new ASMSupportException(
						"The field 'array' has already initialized.");
			}
		}
	}

	public void setArray(Object array) {
		if (single == null && dimensions == null) {
			this.array = array;
		} else {
			if(dimensions != null) {
				throw new ASMSupportException(
						"The field 'allocateDim' has already initialized.");
			} else {
				throw new ASMSupportException(
						"The field 'single' has already initialized.");
			}
		}
	}
	

	public void setDimensions(Param[] dimensions) {
		if (single == null && array == null) {
			this.dimensions = dimensions;
		} else {
			if(single != null) {
				throw new ASMSupportException(
						"The field 'single' has already initialized.");
			} else {
				throw new ASMSupportException(
						"The field 'array' has already initialized.");
			}
		}
	}

	public boolean isArray() {
		return array != null || dimensions != null;
	}

	public Param get() {
		return single;
	}

	public Param getArray(ProgramBlock<?> block, IClass type) {
		if(dimensions != null) {
			return block.makeArray(type, dimensions);
		} else {
			return block.newarray(type, array);
		}
	}

}