package cn.wensiqun.asmsupport.sample.core;

import java.lang.reflect.InvocationTargetException;

import cn.wensiqun.asmsupport.sample.core.create.CreateClass;
import cn.wensiqun.asmsupport.sample.core.create.CreateEnum;
import cn.wensiqun.asmsupport.sample.core.create.CreateInterface;
import cn.wensiqun.asmsupport.sample.core.helloworld.HelloWorld;
import cn.wensiqun.asmsupport.sample.core.operators.ArithmeticOperatorGenerate;
import cn.wensiqun.asmsupport.sample.core.operators.ArrayOperatorGenerate;
import cn.wensiqun.asmsupport.sample.core.operators.AssignmentGenerate;
import cn.wensiqun.asmsupport.sample.core.operators.BitwiseOperatorGenerate;
import cn.wensiqun.asmsupport.sample.core.operators.CrementOperatorGenerate;
import cn.wensiqun.asmsupport.sample.core.operators.InstanceofOperatorGenerate;
import cn.wensiqun.asmsupport.sample.core.operators.LogicalOperatorGenerate;
import cn.wensiqun.asmsupport.sample.core.operators.MethodInvokeOperatorGenerate;
import cn.wensiqun.asmsupport.sample.core.operators.RelationalOperatorGenerate;
import cn.wensiqun.asmsupport.sample.core.operators.ReturnOperatorGenerate;
import cn.wensiqun.asmsupport.sample.core.operators.TernaryOperatorGenerate;
import cn.wensiqun.asmsupport.sample.core.value.BasicValueGenerate;
import cn.wensiqun.asmsupport.sample.core.variable.LocalVariableExample;

public class CoreRunner {

	/**
	 * @param args
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchFieldException 
	 */
	public static void main(String... args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
		//Helloworld
		HelloWorld.main(args);
		
		//create
		CreateClass.main(args);
		CreateEnum.main(args);
		CreateInterface.main(args);
		
		//value
		BasicValueGenerate.main(args);
		
		//variable
		LocalVariableExample.main(args);
		
		//operators
		ReturnOperatorGenerate.main(args);
		AssignmentGenerate.main(args);
		CrementOperatorGenerate.main(args);
		ArithmeticOperatorGenerate.main(args);
		BitwiseOperatorGenerate.main(args);
		RelationalOperatorGenerate.main(args);
		LogicalOperatorGenerate.main(args);
		TernaryOperatorGenerate.main(args);
		InstanceofOperatorGenerate.main(args);
		ArrayOperatorGenerate.main(args);
		MethodInvokeOperatorGenerate.main(args);
	}

}
