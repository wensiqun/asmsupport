package cn.wensiqun.asmsupport.generic.creator;

import cn.wensiqun.asmsupport.core.clazz.AClass;

/**
 * 
 * 
 * @author sqwen
 *
 * @param <_StaticBlockBody>
 * @param <_ConstructorBody>
 * @param <_MethodBody>
 * @param <_StaticMethodBody>
 * @param <_FieldCreator>
 * @param <_MethodCreator>
 */
public interface IClassCreator<_StaticBlockBody, _ConstructorBody, _MethodBody, _StaticMethodBody, _FieldCreator, _MethodCreator> extends IClassContext {
   
	/**
	 * 
	 * Create a field for a class.
	 * 
	 * @param name            the field name
	 * @param modifiers       the field modifiers
	 * @param fieldClass      the field type
	 * @return
	 */
	_FieldCreator createField(String name, int modifiers, AClass fieldClass);
    
	/**
	 * Create a method 
	 * 
	 * @param access          the method modifiers
	 * @param name            the method name
	 * @param argClasses      the method argument type list
	 * @param argNames        the method arguments name list
	 * @param returnClass     the method return class
	 * @param exceptions      throw exceptions for this method
	 * @param body            method body that is method logic implementation
	 * @return
	 */
	_MethodCreator createMethod(int access, String name, AClass[] argTypes,
            String[] argNames, AClass returnClass, AClass[] exceptions,
            _MethodBody body);
    
	/**
	 * Create a static method 
	 * 
	 * @param access          the method modifiers
	 * @param name            the method name
	 * @param argClasses      the method argument type list
	 * @param argNames        the method arguments name list
	 * @param returnClass     the method return class
	 * @param exceptions      throw exceptions for this method
	 * @param body            method body that is method logic implementation
	 * @return
	 */
	_MethodCreator createStaticMethod(int access, String name, AClass[] argClasses,
            String[] argNames, AClass returnClass, AClass[] exceptions,
            _StaticMethodBody body);

	/**
	 * 
	 * create constructor.
	 * 
	 * @param access
	 * @param argTypes
	 * @param argNames
	 * @param body
	 * @return
	 */
	_MethodCreator createConstructor(int access, AClass[] argTypes,
            String[] argNames, _ConstructorBody body);

	_MethodCreator createStaticBlock(_StaticBlockBody block);
}
