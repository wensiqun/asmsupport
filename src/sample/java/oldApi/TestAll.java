package oldApi;

import java.lang.reflect.InvocationTargetException;

import oldApi.create.CreateClass;
import oldApi.create.CreateEnum;
import oldApi.create.CreateInterface;
import oldApi.helloworld.HelloWorld;
import oldApi.operators.ArithmeticOperatorGenerate;
import oldApi.operators.ArrayOperatorGenerate;
import oldApi.operators.AssignmentGenerate;
import oldApi.operators.BitwiseOperatorGenerate;
import oldApi.operators.CrementOperatorGenerate;
import oldApi.operators.InstanceofOperatorGenerate;
import oldApi.operators.LogicalOperatorGenerate;
import oldApi.operators.MethodInvokeOperatorGenerate;
import oldApi.operators.RelationalOperatorGenerate;
import oldApi.operators.ReturnOperatorGenerate;
import oldApi.operators.TernaryOperatorGenerate;
import oldApi.value.BasicValueGenerate;
import oldApi.variable.LocalVariableExample;

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
