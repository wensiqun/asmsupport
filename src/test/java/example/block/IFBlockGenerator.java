package example.block;

import java.lang.reflect.InvocationTargetException;

import org.objectweb.asm.Opcodes;

import cn.wensiqun.asmsupport.block.control.Else;
import cn.wensiqun.asmsupport.block.control.ElseIF;
import cn.wensiqun.asmsupport.block.control.IF;
import cn.wensiqun.asmsupport.block.method.common.StaticMethodBody;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.creator.ClassCreator;
import cn.wensiqun.asmsupport.definition.value.Value;
import cn.wensiqun.asmsupport.definition.variable.LocalVariable;
import example.AbstractExample;

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
		
		creator.createStaticMethod("ifelse", new AClass[]{AClass.STRING_ACLASS, AClass.INT_ACLASS}, new String[]{"str", "i"}, null, null, Opcodes.ACC_PUBLIC,
		        new StaticMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						final LocalVariable str = argus[0];
						final LocalVariable i = argus[1];
						
						ifthan(new IF(invoke(str, "equals", Value.value("A"))){
							@Override
							public void body() {
								ifthan(new IF(equal(i, Value.value(0))){

									@Override
									public void body() {
									    invoke(systemOut, "println", Value.value("str is 'A', i is 0"));
									}
									
								}).elsethan(new Else(){

									@Override
									public void body() {
									    invoke(systemOut, "println", Value.value("str is 'A', i is not 0"));
									}
									
								});
							}
						}).elseif(new ElseIF(invoke(str, "equals", Value.value("B"))){

							@Override
							public void body() {
								ifthan(new IF(equal(i, Value.value(0))){

									@Override
									public void body() {
									    invoke(systemOut, "println", Value.value("str is 'B', i is 0"));
									}
									
								}).elsethan(new Else(){

									@Override
									public void body() {
									    invoke(systemOut, "println", Value.value("str is 'B', i is not 0"));
									}
									
								});
							}
							
						}).elsethan(new Else(){

							@Override
							public void body() {
								ifthan(new IF(equal(i, Value.value(0))){

									@Override
									public void body() {
									    invoke(systemOut, "println", Value.value("str is unknow, i is 0"));
									}
									
								}).elsethan(new Else(){

									@Override
									public void body() {
									    invoke(systemOut, "println", Value.value("str is unknow, i is not 0"));
									}
									
								});
							}
							
						});
						
					    runReturn();
					}
		        }
		);
		
		
		creator.createStaticMethod("main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new StaticMethodBody(){
					@Override
					public void body(LocalVariable... argus) {
						invokeStatic(getMethodOwner(), "ifelse", Value.value("A"), Value.value(0));
						invokeStatic(getMethodOwner(), "ifelse", Value.value("A"), Value.value(1));
						invokeStatic(getMethodOwner(), "ifelse", Value.value("B"), Value.value(0));
						invokeStatic(getMethodOwner(), "ifelse", Value.value("B"), Value.value(1));
						invokeStatic(getMethodOwner(), "ifelse", Value.value("C"), Value.value(0));
						invokeStatic(getMethodOwner(), "ifelse", Value.value("C"), Value.value(1));
						runReturn();
					}
			
		});
		generate(creator);
	}

}
