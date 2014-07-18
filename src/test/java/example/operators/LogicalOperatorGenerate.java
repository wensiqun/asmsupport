package example.operators;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import example.AbstractExample;

public class LogicalOperatorGenerate extends AbstractExample {

	/*
	 * 
	 */
	public static void willGenerate(String[] args) {
		// create truth table for && (conditional AND) operator
		System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
				"Conditional AND (&&)", 
				"false && false", 
				(false && false),
				"false && true", 
				(false && true), 
				"true && false",
				(true && false), 
				"true && true", 
				(true && true));

		// create truth table for || (conditional OR) operator
		System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
				"Conditional OR (||)", "false || false", (false || false),
				"false || true", (false || true), "true || false",
				(true || false), "true || true", (true || true));

		// create truth table for & (boolean logical AND) operator
		System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
				"Boolean logical AND (&)", "false & false", (false & false),
				"false & true", (false & true), "true & false", (true & false),
				"true & true", (true & true));

		// create truth table for | (boolean logical inclusive OR) operator
		System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
				"Boolean logical inclusive OR (|)", "false | false",
				(false | false), "false | true", (false | true),
				"true | false", (true | false), "true | true", (true | true));

		// create truth table for ^ (boolean logical exclusive OR) operator
		System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
				"Boolean logical exclusive OR (^)", "false ^ false",
				(false ^ false), "false ^ true", (false ^ true),
				"true ^ false", (true ^ false), "true ^ true", (true ^ true));

		// create truth table for ! (logical negation) operator
		System.out.printf("%s\n%s: %b\n%s: %b\n", "Logical NOT (!)", "!false",
				(!false), "!true", (!true));
	}

	public static void main(String[] args) {
		//willGenerate(args);
		ClassCreator creator = new ClassCreator(Opcodes.V1_5,
				Opcodes.ACC_PUBLIC,
				"generated.operators.LogicalOperatorGenerateExample", null,
				null);

		/*
		 * 生成一个main方法，方法内容和willGenerate内容相同
		 */
		creator.createStaticMethod("main", new AClass[] { AClassFactory.getProductClass(String[].class) }, new String[] { "args" }, null, null, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody() {

					@Override
					public void body(LocalVariable... argus) {
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Conditional AND (&&)", "false && false", (false && false),
								"false && true", (false && true), "true && false",
								(true && false), "true && true", (true && true));*/
						invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Conditional AND (&&)"), 
								Value.value("false && false"), 
								conditionalAnd(Value.value(false), Value.value(false)), 
								Value.value("false && true"), 
								conditionalAnd(Value.value(false), Value.value(true)), 
								Value.value("true && false"), 
								conditionalAnd(Value.value(true), Value.value(false)),  
								Value.value("true && true"), 
								conditionalAnd(Value.value(true), Value.value(true))
								);

						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Conditional OR (||)", "false || false", (false || false),
								"false || true", (false || true), "true || false",
								(true || false), "true || true", (true || true));*/
						invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Conditional OR (||)"), 
								Value.value("false || false"),  conditionalOr(Value.value(false), Value.value(false)), 
								Value.value("false || true"),  conditionalOr(Value.value(false), Value.value(true)), 
								Value.value("true || false"),  conditionalOr(Value.value(true), Value.value(false)),  
								Value.value("true || true"),  conditionalOr(Value.value(true), Value.value(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Boolean logical AND (&)", "false & false", (false & false),
								"false & true", (false & true), "true & false", (true & false),
								"true & true", (true & true));*/
						invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Boolean logical AND (&)"), 
								Value.value("false & false"),  logicalAnd(Value.value(false), Value.value(false)), 
								Value.value("false & true"),  logicalAnd(Value.value(false), Value.value(true)), 
								Value.value("true & false"),  logicalAnd(Value.value(true), Value.value(false)),  
								Value.value("true & true"),  logicalAnd(Value.value(true), Value.value(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Boolean logical inclusive OR (|)", "false | false",
								(false | false), "false | true", (false | true),
								"true | false", (true | false), "true | true", (true | true));*/
						invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Boolean logical inclusive OR (|)"), 
								Value.value("false | false"),  logicalOr(Value.value(false), Value.value(false)), 
								Value.value("false | true"),  logicalOr(Value.value(false), Value.value(true)), 
								Value.value("true | false"),  logicalOr(Value.value(true), Value.value(false)),  
								Value.value("true | true"),  logicalOr(Value.value(true), Value.value(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Boolean logical exclusive OR (^)", "false ^ false",
								(false ^ false), "false ^ true", (false ^ true),
								"true ^ false", (true ^ false), "true ^ true", (true ^ true));*/
						invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Boolean logical exclusive OR (^)"), 
								Value.value("false ^ false"),  logicalXor(Value.value(false), Value.value(false)), 
								Value.value("false ^ true"),  logicalXor(Value.value(false), Value.value(true)), 
								Value.value("true ^ false"),  logicalXor(Value.value(true), Value.value(false)),  
								Value.value("true ^ true"),  logicalXor(Value.value(true), Value.value(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n", "Logical NOT (!)", "!false",
								(!false), "!true", (!true));*/
						invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n"), 
								Value.value("Logical NOT (!)"), 
								Value.value("!false"),  not(Value.value(false)), 
								Value.value("!true"),  not(Value.value(true))
								);
						
						runReturn();
					}
				});
		generate(creator);
	}

}
