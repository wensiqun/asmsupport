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

我们先介绍创建变量，然后再按照上述顺序自上而下介绍。