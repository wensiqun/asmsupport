package demo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.clinit.ClinitBody;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.block.method.init.InitBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import demo.block.Synchronized;
import demo.control.ForEach;
import demo.control.ForLoop;
import demo.control.IfElse;
import demo.control.TryFinally;
import demo.control.WhileAndDoWhile;
import demo.operators.ArithmeticCalculations;
import demo.operators.Assignment;
import demo.operators.BitwiseOperators;
import demo.operators.Crementforfloat;
import demo.operators.Increment;
import demo.operators.IncrementAndDecrement;
import demo.operators.IncrementAndDecrementInExpression;
import demo.operators.InstanceofOperators;
import demo.operators.LogicalOperators;
import demo.operators.RelationalOperators;
import demo.operators.TernaryOperators;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
@org.junit.Ignore
public class Demo {

	static GlobalVariable out = CreateMethod.out;
	
	/*public static void main(String[] args){
		Label l = new Label();
		System.out.println(l.info);
	}*/
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "demo.Example", null, null);
		creator.createGlobalVariable("testString", Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, AClass.STRING_ACLASS);
		creator.createGlobalVariable("testInt",  Opcodes.ACC_PUBLIC, AClass.INT_ACLASS);
		creator.createStaticBlock(new ClinitBody(){
			@Override
			public void body() {
				GlobalVariable testString = getMethodOwner().getGlobalVariable("testString");
				assign(testString, Value.value("testString"));
				runReturn();
			}
		});
        creator.createConstructor(null, null, new InitBody(){

			@Override
			public void body(LocalVariable... argus) {
				invokeSuperConstructor();
				GlobalVariable testArray = getMethodOwner().getGlobalVariable("testArray");
				assign(testArray, newArray(AClassFactory.getArrayClass(int.class, 3), Value.value(1), Value.value(2), Value.value(3)));
				runReturn();
			}
			
		}, Opcodes.ACC_PUBLIC);
		creator.createGlobalVariable("testArray", Opcodes.ACC_PUBLIC, AClassFactory.getArrayClass(int.class, 3));
		
		
		new Assignment(creator).createMethod();
		new IncrementAndDecrement(creator).createMethod();
		new Increment(creator).createMethod();
		new IncrementAndDecrementInExpression(creator).createMethod();
		new Crementforfloat(creator).createMethod();
		new ArithmeticCalculations(creator).createMethod();
		new BitwiseOperators(creator).createMethod();
		new RelationalOperators(creator).createMethod();
		new LogicalOperators(creator).createMethod();
		new TernaryOperators(creator).createMethod();
		new InstanceofOperators(creator).createMethod();
		new IfElse(creator).createMethod();
		new WhileAndDoWhile(creator).createMethod();
		new ForEach(creator).createMethod();
		new ForLoop(creator).createMethod();
		//new TryCatch(creator).createMethod();
		new TryFinally(creator).createMethod();
		new Synchronized(creator).createMethod();
		otherMethod(creator);
		
		creator.createMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null, 
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
			    new CommonMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						AClass curCls = getMethodOwner();
						
						
						LocalVariable self = createVariable("myMain", this.getMethodOwner(), false, invokeConstructor(curCls));
						invoke(out, "println",Value.value("*****************************************************************"));
						invoke(out, "println", Value.value("**             Several assignment operators method             **"));
						invoke(out, "println", Value.value("*****************************************************************"));
						invoke(self, "assignment");
						invoke(out, "println", Value.value("*****************************************************************"));
						invoke(out, "println", Value.value("**            Increment and Decrement: Demonstrate ++          **"));
						invoke(out, "println", Value.value("*****************************************************************"));
						invoke(self, "increment");
						invoke(out, "println", Value.value("*****************************************************************"));
						invoke(out, "println", Value.value("**             The increment and decrement operators           **"));
						invoke(out, "println", Value.value("*****************************************************************"));
						invoke(self, "incrementAndDecrement");
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("** Using the increment and decrement operators in an expression **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "incrementAndDecrementInExpression");
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**         Using ++ and -- with floating-point variables        **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "crementforfloat");
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                  Arithmetic Calculations                     **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "arithmeticCalculations");
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                       Method Invoke                          **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "methodInvoke");
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                 Array Operator Invoke                       **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "arrayOperator");
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                       CheckCast Invoke                       **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "checkCast", Value.getNullValue(AClass.INTEGER_WRAP_ACLASS), Value.value(1));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                 String Append Operator                       **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "stringappend", Value.value("Hello"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                       Bitwise Operator                       **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "bitwiseOperators");
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                       Relational Operator                    **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "relationalOperators");
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                       Logical Operator                       **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "logicalOperators");
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                       Ternary Operator                       **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "ternaryOperators");
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                       Instanceof Operator                    **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "instanceofOperators");
						
						
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                      IF Else Operator                        **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "ifelse", Value.value("A"), Value.value(0));
						invoke(self, "ifelse", Value.value("A"), Value.value(1));
						invoke(self, "ifelse", Value.value("B"), Value.value(0));
						invoke(self, "ifelse", Value.value("B"), Value.value(1));
						invoke(self, "ifelse", Value.value("C"), Value.value(0));
						invoke(self, "ifelse", Value.value("C"), Value.value(1));

						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                      While Do While Operator                 **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "whileAndDoWhile");

						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                         For Each Operator                    **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "foreach");

						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                         For Loop Operator                    **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "forloop");

						/*invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                        Try Catch Operator                    **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "tryCatch");*/
						
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                        Try Finally Operator                  **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "tryFinally");
						
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(out, "println", Value.value("**                        Synchronized Operato                  **"));
						invoke(out, "println", Value.value("******************************************************************"));
						invoke(self, "synchronizedMethod");
						
						runReturn();
					}
		});
		
		creator.setClassOutPutPath(".\\target\\generate\\");
    	
		Class<?> example = creator.startup();
		
        try {
            Object obj = example.newInstance();
			Method main = example.getMethod("main", String[].class);
			main.invoke(obj, new Object[]{null});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void otherMethod(ClassCreator creator){
		creator.createMethod("methodInvoke", null, null, null, null, Opcodes.ACC_PUBLIC, new CommonMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				
                AClass msu = AClassFactory.getProductClass(MyStaticUtils.class);
                invokeStatic(AClassFactory.getProductClass(msu.getSuperClass()), "s");
                
				runReturn();
			}
			
		});
		creator.createMethod("arrayOperator", null, null, null, null, Opcodes.ACC_PUBLIC, new CommonMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				/*LocalVariable lav = createArrayVariable("array1", AClassFactory.getArrayClass(int[][][].class), false, Value.value(1), Value.value(2), Value.value(3));
				invoke(out, "println", arrayLoad(lav, Value.value(0), Value.value(0)));
				invoke(out,"println", arrayLoad(lav, Value.value(0), Value.value(0), Value.value(0)));
				
				arrayStore(lav, Value.value(10), Value.value(0), Value.value(0), Value.value(0));
				invoke(out, "println", arrayLoad(lav, Value.value(0), Value.value(0), Value.value(0)));*/
				runReturn();
			}
			
		});
		creator.createMethod("checkCast", new AClass[]{AClass.INTEGER_WRAP_ACLASS, AClass.INT_ACLASS}, 
				new String[]{"arg1", "arg2"},  null, null, Opcodes.ACC_PUBLIC, new CommonMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						AClass arrayList = AClassFactory.getProductClass(ArrayList.class);
						LocalVariable list1 = createVariable("list1", AClassFactory.getProductClass(List.class), false,invokeConstructor(arrayList, Value.value(1)));
						createVariable("list2", arrayList, false, checkCast(list1, arrayList));
						invoke(out, "println", Value.value("run checkCast test cast method"));
						runReturn();
					}
			
		});
		creator.createMethod("stringappend", new AClass[]{AClass.STRING_ACLASS}, new String[]{"arg"}, null, null, Opcodes.ACC_PUBLIC,
		        new CommonMethodBody(){
			        
			        @Override
		            public void body(LocalVariable... argus) {
			        	LocalVariable arg = argus[0];
			        	LocalVariable b1 = createVariable("b1", AClass.STRING_ACLASS, false, Value.value(" world"));
			        	invoke(out,  "println", append(arg, b1, Value.value(" "), Value.value(true), Value.value(" to here "), Value.value(1L)));
					    runReturn();
		    	    }
	            }
	    );
	}

}
