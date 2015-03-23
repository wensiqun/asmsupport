# 创建枚举

## 构造对象

使用DummyEnum创建枚举类型
    
    1. DummyEnum dummy = new DummyEnum();
    2. DummyEnum dummy = new DummyEnum("test.Week");
    3. DummyEnum dummy = new DummyEnum("test", "Week");
    
上面三种方式，第一种仅仅知识啊创建一个DummyEnum对象，第二种方式在创建对象的同时赋予的接口的全限定名。第三种方式将包名和类名分开指定。

## 设置类名

如果在构造DummyEnum的同时已经赋予了包名和类名，那么这里就可以不用为其设置。当然也可以在这里重新设置，将以前的值覆盖。

    dummy.package_("test").name("Week")

## 添加接口

    dummy.implements_(List.class, Cloneable.class);
    
## 创建枚举值

根据当前枚举类型定义枚举值

    dummy.newEnum("Monday");
    dummy.newEnum("Tuesday");
    
在java中我们都知道在枚举类型中定义的枚举值实际上是当前类型的一个静态field。如下

    public enum Week {
        Monday, Tuesday;
    }

如果只是简单的创建Week的以及这两个枚举值，那么这样就OK了，但是有两种情况需要我们显式的在静态程序块中调用构造方法并且为其赋值，

> * 自定义了具有参数的构造方法
> * 自定义了静态程序块的实现。

对于枚举类型的枚举值，其实可以看作是如下代码：

    public static class Week {
        public static final Monday = new Week("Monday", 1);
        public static final Tuesday = new Week("Tuesday", 2);
    }

关于枚举类型的字节码内容可以参考bytecode/enum.md

## 创建Field

创建Field和Class的Field相同的。

## 创建构造方法

    DummyEnumConstructor constructor = dummy.newConstructor();
    
### 修改参数列表

    constructor.argTypes(int.class, double.class);
    
### 设置方法参数名

    constructor.argNames("i", "d");

### 增加方法体

    constructor.body(new EnumConstructorBody(){
        
        @Override
        public void body(LocalVariable... args) {
            ...
            return_();
        }
    });

## 创建方法

创建美剧类型的方法与创建class的方法相同


## 设置javaversion

我们会根据当前使用的JDK版本自动选择class的版本，但是也可以显式的设置class版本，通过下面的方式

    dummy.setJavaVersion(Opcodes.V1_5)

## 设置ClassLoader

我们内置ClassLoader来完成我们生成的class的字节数组到class对象，当然也允许自定义classloader通过下面的方法

    dummy.setClassLoader(classloader);

## 设置class文件输出目录

    dummy.setClassOutPutPath("../classes");
    
设置这个属性，我们生成的class将会输出到指定目录，这样我们可以通过反编译大致看下class内容是否和预期相同。

## 开始创建

    Class<?> clazz = dummy.build();    
