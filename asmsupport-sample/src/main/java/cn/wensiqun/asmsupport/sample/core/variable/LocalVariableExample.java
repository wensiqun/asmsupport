package cn.wensiqun.asmsupport.sample.core.variable;

import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;

/**
 * 创建局部变量分为创建
 * 1.普通类型的局部变量
 * 2.数组变量
 *
 */
public class LocalVariableExample extends AbstractExample {
    
	public static void main(String[] args){
		ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.variable.LocalVariableExample", null, null);
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
				"main", new IClass[]{classLoader.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelMethodBody(){

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
						LocalVariable localVariable1 = var("localVariable1", classLoader.getType(String.class), val("This is a Local Variable"));
                        call(systemOut, "println", localVariable1);
                        
						/* ***************************************************************
						       创建数组类型的局部变量                                                                                                         
                                                               创建数组类型有三种方法:                                            
                           1.通过createArrayVariable方法
                           2.通过createArrayVariableWithAllocateDimension方法 
                         ***************************************************************  */
                        
                        IClass stringArrayType = classLoader.getType(String[][].class);
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
                        LocalVariable localArrayVariable1 = var(stringArrayType, makeArray(stringArrayType, val(2), val(2)));//arrayvar2dim("localArrayVariable1", (ArrayClass)classLoader.getType(String[][].class), false, val(2), val(2));
                        call(systemOut, "println", stradd(val("example 1 : "), call(classLoader.getType(ArrayUtils.class), "toString", localArrayVariable1)));

                        /*
                         * 为数组分配部分空间 
                         */
                        LocalVariable localArrayVariable2 = var(stringArrayType, makeArray(stringArrayType, val(2)));//arrayvar2dim("localArrayVariable2", (ArrayClass)classLoader.getType(String[][].class), false, val(2));
                        call(systemOut, "println", stradd(val("example 2 : "), call(classLoader.getType(ArrayUtils.class), "toString", localArrayVariable2)));
                        
                        return_();
					}
			
		});
		generate(creator);
	}
	
}
