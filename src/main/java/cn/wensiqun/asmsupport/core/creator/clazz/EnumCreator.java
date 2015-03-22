/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.core.creator.clazz;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.method.clinit.EnumStaticBlockBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.common.MethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.method.init.EnumConstructorBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.creator.FieldCreator;
import cn.wensiqun.asmsupport.core.creator.IFieldCreator;
import cn.wensiqun.asmsupport.core.creator.MethodCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.array.ArrayLength;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.apache.commons.collections.CollectionUtils;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.ArrayUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class EnumCreator extends AbstractClassCreatorContext {

    private boolean existEnumConstant;
    
    private boolean existField;
    
    private boolean existNoArgumentsConstructor;
    
    List<String> enumConstantNameList;
    
    /**
     * 
     * @param version
     * @param name
     * @param interfaces
     */
    public EnumCreator(int version, String name, Class<?>[] interfaces) {
        super(version, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_SUPER + Opcodes.ACC_ENUM, name, 
                Enum.class, interfaces);
        enumConstantNameList = new ArrayList<String>();
    }

    /**
     * 
     * Create a field with null value.
     * 
     * @param name            the field name
     * @param modifiers       the field modifiers
     * @param type      the field type
     * @return
     */
    public IFieldCreator createField(String name, int modifiers, AClass type) {
        return createField(name, modifiers, type, null);
    }
    
    
    /**
     * Create a field with special value.
     * 
     * @param name
     * @param modifiers
     * @param type
     * @param value The initial value, this value is only support static field, 
     *              otherwise will ignored.This parameter, which may be null 
     *              if the field does not have an initial value, 
     *              must be an Integer, a Float, a Long, a Double or a 
     *              String (for int, float, long or String fields respectively). 
     *              This parameter is only used for static fields. Its value is 
     *              ignored for non static fields, which must be initialized 
     *              through bytecode instructions in constructors or methods.
     * @return
     */
    public IFieldCreator createField(String name, int modifiers, AClass type, Object value) {
        if(!existEnumConstant){
            throw new ASMSupportException("first field must be an enum object of current enum type " + sc.getName());
        }
        FieldCreator fc = new FieldCreator(name, modifiers, type, value);
        fieldCreators.add(fc);
        existField = !ModifierUtils.isEnum(modifiers);
        return fc;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public void createEnumConstant(String name){
        if(existField){
            throw new ASMSupportException("declare enum constant must before other field");
        }
    	existEnumConstant = true;
    	sc.setEnumNum(sc.getEnumNum() + 1);
    	enumConstantNameList.add(name);
    	createField(name, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL + Opcodes.ACC_ENUM, sc);
    }
    
    /**
     * 
     * @param name
     * @param argClasses
     * @param argNames
     * @param returnClass
     * @param exceptions
     * @param access
     * @param mb
     */
    public final void createMethodForDummy(String name, AClass[] argClasses,
            String[] argNames, AClass returnClass, AClass[] exceptions,
            int access, MethodBodyInternal mb) {
        methodCreaters.add(MethodCreator.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, access, mb));
    }
    
    /**
     * 
     * @param name
     * @param arguments
     * @param argNames
     * @param returnClass
     * @param exceptions
     * @param access
     * @param mb
     * @return
     */
    public final void createMethod(String name, AClass[] argClasses,
            String[] argNames, AClass returnClass, AClass[] exceptions,
            int access, MethodBodyInternal mb) {
    	if((access & Opcodes.ACC_STATIC) != 0){
    		access -= Opcodes.ACC_STATIC;
    	}
        methodCreaters.add(MethodCreator.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, access, mb));
    }
    
    /**
     * 
     * @param name
     * @param argClasses
     * @param argNames
     * @param returnClass
     * @param exceptions
     * @param access
     * @param mb
     * @return
     */
    public void createStaticMethod(String name, AClass[] argClasses,
            String[] argNames, AClass returnClass, AClass[] exceptions,
            int access, StaticMethodBodyInternal mb) {
    	if((access & Opcodes.ACC_STATIC) == 0){
    		access += Opcodes.ACC_STATIC;
    	}
        methodCreaters.add(MethodCreator.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, access, mb));
    }

    /**
     * create constructor;
     * 
     * @param arguments
     * @param argNames
     * @param mb
     * @param access
     */
    public void createConstructor(AClass[] argClasses,
            String[] argNames, EnumConstructorBodyInternal mb) {
    	
    	if(ArrayUtils.isNotEmpty(argClasses) && ArrayUtils.isEmpty(argNames)) {
            argNames = new String[argClasses.length];
            for(int i=0; i<argNames.length; i++) {
                argNames[i] = "arg" + i;
            }
        }
    	
    	if(argNames == null){
            argNames = new String[0];
        }
        if(argClasses == null){
            argClasses = new AClass[0];
        }
    	
    	if(argNames.length != argClasses.length){
    		throw new IllegalArgumentException("Different arugment class number and argument name number");
    	}
    	
    	if(argNames.length == 0){
    		existNoArgumentsConstructor = true;
    	}
    	
    	String[] enumArgNames = new String[argNames.length + 2];
    	enumArgNames[0] = "name";
    	enumArgNames[1] = "ordinal";
    	System.arraycopy(argNames, 0, enumArgNames, 2, argNames.length);
    	
    	AClass[] enumArgClasses = new AClass[argClasses.length + 2];
    	enumArgClasses[0] = AClass.STRING_ACLASS;
    	enumArgClasses[1] = AClass.INT_ACLASS;
    	System.arraycopy(argClasses, 0, enumArgClasses, 2, argClasses.length);
    	
    	methodCreaters.add(MethodCreator.methodCreatorForAdd(ASConstant.INIT, enumArgClasses, enumArgNames,
                null, null, Opcodes.ACC_PRIVATE, mb));
    	haveInitMethod = true;
    }

    /**
     * 
     * @param mb
     */
    public void createStaticBlock(final EnumStaticBlockBodyInternal body) {
    	checkStaticBlock();
    	body.setEnumNameList(enumConstantNameList);
		if(existEnumConstant){
	        //create implicit global variable ENUM$VALUES for enum type
		    createField("ENUM$VALUES", Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC + Opcodes.ACC_SYNTHETIC, AClassFactory.defArrayType(sc, 1));
	       
	    }
		existedStaticBlock = true;
        methodCreaters.add(0, MethodCreator.methodCreatorForAdd(ASConstant.CLINIT, null, null, null, null,
                Opcodes.ACC_STATIC, body));
    }

	@Override
    protected void createDefaultConstructor() {
        createConstructor(null, null, new EnumConstructorBodyInternal() {
            @Override
            public void body(LocalVariable... argus) {
		    	return_();
            }
        });
        createDefaultStaticBlock();
    }

	@Override
	protected void beforeCreate(){
	   
	    final ArrayClass enumArrayType = AClassFactory.defArrayType(sc, 1);
	    
	    
	    //create "publis static Enum[] values()" method
	    createStaticMethod("values", null, null, enumArrayType, null, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                AClass owner = getMethodOwner();
                //get ENUM$VALUES
                GlobalVariable values = owner.field("ENUM$VALUES");
                
                //get length operator
                ArrayLength al = arrayLength(values);
                LocalVariable copy = arrayvar2dim("", enumArrayType, true, al);
                
                //get lengt operator for tmpValues;
                Parameterized copyLen = arrayLength(copy);
                
                //System
                AClass systemClass = AClassFactory.defType(System.class);
                
                //zero value
                Value zero = Value.value(0);
                
                //call arraycopy
                call(systemClass, "arraycopy", values, zero, copy, zero, copyLen);
                
                return_(copy);
            }
	        
	    });
	    
	     //create "public static Enum valueOf(java.lang.String)" method
        this.createStaticMethod("valueOf", new AClass[]{AClass.STRING_ACLASS}, new String[]{"name"}, sc, null, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                LocalVariable name = argus[0];
                AClass owner = getMethodOwner();
                return_(checkcast(call(owner, "valueOf", Value.value(owner), name), owner));
            }
            
        });
	    
	}
	
	/**
	 * 
	 */
	private void createDefaultStaticBlock(){
    	if(!existedStaticBlock && !CollectionUtils.isEmpty(enumConstantNameList) && existNoArgumentsConstructor){
    		createStaticBlock(new EnumStaticBlockBodyInternal(){
    			
    			{
    				enumNameList = enumConstantNameList;
    			}

				@Override
				public void constructEnumConsts() {
				    for(String name : enumNameList){
				    	constructEnumConst(name);
				    }
				}

                @Override
                public void body(LocalVariable... argus)
                {
                    return_();
                }
    		});
    	}
	}
}
