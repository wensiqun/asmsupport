package cn.wensiqun.asmsupport.core.creator.clazz;


import cn.wensiqun.asmsupport.core.block.classes.method.clinit.ClinitBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.CommonMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.init.InitBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.creator.FieldCreatorIternel;
import cn.wensiqun.asmsupport.core.creator.IFieldCreator;
import cn.wensiqun.asmsupport.core.creator.IMethodCreator;
import cn.wensiqun.asmsupport.core.creator.MethodCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.generic.creator.IClassCreator;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;


/**
 * 
 * 
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ClassCreatorInternal extends AbstractClassCreatorContext implements IClassCreator<ClinitBodyInternal, InitBodyInternal, CommonMethodBodyInternal, StaticMethodBodyInternal, IFieldCreator, IMethodCreator> {

    public ClassCreatorInternal(int version, int access, String name,
            Class<?> superCls, Class<?>[] interfaces) {
        super(version, access, name, superCls, interfaces);
    }

    @Override
    public IFieldCreator createField(String name, int modifiers,
            AClass fieldClass) {
        IFieldCreator fc = new FieldCreatorIternel(name, modifiers,
                fieldClass);
        fieldCreators.add(fc);
        return fc;
    }

	@Override
	public IMethodCreator createConstructor(int access, AClass[] argTypes, String[] argNames, InitBodyInternal body) {
		IMethodCreator creator = MethodCreatorInternal.methodCreatorForAdd(ASConstant.INIT, argTypes, argNames,
                null, null, access, body);
        methodCreaters.add(creator);
        haveInitMethod = true;
        return creator;
	}


	@Override
	public IMethodCreator createMethod(int access, String name, AClass[] argTypes, String[] argNames,
			AClass returnClass, AClass[] exceptions, CommonMethodBodyInternal body) {
		if((access & Opcodes.ACC_STATIC) != 0){
    		access -= Opcodes.ACC_STATIC;
    	}
		IMethodCreator creator = MethodCreatorInternal.methodCreatorForAdd(name, argTypes, argNames,
                returnClass, exceptions, access, body);
		methodCreaters.add(creator);
		return creator;
	}

	@Override
	public IMethodCreator createStaticMethod(int access, String name, AClass[] argClasses, String[] argNames,
			AClass returnClass, AClass[] exceptions, StaticMethodBodyInternal body) {
		if((access & Opcodes.ACC_STATIC) == 0){
    		access += Opcodes.ACC_STATIC;
    	}
		IMethodCreator creator = MethodCreatorInternal.methodCreatorForAdd(name, argClasses, argNames,
                returnClass, exceptions, access, body);
        methodCreaters.add(creator);
        return creator;
	}

	@Override
	public IMethodCreator createStaticBlock(ClinitBodyInternal block) {
    	checkStaticBlock();
    	existedStaticBlock = true;
    	IMethodCreator creator = MethodCreatorInternal.methodCreatorForAdd(ASConstant.CLINIT, null, null, null, null,
                Opcodes.ACC_STATIC, block);
    	methodCreaters.add(0, creator);
    	return creator;
	}

    @Override
    protected void createDefaultConstructor() {
        createConstructor(Opcodes.ACC_PUBLIC, null, null, new InitBodyInternal() {
            @Override
            public void body(LocalVariable... argus) {
                invokeSuperConstructor();
                _return();
            }
            
        });
    }
}
