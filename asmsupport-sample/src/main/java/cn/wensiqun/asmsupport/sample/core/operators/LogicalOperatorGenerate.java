package cn.wensiqun.asmsupport.sample.core.operators;


import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

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
		ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5,
				Opcodes.ACC_PUBLIC,
				"generated.operators.LogicalOperatorGenerateExample", null,
				null);

		/*
		 * 生成一个main方法，方法内容和willGenerate内容相同
		 */
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new IClass[] { classLoader.getType(String[].class) },
				new String[] { "args" }, null, null, new KernelMethodBody() {

					@Override
					public void body(LocalVariable... argus) {
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Conditional AND (&&)", "false && false", (false && false),
								"false && true", (false && true), "true && false",
								(true && false), "true && true", (true && true));*/
						call(systemOut, "printf", 
								val("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								val("Conditional AND (&&)"), 
								val("false && false"), 
								and(val(false), val(false)), 
								val("false && true"), 
								and(val(false), val(true)), 
								val("true && false"), 
								and(val(true), val(false)),  
								val("true && true"), 
								and(val(true), val(true))
								);

						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Conditional OR (||)", "false || false", (false || false),
								"false || true", (false || true), "true || false",
								(true || false), "true || true", (true || true));*/
						call(systemOut, "printf", 
								val("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								val("Conditional OR (||)"), 
								val("false || false"),  or(val(false), val(false)), 
								val("false || true"),  or(val(false), val(true)), 
								val("true || false"),  or(val(true), val(false)),  
								val("true || true"),  or(val(true), val(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Boolean logical AND (&)", "false & false", (false & false),
								"false & true", (false & true), "true & false", (true & false),
								"true & true", (true & true));*/
						call(systemOut, "printf", 
								val("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								val("Boolean logical AND (&)"), 
								val("false & false"),  logicalAnd(val(false), val(false)), 
								val("false & true"),  logicalAnd(val(false), val(true)), 
								val("true & false"),  logicalAnd(val(true), val(false)),  
								val("true & true"),  logicalAnd(val(true), val(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Boolean logical inclusive OR (|)", "false | false",
								(false | false), "false | true", (false | true),
								"true | false", (true | false), "true | true", (true | true));*/
						call(systemOut, "printf", 
								val("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								val("Boolean logical inclusive OR (|)"), 
								val("false | false"),  logicalOr(val(false), val(false)), 
								val("false | true"),  logicalOr(val(false), val(true)), 
								val("true | false"),  logicalOr(val(true), val(false)),  
								val("true | true"),  logicalOr(val(true), val(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n",
								"Boolean logical exclusive OR (^)", "false ^ false",
								(false ^ false), "false ^ true", (false ^ true),
								"true ^ false", (true ^ false), "true ^ true", (true ^ true));*/
						call(systemOut, "printf", 
								val("%s\n%s: %b\n%s: %b\n%s: %b\n%s: %b\n\n"), 
								val("Boolean logical exclusive OR (^)"), 
								val("false ^ false"),  logicalXor(val(false), val(false)), 
								val("false ^ true"),  logicalXor(val(false), val(true)), 
								val("true ^ false"),  logicalXor(val(true), val(false)),  
								val("true ^ true"),  logicalXor(val(true), val(true))
								);
						
						/*System.out.printf("%s\n%s: %b\n%s: %b\n", "Logical NOT (!)", "!false",
								(!false), "!true", (!true));*/
						call(systemOut, "printf", 
								val("%s\n%s: %b\n%s: %b\n"), 
								val("Logical NOT (!)"), 
								val("!false"),  no(val(false)), 
								val("!true"),  no(val(true))
								);
						
						return_();
					}
				});
		generate(creator);
	}

}
