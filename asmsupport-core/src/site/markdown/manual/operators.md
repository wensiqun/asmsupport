# 基本操作

在这一节中将会介绍java的基本操作在asmsupport的实现。在介绍操作之前，我们首先确定我们有哪些基本的操作，可以参考下表

- **后置自增减**  `expr++ expr--`
- **一元操作:**  `++expr --expr +expr -expr ~ !`
- **乘除模:**  `* / %`
- **加减操作:**  `+ -`
- **位移操作:**  `<<,>>,>>>`
- **关系操作:**  `<,>,<=,>=,instanceof`
- **等值操作:**  `==,!=`
- **按位与:**  `&`
- **按位异或:**  `^`
- **按位或:**  `|`
- **条件与:**  `&&`
- **条件或:**  `||`
- **三元操作:**  `? :`
- **赋值操作:**  `=,+=,-=,*=,/=,%=,&=,^=,|=,<<=,>>=,>>>=`

除了上述的操作，还有一下操作

- **创建变量 **
- **数组操作 **
- **返回操作 **
- **方法调用 **
- **获取field** 
- **抛出异常 **

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
          public void body(LocVar... args) {
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
    1. LocVar name = var("name", String.class, val("asmsupport"));
    
    //创建一个String数组
    //String[] names = new String[]{"Hello", "ASMSupport"};
    2. LocVar names = arrayvar("names", String[].class, 
            newArray(String[].class, new Parameterized[]{val("Hello"), val("ASMSupport")}););

## 后置自增减(expr++ expr--)

这个两个操作是通过CrementAction接口完成，该接口是完成自增减操作。

    1. LocVar i = var("i", int.class, val(1));
    2. postinc(i); //i++
    3. postdec(i); //i--
    
## 一元操作(++expr --expr +expr -expr ~ !)

这里的操作都是一元操作，其中++expr --expr依然采用CrementAction接口的方法完成。+expr不需要任何操作，-expr是取负数采用ActionSet接口中的neg方法。~则是取反操作，使用BitwiseAction的reverse方法。

    1. LocVar i = var("i", int.class, val(8));
    2. perinc(i); // ++i;
    3. perdec(i); // --i;
    4. neg(i); // -i;
    5. reverse(i); // ~i
    
## 乘除模(* / %)

这里实现了乘法除法取模操作，其方法是在ArithmeticAction接口中

    1. LocVar i = var("i", int.class, val(8));
    2. LocVar j = var("i", int.class, val(2));
    3. mul(i, j); // i * j
    4. div(i, j); // i / j
    5. mod(i, j); // i % j

## 加减操作( \+ -)

加法减法操作

    1. LocVar i = var("i", int.class, val(8));
    2. LocVar j = var("i", int.class, val(2));
    3. add(i, j); // i + j
    4. sub(i, j); // i - j
    

## 位移操作(<<, >>, >>>)

这里的操作实现了位移操作分别是左移，右移，无符号右移,通过BitwiseAction接口的方法完成

    1. LocVar i = var("i", int.class, val(8));
    2. shl(i, val(2));  // i << 2 
    3. shr(i, val(2));  // i >> 2
    4. ushr(i, val(2)); // i >>> 2
    
## 关系操作(<,>,<=,>=,instanceof)
    
这组的操作关系操作，其中这一组(<,>,<=,>=)是使用RelationalAction接口，instanceof使用的是ActionSet接口的instanceof_方法。

    1. LocVar i = var("i", int.class, val(8));
    2. LocVar j = var("i", int.class, val(2));
    3. lt(i, j); // i < j
    4. gt(i, j); // i > j
    5. le(i, j); // i <= j
    6. ge(i, j); // i >= j
    7. LocVar list = var("list", List.class, new(ArrayList.class))
    8. instanceof_(list, ArrayList.class);(

## 等值操作(==,!=)

    1. LocVar i = var("i", int.class, val(8));
    2. LocVar j = var("i", int.class, val(2));
    3. eq(i, j); // i == j
    4. ne(i, j); // i != j)

这操同样能够比较两个对象比如obj1 == obj2或obj1 != obj2. 但是这个操作并不是会调用Object的equals的方法。

## 按位与(&)

这里的的与操作存在两种含义，一个是对数字类型的进行与运算，比如1&2，还有就是对于boolean类型的的逻辑与，但底层是同样的字节码实现。这样的两种操作很容易操作失误护着产生歧义，后期版本将会修复。

    1. LocVar i = var("i", int.class, val(8));
    2. LocVar j = var("i", int.class, val(2));
    3. LocVar m = var("i", boolean.class, val(true));
    4. LocVar n = var("i", boolean.class, val(false));
    5. band(i, j); // i & j
    6. logicAnd(m, n); // m & n
    
## 位异或(^)

异或操作，使用BitwiseAction接口

    1. bxor(i, j); // i ^ j
    
## 位或(|)

这个操作同&操作类似，使用BitwiseAction接口

    1. bor(i, j); // i | j
    2. logicOr(m, n); // m | n
    
## 条件与(&&)

使用RelationalAction接口的and方法

    1. and(val(true), val(false)) // true && false
    
## 条件或(||)

使用RelationalAction接口的or方法

    1. or(val(true), val(false)) // true || false
 
## 三元操作(? : )


这个操作完成的是三元操作，使用的是ActionSet接口的ternary方法

    1. ternary(gt(i, j), sub(i, j), add(i, j));// i > j ? i - j : i + j


## 赋值操作(=,+=,-=,*=,/=,%=,&=,^=,|=,<<=,>>=,>>>=)

这里的赋值操作非常多，但是asmsupport仅仅实现了单一的赋值操作，其余的都是先执行运算再赋值大同小异。使用的是VariableAction接口中的assign.

    1. LocVar i = var("i", int.class, val(1));
    2. assign(i, val(10));
    
## 数组操作

之前已经介绍如何创建一个数组，这里将介绍如何对数组操作，比如获取数组元素，获取数组长度，保存数组元素，这些操作均来自与接口ArrayAction接口

    1. LocVar array = arrayvar("i", int[][].class, makeArray(int[][].class, val(2), val(2)));//int[][] i = new int[2][2];
    2. arrayStore(array, val(100), val(1), val(1)); //array[1][1] = 100;
    3. arrayLoad(array, val(1), val(1)); //array[1][1]
    4. arrayLength(array, val[1]); //array[1].length
   
## 返回操作

在中我们可以使用return返回，如果该方法有返回值，则需要在return后添加返回值。在asmsupport中使用ActionSet接口中的return\_()方法和return\_(returnObj).

    1. return_(); //return;
    2. return_(add(val(1), val(2))); //return 1+2;

这里面需要注意的是，在java代码中一个没有返回值的方法，如果没有显式的执行return，那么java编译器会自动的在方法最后追加return操作。但是在asmsupport中并没这样的判断，所以需要显式的执行return方法。这一点要注意，后期版本将会支持。

## 获取field

有两种field获取，一个静态field，另一个是非静态field。和java一样静态方法通过AClass对象的field方法获取，非静态方法通过IVariable对象的field方法获取。但是如果通过
IVariable对象获取静态field同样也允许的。比如有下面这个类

    public class A {
        
        public String field1 = "Hello";
        
        public static String field2 = "ASMSupport";
    
    }
    
下面是asmsupport获取field的方式

    1. LocVar a = var("a", new_(A.class)); //A a = new A();
    2. a.field("field1"); // a.field1
    3. a.field("field2"); // A.field2
    4. defType(A.class).field("field2"); //A.field2

第2行是获取非静态field，第3，4行分别是通过变量和Class获取静态field。

## 方法调用

总共有三种类型的方法调用：1.调用构造方法，2.调用对象的方法，3.调用静态方法。这些操作是在MethodInvokeAction接口中

    1. LocVar list = var("list", List.class, new_(ArrayList.class)); //创建一个ArrayList对象，new ArrayList();
    2. call(list, "add", val(1)); // list.add(1);
    3. call(Integer.class, "parseInt", val("100")); // Integer.parseInt("100")

第1行调用构造方法，第2行调用非静态方法，第3行调用静态方法。

## 抛出异常

抛出异常是通过throw关键字，在asmsupport中是通过ActionSet接口的throw_方法。

    1. throw_(new_(RuntimeException.class));//throw new RuntimeException();
    