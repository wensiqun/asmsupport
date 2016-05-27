package cn.wensiqun.asmsupport.sample.core.block;

import cn.wensiqun.asmsupport.core.block.control.condition.KernelElse;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelElseIF;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelIF;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

import java.lang.reflect.InvocationTargetException;

/**
 * 这里我们将创建如下内容的类
 * 
 * public class IFBlockGeneratorExample{
 * 
 *     public static void main(String[] args){
 *         ifelse("A", 0);
 *         ifelse("A", 1);
 *         ifelse("B", 0);
 *         ifelse("B", 1);
 *         ifelse("C", 0);
 *         ifelse("C", 1);
 *     }
 * 
 *     public static void ifelse(String str, int i)
 *     {
 *         if (str.equals("A"))
 *         {
 *             if (i == 0){
 *                 System.out.println("str is 'A', i is 0");
 *             }else{
 *                 System.out.println("str is 'A', i is not 0");
 *             }
 *         }else if (str.equals("B")){
 *             if (i == 0){
 *                 System.out.println("str is 'B', i is 0");
 *             }else{
 *                 System.out.println("str is 'B', i is not 0");
 *             }
 *         }else(){
 *             if(i == 0){
 *                 System.out.println("str is unknow, i is 0");
 *             }else{
 *                 System.out.println("str is unknow, i is not 0");
 *             }
 *         }
 *             
 *     }
 * 
 * }
 * 
 *
 */
public class IFBlockGenerator extends AbstractExample{

	/**
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) {
		
		ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.IFBlockGeneratorExample", null, null);
		
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
				"ifelse", 
				new IClass[]{classLoader.getType(String.class), classLoader.getType(int.class)}, 
				new String[]{"str", "i"}, null, null,
		        
				new KernelMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						final LocalVariable str = argus[0];
						final LocalVariable i = argus[1];
						
						if_(new KernelIF(call(str, "equals", val("A"))){
							@Override
							public void body() {
								if_(new KernelIF(eq(i, val(0))){

									@Override
									public void body() {
									    call(systemOut, "println", val("str is 'A', i is 0"));
									}
									
								}).else_(new KernelElse(){

									@Override
									public void body() {
									    call(systemOut, "println", val("str is 'A', i is not 0"));
									}
									
								});
							}
						}).elseif(new KernelElseIF(call(str, "equals", val("B"))){

							@Override
							public void body() {
								if_(new KernelIF(eq(i, val(0))){

									@Override
									public void body() {
									    call(systemOut, "println", val("str is 'B', i is 0"));
									}
									
								}).else_(new KernelElse(){

									@Override
									public void body() {
									    call(systemOut, "println", val("str is 'B', i is not 0"));
									}
									
								});
							}
							
						}).else_(new KernelElse(){

							@Override
							public void body() {
								if_(new KernelIF(eq(i, val(0))){

									@Override
									public void body() {
									    call(systemOut, "println", val("str is unknow, i is 0"));
									}
									
								}).else_(new KernelElse(){

									@Override
									public void body() {
									    call(systemOut, "println", val("str is unknow, i is not 0"));
									}
								
								});
							}
							
						});
						
					    return_();
					}
		        }
		);
		
		
		creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new IClass[]{classLoader.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						call(getMethodDeclaringClass(), "ifelse", val("A"), val(0));
						call(getMethodDeclaringClass(), "ifelse", val("A"), val(1));
						call(getMethodDeclaringClass(), "ifelse", val("B"), val(0));
						call(getMethodDeclaringClass(), "ifelse", val("B"), val(1));
						call(getMethodDeclaringClass(), "ifelse", val("C"), val(0));
						call(getMethodDeclaringClass(), "ifelse", val("C"), val(1));
						return_();
					}
			
		});
		generate(creator);
	}

}
