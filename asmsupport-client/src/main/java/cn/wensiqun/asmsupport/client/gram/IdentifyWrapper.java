package cn.wensiqun.asmsupport.client.gram;

import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

class IdentifyWrapper {
	
	String iden;
	
	int dim = 0;
	
	public IClass determineType(ProgramBlock<?> block, IClass type) {
		if(dim == 0) {
			return type;
		}
		return block.getArrayType(type, dim);
	}
}