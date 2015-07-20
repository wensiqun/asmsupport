package cn.wensiqun.asmsupport.standard.def.clazz;

public interface ClassHolder {

	/*IClass loadType(Class<?> clazz);
	
	IClass loadType(String name);*/
	
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
	
	IClass getArrayClass(Class<?> root, int dim);

	IClass getArrayClass(IClass root, int dim);
	
}
