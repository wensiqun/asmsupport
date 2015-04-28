# 创建类

## 构造对象

使用DummyClass创建class

    1. DummyClass dummy = new DummyClass();
    2. DummyClass dummy = new DummyClass("asmsupport.test.Test");
    3. DummyClass dummy = new DummyClass("asmsupport.test", "Test");

上面三种方式，第一种仅仅知识啊创建一个DummyClass对象，第二种方式在创建对象的同时赋予的接口的全限定名。第三种方式将包名和类名分开指定。

## 设置类名

如果在构造DummyInterface的同时已经赋予了包名和类名，那么这里就可以不用为其设置。当然也可以在这里重新设置，将以前的值覆盖。

    dummy.package_("asmsupport.test").name("Test")

## 设置接口的访问权限

接口的访问控制权限

    dummy.public_() : 设置成public
    dummy.protected_(); 设置为protected
    dummy.default_() : 设置成默认
    dummy.private_() : 设置为private

## 设置为抽象类

    dummy.abstract_();

## 设置类为static

    dummy.static_();

## 添加父类型

    dummy.extends_(ArrayList.class);
    
## 添加接口

    dummy.implements_(List.class, Cloneable.class);
     
## 创建Field

    DummyField field = dummy.newField(String.class, "FIELD");
    
上面的代码为类创建了名为FIELD的字段。默认情况下使用的是默认的修饰符，上面的代码生成的对应java代码如下：

    String FIElD;
    
### 设置field名

    field.name("fieldName");

通过这个方法可以重新设置field名

### 设置field类型

    field.type(int.class);

通过这个方法可以重新设置field的类型

### 修改field访问控制符号

默认为default。

    field.public_(); 
    field.protected_();
    field.default_();
    field.private_();
 
### 其他修饰

    field.transient_(); //增加transient修饰
    field.static_(); //设置为static
    field.volatile_(); //增加volatile修饰
    field.final_(); //增加final修饰

### Field赋值1

上面看到了newField返回的是DummyField对象。可以通过这个对象的一系列重载方法val()为其赋值。但是这样只允许根据field类型为其赋予基本的值。比如上面我们为FIELD赋值为"asmsupport"

    field.val("ASMSupport").
    
### Field赋值2

如果希望类型通过方法调用的方式赋值比如下面的代码

    public String FIELD = String.valueOf(100);

那么赋值就需要在静态程序块中通过assign方法赋值，为什么在静态程序块可以参考文档site/bytecode/init_clinit.md

## 创建方法

这里将介绍如何创建接口的方法。通过DummyInterface的newMethod方法创

    DummyMethod method = dummy.newMethod("test");
    
 通过上面的代码就已经告诉asmsupport了，创建一个test的方法，但是test方法仅仅是如下内容
 
    public void test();
     
由于是接口方法，所以public的修饰符是无法改变的，但是方法的返回类型已经参数列表是可以改变了。

### 修改返回类型
    
    method.return_(Stirng.class);
 
### 修改参数列表

    method.argTypes(int.class, double.class);
    
### 设置方法参数名

    mehtod.argNames("i", "d");

### 抛出捕获

    method.throws_(RuntimeException.class, IOException.class);

### 修改访问权限

    method.public_(); 
    method.protected_();
    method.default_();
    method.private_();

### 修改其他修饰

    method.abstract_(); //增加abstract修饰
    method.static_(); //设置为static
    method.synchronized_(); //增加synchronized修饰
    method.final_(); //增加final修饰
    method.native_(); //增加native修饰
    method.strictfp_(); //增加strictfp修饰

### 增加方法体

    method.body(new MethodBody() {
        
        @Override
        public void body(LocVar... args) {
            //DO method logic here.
        }
        
    });

## 创建构造方法

    DummyConstructor constructor = dummy.newConstructor();
 
### 修改参数列表

    constructor.argTypes(int.class, double.class);
    
### 设置方法参数名

    constructor.argNames("i", "d");

### 抛出捕获

    constructor.throws_(RuntimeException.class, IOException.class);
    
### 修改构造方法修饰符

    constructor.public_(); 
    constructor.protected_();
    constructor.default_();
    constructor.private_();

### 增加方法体

    constructor.body(new ConstructorBody(){
        
        @Override
        public void body(LocVar... args) {
            supercall();
            return_();
        }
    });

注意如果需要调用父类的构造方法则需要通过supercall方法调用。


## 创建静态程序块

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

在这里我们创建出了一个静态程序块，在程序块中为我们在上面创建的FIELD赋值。最后我们显式的调用了return。

## 设置javaversion

我们会根据当前使用的JDK版本自动选择class的版本，但是也可以显式的设置class版本，通过下面的方式

    dummy.setJavaVersion(Opcodes.V1_5)

## 设置ClassLoader

我们内置ClassLoader来完成我们生成的class的字节数组到class对象，当然也允许自定义classloader通过下面的方法

    dummy.setClassLoader(classloader);

## 设置class文件输出目录

    dummy.setClassOutPutPath("../classes/");
    
设置这个属性，我们生成的class将会输出到指定目录，这样我们可以通过反编译大致看下class内容是否和预期相同。

## 开始创建

    Class<?> clazz = dummy.build();    

