package cn.wensiqun.asmsupport.core.creator.clazz;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.method.clinit.EnumClinitBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.CommonMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.init.EnumInitBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.creator.FieldCreatorIternel;
import cn.wensiqun.asmsupport.core.creator.MethodCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.operator.array.ArrayLength;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.apache.commons.collections.CollectionUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class EnumCreatorInternal extends AbstractClassCreatorContext {

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
    public EnumCreatorInternal(int version, String name, Class<?>[] interfaces) {
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
        FieldCreatorIternel fc = new FieldCreatorIternel(name, modifiers,
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
            int access, CommonMethodBodyInternal mb) {
    	if((access & Opcodes.ACC_STATIC) != 0){
    		access -= Opcodes.ACC_STATIC;
    	}
        methodCreaters.add(MethodCreatorInternal.methodCreatorForAdd(name, argClasses, argNames,
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
        methodCreaters.add(MethodCreatorInternal.methodCreatorForAdd(name, argClasses, argNames,
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
            String[] argNames, EnumInitBodyInternal mb) {
    	
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
    	
    	methodCreaters.add(MethodCreatorInternal.methodCreatorForAdd(ASConstant.INIT, enumArgClasses, enumArgNames,
                null, null, Opcodes.ACC_PRIVATE, mb));
    	haveInitMethod = true;
    }

    /**
     * 
     * @param mb
     */
    public void createStaticBlock(final EnumClinitBodyInternal body) {
    	checkStaticBlock();
    	body.setEnumNameList(enumConstantNameList);
		if(existEnumConstant){
	        //create implicit global variable ENUM$VALUES for enum type
			createGlobalVariable("ENUM$VALUES", Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC + Opcodes.ACC_SYNTHETIC, AClassFactory.getArrayClass(sc, 1));
	       
	    }
		existedStaticBlock = true;
        methodCreaters.add(0, MethodCreatorInternal.methodCreatorForAdd(ASConstant.CLINIT, null, null, null, null,
                Opcodes.ACC_STATIC, body));
    }

	@Override
    protected void createDefaultConstructor() {
        createConstructor(null, null, new EnumInitBodyInternal() {
            @Override
            public void body(LocalVariable... argus) {
		    	_return();
            }
        });
        createDefaultStaticBlock();
    }

	@Override
	protected void beforeCreate(){
	   
	    final ArrayClass enumArrayType = AClassFactory.getArrayClass(sc, 1);
	    
	    
	    //create "publis static Enum[] values()" method
	    createStaticMethod("values", null, null, enumArrayType, null, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                AClass owner = getMethodOwner();
                //get ENUM$VALUES
                GlobalVariable values = owner.getGlobalVariable("ENUM$VALUES");
                
                //get length operator
                ArrayLength al = _arrayLength(values);
                LocalVariable copy = _createArrayVariableWithAllocateDimension("", enumArrayType, true, al);
                
                //get lengt operator for tmpValues;
                Parameterized copyLen = _arrayLength(copy);
                
                //System
                AClass systemClass = AClassFactory.getProductClass(System.class);
                
                //zero value
                Value zero = Value.value(0);
                
                //call arraycopy
                _invokeStatic(systemClass, "arraycopy", values, zero, copy, zero, copyLen);
                
                _return(copy);
            }
	        
	    });
	    
	     //create "public static Enum valueOf(java.lang.String)" method
        this.createStaticMethod("valueOf", new AClass[]{AClass.STRING_ACLASS}, new String[]{"name"}, sc, null, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBodyInternal(){

            @Override
            public void body(LocalVariable... argus)
            {
                LocalVariable name = argus[0];
                AClass owner = getMethodOwner();
                _return(_checkcast(_invokeStatic(owner, "valueOf", Value.value(owner), name), owner));
            }
            
        });
	    
	}
	
	/**
	 * 
	 */
	private void createDefaultStaticBlock(){
    	if(!existedStaticBlock && !CollectionUtils.isEmpty(enumConstantNameList) && existNoArgumentsConstructor){
    		createStaticBlock(new EnumClinitBodyInternal(){
    			
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
                    _return();
                }
    		});
    	}
	}
}
