#字节码中的两个方法&lt;init&gt;,&lt;clinit&gt;

##&lt;init&gt;方法

在JVM层面每一个构造方法都有一个特殊的名字叫&lt;init&gt;，这个名字是在编译器中就提供了的, 由于&lt;init&gt;不规范的java命名，所以我们在java代码里是没法直接使用的，一个实例的初始化方法在虚拟机中是通过调用invokespecial指令来完成的。

##&lt;clinit&gt;方法

同样对于类或者接口的静态程序块，在JVM层面也给了他一个特殊的名字&lt;clinit&gt;，无论是类或者接口在被加载到class loader的时候，Java虚拟机都会调用这个类或接口的&lt;clinit&gt;的方法，这个方法是个无参的，返回类型为void方法，这个方法名也是编译器指定的，由于这个方法名并不是一个规范的方法名，所以我们无法在代码中直接使用。

说了这么多，其实读到这里只要记住两点：在字节码层面&lt;init&gt;表示构造方法，&lt;clinit&gt;表示静态程序块。

ref：The JavaTM Virtual Machine Specification(Second Edition) 3.9 Specially Named Initialization Methods