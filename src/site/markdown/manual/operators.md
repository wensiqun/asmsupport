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

- **创建变量** ：
- **返回操作** ：
- **抛出异常** ：

我们先介绍创建变量，然后再按照上述顺序自上而下介绍。我们将会使用asmsupport先创建一个类OperatorTest，同时在这个类中添加一个main方法。在这里我们会提前使用到如何创建一个class或者method的知识，但是这并不是本节的重点，创建class/enum/interface等将在后面做详细介绍。生产的类和方法大致如下：

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

在上面的代码中我们就通过asmsupport创建出一个class的简单框架。我们也将在TODO位置生产我们预期的内容，而在下面介绍操作的时候，将省去类创建的过程。

## 创建变量

通过VariableAction接口的var方法即可创建局部变量。




