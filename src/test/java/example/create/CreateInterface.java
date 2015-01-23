package example.create;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import cn.wensiqun.asmsupport.core.block.classes.method.clinit.ClinitBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.creator.clazz.InterfaceCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import example.AbstractExample;

/**
 * 这里类中我们主要内容如下:
 * 1.创建一个接口名为"example.generated.CreateInterfaceExample"
 * 2.在这个接口里声明一个名为"test"方法。
 * 3.在接口中创建一个globalValue的全局变量
 * 4.创建一个static语句块。在这个语句块中给globalValue赋值并且答应一段话
 * 
 */
public class CreateInterface extends AbstractExample {

	/**
	 * @param args
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		/*
		 * 如果需要创建接口则采用 InterfaceCreator类
		 * 
		 * 该类的构造方法包含了三个参数
		 * 1.接口的JDK版本,可以用过org.objectweb.asm.Opcodes获取，比如：Opcodes.V1_5表示1.5版本的jdk
		 * 2.接口的全路径名
		 * 3.接口所继承哪些接口,是一个Class的数组
		 */
		InterfaceCreatorInternal interfaceCreator = new InterfaceCreatorInternal(Opcodes.V1_6, "generated.create.CreateInterfaceExample", null);
		
		/*
		 * 通过createMethod声明方法
		 * 
		 */
		interfaceCreator.createMethod("test", new AClass[]{AClass.STRING_ACLASS, AClass.INT_ACLASS}, AClass.BOOLEAN_ACLASS, null);
		
		/*
		 * 通过createGlobalVariable创建局部变量，当然这个变量的修饰符是public static final的
		 * 需要注意的一点是，我们不能像java代码里一样在声明变量的同时给变量赋值，因为这个变量是
		 * static的，所以我们只能在下面的代码createStaticBlock中对其赋值。事实上，如果你在编写
		 * java代码的时候在接口中申明了一个变量，java的编译器其实也会将你代码中的赋值部分解释出来，
		 * 再将这一部分的字节码放到static块中。
		 * 
		 * 对于非static类型的全局变量如何申明和赋值将在CreateClass.java和CreateEnum.java中
		 * 有详细的解释
		 * 
		 * 对应的java代码如下:
		 * public static final String globalValue;
		 * 
		 */
		interfaceCreator.createGlobalVariable("globalValue", AClass.STRING_ACLASS);
		
		/*
		 * 通过createStaticBlock创建static块。
		 * 对应的java代码如下：
		 * static{
		 *     System.out.println("I'm in static block at interface");
		 * }
		 * 
		 * 
		 * 这里区别于java代码。在用java代码编写接口的时候，是不允许编写static程序块的。但是在
		 * 这里你可以创建它，它的作用和Class中的static程序块是相同的，都是在Class被装载的时候
		 * 执行，且执行一次。 你可以在这个程序块中编写任何代码。
		 * 
		 * 如下，这里首先将"I'm a global variable at Interface"字符串赋值给我们上面申明的
		 * 变量。然后在打印"I'm in static block at interface".
		 * 
		 * 这里我们发现有一个runReturn()方法。这个方法是生成return语句。这里为什么要这么写。首先要
		 * 明确个概念，对于JVM来说，或者说在字节码的层面上来讲，静态语句块其实是特殊一个静态方法，它的
		 * 名字叫做"<cinit>"，返回类型是void，我们用java代码来描述就是static void <cinit>()，
		 * 所以我们需要执行return操作。当然在我们编写java代码的时候，对于void返回类型的方法我们是不需
		 * 要显式的写return的，但是在使用asmsupport的时候，我们需要显式的执行一次runReturn().
		 * 事实上在你编写java代码的时候，对于void的方法，如果你不写return，java编译器会自动在你所写
		 * 方法的最后自动加上一条return指令。
		 * 
		 * 注意：createStaticBlock我们只能调用一次，因为static块实际上就是一个方法，根据方法的
		 * 重载很容易理解，我们不可能创建多个static void <cinit>()方法。虽然我们在编写java代码
		 * 的时候可以写多个static块在同一个类中，但是java编译器到最后会将所有写在static块中的代
		 * 码归并到<cinit>方法当中去。
		 */
		interfaceCreator.createStaticBlock(new ClinitBodyInternal(){

			@Override
			public void body() {
				/*
				 * 这里是将"I'm a global variable at Interface"赋值给上面我们创建的globalValue全局变量
				 * 对应的java代码可以理解为:
				 * 
				 * example.generated.CreateInterfaceExample.globalValue="I'm a global variable at Interface";
				 * 
				 * 这里要记住一点，所有静态变量的获取都是需要通过AClass，AClass是你所需要获取变量的宿主Class。这个
				 * getMethodOwner()是获取当前方法的宿主class，就是你当前创建或者正在修改的Class。比如我们最常用到的
				 * System.out对因的asmsupport代码就是:
				 * 
				 * AClassFactory.getProductClass(System.class).getGlobalVariable("out");
				 * 
				 * 具体如何使用全局变量在以后的例子中有详细的解释。
				 * 
				 */
				_assign(getMethodOwner().getGlobalVariable("globalValue"), Value.value("I'm a global variable at Interface"));
				/*
				 * 这段代码是调用println方法 
				 * 
				 * 具体如何调用方法在以后的例子中有详细的解释。
				 */
			    _invoke(systemOut, "println", Value.value("I'm in static block at interface"));
				_return();
			}
			
		});
		
		//如果你看代码看到了这里。那么请进入generate方法看看里面做了什么吧
		Class inter = generate(interfaceCreator);
		Field globalValue = inter.getField("globalValue");
		System.out.println(globalValue + " : value is " + globalValue.get(inter));
		System.out.println(inter);
	}

}
