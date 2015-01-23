package example.operators;


import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
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
		
		ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.operators.BitwiseOperatorGenerateExample", null, null);
		
		/*
		 * 生成一个main方法，方法内容和main1内容相同
		 */
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

			@Override
			public void body(LocalVariable... argus) {
				/*String binary[] = {
			      "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
			      "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"
			    };*/
				LocalVariable binary = _createArrayVariable("binary", AClassFactory.getArrayClass(String[].class), false, _newArrayWithValue(AClassFactory.getArrayClass(String[].class), stringValueArray(new String[]{
					      "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
					      "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"})));
			    //int a = 3; 
				LocalVariable a = _createVariable("a", AClass.INT_ACLASS, false, Value.value(3));
			    
			    //int b = 6; 
				LocalVariable b = _createVariable("b", AClass.INT_ACLASS, false, Value.value(6));
				
			    //int c = a | b;
				LocalVariable c = _createVariable("c", AClass.INT_ACLASS, false, this._bitOr(a, b));
				
			    //int d = a & b;
				LocalVariable d = _createVariable("d", AClass.INT_ACLASS, false, this._bitAnd(a, b));
				
			    //int e = a ^ b;
				LocalVariable e = _createVariable("e", AClass.INT_ACLASS, false, this._bitXor(a, b));
				
			    //int f = (~a & b) | (a & ~b);
				LocalVariable f = _createVariable("f", AClass.INT_ACLASS, false, _bitOr(_bitAnd(_inverts(a), b), _bitAnd(a, _inverts(b))));
				
			    //int g = ~a & 0x0f;
				LocalVariable g = _createVariable("g", AClass.INT_ACLASS, false, this._bitAnd(_inverts(a), Value.value(0x0f)));
				
			    //System.out.println("        a = " + binary[a]);
				_invoke(systemOut, "println", _append(Value.value("        a = "), _arrayLoad(binary, a)));
				
			    //System.out.println("        b = " + binary[b]);
				_invoke(systemOut, "println", _append(Value.value("        b = "), _arrayLoad(binary, b)));
				
			    //System.out.println("      a|b = " + binary[c]);
				_invoke(systemOut, "println", _append(Value.value("      a|b = "), _arrayLoad(binary, c)));
				
			    //System.out.println("      a&b = " + binary[d]);
				_invoke(systemOut, "println", _append(Value.value("      a&b = "), _arrayLoad(binary, d)));
				
			    //System.out.println("      a^b = " + binary[e]);
				_invoke(systemOut, "println", _append(Value.value("      a^b = "), _arrayLoad(binary, e)));
				
			    //System.out.println("~a&b|a&~b = " + binary[f]);
				_invoke(systemOut, "println", _append(Value.value("~a&b|a&~b = "), _arrayLoad(binary, f)));
				
			    //System.out.println("       ~a = " + binary[g]);
				_invoke(systemOut, "println", _append(Value.value("       ~a = "), _arrayLoad(binary, g)));
				
				_return();
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
