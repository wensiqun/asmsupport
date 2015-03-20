package sample.operators;


import sample.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class BitwiseOperatorGenerate extends AbstractExample {
	
	public static void main1(String args[]) {
	    String binary[] = {
	      "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
	      "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"
	    };
	    int a = 3; // 0 + 2 + 1 or 0011 in binary
	    int b = 6; // 4 + 2 + 0 or 0110 in binary
	    int c = a | b;
	    int d = a & b;
	    int e = a ^ b;
	    int f = (~a & b) | (a & ~b);
	    int g = ~a & 0x0f;
	   
	    System.out.println("        a = " + binary[a]);
	    System.out.println("        b = " + binary[b]);
	    System.out.println("      a|b = " + binary[c]);
	    System.out.println("      a&b = " + binary[d]);
	    System.out.println("      a^b = " + binary[e]);
	    System.out.println("~a&b|a&~b = " + binary[f]);
	    System.out.println("       ~a = " + binary[g]);
	    
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.BitwiseOperatorGenerateExample", null, null);
		
		/*
		 * 生成一个main方法，方法内容和main1内容相同
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.deftype(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				/*String binary[] = {
			      "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
			      "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"
			    };*/
				LocalVariable binary = arrayVar("binary", AClassFactory.defArrayType(String[].class), false, newarrayWithValue(AClassFactory.defArrayType(String[].class), stringValueArray(new String[]{
					      "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
					      "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"})));
			    //int a = 3; 
				LocalVariable a = var("a", AClass.INT_ACLASS, false, Value.value(3));
			    
			    //int b = 6; 
				LocalVariable b = var("b", AClass.INT_ACLASS, false, Value.value(6));
				
			    //int c = a | b;
				LocalVariable c = var("c", AClass.INT_ACLASS, false, this.bor(a, b));
				
			    //int d = a & b;
				LocalVariable d = var("d", AClass.INT_ACLASS, false, this.band(a, b));
				
			    //int e = a ^ b;
				LocalVariable e = var("e", AClass.INT_ACLASS, false, this.bxor(a, b));
				
			    //int f = (~a & b) | (a & ~b);
				LocalVariable f = var("f", AClass.INT_ACLASS, false, bor(band(reverse(a), b), band(a, reverse(b))));
				
			    //int g = ~a & 0x0f;
				LocalVariable g = var("g", AClass.INT_ACLASS, false, this.band(reverse(a), Value.value(0x0f)));
				
			    //System.out.println("        a = " + binary[a]);
				call(systemOut, "println", stradd(Value.value("        a = "), arrayLoad(binary, a)));
				
			    //System.out.println("        b = " + binary[b]);
				call(systemOut, "println", stradd(Value.value("        b = "), arrayLoad(binary, b)));
				
			    //System.out.println("      a|b = " + binary[c]);
				call(systemOut, "println", stradd(Value.value("      a|b = "), arrayLoad(binary, c)));
				
			    //System.out.println("      a&b = " + binary[d]);
				call(systemOut, "println", stradd(Value.value("      a&b = "), arrayLoad(binary, d)));
				
			    //System.out.println("      a^b = " + binary[e]);
				call(systemOut, "println", stradd(Value.value("      a^b = "), arrayLoad(binary, e)));
				
			    //System.out.println("~a&b|a&~b = " + binary[f]);
				call(systemOut, "println", stradd(Value.value("~a&b|a&~b = "), arrayLoad(binary, f)));
				
			    //System.out.println("       ~a = " + binary[g]);
				call(systemOut, "println", stradd(Value.value("       ~a = "), arrayLoad(binary, g)));
				
				return_();
			}
        });
		generate(creator);
	}
	
	public static Value[] stringValueArray(String[] strs){
		Value[] vals = new Value[strs.length];
		for(int i=0; i<vals.length; i++){
			vals[i] = Value.value(strs[i]);
		}
		return vals;
	}
}
