package example.operators;


import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import example.AbstractExample;

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
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

			@Override
			public void body(LocalVariable... argus) {
				/*String binary[] = {
			      "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
			      "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"
			    };*/
				LocalVariable binary = createArrayVariable("binary", AClassFactory.getArrayClass(String[].class), false, newArrayWithValue(AClassFactory.getArrayClass(String[].class), stringValueArray(new String[]{
					      "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
					      "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"})));
			    //int a = 3; 
				LocalVariable a = createVariable("a", AClass.INT_ACLASS, false, Value.value(3));
			    
			    //int b = 6; 
				LocalVariable b = createVariable("b", AClass.INT_ACLASS, false, Value.value(6));
				
			    //int c = a | b;
				LocalVariable c = createVariable("c", AClass.INT_ACLASS, false, this.bitOr(a, b));
				
			    //int d = a & b;
				LocalVariable d = createVariable("d", AClass.INT_ACLASS, false, this.bitAnd(a, b));
				
			    //int e = a ^ b;
				LocalVariable e = createVariable("e", AClass.INT_ACLASS, false, this.bitXor(a, b));
				
			    //int f = (~a & b) | (a & ~b);
				LocalVariable f = createVariable("f", AClass.INT_ACLASS, false, bitOr(bitAnd(inverts(a), b), bitAnd(a, inverts(b))));
				
			    //int g = ~a & 0x0f;
				LocalVariable g = createVariable("g", AClass.INT_ACLASS, false, this.bitAnd(inverts(a), Value.value(0x0f)));
				
			    //System.out.println("        a = " + binary[a]);
				invoke(systemOut, "println", append(Value.value("        a = "), arrayLoad(binary, a)));
				
			    //System.out.println("        b = " + binary[b]);
				invoke(systemOut, "println", append(Value.value("        b = "), arrayLoad(binary, b)));
				
			    //System.out.println("      a|b = " + binary[c]);
				invoke(systemOut, "println", append(Value.value("      a|b = "), arrayLoad(binary, c)));
				
			    //System.out.println("      a&b = " + binary[d]);
				invoke(systemOut, "println", append(Value.value("      a&b = "), arrayLoad(binary, d)));
				
			    //System.out.println("      a^b = " + binary[e]);
				invoke(systemOut, "println", append(Value.value("      a^b = "), arrayLoad(binary, e)));
				
			    //System.out.println("~a&b|a&~b = " + binary[f]);
				invoke(systemOut, "println", append(Value.value("~a&b|a&~b = "), arrayLoad(binary, f)));
				
			    //System.out.println("       ~a = " + binary[g]);
				invoke(systemOut, "println", append(Value.value("       ~a = "), arrayLoad(binary, g)));
				
				runReturn();
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
