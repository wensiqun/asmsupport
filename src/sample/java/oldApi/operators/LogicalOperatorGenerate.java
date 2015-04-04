package oldApi.operators;


import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

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
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[] { AClassFactory.defType(String[].class) }, 
				new String[] { "args" }, null, null, new StaticMethodBodyInternal() {

					@Override
					public void body(LocalVariable... argus) {
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Conditional AND (&&)", "false && false", (false && false),
								"false && true", (false && true), "true && false",
								(true && false), "true && true", (true && true));*/
						call(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Conditional AND (&&)"), 
								Value.value("false && false"), 
								and(Value.value(false), Value.value(false)), 
								Value.value("false && true"), 
								and(Value.value(false), Value.value(true)), 
								Value.value("true && false"), 
								and(Value.value(true), Value.value(false)),  
								Value.value("true && true"), 
								and(Value.value(true), Value.value(true))
								);

						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Conditional OR (||)", "false || false", (false || false),
								"false || true", (false || true), "true || false",
								(true || false), "true || true", (true || true));*/
						call(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Conditional OR (||)"), 
								Value.value("false || false"),  or(Value.value(false), Value.value(false)), 
								Value.value("false || true"),  or(Value.value(false), Value.value(true)), 
								Value.value("true || false"),  or(Value.value(true), Value.value(false)),  
								Value.value("true || true"),  or(Value.value(true), Value.value(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Boolean logical AND (&)", "false & false", (false & false),
								"false & true", (false & true), "true & false", (true & false),
								"true & true", (true & true));*/
						call(systemOut, "printf", 
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
						call(systemOut, "printf", 
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
						call(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Boolean logical exclusive OR (^)"), 
								Value.value("false ^ false"),  logicalXor(Value.value(false), Value.value(false)), 
								Value.value("false ^ true"),  logicalXor(Value.value(false), Value.value(true)), 
								Value.value("true ^ false"),  logicalXor(Value.value(true), Value.value(false)),  
								Value.value("true ^ true"),  logicalXor(Value.value(true), Value.value(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n", "Logical NOT (!)", "!false",
								(!false), "!true", (!true));*/
						call(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n"), 
								Value.value("Logical NOT (!)"), 
								Value.value("!false"),  no(Value.value(false)), 
								Value.value("!true"),  no(Value.value(true))
								);
						
						return_();
					}
				});
		generate(creator);
	}

}
