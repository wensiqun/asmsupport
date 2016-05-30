package cn.wensiqun.asmsupport.standard.def.clazz;

public interface ClassHolder {
	
	/**
	 * Get {@link BaseClass} according the specify {@link Class}, 
	 * but must return {@link AnyException} object if the specify 
	 * class is {@link AnyException}
	 * 
	 * @param clazz
	 * @return
	 */
	IClass getType(Class<?> clazz);
	
	IClass getType(String className);
	
	IClass getArrayType(Class<?> root, int dim);

	IClass getArrayType(IClass root, int dim);
	
}
