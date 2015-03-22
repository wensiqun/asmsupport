#枚举类型字节码详解

枚举类型作为jdk1.5开始的新特性，给java带来了很多的方便，在这里我们从字节码层面了解下枚举类型是如何实现的。首先我们看下一段简单的枚举类型代码：

	public enum Week {
	    MONDAY,
	    SUNDAY
	}
	
我们在代码中仅仅只是为Week枚举类型定义了两个常量MONDAY，SUNDAY。那么我们可以再看看这段代码的字节码内容。

	// Signature: Ljava/lang/Enum<LWeek;>;
	public final enum Week {
	 
	  public static final enum Week MONDAY; 
	  public static final enum Week SUNDAY; 
	  private static final synthetic Week[] ENUM$VALUES; 
	 
	  static {};
	     0 new Week [1]
	     3 dup
	     4 ldc <String "MONDAY"> [13]
	     6 iconst_0
	     7 invokespecial Week(java.lang.String, int) [14]
	    10 putstatic Week.MONDAY : Week [18]
	    13 new Week [1]
	    16 dup
	    17 ldc <String "SUNDAY"> [20]
	    19 iconst_1
	    20 invokespecial Week(java.lang.String, int) [14]
	    23 putstatic Week.SUNDAY : Week [21]
	    26 iconst_2
	    27 anewarray Week [1]
	    30 dup
	    31 iconst_0
	    32 getstatic Week.MONDAY : Week [18]
	    35 aastore
	    36 dup
	    37 iconst_1
	    38 getstatic Week.SUNDAY : Week [21]
	    41 aastore
	    42 putstatic Week.ENUM$VALUES : Week[] [23]
	    45 return
	 
	  private Week(java.lang.String arg0, int arg1);
	    0 aload_0 [this]
	    1 aload_1 [arg0]
	    2 iload_2 [arg1]
	    3 invokespecial java.lang.Enum(java.lang.String, int) [27]
	    6 return
	 
	  public static Week[] values();
	     0 getstatic Week.ENUM$VALUES : Week[] [23]
	     3 dup
	     4 astore_0
	     5 iconst_0
	     6 aload_0
	     7 arraylength
	     8 dup
	     9 istore_1
	    10 anewarray Week [1]
	    13 dup
	    14 astore_2
	    15 iconst_0
	    16 iload_1
	    17 invokestatic java.lang.System.arraycopy(java.lang.Object, int, java.lang.Object, int, int) : void [31]
	    20 aload_2
	    21 areturn
	 
	  public static Week valueOf(java.lang.String arg0);
	     0 ldc <Class Week> [1]
	     2 aload_0 [arg0]
	     3 invokestatic java.lang.Enum.valueOf(java.lang.Class, java.lang.String) : java.lang.Enum [39]
	     6 checkcast Week [1]
	     9 areturn
	}
	
虽然只有简单的几行java代码，但是对于的字节码却产生了下面这几个部分：

> * 1.创建了三个静态常量：（MONDAY，SUNDAY，ENUM$VALUES）。
> * 2.创建了一个static程序块。
> * 3.创建了一个私有类型的构造方法，并且这个构造方法有两个参数分别是String类型和int类型。
> * 4.创建了一个public类型的静态方法values，这个方法返回的是一个Week数组。
> * 5.创建了一个public类型的静态方法valueOf，这个方法传入一个String类型的值，返回Week类型对象。

那么为何会创建这么多的东西呢，首先大体上从字节码层面了解下枚举类型：枚举类型本质上其实也是一个类，并且这个类是继承了java.lang.Enum类，当然这些工作都是java虚拟机来完成的，接下来逐一介绍。
	
## 1.静态常量的创建：

上面我们介绍了枚举类型其实本质上是一个class，那么我们定义的每一个枚举类型常量就是一个该类型的实例，而这些实例都是存储到类的静态常量中，这也是为什么我们能够通过"."操作符直接通过枚举类型获取指定的枚举常量。所以我们可以看到我们的MONDAY和SUNDAY常量，但是这里还有一个常量ENUM$VALUES，这个常量是一个数组，是存储当前枚举类的所有常量的一个集合（注意：如果我们在java代码中显示创建了一个任何类型的常量ENUM$VALUES，那么java虚拟机创建的集合常量名则是ENUM$VALUES$VALUES，以此类推）。

## 2. static程序块的创建：

上面介绍了，枚举类型会创建静态常量，而这些静态常量自然是在静态程序块中完成初始化的。所以这里会自动的创建出static程序块。

## 3.私有类型的构造方法的创建：

在了解这一块的时候，首先我们要关注的是私有，枚举类型的构造方法一定是私有的，这个规范定义的。然后我们在往回关注下java.lang.Enum类，这个类中我们能够看到两个成员变量name和ordinal，官方文档里有解释，分表表示枚举常量的名字和该常量声明的顺序。这两个属性是必须的，而我们也能够看到Enum类型的唯一的一个构造方法就是传入这两个成员变量的值并且设置。上面我们介绍了，java虚拟机会为每一个枚举类型的class自动让它继承java.lang.Enuml类，所以这里会自动创建一个私有的构造方法就足为奇。

这里个有个问题，如果我们自定义了一个构造方法呢！如果是java虚拟机就会在修改我们的构造方法，他会添加两个参数分别（表示name和ordinal）到我们构造方法的参数列表中去，然后在构造方法开始的时候就调用父类java.lang.Enum的构造方法。

## 4.静态方法values的创建。

这个方法是Java虚拟机自动为每个枚举类型创建的，我们不能够显式的创建这个方法，否则编译器会报错。这个方法返回的是当前这个枚举类型的所有常量集合，其返回的是一个素组，这个数组其实就是上面自动创建的ENUM$VALUES静态成员变量的一个副本。通过字节码我们可以看到这个方法大概执行了对应的下面的这段代码:

    Week[] copy = new Week[ENUM$VALUES.length];
    System.arraycopy(ENUM$VALUES, 0, copy, 0, copy.length);

## 5.静态方法valueOf的创建。

这个也是Java虚拟机自动为每个枚举类型创建的，其实就是通过传入一个枚举类型常量名字符串来获得对应的枚举类型常量，其底层就是通过调用java.lang.Enum.valueOf(Enum, String)方法实现，更具上面的字节码可以得到大致的java代码

    public static Week valueOf(String name){
        return java.lang.Enum.valueOf(Week.class, name)
    }
