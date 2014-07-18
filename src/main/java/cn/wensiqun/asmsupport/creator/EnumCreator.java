package cn.wensiqun.asmsupport.creator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.method.clinit.EnumClinitBody;
import cn.wensiqun.asmsupport.block.method.common.CommonMethodBody;
import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.block.method.init.EnumInitBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.clazz.ArrayClass;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.operators.array.ArrayLength;
import cn.wensiqun.asmsupport.utils.ASConstant;
import cn.wensiqun.asmsupport.utils.reflet.ModifierUtils;


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
     * @param name
     * @param modifiers
     * @param fieldClass
     * @param value
     * @return
     */
    public void createGlobalVariable(String name, int modifiers,
            AClass fieldClass) {
        if(!existEnumConstant){
            throw new ASMSupportException("first field must be an enum object of current enum type " + sc.getName());
        }
        GlobalVariableCreator fc = new GlobalVariableCreator(name, modifiers,
                fieldClass);
        fieldCreators.add(fc);
        existField = !ModifierUtils.isEnum(modifiers);
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
        createGlobalVariable(name, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL + Opcodes.ACC_ENUM, sc);
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
            int access, CommonMethodBody mb) {
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
            int access, StaticMethodBody mb) {
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
            String[] argNames, EnumInitBody mb) {
    	
    	if(argNames == null){
    		argNames = new String[0];
    	}
    	if(argClasses == null){
    		argClasses = new AClass[0];
    	}
    	
    	if(argNames.length != argClasses.length){
    		throw new IllegalArgumentException("different arugment class number and argument name number");
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
    public void createStaticBlock(final EnumClinitBody body) {
    	checkStaticBlock();
    	body.setEnumNameList(enumConstantNameList);
		if(existEnumConstant){
	        //create implicit global variable ENUM$VALUES for enum type
			createGlobalVariable("ENUM$VALUES", Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC + Opcodes.ACC_SYNTHETIC, AClassFactory.getArrayClass(sc, 1));
	       
	    }
		existedStaticBlock = true;
        methodCreaters.add(0, MethodCreator.methodCreatorForAdd(ASConstant.CLINIT, null, null, null, null,
                Opcodes.ACC_STATIC, body));
    }

	@Override
    protected void createDefaultConstructor() {
        createConstructor(null, null, new EnumInitBody() {
            @Override
            public void body(LocalVariable... argus) {
		    	runReturn();
            }
        });
        createDefaultStaticBlock();
    }

	@Override
	protected void beforeCreate(){
	   
	    final ArrayClass enumArrayType = AClassFactory.getArrayClass(sc, 1);
	    
	    
	    //create "publis static Enum[] values()" method
	    createStaticMethod("values", null, null, enumArrayType, null, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                AClass owner = getMethodOwner();
                //get ENUM$VALUES
                GlobalVariable values = owner.getGlobalVariable("ENUM$VALUES");
                
                //get length operator
                ArrayLength al = arrayLength(values);
                LocalVariable copy = createArrayVariableWithAllocateDimension("", enumArrayType, true, al);
                
                //get lengt operator for tmpValues;
                Parameterized copyLen = arrayLength(copy);
                
                //System
                AClass systemClass = AClassFactory.getProductClass(System.class);
                
                //zero value
                Value zero = Value.value(0);
                
                //call arraycopy
                invokeStatic(systemClass, "arraycopy", values, zero, copy, zero, copyLen);
                
                runReturn(copy);
            }
	        
	    });
	    
	     //create "public static Enum valueOf(java.lang.String)" method
        this.createStaticMethod("valueOf", new AClass[]{AClass.STRING_ACLASS}, new String[]{"name"}, sc, null, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){

            @Override
            public void body(LocalVariable... argus)
            {
                LocalVariable name = argus[0];
                AClass owner = getMethodOwner();
                runReturn(checkCast(invokeStatic(owner, "valueOf", Value.value(owner), name), owner));
            }
            
        });
	    
	}
	
	/**
	 * 
	 */
	private void createDefaultStaticBlock(){
    	if(!existedStaticBlock && !CollectionUtils.isEmpty(enumConstantNameList) && existNoArgumentsConstructor){
    		createStaticBlock(new EnumClinitBody(){
    			
    			{
    				enumNameList = enumConstantNameList;
    			}

				@Override
				public void constructEnumField() {
				    for(String name : enumNameList){
				    	newEnum(name);
				    }
				}

                @Override
                public void body(LocalVariable... argus)
                {
                    runReturn();
                }
    		});
    	}
	}
}
