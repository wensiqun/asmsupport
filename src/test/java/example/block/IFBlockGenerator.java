package example.block;

import java.lang.reflect.InvocationTargetException;

import cn.wensiqun.asmsupport.core.block.classes.control.condition.ElseIFInternal;
import cn.wensiqun.asmsupport.core.block.classes.control.condition.ElseInternal;
import cn.wensiqun.asmsupport.core.block.classes.control.condition.IFInternal;
import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
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
		
		ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.IFBlockGeneratorExample", null, null);
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC, 
				"ifelse", 
				new AClass[]{AClass.STRING_ACLASS, AClass.INT_ACLASS}, 
				new String[]{"str", "i"}, null, null,
		        
				new StaticMethodBodyInternal(){
					@Override
					public void body(LocalVariable... argus) {
						final LocalVariable str = argus[0];
						final LocalVariable i = argus[1];
						
						_if(new IFInternal(_invoke(str, "equals", Value.value("A"))){
							@Override
							public void body() {
								_if(new IFInternal(_equals(i, Value.value(0))){

									@Override
									public void body() {
									    _invoke(systemOut, "println", Value.value("str is 'A', i is 0"));
									}
									
								})._else(new ElseInternal(){

									@Override
									public void body() {
									    _invoke(systemOut, "println", Value.value("str is 'A', i is not 0"));
									}
									
								});
							}
						})._elseif(new ElseIFInternal(_invoke(str, "equals", Value.value("B"))){

							@Override
							public void body() {
								_if(new IFInternal(_equals(i, Value.value(0))){

									@Override
									public void body() {
									    _invoke(systemOut, "println", Value.value("str is 'B', i is 0"));
									}
									
								})._else(new ElseInternal(){

									@Override
									public void body() {
									    _invoke(systemOut, "println", Value.value("str is 'B', i is not 0"));
									}
									
								});
							}
							
						})._else(new ElseInternal(){

							@Override
							public void body() {
								_if(new IFInternal(_equals(i, Value.value(0))){

									@Override
									public void body() {
									    _invoke(systemOut, "println", Value.value("str is unknow, i is 0"));
									}
									
								})._else(new ElseInternal(){

									@Override
									public void body() {
									    _invoke(systemOut, "println", Value.value("str is unknow, i is not 0"));
									}
								
								});
							}
							
						});
						
					    _return();
					}
		        }
		);
		
		
		creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
				new StaticMethodBodyInternal(){
					@Override
					public void body(LocalVariable... argus) {
						_invokeStatic(getMethodOwner(), "ifelse", Value.value("A"), Value.value(0));
						_invokeStatic(getMethodOwner(), "ifelse", Value.value("A"), Value.value(1));
						_invokeStatic(getMethodOwner(), "ifelse", Value.value("B"), Value.value(0));
						_invokeStatic(getMethodOwner(), "ifelse", Value.value("B"), Value.value(1));
						_invokeStatic(getMethodOwner(), "ifelse", Value.value("C"), Value.value(0));
						_invokeStatic(getMethodOwner(), "ifelse", Value.value("C"), Value.value(1));
						_return();
					}
			
		});
		generate(creator);
	}

}
