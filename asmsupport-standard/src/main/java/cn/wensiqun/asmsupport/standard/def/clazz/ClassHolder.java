package cn.wensiqun.asmsupport.standard.def.clazz;

public interface ClassHolder {

	IClass loadType(Class<?> clazz);
	
	IClass loadType(String name);
	
	/**
	 * Get {@link AClass} according the specify {@link Class}, 
	 * but must return {@link AnyException} object if the specify 
	 * class is {@link AnyException}
	 * 
	 * @param clazz
	 * @return
	 */
	@Deprecated
	AClass getType(Class<?> clazz);
	
	@Deprecated
	AClass getType(String className);
	
	@Deprecated
	ArrayClass getArrayClass(Class<?> root, int dim);

	@Deprecated
	ArrayClass getArrayClass(IClass root, int dim);
	
}
