package example.operators;


import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
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
		ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5,
				Opcodes.ACC_PUBLIC,
				"generated.operators.LogicalOperatorGenerateExample", null,
				null);

		/*
		 * 生成一个main方法，方法内容和willGenerate内容相同
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[] { AClassFactory.getProductClass(String[].class) }, 
				new String[] { "args" }, null, null, new StaticMethodBodyInternal() {

					@Override
					public void body(LocalVariable... argus) {
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Conditional AND (&&)", "false && false", (false && false),
								"false && true", (false && true), "true && false",
								(true && false), "true && true", (true && true));*/
						_invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Conditional AND (&&)"), 
								Value.value("false && false"), 
								_conditionalAnd(Value.value(false), Value.value(false)), 
								Value.value("false && true"), 
								_conditionalAnd(Value.value(false), Value.value(true)), 
								Value.value("true && false"), 
								_conditionalAnd(Value.value(true), Value.value(false)),  
								Value.value("true && true"), 
								_conditionalAnd(Value.value(true), Value.value(true))
								);

						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Conditional OR (||)", "false || false", (false || false),
								"false || true", (false || true), "true || false",
								(true || false), "true || true", (true || true));*/
						_invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Conditional OR (||)"), 
								Value.value("false || false"),  _conditionalOr(Value.value(false), Value.value(false)), 
								Value.value("false || true"),  _conditionalOr(Value.value(false), Value.value(true)), 
								Value.value("true || false"),  _conditionalOr(Value.value(true), Value.value(false)),  
								Value.value("true || true"),  _conditionalOr(Value.value(true), Value.value(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Boolean logical AND (&)", "false & false", (false & false),
								"false & true", (false & true), "true & false", (true & false),
								"true & true", (true & true));*/
						_invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Boolean logical AND (&)"), 
								Value.value("false & false"),  _logicalAnd(Value.value(false), Value.value(false)), 
								Value.value("false & true"),  _logicalAnd(Value.value(false), Value.value(true)), 
								Value.value("true & false"),  _logicalAnd(Value.value(true), Value.value(false)),  
								Value.value("true & true"),  _logicalAnd(Value.value(true), Value.value(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Boolean logical inclusive OR (|)", "false | false",
								(false | false), "false | true", (false | true),
								"true | false", (true | false), "true | true", (true | true));*/
						_invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Boolean logical inclusive OR (|)"), 
								Value.value("false | false"),  _logicalOr(Value.value(false), Value.value(false)), 
								Value.value("false | true"),  _logicalOr(Value.value(false), Value.value(true)), 
								Value.value("true | false"),  _logicalOr(Value.value(true), Value.value(false)),  
								Value.value("true | true"),  _logicalOr(Value.value(true), Value.value(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Boolean logical exclusive OR (^)", "false ^ false",
								(false ^ false), "false ^ true", (false ^ true),
								"true ^ false", (true ^ false), "true ^ true", (true ^ true));*/
						_invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								Value.value("Boolean logical exclusive OR (^)"), 
								Value.value("false ^ false"),  _logicalXor(Value.value(false), Value.value(false)), 
								Value.value("false ^ true"),  _logicalXor(Value.value(false), Value.value(true)), 
								Value.value("true ^ false"),  _logicalXor(Value.value(true), Value.value(false)),  
								Value.value("true ^ true"),  _logicalXor(Value.value(true), Value.value(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n", "Logical NOT (!)", "!false",
								(!false), "!true", (!true));*/
						_invoke(systemOut, "printf", 
								Value.value("%s\n%s: %b\n%s: %b\n"), 
								Value.value("Logical NOT (!)"), 
								Value.value("!false"),  _not(Value.value(false)), 
								Value.value("!true"),  _not(Value.value(true))
								);
						
						_return();
					}
				});
		generate(creator);
	}

}
