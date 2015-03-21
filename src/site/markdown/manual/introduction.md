# Welcome to ASMSupport

在这个部分内容中，我们将介绍如何使用asmsupport来创建各种操作以及class。首先会介绍一下java中的基本操作在asmsupport中如何实现（在这个过程中会简单的使用asmsupport创建一个类来支持这些操作），然后再介绍如何创建一个class，方法，属性，最后再介绍如何创建各种程序块。在介绍这些之前，先来了解下asmsupport的一些基本类型。

# 1.基本类型介绍

## AClass

这个类是对java中class的一个统一抽象，这个类是一个抽象类，这个类有几个重要的子类，ArrayClass，ProductClass，SemiClass

## ArrayClass

ArrayClass是ASMSupport对java中数组类型的一个封装,比如int[], String[][]。 对应的在ArrayClass中我们也需要保存数组的相关的信息，比如数组的维度，数组的基本类型等等

## ProductClass

这个类主要是用来表示已经存在的class，也就说已经存在于虚拟机中的class，比如java.lang.String或者我们随便创建的某个class，在asmsupport中都将抽象成ProductClass，我们称之为成品类

## SemiClass

这个类是我们在使用ASMSupport创建类的时候的中间产物,这里面包括了很多需要创建的类的元信息，比如类名，这个类有哪些方法等等。

## Parameterized

实现了这个接口的类，表示该类可以作为一个操作的操作因子。比如一个变量，一个加法操作这些对象的类都会是实现了该接口的。

## IValue

这个接口定义了基本数据类型的值，通常可以理解为允许进入常量池的值。比如基本类型，boolean，true. 数字类型1，2，2.1 等等，除了基本类型还有字符串类型的值以及Class对象。

# 2.总体思想

## 程序结构化

Asmsupport的总体思想就是，尽可能的还原真实代码的结构。所以在asmsupport中将会大量使用匿名类的方式。比如下面的代码:

    new MethodBody() {
        public void body(LocalVariable... args) {//方法开始
            //位置1
            if_(new IF(condition){
             
                public void body(){//if块开始
                    //位置2
                    LocalVariable name = var("name", String.class, val("asmsupport"))
                }//if块结束
                
            }).elseif(new Else(){
            
                public void body(){//else块开始
                    //位置3
                }//else块开始
            
            })
        
        }//方法体结束
    };


上面的代码正好对应于java代码

    public void test(){
        //位置1
        if(condition) {//if开始
            //位置2
            String name = "asmsupport";
        } else {//else 开始
            //位置3
        }
    }

使用这种方式还有一个原因。在使用asm的时候整个局部变量对开发者是开放的，那么程序可以在任意时刻访问任意位置的局部变量，比如在上面代码中的位置3获取位置2定义的变量name。采用这种方式就避免了这种问题。

## 操作命名

在asmsupport中我们使用的很多操作都是非常好理解的，比如==操作则是eq方法; >操作为gt方法。 除此之外还有一类操作比如break, return这一类是关键字的。那么在asmsupport中将这一类操作统一在关键字后面加上下划线，比如return_()表示返回操作。

