package sample;

import java.lang.reflect.InvocationTargetException;

import sample.create.CreateClass;
import sample.create.CreateEnum;
import sample.create.CreateInterface;
import sample.helloworld.HelloWorld;
import sample.operators.ArithmeticOperatorGenerate;
import sample.operators.ArrayOperatorGenerate;
import sample.operators.AssignmentGenerate;
import sample.operators.BitwiseOperatorGenerate;
import sample.operators.CrementOperatorGenerate;
import sample.operators.InstanceofOperatorGenerate;
import sample.operators.LogicalOperatorGenerate;
import sample.operators.MethodInvokeOperatorGenerate;
import sample.operators.RelationalOperatorGenerate;
import sample.operators.ReturnOperatorGenerate;
import sample.operators.TernaryOperatorGenerate;
import sample.value.BasicValueGenerate;
import sample.variable.LocalVariableExample;

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
