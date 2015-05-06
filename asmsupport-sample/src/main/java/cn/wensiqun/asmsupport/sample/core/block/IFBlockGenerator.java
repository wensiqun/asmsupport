package cn.wensiqun.asmsupport.sample.core.block;

import java.lang.reflect.InvocationTargetException;

import cn.wensiqun.asmsupport.sample.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelElseIF;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelElse;
import cn.wensiqun.asmsupport.core.block.control.condition.KernelIF;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

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
		
		ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.IFBlockGeneratorExample", null, null);
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC, 
				"ifelse", 
				new AClass[]{AClassFactory.getType(String.class), AClassFactory.getType(int.class)}, 
				new String[]{"str", "i"}, null, null,
		        
				new KernelStaticMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						final LocalVariable str = argus[0];
						final LocalVariable i = argus[1];
						
						if_(new KernelIF(call(str, "equals", Value.value("A"))){
							@Override
							public void body() {
								if_(new KernelIF(eq(i, Value.value(0))){

									@Override
									public void body() {
									    call(systemOut, "println", Value.value("str is 'A', i is 0"));
									}
									
								}).else_(new KernelElse(){

									@Override
									public void body() {
									    call(systemOut, "println", Value.value("str is 'A', i is not 0"));
									}
									
								});
							}
						}).elseif(new KernelElseIF(call(str, "equals", Value.value("B"))){

							@Override
							public void body() {
								if_(new KernelIF(eq(i, Value.value(0))){

									@Override
									public void body() {
									    call(systemOut, "println", Value.value("str is 'B', i is 0"));
									}
									
								}).else_(new KernelElse(){

									@Override
									public void body() {
									    call(systemOut, "println", Value.value("str is 'B', i is not 0"));
									}
									
								});
							}
							
						}).else_(new KernelElse(){

							@Override
							public void body() {
								if_(new KernelIF(eq(i, Value.value(0))){

									@Override
									public void body() {
									    call(systemOut, "println", Value.value("str is unknow, i is 0"));
									}
									
								}).else_(new KernelElse(){

									@Override
									public void body() {
									    call(systemOut, "println", Value.value("str is unknow, i is not 0"));
									}
								
								});
							}
							
						});
						
					    return_();
					}
		        }
		);
		
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getType(String[].class)}, new String[]{"args"}, null, null,
				new KernelStaticMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						call(getMethodDeclaringClass(), "ifelse", Value.value("A"), Value.value(0));
						call(getMethodDeclaringClass(), "ifelse", Value.value("A"), Value.value(1));
						call(getMethodDeclaringClass(), "ifelse", Value.value("B"), Value.value(0));
						call(getMethodDeclaringClass(), "ifelse", Value.value("B"), Value.value(1));
						call(getMethodDeclaringClass(), "ifelse", Value.value("C"), Value.value(0));
						call(getMethodDeclaringClass(), "ifelse", Value.value("C"), Value.value(1));
						return_();
					}
			
		});
		generate(creator);
	}

}
