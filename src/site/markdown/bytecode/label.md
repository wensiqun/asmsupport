#字节码Label

Lable是ASM的一个概念，当我在下一条PC指令执行前传入一个Label对象调用MethodVisitor.visitLabel方法，这个Label对象就能够用来表示下一条指令的位置。我们可以简单的认为Label就是class字节码每一条指令的位置

他的作用有两个：程序跳转和范围划定

## 程序跳转

在字节码层面，并没有if...else if...else的概念，那么这些条件分支的操作是如何实现的呢，就是通过goto语句或带有跳转性质的指令和label标签一起完成的。比如如下指令:

ifeq &lt;label&gt;表示如果栈顶元素等于0则跳转到label位置。

假如有个方法

	public static void say(boolean say){
	    if(say) {
	        System.out.println("I say : Go ");
	    } else {
	        System.out.println("nothing");
	    }
	}
	
## 对应的字节码

     0 iload_0 [say]
     1 ifeq 15
     4 getstatic java.lang.System.out : java.io.PrintStream [20]
     7 ldc <String "I say : Go "> [26]
     9 invokevirtual java.io.PrintStream.println(java.lang.String) : void [28]
    12 goto 23
    15 getstatic java.lang.System.out : java.io.PrintStream [20]
    18 ldc <String "nothing"> [34]
    20 invokevirtual java.io.PrintStream.println(java.lang.String) : void [28]
    23 return
    
这里每一行的数字表示当前这条指令的PC， true和false在字节码中分别用1和0表示，所以上面的代码可以看到

- **0 iload_0[say]:**  将方法的参数say压入栈
- **1 ifeq 15:** 如果是0，也就是false则跳到18往下执行，从15-20就是代码中的else部分，很显然，如果不是0（是true）则继续往下执行（执行if部分），直到if部分结束（if部分是4-9），再调用goto 23跳过else部分。

## 划定范围

在上面条件跳转中就能看出他的划定程序块范围的功能，上面的if是4到9，else是15-20，当然除了if...else, 还有其他的比如try，catch，finally，synchronized，for，while等等都需要用它来划定范围，有了范围java虚拟机就可以对这个范围做定制的一些操作，比如在这个范围内变量位置是1的变量叫什么名字等等。
