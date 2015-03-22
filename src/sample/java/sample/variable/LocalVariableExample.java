package sample.variable;


import sample.AbstractExample;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.ArrayUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

/**
 * 创建局部变量分为创建
 * 1.普通类型的局部变量
 * 2.数组变量
 *
 */
public class LocalVariableExample extends AbstractExample {
    
	public static void main(String[] args){
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.variable.LocalVariableExample", null, null);
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
				"main", new AClass[]{AClassFactory.defType(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){

					@Override
					public void body(LocalVariable... argus) {
						
						/* ***************************  */
						/*      创建非数组类型的局部变量       */
						/* ***************************  */
						
						/*
						 * 创建普通类型局部变量
						 * 这个方法有四个参数
						 * 第一个参数：变量名
						 * 第二个参数 ：变量的类型
						 * 第三个参数：是否是匿名参数
						 * 第四个参数：初始值，可以为null，null则表示
						 * 这里的第三个参数是个boolean类型。如果设置成true，则所设置的变量名将不起作用。
						 */
						LocalVariable localVariable1 = var("localVariable1", AClass.STRING_ACLASS, false, Value.value("This is a Local Variable"));
                        call(systemOut, "println", localVariable1);
                        
						/* ***************************************************************
						       创建数组类型的局部变量                                                                                                         
                                                               创建数组类型有三种方法:                                            
                           1.通过createArrayVariable方法
                           2.通过createArrayVariableWithAllocateDimension方法 
                         ***************************************************************  */
                        
                        
                        /*
                         * 创建一个数组局部变量
                         * 同样也有四个变量
                         * 1：变量名
                         * 2：变量类型 这里的必须是数组类型ArrayClass
                         * 3: 是否是匿名
                         * 4：为数组分配大小
                         * 这段代码将创建一个String类型的二维数组的变量,对应的java代码如下
                         * String[][] localArrayVariable1 = new String[2][2];
                         */
                        LocalVariable localArrayVariable1 = arrayvar2dim("localArrayVariable1", AClassFactory.defArrayType(String[][].class), false, Value.value(2), Value.value(2));
                        call(systemOut, "println", stradd(Value.value("example 1 : "), call(AClassFactory.defType(ArrayUtils.class), "toString", localArrayVariable1)));

                        /*
                         * 为数组分配部分空间 
                         */
                        LocalVariable localArrayVariable2 = arrayvar2dim("localArrayVariable2", AClassFactory.defArrayType(String[][].class), false, Value.value(2));
                        call(systemOut, "println", stradd(Value.value("example 2 : "), call(AClassFactory.defType(ArrayUtils.class), "toString", localArrayVariable2)));
                        
                        /*
                         * 也可以直接设置为null 
                         * 
                         */
                        LocalVariable localArrayVariable3 = arrayvar2dim("localArrayVariable3", AClassFactory.defArrayType(String[][].class), false, (Parameterized[])null);
                        call(systemOut, "println", stradd(Value.value("example 3 : "), call(AClassFactory.defType(ArrayUtils.class), "toString", localArrayVariable3)));
                        
                        return_();
					}
			
		});
		generate(creator);
	}
	
}
