package cn.wensiqun.asmsupport.grammar.def;

import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public class IdentifyWrapper {
	
	public String iden;
	
	public int dim = 0;
	
	public IClass determineType(ProgramBlock<?> block, IClass type) {
		if(dim == 0) {
			return type;
		}
		return block.getArrayType(type, dim);
	}
}