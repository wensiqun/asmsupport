package example;

import java.lang.reflect.InvocationTargetException;

import example.create.CreateClass;
import example.create.CreateEnum;
import example.create.CreateInterface;
import example.helloworld.HelloWorld;
import example.operators.ArithmeticOperatorGenerate;
import example.operators.ArrayOperatorGenerate;
import example.operators.AssignmentGenerate;
import example.operators.BitwiseOperatorGenerate;
import example.operators.CrementOperatorGenerate;
import example.operators.InstanceofOperatorGenerate;
import example.operators.LogicalOperatorGenerate;
import example.operators.MethodInvokeOperatorGenerate;
import example.operators.RelationalOperatorGenerate;
import example.operators.ReturnOperatorGenerate;
import example.operators.TernaryOperatorGenerate;
import example.value.BasicValueGenerate;
import example.variable.LocalVariableExample;

public class TestAll {

	/**
	 * @param args
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchFieldException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
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
	
	@org.junit.Test
	public void test() throws Exception{
		main(null);
	}

}
