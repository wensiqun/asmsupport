#字节码声明成员变量的那点事

我们知道，在编写java代码的时候，为某个类添加成员变量，无论是静态的还是非静态的我们都能够为其赋予初始值，比如：

    public class Main{
        private String str1 = "Hello";
        private Object obj1 = new Object();
        private static String str2 = "World";
        private static Object obj2 = new Object();
    }
    
在字节码层面，所有的操作比如上面的赋值和new操作都是要通过对栈的操作来实现的，而栈值存在于方法块的字节码部分，所以表面上我们编写代码认为是在声明变量的时候为其赋值并且完成new操作的。其实java虚拟机是将其分成两个部分，第一部分是声明变量，第二部分是赋值操作，而第二部分就是在不同的方法体上完成的。如果我们声明的变量是静态的，那么这个方法体就是类的静态程序块；如果声明的变量是非静态的，那么就会在当前类中的每个构造方法调用完父类构造方法之后立即完成初始化赋值。那么对于我们上面的例子，从字节码的角度来看将生产出对应于下面这段代码的内容：

    public class Main{
        private String str1 = "Hello";
        private Object obj1 = new Object();
        private static String str1;
        private static Object obj2;
    
        static {
             str2 = "World";
             obj2 = new Object();
        }
    
        public Main(){
            this.str1 = "Hello";
            this.obj2 = new Object();
        }
        
    }