# 创建接口

## 构造对象

创建接口我们使用DummyInterface类来创建。首先创建一个DummyInterface对象，可以通过一下几种方式

    1. DummyInterface dummy = new DummyInterface();
    2. DummyInterface dummy = new DummyInterface("asmsupport.test.Test");
    3. DummyInterface dummy = new DummyInterface("asmsupport.test", "Test");

上面三种方式，第一种仅仅知识啊创建一个DummyInterface对象，第二种方式在创建对象的同时赋予的接口的全限定名。第三种方式将包名和类名分开指定。

## 设置接口名

如果在构造DummyInterface的同时已经赋予了包名和类名，那么这里就可以不用为其设置。当然也可以在这里重新设置，将以前的值覆盖。

    dummy.package_("asmsupport.test").name("Test")

## 设置接口的访问权限

接口的访问控制权限有public和默认(default)值。默认情况先创建的DummyInterface是default的访问权限，可以通过一下方式设置或者充值访问权限

    dummy.public_() : 设置成public
    dummy.default_() : 设置成默认
    
## 添加父类型

如果希望接口继承其他类型的接口通过一下方式实现

    dummy.extends_(List.class, Cloneable.class);
    
 这是一个变元方法，也就是允许继承多个。
 
## 创建Field

在接口里面创建的field是都是默认具有public static final修饰的。通过下面代码创建field

    DummyField field = dummy.newField(String.class, "FIELD");
    
上面的代码为接口创建了FIELD的field。等同于"public static final String FIELD",如果仅仅值调用了上面的代码，那么FIELD的值就是null。有两种方式为.

### Field赋值1

上面看到了newField返回的是DummyField对象。可以通过这个对象的一系列重载方法val()为其赋值。但是这样只允许根据field类型为其赋予基本的值。比如上面我们为FIELD赋值为"asmsupport"

    field.val("ASMSupport").
    
### Field赋值2

如果希望类型通过方法调用的方式赋值比如下面的代码

    public String FIELD = String.valueOf(100);

那么赋值就需要在静态程序块中通过assign方法赋值，为什么在静态程序块可以参考文档site/bytecode/init_clinit.md

## 创建方法

这里将介绍如何创建接口的方法。通过DummyInterface的newMethod方法创

    DummyInterfaceMethod method = dummy.newMethod("test");
    
 通过上面的代码就已经告诉asmsupport了，创建一个test的方法，但是test方法仅仅是如下内容
 
    public void test();
     
由于是接口方法，所以public的修饰符是无法改变的，但是方法的返回类型已经参数列表是可以改变了。

### 修改返回类型
    
    method.return_(Stirng.class);
 
### 修改参数列表

    method.argTypes(int.class, double.class);

### 抛出捕获

    method.throws_(RuntimeException.class, IOException.class);


通过上面的修改创建出来的方法就变成如下内容

    public String test(int arg1, double arg2) throws RuntimeException, IOException
    
## 创建静态程序块

在编写java代码的时候，接口内是不允许有static程序块的，但是使用asmsupport有这样的特权。通过下面的方式

    dummy.newStaticBlock(new StaticBlockBody() {
        
        @Override
        public void body(){
            GlobalVariable FIELD = getMethodOwner().field("FIELD");
            assign(FIELD, call(String.class, "valueOf", val(100)));
            
            GlobalVariable out = defType(System.class).field("out");
            call(out, "println", val("Hello ASMSupport"))
            
            return_();
        }
        
    });

在这里我们创建出了一个静态程序块，在程序块中为我们在上面创建的FIELD赋值，同时还打印了"Hello ASMSupport"，这个在java中是无法实现的。最后我们显式的调用了return。

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
    
到这里我们就完成了一个接口的创建。生产的代码大致如下：
     
    package asmsupport.test
     
    public interface Test extends List, Cloneable{
        
        public String FIELD = String.valueOf(100);
        
        static {
            System.out.println("Hello ASMSupport");
        }
        
        public String test(int arg1, double arg2) throws RuntimeException, IOException;
    }
    

