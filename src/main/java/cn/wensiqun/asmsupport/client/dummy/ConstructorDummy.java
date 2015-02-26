package cn.wensiqun.asmsupport.client.dummy;

import cn.wensiqun.asmsupport.client.ConstructorBody;
import cn.wensiqun.asmsupport.core.clazz.AClass;

public class ConstructorDummy {

	ConstructorDummy _private(){
        return null;
    }
	
	ConstructorDummy _public(){
        return null;
    }
	
	ConstructorDummy _protected(){
        return null;
    }
	
	ConstructorDummy setArgumentTypes(AClass... argus){
        return null;
    }

	ConstructorDummy setArgumentTypes(Class<?>... argus){
        return null;
    }
	
	ConstructorDummy setArgumentNames(String... argNames){
        return null;
    }
	
	ConstructorDummy throwTypes(Class<?>... exceptionTypes){
        return null;
    }
	
	/**
	 * 
	 * @param exceptionTypes
	 * @return
	 */
	ConstructorDummy throwTypes(AClass[] exceptionTypes){
        return null;
    }
	
	/**
	 * 
	 * @param body
	 * @return
	 */
	ConstructorDummy body(ConstructorBody body){
        return null;
    }
	
}
