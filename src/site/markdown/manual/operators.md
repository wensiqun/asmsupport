# 基本操作

在这一节中将会介绍java的基本操作在asmsupport的实现。在介绍操作之前，我们首先确定我们有哪些基本的操作，可以参考下表

|操作|优先级|
|:---|:--|
|postfix|expr++ expr--|
|unary|++expr --expr +expr -expr ~ !|
|multiplicative|* / %|
|additive| \+ -|
|shift|<<,>>,>>>|
|relational|,<,>,<=,>=,instanceof|
|equality|==,!=|
|bitwise AND|&|
|bitwise exclusive OR|^|
|bitwise inclusive OR|\||
|logical AND|&&|
|logical OR|\|\||
|ternary| ? : |
|assignment|=,+=,-=,*=,/=,%=,&=,^=,\|=,<<=,>>=,>>>=|

除了上述的操作，还有一下操作

- **创建变量** :
- **返回操作** :
- **抛出异常** :
- **方法调用** :
- **获取field** : 
- **数组操作** :

我们先介绍三个基础的操作1。获取常量值，2.获取AClass对象，3.创建变量，然后再按照上述顺序自上而下介绍。我们将会使用asmsupport先创建一个类OperatorTest，同时在这个类中添加一个main方法。在这里我们会提前使用到如何创建一个class或者method的知识，但是这并不是本节的重点，创建class/enum/interface等将在后面做详细介绍。生产的类和方法大致如下：

    public class OperatorTest {
        public static void main(String[] args) {
            //我们将用asmsupport在这里位置生产我们期望的内容
        }
    }
    
## 创建class

    DummyClass dc = new DummyClass("asmsupport.OperatorTest").public_();
    dc.newMethod("main").public_().static_().argTypes(Stirng[].class)._argNames("args")
      .body(new MethodBody(){
          @Override
          public void body(LocalVariable... args) {
              //在这里调用相印的操作生产字节码。
              //TODO
          }
      });
    Class cls = dc.builder();

在上面的代码中我们就通过asmsupport创建出一个class的简单框架。我们也将在TODO位置生产我们预期的内容，也就是说下面我们介绍的操作的所有代码都将是在这个位置调用的，而在下面介绍操作的时候，将省去类创建的过程。

## 常量值

这里介绍asmsupport如何获得常量值，在asmsupport可以直接使用的值包括基本类型的值(boolean, byte, char, short, int, long, float, double), String类型，Class类型，以及null(null值允许是任何非基本类型).在asmsupport这些操作主要通过两种方式获得。

### 方式1

直接通过Value类的静态方法value获取非null值，获取null值通过方法getNullValue。代码如下：
    
    IValue one = Value.val(1); //获取一个int类型的值1
    IValue oneDotOne = Value.val(2.2D); //获取一个double类型的值2.2
    IValue str = Value.val("asmsupport"); //获取一个String类型的"asmsupport"值
    IValue nullVal = Value.getNullValue(String.class); //获取String类型的

### 方式2

如果你的代码已经在一个程序块中了，正如我们的实例，我们的代码在TODO的位置，那么我们就可以使用另一种方式，通过ValueAction接口来获取IValue对象。

    IValue one =val(1); //获取一个int类型的值1
    IValue oneDotOne = val(2.2D); //获取一个double类型的值2.2
    IValue str = val("asmsupport"); //获取一个String类型的"asmsupport"值
    IValue nullVal = null_(String.class); //获取String类型的

上面的Value.val方法和ValueAction.val方法均为重载方法，具体希望获取什么类型的值取决于你传入的参数。

## 数组值

上面介绍的是如何创建常量值，这里我们介绍如何获取一个数组值。通常在java中创建数组的时候一般有两种方式如下

    new int[1][2][];
    new String[]{"Hello", "ASMSupport"}
    
上面两种方式一个只创建了数组空间，而第二个则是在创建数组的时候就赋予了值。那么接下来就是使用asmsupport创建上面两个值，我们使用ArrayAction接口中的makeArray和newarray两个方法来实现。
   
    makeArray(int[][][].class, val(1), val(2));
    newArray(String[].class, new Parameterized[]{val("Hello"), val("ASMSupport")});
    
该接口下还有很多重载方法。    
    
## 获取AClass

通常我们在使用asmsupport的时候，很多方法都直接使用Class对象即可，但是还有部分操作是要通过AClass完成的，比如获取静态field,和常量值一样，这里同意有两种方式获取AClass。

### 方式1

通过AClassFactory类，这个类中包含了多个静态方法。

    1. AClass arrayList = AClassFactory.deftype(ArrayList.class);
    2. AClass stringDoubleDimArray = AClassFactory.deftype(String[][].class);
    3. AClass stringDoubleDimArray = AClassFactory.defArrayType(String[][].class);
    4. AClass stringDoubleDimArray = AClassFactory.defArrayType(String.class, 2);
    5. AClass stringDoubleDimArray = AClassFactory.defArrayType(AClassFactory.deftype(String.class), 2);
    
如果你的代码在一个asmsupport的程序块中，比如上面代码中的TODO位置，我们可以通过AClassDefAction接口来获取


    1. AClass arrayList = deftype(ArrayList.class);
    2. AClass stringDoubleDimArray = deftype(String[][].class);
    3. AClass stringDoubleDimArray = defArrayType(String[][].class);
    4. AClass stringDoubleDimArray = defArrayType(String.class, 2);
    5. AClass stringDoubleDimArray = defArrayType(AClassFactory.deftype(String.class), 2);

上面的例子中，第一行创建的是一个ArrayList的AClass，2~5都是创建一个ArrayClass，都是String类型的一个二维数组。

## 创建变量

通过VariableAction接口的var方法即可创建局部变量。

     //创建String类型变量name，并且赋值为"asmsupport"
     //String name = "asmsupport";
    1. LocalVariable name = var("name", String.class, val("asmsupport"));
    
    //创建一个String数组
    //String[] names = new String[]{"Hello", "ASMSupport"};
    2. LocalVariable names = arrayvar("names", String[].class, 
            newArray(String[].class, new Parameterized[]{val("Hello"), val("ASMSupport")}););

## Postfix expr++ expr--

这个两个操作是通过CrementAction接口完成，该接口是完成自增减操作。

    1. LocalVariable i = var("i", int.class, val(1));
    2. postinc(i); //i++
    3. postdec(i); //i--
    



