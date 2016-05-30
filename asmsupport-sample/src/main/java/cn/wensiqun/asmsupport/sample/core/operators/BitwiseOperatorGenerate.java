package cn.wensiqun.asmsupport.sample.core.operators;


import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.build.resolver.ClassResolver;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.ArrayClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

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
		
		ClassResolver creator = new ClassResolver(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.BitwiseOperatorGenerateExample", null, null);
		
		/*
		 * 生成一个main方法，方法内容和main1内容相同
		 */
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new IClass[]{classLoader.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				/*String binary[] = {
			      "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
			      "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"
			    };*/
				LocalVariable binary = var("binary", (ArrayClass) classLoader.getType(String[].class),
						newarray((ArrayClass) classLoader.getType(String[].class), stringValueArray(new String[]{
					      "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
					      "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"})));
			    //int a = 3; 
				LocalVariable a = var("a", classLoader.getType(int.class), val(3));
			    
			    //int b = 6; 
				LocalVariable b = var("b", classLoader.getType(int.class), val(6));
				
			    //int c = a | b;
				LocalVariable c = var("c", classLoader.getType(int.class), this.bor(a, b));
				
			    //int d = a & b;
				LocalVariable d = var("d", classLoader.getType(int.class), this.band(a, b));
				
			    //int e = a ^ b;
				LocalVariable e = var("e", classLoader.getType(int.class), this.bxor(a, b));
				
			    //int f = (~a & b) | (a & ~b);
				LocalVariable f = var("f", classLoader.getType(int.class), bor(band(reverse(a), b), band(a, reverse(b))));
				
			    //int g = ~a & 0x0f;
				LocalVariable g = var("g", classLoader.getType(int.class), this.band(reverse(a), val(0x0f)));
				
			    //System.out.println("        a = " + binary[a]);
				call(systemOut, "println", stradd(val("        a = "), arrayLoad(binary, a)));
				
			    //System.out.println("        b = " + binary[b]);
				call(systemOut, "println", stradd(val("        b = "), arrayLoad(binary, b)));
				
			    //System.out.println("      a|b = " + binary[c]);
				call(systemOut, "println", stradd(val("      a|b = "), arrayLoad(binary, c)));
				
			    //System.out.println("      a&b = " + binary[d]);
				call(systemOut, "println", stradd(val("      a&b = "), arrayLoad(binary, d)));
				
			    //System.out.println("      a^b = " + binary[e]);
				call(systemOut, "println", stradd(val("      a^b = "), arrayLoad(binary, e)));
				
			    //System.out.println("~a&b|a&~b = " + binary[f]);
				call(systemOut, "println", stradd(val("~a&b|a&~b = "), arrayLoad(binary, f)));
				
			    //System.out.println("       ~a = " + binary[g]);
				call(systemOut, "println", stradd(val("       ~a = "), arrayLoad(binary, g)));
				
				return_();
			}
        });
		generate(creator);
	}
	
	public static Value[] stringValueArray(String[] strs){
		Value[] vals = new Value[strs.length];
		for(int i=0; i<vals.length; i++){
			vals[i] = Value.value(classLoader, strs[i]);
		}
		return vals;
	}
}
